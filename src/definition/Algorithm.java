package definition;

/**
 * 
 * @author BFH-Boys
 * 
 */
public abstract class Algorithm implements Runnable {

	private Building building;
	private boolean isRunning;

	/**
	 * 
	 * @param building
	 *            Building (with elevators)
	 */
	public Algorithm(Building building) {
		this.building = building;
	}

	/**
	 * 
	 * @return The building currently assigned to the algorithm
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Stops the algorithm from processing further actions and stops the thread.
	 */
	public void stop() {
		this.isRunning = false;
	}

	// /**
	// * Starts the processing of the actions by starting the algorithm
	// * and creating a new thread. There is just one running algorithm at
	// * the same time. If this method gets called and the algorithm is already
	// * in progress, then it does not call run().
	// */
	// public void start() {
	// this.isRunning = true;
	// if (this.isRunning){
	// return;
	// }
	// run();
	// }

	/**
	 * 
	 * @return The current state of the algorithm
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 
	 * @param isRunning
	 *            sets the current state
	 */
	protected void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * 
	 * @param action
	 *            Adds an action to process to the algorithms datastructure
	 */
	public abstract void performAction(Action action);
}
