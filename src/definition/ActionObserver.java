package definition;

import logic.Elevator;

/**
 * Interface of an object which observes an ActionObservable
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
	public void actionStarted(Action action);

	/**
	 * This Method is called when an action is done processing
	 * 
	 * @param elevator
	 *            Elevator which processed the action
	 * @param action
	 *            Action which was processed
	 */
	public void actionPerformed(Action action);

	/**
	 * This method is called when people enter the elevator 
	 * 
	 * @param elevator
	 * @param action
	 */
	public void actionPeopleLoaded(Action action);

	


}
