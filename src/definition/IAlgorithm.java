package definition;


public abstract class IAlgorithm implements Runnable {
	
	private IBuilding building;
	
	public IAlgorithm(IBuilding c) {
		this.building = c;
	}

	public IBuilding getBuilding() {
		return building;
	}

	public abstract void performAction(IAction action);
	
	protected abstract void moveElevator(ILiftable lift);

}
