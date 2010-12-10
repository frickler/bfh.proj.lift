package definition;

import logic.Elevator;

public interface PeopleLoadedObserverable {
	
	/**
	 * Adds an observerser
	 * 
	 * @param observer
	 *            Observer to add
	 */
	public void addPeopleLoadedObserver(ActionObserver observer);

	/**
	 * Removes an observer
	 * 
	 * @param observer
	 *            Observer to remove
	 */
	public void deletePeopleLoadedObserver(ActionObserver observer);

	/**
	 * This Method is called when an action gets start processing
	 * 
	 * @param elevator
	 *            The Elevator which processes the action
	 * @param difference
	 *            Difference of people if the elevator is loading/unloading
	 */
	public void notifyObserversActionStarted(VerticalTransporter elevator, int difference);



}
