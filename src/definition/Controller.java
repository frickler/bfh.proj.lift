package definition;

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
	 */
	public void performAction(Action action);
	
	/**
	 * Starts the performing of the actions
	 */
	public void startController();
	
	/**
	 * Stops the performing of the actions
	 */
	public void stopController();
		

}
