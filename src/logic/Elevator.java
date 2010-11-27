package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import definition.Action;
import definition.ActionObserver;
import definition.HorizontalTransporter;
import definition.MovementObserver;
import definition.MovementObserverable;
import exceptions.ElevatorConfigException;
import exceptions.IllegalRangeException;
import exceptions.IllegalStartLevelException;

public class Elevator implements HorizontalTransporter {

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

	private Movement movement;
	private Action currentAction;

	private List<ActionObserver> actionObservers;

	public Elevator(int minLevel, int maxLevel, int maxPeople, int startLevel)
			throws Exception {
	this(minLevel,maxLevel,maxPeople,startLevel,40f,0.5f);
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
	public Elevator(int minLevel, int maxLevel, int maxPeople, int startLevel,float maxSpeed,float acceleration)
		throws Exception {
		
		checkParameters(minLevel,maxLevel,maxPeople,startLevel,maxSpeed,acceleration);
		
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentPosition = startLevel;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.actionObservers = new ArrayList<ActionObserver>();
	}

	/**
	 * Checks all parameter if they are valid
	 * 
	 */
	private void checkParameters(int minLevel, int maxLevel, int maxPeople,
			int startLevel, float maxSpeed, float acceleration)
			throws Exception {
		if (minLevel >= maxLevel) {
			throw new IllegalRangeException("minLevel cannot be less or equal maxLevel");
		}
		if (startLevel > maxLevel || startLevel < minLevel){
			throw new IllegalStartLevelException("startLevel is not in the range of the elevator");			
		}
		if (acceleration < 0.2 || acceleration > 4) {
			throw new ElevatorConfigException("acceleration must be between 0.2 and 4");
		}
		if (maxSpeed < 20 || maxSpeed > 80) {
			throw new ElevatorConfigException("maxSpeed must be between 20 and 80");
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

	@Override
	public void move(Action action) {

		this.currentAction = action;
		notifyObserversActionStarted(this, this.currentAction);

		if (action.getStartLevel() == getCurrentLevel()) {
			this.movement = new Movement(this, action, new MovementObserver() {

				@Override
				public void moved(MovementObserverable object,
						Action performedAction) {
					Elevator.this.moved(Elevator.this.currentAction);
					actionDone();
				}

				@Override
				public void stepDone(Movement movement, Action action,
						double stepSize) {
					currentPosition += stepSize;

				}
			});
		} else {
			// Start with movement to startLevel and then perform action
			ElevatorAction moveTo = new ElevatorAction(getCurrentLevel(),
					action.getStartLevel(), 0);
			this.movement = new Movement(this, moveTo, new MovementObserver() {

				@Override
				public void moved(MovementObserverable object,
						Action performedAction) {
					Elevator.this.moved(performedAction);
					Elevator.this.move(Elevator.this.currentAction);

				}

				@Override
				public void stepDone(Movement movement, Action action,
						double stepSize) {
					currentPosition += stepSize;
				}

			});
		}
		this.movement.start();
	}

	private void actionDone() {
		Elevator.this.movement = null;
		notifyObserversActionPerformed(this, this.currentAction);
		Elevator.this.currentAction = null;
	}

	public int getCurrentLevel() {
		// ToDo: Floor
//		log4j.debug("CurrentPosition:" + currentPosition);
//		log4j.debug("getCurrentLevel:" + Math.round(currentPosition) );
		return (int) Math.round(currentPosition);
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

	public Action getCurrentAction() {
		return this.currentAction;
	}

	@Override
	public boolean isBusy() {
		return currentAction != null;
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
