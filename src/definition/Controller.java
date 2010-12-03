package definition;

import java.util.List;

import logic.Elevator;

import exceptions.IllegalActionException;

/**
 * 
 * @author BFH-Boys
 * 
 */
public interface Controller {

	/**
	 * Performs the given action. Usually by giving it to the algorithm
	 * 
	 * @param action
	 *            to perform
	 * @throws IllegalActionException
	 *             if an action is invalid (invalid levels p.E.)
	 */
	public void performAction(Action action) throws IllegalActionException;

	/**
	 * Performs the given actions. Usually by giving it to 
	 * the algorithm
	 * 
	 * @param actions to perform
	 * @throws IllegalActionException if an action is invalid (invalid levels p.E.)
	 */
	public void performActions(List<Action> action) throws IllegalActionException;
	
	/**
	 * Starts the performing of the actions
	 */
	public void startController();

	/**
	 * Stops the performing of the actions
	 */
	public void stopController();

	/**
	 * Return the action with the smallest timestamp and removes
	 * it from the controllers datastructure
	 */
	public Action getActionWithHighestPriority();

	/**
	 * 
	 * @param startLevel Startlevel (currentlevel)
	 * @param endlevel Destinationlevel
	 * @param maxPerson Maximal capacity of elevator
	 * @return returns a list of action which fulfill the given criteria (Moving
	 *         up/down, amount of people in action should not exceed the param
	 *         maxPerson. All returned actions are removed from the controllers 
	 *         datastructure.
	 */
	public List<Action> getActions(int startLevel, int endlevel, int maxPerson);

	public boolean removeElevator(int removeId);

	public List<Action> getDoneActions();

	public void addElevator(Elevator e);

	public void setBuilding(Building building);

	public Building getBuilding();

}
