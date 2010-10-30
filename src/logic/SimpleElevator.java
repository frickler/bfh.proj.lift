package logic;

import java.util.Date;

import definition.IAction;
import definition.ILiftable;


public class SimpleElevator  implements ILiftable{
	
	// current Level (Stockwerk) of the elevator
	private final float stepSize = (float) 0.1;
	// current Level (Stockwerk) of the elevator
	private final long timeForOneStep = (long) 200;
	// current Level (Stockwerk) of the elevator
	private float currentLevel;
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

	public float getCurrentLevel() {
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

	public SimpleElevator(int minLevel, int maxLevel, int maxPeople, int currentLevel) throws Exception {
		if(minLevel>=maxLevel){	
			throw new Exception("minLevel cannot be less or equal mayLevel");
		}
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxPeople = maxPeople;
		this.currentLevel = currentLevel;
	}
	
	/**
	 * Call this method when an action is performed on a elevator to update 
	 * his data
	 * @param Performed action
	 */
	public void performedAction(IAction action){
		if (action.getPeopleAmount() == 0){ // Lift runs empty
			this.drivenLevelsEmpty += Math.abs(action.getStartLevel() - action.getEndLevel());			
		} else  { // Lift runs with people			
			this.transportedPeople += action.getPeopleAmount();			
		}
		this.drivenLevels += Math.abs(action.getStartLevel() - action.getEndLevel());
		this.timeInMotion += action.getTimestampEnded().getTime() - action.getTimestampStarted().getTime(); 
		this.currentLevel = action.getEndLevel();
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

}
