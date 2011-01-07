package definition;

/**
 * 
 * This observer can be used as a callback if the amount of people in an
 * elevator changes
 * 
 * @author BFH-Boys
 * 
 */
public interface PeopleLoadedObserver {

	/**
	 * This Method is called when an action gets start processing
	 * 
	 * @param elevator
	 *            The Elevator which processes the action
	 * @param difference
	 *            Difference of the amount of people in the elevator. This value
	 *            can be positive or negative
	 */
	public void peopleLoaded(VerticalTransporter elevator, int difference);

}
