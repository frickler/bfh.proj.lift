package logic;

import org.apache.log4j.Logger;
import definition.IAction;
import definition.ILiftable;

public class Elevator implements ILiftable {
	
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
	// elevator is performing an action
	private boolean isBusy;

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

	public Elevator(int minLevel, int maxLevel, int maxPeople,
			int currentLevel) throws Exception {
		if (minLevel >= maxLevel) {
			throw new Exception("minLevel cannot be less or equal mayLevel");
		}
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentLevel = currentLevel;
	}

	/**
	 * This method is called when an action is performed on a elevator to update his
	 * data/statistics
	 * 
	 * @param Performed
	 *            action
	 */
	public void moved(IAction action) {
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
		//log4j.debug("Elevator" + hashCode() + " moved " + this);		
	}

	@Override
	public boolean isBusy() {
		return isBusy;
	}

	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	@Override
	public String toString() {
		return "SimpleElevator [currentLevel=" + currentLevel
				+ ", transportedPeople=" + transportedPeople
				+ ", drivenLevels=" + drivenLevels + ", drivenLevelsEmpty="
				+ drivenLevelsEmpty + "]";
	}

	@Override
	public int getTimeForOneLevel() {
		return timeForOneLevel;
	}


}
