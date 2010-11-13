package definition;

/**
 * @author BFH-Boys
 * 
 */
public interface HorizontalTransporter extends ActionObservable {

	/**
	 * 
	 * @return the lowest floor this transporter can reach
	 */
	public int getMinLevel();

	/**
	 * 
	 * @return the higest floor this transporter can reach
	 */
	public int getMaxLevel();

	/**
	 * 
	 * @return the maximum amount of people
	 */
	public int getMaxPeople();

	/**
	 * 
	 * @return the current level (floor) of this transporter
	 */
	public int getCurrentLevel();

	/**
	 * 
	 * @return sum of all transported people
	 */
	public int getTransportedPeople();

	/**
	 * 
	 * @return sum of all driven levels
	 */
	public int getDrivenLevels();

	/**
	 * 
	 * @return sum of levels driven without any people
	 */
	public int getDrivenLevelsEmpty();

	/**
	 * 
	 * @return sum of time in motion
	 */
	public float getTimeInMotion();

	/**
	 * 
	 * @return sum of time in motion without any people
	 */
	public float getTimeInMotionEmpty();

	/**
	 * 
	 * @return The time in milliseconds for a transporter to pass one level
	 */
	public int getTimeForOneLevel();

	/**
	 * indicates if a transporter object is busy (moving)
	 * 
	 * @return
	 */
	public boolean isBusy();

	/**
	 * Moves the transporter based on the arguments given in the action.
	 * 
	 * @param action
	 *            Action with movement parameters (StartLevel should not equal
	 *            the EndLevel). This method sets the values of the two
	 *            variables timestampStarted and timeStampEnded
	 * 
	 */
	void move(Action action);

	/**
	 * Aborts the processing of the current action. The transporter stays at the
	 * currentLevel and waits for further actions.
	 */
	void stop();
}
