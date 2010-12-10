package definition;


public interface PeopleLoadedObserver {
	
	/**
	 * This Method is called when an action gets start processing
	 * 
	 * @param elevator
	 *            The Elevator which processes the action
	 * @param action
	 *            Action to process
	 */
	public void peopleLoaded(VerticalTransporter elevator, int difference);


}
