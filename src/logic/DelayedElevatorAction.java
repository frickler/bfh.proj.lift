package logic;

public class DelayedElevatorAction extends ElevatorAction {

	/**
	 * delays the action before sending to the controller
	 */
	private int delayInSeconds = 0;
	
	public DelayedElevatorAction(int startLevel, int endLevel, int pepoleAmount,int delay) {
		super(startLevel, endLevel, pepoleAmount);
		this.setDelayInSeconds(delay);
	}
	
	public DelayedElevatorAction(int startLevel, int endLevel, int pepoleAmount) {
		this(startLevel, endLevel, pepoleAmount,0);
	}

	public void setDelayInSeconds(int delayInSeconds) {
		this.delayInSeconds = delayInSeconds;
	}

	public int getDelayInSeconds() {
		return delayInSeconds;
	}

}
