package definition;


/**
 * Interface which provides the possibility to
 * observe a object (typically an elevator) which handles actions. 
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
	 */
	public void notifyObserversActionStarted();

	/**
	 * This Method is called when an action is done processing
	 * 
	 */
	public void notifyObserversActionPerformed();

	/**
	 * This method is calls when people enter 
	 * 
	 */
	public void notifyObserversActionPeopleLoaded();

}
