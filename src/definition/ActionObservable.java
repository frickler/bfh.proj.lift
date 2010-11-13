package definition;

import logic.Elevator;

/**
 * 
 * @author BFH-Boys
 * 
 */
public interface ActionObservable {

	/**
	 * Adds an observerser
	 * 
	 * @param observer
	 *            Observer to add
	 */
	public void addActionObserver(ActionObserver observer);

	/**
	 * Removes an observer
	 * 
	 * @param observer
	 *            Observer to remove
	 */
	public void deleteActionObserver(ActionObserver observer);

	/**
	 * This Method is called when an action gets start processing
	 * 
	 * @param elevator
	 *            The Elevator which processes the action
	 * @param action
	 *            Action to process
	 */
	public void notifyObserversActionStarted(Elevator elevator, Action action);

	/**
	 * This Method is called when an action is done processing
	 * 
	 * @param elevator
	 *            Elevator which processed the action
	 * @param action
	 *            Action which was processed
	 */
	public void notifyObserversActionPerformed(Elevator elevator, Action action);

}
