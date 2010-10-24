package Interface;
import Interface.IAction;

public abstract class IElevator implements Runnable {

	// current Level (Stockwerk) of the elevator
	private final double stepSize = (double) 0.1;
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
	private int timeInMotion;
	// time in second the elevator moved without any passangers.
	private int timeInMotionEmpty;
	// current action
	private IAction currentAction = null;
	
	public IElevator(int minLevel,int maxLevel,int maxPeople,float currentLevel) throws Exception{
		if(minLevel>=maxLevel){	
			throw new Exception("minLevel cannot be less or equal mayLevel");
		}	
		setMinLevel(minLevel);
		setMaxLevel(maxLevel);
		setMaxPeople(maxPeople);
		setCurrentLevel(currentLevel);
		setTransportedPeople(0);
		setDrivenLevels(0);
		setDrivenLevelsEmpty(0);
		setTimeInMotion(0);
		setTimeInMotionEmpty(0);	
	}
	
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}
	public int getMaxPeople() {
		return maxPeople;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public void setTransportedPeople(int transportedPeople) {
		this.transportedPeople = transportedPeople;
	}
	public int getTransportedPeople() {
		return transportedPeople;
	}
	public void setDrivenLevels(int drivenLevels) {
		this.drivenLevels = drivenLevels;
	}
	public int getDrivenLevels() {
		return drivenLevels;
	}
	public void setDrivenLevelsEmpty(int drivenLevelsEmpty) {
		this.drivenLevelsEmpty = drivenLevelsEmpty;
	}
	public int getDrivenLevelsEmpty() {
		return drivenLevelsEmpty;
	}
	public void setTimeInMotion(int timeInMotion) {
		this.timeInMotion = timeInMotion;
	}
	public int getTimeInMotion() {
		return timeInMotion;
	}
	public void setTimeInMotionEmpty(int timeInMotionEmpty) {
		this.timeInMotionEmpty = timeInMotionEmpty;
	}
	public int getTimeInMotionEmpty() {
		return timeInMotionEmpty;
	}

	public void setCurrentLevel(float currentLevel) {
		this.currentLevel = currentLevel;
	}

	public double getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentAction(IAction currentAction) throws Exception {
		this.currentAction = currentAction;
		runAction();
	}
		
	protected abstract void runAction() throws Exception;
	protected abstract void actionDone();
	
	public boolean isRunning(){		
		return (getCurrentAction() != null);
	}

	public IAction getCurrentAction() {
		return currentAction;
	}

	public long getTimeForOneStep() {
		return timeForOneStep;
	}

	public double getStepSize() {
		return stepSize;
	}
}
