package definition;

/**
 * Abstract class which provides a skeleton of an algorithm. Each algorithm runs
 * as a separate thread and is typically controlled by an object which
 * implements {@link Controller}
 * 
 * @author BFH-Boys
 */
public abstract class Algorithm implements Runnable {

	private Building building;
	private Controller controller;
	private boolean isRunning;

	/**
	 * 
	 * @param building
	 *            Building (with elevators)
	 */
	public Algorithm(Building building, Controller controller) {
		this.building = building;
		this.controller = controller;
	}

	/**
	 * @return controller assigned to this algorithm
	 */
	protected Controller getController() {
		return controller;
	}

	/**
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

	/**
	 * @return The current state of the algorithm
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param isRunning
	 *            sets the current state
	 */
	protected void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
