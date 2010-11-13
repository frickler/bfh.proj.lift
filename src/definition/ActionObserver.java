package definition;

import logic.Elevator;

/**
 * 
 * @author BFH-Boys
 *
 */
public interface ActionObserver {

	/**
	 * This Method is called when an action gets start processing
	 * 
	 * @param elevator
	 *            The Elevator which processes the action
	 * @param action
	 *            Action to process
	 */
	public void actionStarted(Elevator elevator, Action action);

	/**
	 * This Method is called when an action is done processing
	 * 
	 * @param elevator
	 *            Elevator which processed the action
	 * @param action
	 *            Action which was processed
	 */
	public void actionPerformed(Elevator elevator, Action action);

}
