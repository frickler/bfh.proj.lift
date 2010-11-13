package definition;

/**
 * 
 * @author BFH-Boys
 * 
 */
public interface MovementObserverable {

	/**
	 * 
	 * @param observer
	 *            Observer to add
	 */
	public void addMovedObserver(MovementObserver observer);

	/**
	 * 
	 * @param observer
	 *            Observer to delete
	 */
	public void deleteMovedObserver(MovementObserver observer);

	/**
	 * 
	 * @param action
	 *            Action that caused this movement
	 */
	public void notifyObservers(Action action);

}
