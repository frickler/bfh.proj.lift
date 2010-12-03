package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import definition.Action;
import definition.ActionObserver;
import definition.Direction;
import definition.MovementObserver;
import definition.MovementObserverable;
import definition.VerticalTransporter;
import exceptions.ElevatorConfigException;
import exceptions.IllegalRangeException;
import exceptions.IllegalStartLevelException;

/**
 * 
 * TODO Overthink setPeopleLoaded
 */
public class Elevator implements VerticalTransporter {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// returns the currentPosition of the elevator
	private double currentPosition;
	// min Level (Stockwerk) the elevator can reach
	private int minLevel;
	// max Level (Stockwerk) the elevator can reach
	private int maxLevel;
	// max people the elevator can take
	private int maxPeople;
	// time in second the elevator moved
	private float acceleration;
	// time in second the elevator moved without any passangers.
	private float maxSpeed;
	// sum of all transported people
	private int transportedPeople;
	// amount of all passed levels
	private int drivenLevels;
	// amount of all passed levels without any passangers
	private int drivenLevelsEmpty;
	// time in second the elevator moved
	private float timeInMotion;
	// time in second the elevator moved without any passangers.
	private float timeInMotionEmpty;
	// Movement currently processing
	private Movement movement;
	// Action listeners
	private List<ActionObserver> actionObservers;
	// List of actions the elevator has to process
	private List<Action> actions;
	// direction
	private Direction direction;
	// indicates if the elevator is busy
	private boolean isBusy;

	public Elevator(int minLevel, int maxLevel, int maxPeople, int startLevel)
			throws Exception {
		this(minLevel, maxLevel, maxPeople, startLevel, 40f, 0.5f);
	}

	/**
	 * 
	 * @param minLevel
	 *            must be lower than maxLevel
	 * @param maxLevel
	 *            must be higher than minLevel
	 * @param maxPeople
	 *            must be bigger than one
	 * @param currentLevel
	 *            must be between minLevel and maxLevel
	 * @param maxSpeed
	 *            must be between 20 and 80
	 * @param acceleration
	 *            must be between 0.2 and 4
	 * @throws Exception
	 *             if an parameter is not valid
	 */
	public Elevator(int minLevel, int maxLevel, int maxPeople, int startLevel,
			float maxSpeed, float acceleration) throws Exception {

		checkParameters(minLevel, maxLevel, maxPeople, startLevel, maxSpeed,
				acceleration);

		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentPosition = startLevel;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.actionObservers = new ArrayList<ActionObserver>();
		this.actions = new LinkedList<Action>();
	}

	/**
	 * Checks all parameter if they are valid private List<Action> actions;
	 */
	private void checkParameters(int minLevel, int maxLevel, int maxPeople,
			int startLevel, float maxSpeed, float acceleration)
			throws Exception {
		if (minLevel >= maxLevel) {
			throw new IllegalRangeException(
					"minLevel cannot be less or equal maxLevel");
		}
		if (startLevel > maxLevel || startLevel < minLevel) {
			throw new IllegalStartLevelException(
					"startLevel is not in the range of the elevator");
		}
		if (acceleration < 0.2 || acceleration > 4) {
			throw new ElevatorConfigException(
					"acceleration must be between 0.2 and 4");
		}
		if (maxSpeed < 20 || maxSpeed > 80) {
			throw new ElevatorConfigException(
					"maxSpeed must be between 20 and 80");
		}
		if (maxPeople < 1) {
			throw new ElevatorConfigException("maxPeople must be bigger than 1");
		}
	}

	/**
	 * This method is called when an action is performed on a elevator to update
	 * his data/statistics
	 * 
	 * @param Performed
	 *            action
	 */
	public void moved(Action action) {
		if (action.getPeopleAmount() == 0) { // Lift runs empty
			this.drivenLevelsEmpty += Math.abs(action.getStartLevel()
					- action.getEndLevel());
		} else { // Lift runs with people
			this.transportedPeople += action.getPeopleAmount();
		}
		this.drivenLevels += Math.abs(action.getStartLevel()
				- action.getEndLevel());
		this.timeInMotion += action.getTimestampEnded().getTime()
				- action.getTimestampStarted().getTime();
		this.currentPosition = action.getEndLevel();
		// log4j.debug("Elevator" + hashCode() + " moved " + this);
	}

	/**
	 * 
	 * @param targetLevel
	 * @return All actions with the destination level given in the parameter
	 */
	private List<Action> getActionsWithDestination(int targetLevel) {
		List<Action> matchingActions = new LinkedList<Action>();
		for (Action action : actions) {
			if (action.getEndLevel() == targetLevel) {
				matchingActions.add(action);
			}
		}
		return matchingActions;
	}

	/**
	 * 
	 * @return returns the next higher Level if the elevator is moving up
	 */
	private int getNextHigherLevel() {
		int nextLevel = getCurrentLevel();
		for (Action act : actions) {
			if (act.getStartLevel() > nextLevel
					&& act.getStartLevel() > getCurrentLevel()) {
				nextLevel = act.getStartLevel();
			}
		}
		return nextLevel;
	}

	/**
	 * 
	 * @return returns the next higher Level if the elevator is moving down
	 */
	private int getNextLowerLevel() {
		int nextLevel = getCurrentLevel();
		for (Action act : actions) {
			if (act.getStartLevel() < nextLevel
					&& act.getStartLevel() < getCurrentLevel()) {
				nextLevel = act.getStartLevel();
			}
		}
		return nextLevel;
	}

	private int getTarget() {

		int target;
		if (actions.size() > 1) {
			if (direction == Direction.UP) {
				target = getNextHigherLevel();
			} else {
				target = getNextLowerLevel();
			}
		} else {
			target = actions.get(0).getEndLevel();
		}
		return target;
	}

	private void move() {

		if (actions.isEmpty()) {
			isBusy = false;
			return;
		}

		// Amount of people enter/leave elevator on this floor
		int peopleInOut = 0;
		// get target floor
		int target = getTarget();
		
		// Remove processed actions
		for (int i = actions.size() - 1; i >= 0; i--) {
			Action a = actions.get(i);
			if (a.getEndLevel() == getCurrentLevel()) {
				peopleInOut += a.getPeopleAmount();
				a.setTimestampEnded(new Date());
				moved(a);
				actions.remove(a);
				log4j.debug("Elevator " + this.hashCode()  + " Action done: " + a + " left: " + actions.size());
			}
			if (a.getStartLevel() == getCurrentLevel()) {
				peopleInOut += a.getPeopleAmount();
				a.setTimestampStarted(new Date());
			}
		}

		this.movement = new Movement(this, getCurrentLevel(), target,
				peopleInOut, new MovementObserver() {

					@Override
					public void moved(MovementObserverable object) {
						move();
					}

					@Override
					public void stepDone(Movement movement, double stepSize) {
						currentPosition += stepSize;
					}

				});

		this.movement.start();
	}

	/**
	 * TODO Add statistics
	 * 
	 * @param startLevel
	 */
	private void moveToStart(int startLevel) {

		if (startLevel == getCurrentLevel()) {
			move();
			return;
		}

		this.movement = new Movement(this, getCurrentLevel(), startLevel,
				new MovementObserver() {

					@Override
					public void moved(MovementObserverable object) {
						Elevator.this.move();

					}

					@Override
					public void stepDone(Movement movement, double stepSize) {
						currentPosition += stepSize;
					}
				});

		this.movement.start();

	}

	@Override
	public void move(List<Action> actions, int startLevel, Direction direction) {
		this.isBusy = true;
		this.setDirection(direction);
		this.actions.addAll(actions);
		moveToStart(startLevel);
	}

	@Override
	public void move(Action action) {
		this.isBusy = true;
		log4j.debug("Elevator " + this + " received " + action);
		List<Action> moveActions = new LinkedList<Action>();
		moveActions.add(action);
		Direction dir = Direction.DOWN;
		if (action.getStartLevel() < action.getEndLevel()) {
			dir = Direction.UP;
		}
		move(moveActions, action.getStartLevel(), dir);
	}

	private void actionDone() {
		Elevator.this.movement = null;
		// notifyObserversActionPerformed(this, this.currentAction);
	}

	public int getCurrentLevel() {
		// ToDo: Floor
		// log4j.debug("CurrentPosition:" + currentPosition);
		// log4j.debug("getCurrentLevel:" + Math.round(currentPosition) );
		return (int) Math.round(currentPosition);
	}

	public Direction getDirection() {
		return direction;
	}

	protected void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public int getTransportedPeople() {
		return transportedPeople;
	}

	public int getDrivenLevels() {
		return drivenLevels;
	}

	public int getDrivenLevelsEmpty() {
		return drivenLevelsEmpty;
	}

	public float getTimeInMotion() {
		return timeInMotion;
	}

	public float getTimeInMotionEmpty() {
		return timeInMotionEmpty;
	}

	@Override
	public boolean isBusy() {
		return isBusy;
	}

	@Override
	public String toString() {
		return "SimpleElevator [currentLevel=" + getCurrentLevel()
				+ ", transportedPeople=" + transportedPeople
				+ ", drivenLevels=" + drivenLevels + ", drivenLevelsEmpty="
				+ drivenLevelsEmpty + "]";
	}

	@Override
	public void stop() {
		if (this.movement != null) {
			movement.stopMovement();
		}
	}

	@Override
	public void addActionObserver(ActionObserver observer) {
		this.actionObservers.add(observer);
	}

	@Override
	public void deleteActionObserver(ActionObserver observer) {
		this.actionObservers.remove(observer);
	}

	@Override
	public void notifyObserversActionStarted(Elevator elevator, Action action) {
		for (ActionObserver observer : actionObservers) {
			observer.actionStarted(this, action);
		}
	}

	@Override
	public void notifyObserversActionPerformed(Elevator elevator, Action action) {
		for (ActionObserver observer : actionObservers) {
			observer.actionPerformed(this, action);
		}
	}

	@Override
	public double getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public float getAcceleration() {
		return this.acceleration;
	}

	@Override
	public float getMaxSpeed() {
		return this.maxSpeed;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}
