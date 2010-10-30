package definition;

public interface IController  {
	
	/**
	 * Adds an actionListener to the controller
	 * @param listener Listener to add
	 */
	public void addActionListener(IActionListener listener);
	
	/**
	 * Removes an actionListener from the controller
	 * @param listener Listener to remove
	 */
	public void removeActionListener(IActionListener listener);
	
	/**
	 * Performs the given action. Usually by giving it to 
	 * the algorithm
	 * 
	 * @param action to perform
	 */
	public void performAction(IAction action);
	
	public void startController();
	
	public void stopController();
		

}
