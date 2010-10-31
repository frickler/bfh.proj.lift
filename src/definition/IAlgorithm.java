package definition;


public abstract class IAlgorithm implements Runnable {
	
	private IBuilding building;
	private boolean isRunning;
	
	public IAlgorithm(IBuilding c) {
		this.building = c;
	}

	public IBuilding getBuilding() {
		return building;
	}

	public abstract void performAction(IAction action);

	public void stop(){		
		setRunning(false);
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isRunning() {
		return isRunning;
	}
	

}
