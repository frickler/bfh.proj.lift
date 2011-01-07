package definition;

import java.util.List;

import logic.Elevator;
import logic.Simulation;
import exceptions.IllegalActionException;

/**
 * The controller is one of the key-logic-classes. It instances the algorithm
 * and holds a list with all actions who are not performed yet. The algorithm
 * can query the controller for the action with the highest priority similar to
 * a queue. <br>
 * The controller is also responsible for starting/stopping a simulation.
 * 
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
	 * Performs the given actions. Usually by giving it to the algorithm
	 * 
	 * @param action
	 *            Action to perform
	 * @throws IllegalActionException
	 *             Gets called if an action is invalid (invalid levels p.E.)
	 */
	public void performActions(List<Action> action)
			throws IllegalActionException;

	/**
	 * Starts the performing of the actions
	 */
	public void startController();

	/**
	 * Stops the performing of the actions
	 */
	public void stopController();

	/**
	 * Return the action with the smallest timestamp and removes it from the
	 * controllers datastructure
	 */
	public Action getActionWithHighestPriority();
	
	/**
	 * 
	 * @param action Action to process
	 * @return Closes elevator to the actions StartLevel
	 */
	public Elevator getClosestFreeElevator(Action action);

	/**
	 * 
	 * @param startLevel
	 *            Startlevel (currentlevel)
	 * @param endlevel
	 *            Destinationlevel
	 * @param maxPerson
	 *            Maximal capacity of elevator
	 * @return returns a list of action which fulfill the given criteria (Moving
	 *         up/down, amount of people in action should not exceed the param
	 *         maxPerson. All returned actions are removed from the controllers
	 *         datastructure.
	 */
	public List<Action> getActions(int startLevel, int endlevel, int maxPerson);

	/**
	 * Removes an elevator from the simulation
	 * 
	 * @param removeId
	 *            Id of the elevator
	 * @return Wheter the elevator could be removed or not
	 */
	public boolean removeElevator(int removeId);

	/**
	 * 
	 * @return List of all processed actions
	 */
	public List<Action> getDoneActions();

	/**
	 * Adds a new elevator to the simulation
	 * 
	 * @param e
	 *            Elevator to add
	 */
	public void addElevator(Elevator e);

	/**
	 * 
	 * @param building
	 *            Set the building for the simulation
	 */
	public void setBuilding(Building building);

	/**
	 * 
	 * @return Returns the building currently assigned to the controllers
	 */
	public Building getBuilding();

	/**
	 * Starts a random simulation
	 * 
	 * @param amountAction
	 *            Amount of actions for this random simulation
	 */
	public void startRandomSimulation(int amountAction);

	/**
	 * Stops the currently running random simulation
	 */
	public void stopRandomSimulation();

	/**
	 * Starts a simulation with a XML-File
	 * 
	 * @param path
	 *            Path to the XML-File
	 * @param simulationSpeed
	 *            Speed of the simulation
	 * @param actions
	 *            List of action to process
	 */
	public void startSimulation(String path, int simulationSpeed,
			List<Action> actions);

	/**
	 * Resets the evaluation
	 */
	public void resetLiftEvaluation();

	/**
	 * Clears all processed actions
	 */
	public void resetDoneActions();

	/**
	 * Clears all unprocessed actions
	 */
	public void resetActions();

	/**
	 * After a action is performed, it can be saved in the controller for
	 * statistic reasons
	 * 
	 * @param a
	 *            Processed action
	 */
	void addDoneAction(Action a);

	/**
	 * 
	 * @return Returns the active simulation
	 */
	public Simulation getSimulation();

	/**
	 * 
	 * @return Speed of the simulation
	 */
	public int getSimluationSpeed();

	/**
	 * 
	 * @return Name of the active algorithm
	 */
	public String getAlgorithmName();

	/**
	 * Sets the new algorithm. If a invalid name is passed, a default
	 * implementation will be taken
	 * 
	 * @param name
	 *            Name of the Algorithm
	 */
	public void setAlgorithmName(String name);

	/**
	 * 
	 * @return Amount of unprocessed actions
	 */
	public int getTodoActionsAmount();

}
