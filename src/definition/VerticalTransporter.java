package definition;

import java.util.List;

/**
 * @author BFH-Boys
 * 
 */
public interface VerticalTransporter extends ActionObservable {

	/**
	 * 
	 * @return the accelaration of the elevator
	 */
	public float getAcceleration();

	/**
	 * 
	 * @return maximal speed of the elevator
	 */
	public float getMaxSpeed();

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
	 * @return the current Position of the elevator. An elevator can be between
	 *         two levels
	 */
	public double getCurrentPosition();

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
	 * Gives a list of actions to perform
	 * 
	 * @param action
	 *            Actions with movement parameters (StartLevel should not equal
	 *            the EndLevel). This method sets the values of two variables
	 *            timestampStarted and timeStampEnded in each action
	 * @param startLevel
	 *            Level where the elevator should goto first
	 * 
	 */
	void move(List<Action> actions, int startLevel, Direction direction);

	/**
	 * Aborts the processing of the current action. The transporter stays at the
	 * currentLevel and waits for further actions.
	 */
	void stop();

	public void setSimulationSpeed(int speed);
}
