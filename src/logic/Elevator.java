package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import definition.ActionObservable;
import definition.ActionObserver;
import definition.Action;
import definition.HorizontalTransporter;
import definition.MovementObserver;
import definition.MovementObserverable;

public class Elevator implements HorizontalTransporter {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// time to move one level
	private final int timeForOneLevel = 200;
	// current Level (Stockwerk) of the elevator
	private int currentLevel;
	// min Level (Stockwerk) the elevator can reach
	private int minLevel;
	// max Level (Stockwerk) the elevator can reach
	private int maxLevel;
	// max people the elevator can take
	private int maxPeople;
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

	public Elevator(int minLevel, int maxLevel, int maxPeople, int currentLevel)
			throws Exception {
		if (minLevel >= maxLevel) {
			throw new Exception("minLevel cannot be less or equal maxLevel");
		}
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentLevel = currentLevel;
		this.actionObservers = new ArrayList<ActionObserver>();
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
		this.currentLevel = action.getEndLevel();
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
			});
		}
		this.movement.start();
	}
	
	private void actionDone(){
		Elevator.this.movement = null;		
	    notifyObserversActionPerformed(this, this.currentAction);
	    Elevator.this.currentAction = null;	
	}

	public int getCurrentLevel() {
		return currentLevel;
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
	public int getTimeForOneLevel() {
		return timeForOneLevel;
	}

	@Override
	public String toString() {
		return "SimpleElevator [currentLevel=" + currentLevel
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

}