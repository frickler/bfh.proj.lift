package definition;


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
	 * @param action
	 *            Action to process
	 */
	public void actionStarted(Action action);

	/**
	 * This Method is called when an action is done processing
	 * 
	 * @param action
	 *            Action which was processed
	 */
	public void actionPerformed(Action action);

	/**
	 * This method is called when people enter the elevator 
	 * 
	 * @param action Assigned action
	 */
	public void actionPeopleLoaded(Action action);

	


}
