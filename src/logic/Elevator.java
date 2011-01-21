package logic;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import definition.Action;
import definition.Direction;
import definition.MovementObserver;
import definition.PeopleLoadedObserver;
import definition.VerticalTransporter;
import exceptions.ElevatorConfigException;
import exceptions.IllegalRangeException;
import exceptions.IllegalStartLevelException;

public class Elevator implements VerticalTransporter {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// returns the currentPosition of the elevator
	private double currentPosition;
	// returns the current accelaeration
	private double currentSpeed;
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
	// simulation speed
	private int simulationSpeed = 1;
	// sum of all transported people
	private int transportedPeople;
	// amount of all passed levels
	private int drivenLevels;
	// amount of all passed levels without any passangers
	private int drivenLevelsEmpty;
	// time in second the elevator moved
	private float timeInMotion;
	// time in second the elevator load and deload people
	private float timePeopleLoad;
	// time in second the elevator moved without any passangers.
	private float timeInMotionEmpty;
	// Movement currently processing
	private Movement movement;
	// List of actions the elevator has to process
	private List<Action> actions;
	// direction
	private Direction direction;
	// indicates if the elevator is busy
	private boolean isBusy = false;
	// current people loaded
	private int currentPeople;
	// unique number for each elevator
	private int identityNumber;

	public AlgorithmListener listener;

	public static int elevatorCounter = 0;

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
	 * @param startLevel
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
		this.identityNumber = ++elevatorCounter;
		checkParameters(minLevel, maxLevel, maxPeople, startLevel, maxSpeed,
				acceleration);

		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentPosition = startLevel;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
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
	 * @param action
	 *            Performed action
	 */
	public void moved(Action action) {
		if (action.getPeopleAmount() == 0) { // Lift ran empty
			this.drivenLevelsEmpty += Math.abs(action.getStartLevel()
					- action.getEndLevel());
		} else { // Lift runs with people
			this.transportedPeople += action.getPeopleAmount();
		}
		this.drivenLevels += Math.abs(action.getStartLevel()
				- action.getEndLevel());
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
		synchronized (actions) {
			for (Action action : actions) {
				if (action.getEndLevel() == targetLevel) {
					matchingActions.add(action);
				}
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
		synchronized (actions) {

			if (actions.isEmpty()) {
				return nextLevel;
			}
			for (Action act : actions) {
				if (act.getStartLevel() > nextLevel
						&& act.getStartLevel() > getCurrentLevel()) {
					nextLevel = act.getStartLevel();
				}
			}
			if (nextLevel == getCurrentLevel()) {
				nextLevel = Integer.MAX_VALUE;
				for (Action act : actions) {
					if (act.getEndLevel() < nextLevel
							&& act.getEndLevel() > getCurrentLevel()) {
						nextLevel = act.getEndLevel();
					}
				}
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
		synchronized (actions) {
			if (actions.isEmpty()) {
				return nextLevel;
			}
			for (Action act : actions) {
				if (act.getStartLevel() < nextLevel
						&& act.getStartLevel() < getCurrentLevel()) {
					nextLevel = act.getStartLevel();
				}
			}
			if (nextLevel == getCurrentLevel()) {
				nextLevel = Integer.MIN_VALUE;
				for (Action act : actions) {
					if (act.getEndLevel() > nextLevel
							&& act.getEndLevel() < getCurrentLevel()) {
						nextLevel = act.getEndLevel();
					}
				}
			}
		}
		return nextLevel;
	}

	private int getTarget() {

		int target;

		if (direction == Direction.UP) {
			log4j.debug(getName() + " Direction = Up");
			target = getNextHigherLevel();
		} else {
			log4j.debug(getName() + " Direction = Down");
			target = getNextLowerLevel();
		}

		return target;
	}

	public enum Caller {
		StartLevel, EndLevel, Between
	};

	private void move(Caller enCaller) {
		synchronized (actions) {
			if (actions.isEmpty()) {
				isBusy = false;
				return;
			}
		}

		// Amount of people enter/leave elevator on this floor
		int peopleIn = 0;
		int peopleOut = 0;
		int minmaxLevel = (direction == Direction.UP) ? getMaxLevel()
				: getMinLevel();

		/*
		 * if the Caller is StartLevel, then we don't have to load more action
		 * because they are already loaded at the selected algortihm
		 */
		if (listener != null && enCaller == Caller.Between) {
			List<Action> as = listener.actionPerformed(getCurrentLevel(),
					minmaxLevel, getMaxPeople() - getCurrentPeople());
			synchronized (actions) {
				actions.addAll(as);
			}
		}

		synchronized (actions) {
			// Remove processed actions
			for (int i = actions.size() - 1; i >= 0; i--) {
				Action a = actions.get(i);
				if (a.getEndLevel() == getCurrentLevel()) {
					peopleOut += a.getPeopleAmount();
					// currentPeople -= a.getPeopleAmount();
					a.setTimestampElevatorLeft(new Date());
					actions.remove(a);
					moved(a);
					log4j.debug("Elevator " + this.hashCode()
							+ " Action done: " + a + " left: " + actions.size());
				}
				if (a.getStartLevel() == getCurrentLevel()) {
					peopleIn += a.getPeopleAmount();
					// currentPeople += a.getPeopleAmount();
					a.setTimestampElevatorEntered(new Date()); // todo new date?
				}
			}
		}

		// get target floor
		int target = getTarget();
		synchronized (actions) {
			if (actions.isEmpty()) {
				target = getCurrentLevel();
			}
		}

		if (target > getMaxLevel()) {
			log4j.error("Elevator " + this.getName() + " Target Level ("
					+ target + ") > maxLevel (" + getMaxLevel() + ")"
					+ actions.size() + direction);
			
			target = maxLevel;
		}
		if (target < getMinLevel()){
			target = minLevel;
		}
		
		final Direction d = getCurrentLevel() < target ? Direction.UP
				: Direction.DOWN;
		this.movement = new Movement(this, getCurrentLevel(), target, peopleIn,
				peopleOut, this.simulationSpeed, new MovementObserver() {

					@Override
					public void moved(Movement movement) {
						move(Caller.Between);
					}

					@Override
					public void stepDone(Movement movement, double stepSize) {
						currentPosition += stepSize;
						// log4j.debug("Current Position: " + currentPosition);
					}

					@Override
					public void updateSpeed(double speed) {
						currentSpeed = speed;
					}

				}, new PeopleLoadedObserver() {

					@Override
					public void peopleLoaded(VerticalTransporter elevator,
							int difference) {
						currentPeople += difference;
					}

				});

		this.movement.start();
	}

	private void moveToStart(int startLevel) {

		currentPeople = 0;

		if (startLevel == getCurrentLevel()) {
			move(Caller.StartLevel);
			return;
		}
		final int levelsStarted = getCurrentLevel() - startLevel;
		this.movement = new Movement(this, getCurrentLevel(), startLevel,
				this.simulationSpeed, new MovementObserver() {

					@Override
					public void moved(Movement movement) {
						drivenLevelsEmpty += Math.abs(levelsStarted);
						drivenLevels += Math.abs(levelsStarted);
						Elevator.this.move(Caller.StartLevel);

					}

					@Override
					public void updateSpeed(double speed) {
						currentSpeed = speed;

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
		synchronized (actions) {
			for (int i = 0; i < actions.size(); i++) {
				actions.get(i).setTimestampElevatorEntered(new Date());
				log4j.debug(this.getName() + "" + actions.get(i));
			}

			this.actions.addAll(actions);
			for (Action a : actions) {
				if (getMinLevel() <= a.getStartLevel()
						&& getMaxLevel() >= a.getStartLevel()
						&& getMinLevel() <= a.getEndLevel()
						&& getMaxLevel() >= a.getEndLevel()) {

				} else {
					new Exception("illegal action").printStackTrace();
				}
			}
		}
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

	@Override
	public void setSimulationSpeed(int speed) {
		this.simulationSpeed = speed;
	}

	@Override
	public int getCurrentPeople() {
		return currentPeople;
	}

	@Override
	public void resetStatistics() {
		this.drivenLevels = 0;
		this.drivenLevelsEmpty = 0;
		this.timeInMotion = 0;
		this.timeInMotionEmpty = 0;
		this.transportedPeople = 0;
		this.timePeopleLoad = 0;
	}

	@Override
	public double getCurrentSpeed() {
		return this.currentSpeed;
	}

	public int getIdentityNumber() {
		return identityNumber;
	}

	/**
	 * Add on the Element @e all attributes of the elevator class as attributes
	 * 
	 * @param e
	 */
	public Node getXML(Element e, int totalTime) {
		e.setAttribute("simulationSpeed", simulationSpeed + "");
		e.setAttribute("minLevel", minLevel + "");
		e.setAttribute("maxLevel", maxLevel + "");
		e.setAttribute("currentPosition", currentPosition + "");
		e.setAttribute("maxSpeed", maxSpeed + "");
		e.setAttribute("maxPeople", maxPeople + "");
		e.setAttribute("transportedPeople", transportedPeople + "");
		e.setAttribute("timeInMotion", timeInMotion + "");
		e.setAttribute("timeInMotionEmpty", timeInMotionEmpty + "");
		e.setAttribute("timePeopleLoad", timePeopleLoad + "");
		if (totalTime > 0) {
			e.setAttribute("timeStillStand", getTimeStillStand(totalTime) + "");
		}
		e.setAttribute("utalization", getUtilization(totalTime) + "");
		e.setAttribute("drivenLevels", drivenLevels + "");
		e.setAttribute("drivenLevelsEmpty", drivenLevelsEmpty + "");
		e.setAttribute("drivenLevelsEmpty", drivenLevelsEmpty + "");
		return e;
	}

	@Override
	public boolean isLoaded() {
		return (currentPeople > 0);
	}

	@Override
	public void addTimeInMotion(int milliseconds) {
		this.timeInMotion += milliseconds;
	}

	@Override
	public void addTimeInMotionEmpty(int milliseconds) {
		this.timeInMotionEmpty += milliseconds;
	}

	@Override
	public String getName() {
		return "Elevator" + getIdentityNumber() + " " + hashCode();
	}

	@Override
	public int getTimeStillStand(int TotalTime) {
		// TODO check if correct (what s with the people loading time
		return (int) (TotalTime - getTimeInMotion());
	}

	@Override
	public int getUtilization(int totalTime) {
		return (int) ((getTimeInMotion() + getTimePeopleLoad()) / totalTime * 100);
	}

	/***
	 * Check if the elevator is not busy and the action's end- and startlevel
	 * are in the area of the elevator
	 * 
	 * @param a
	 *            Action to check
	 * @return if the elevator is able to perform the action
	 */
	public boolean canPerformAction(Action a) {

		if (isBusy())
			return false;

		if (getMinLevel() <= a.getStartLevel()
				&& getMaxLevel() >= a.getStartLevel()
				&& getMinLevel() <= a.getEndLevel()
				&& getMaxLevel() >= a.getEndLevel()) {
			return true;
		}
		return false;
	}

	public void move(List<Action> acts, int startLevel, Direction dir,
			AlgorithmListener actionListener) {
		move(acts, startLevel, dir);
		this.listener = actionListener;

	}

	@Override
	public void addTimePepoleLoad(int timeInMilliSeconds) {
		timePeopleLoad += timeInMilliSeconds;

	}

	@Override
	public float getTimePeopleLoad() {
		return timePeopleLoad;
	}

	@Override
	public void setBusy(Boolean busy) {
		this.isBusy = busy;
	}

}
