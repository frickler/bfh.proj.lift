package definition;

import exceptions.IllegalActionException;

/**
 * 
 * @author BFH-Boys
 *
 */
public interface Controller  {
	
	/**
	 * Performs the given action. Usually by giving it to 
	 * the algorithm
	 * 
	 * @param action to perform
	 * @throws IllegalActionException if an action is invalid (invalid levels p.E.)
	 */
	public void performAction(Action action) throws IllegalActionException;
	
	/**
	 * Starts the performing of the actions
	 */
	public void startController();
	
	/**
	 * Stops the performing of the actions
	 */
	public void stopController();
		

}
