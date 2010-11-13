package definition;

/**
 * 
 * @author BFH-Boys
 * 
 */
public interface MovementObserver {

	/**
	 * This method gets called when the elevator has moved.
	 * 
	 * @param object
	 *            Movement which processed the action
	 * @param action
	 *            Processed action (or a part of it)
	 */
	public void moved(MovementObserverable object, Action action);
}
