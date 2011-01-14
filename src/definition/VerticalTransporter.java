package definition;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This is the interface for the elevators. It represents all the properties
 * like capacity, speed, highest and lowest level the elevator can reach, etc.
 * 
 * @author BFH-Boys
 * 
 */
public interface VerticalTransporter {

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
	 * @return the time the elevator doesn't move
	 */
	public int getTimeSillStand(int TotalTime);

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
	 * @param actions
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

	/**
	 * 
	 * @param speed
	 *            Sets the simulation speed
	 */
	public void setSimulationSpeed(int speed);

	/**
	 * 
	 * @return Current amount of people in this transporter
	 */
	public int getCurrentPeople();

	/**
	 * Resets all the statistics (drivenlevels, transported peoples, etc)
	 */
	public void resetStatistics();

	/**
	 * 
	 * @return Current speed of the elevator
	 */
	public double getCurrentSpeed();

	/**
	 * 
	 * @return Returns a unique ID
	 */
	public int getIdentityNumber();

	/**
	 * 
	 * @return Indicates if the transporter is empty
	 */
	public boolean isLoaded();

	/**
	 * Adds statistic information of this transporter to an XML-Element
	 * 
	 * @param createElement
	 *            Element in which the statistic nodes should be added
	 * @return Node with the statistic of this elevator
	 */
	public Node getXML(Element createElement);

	/**
	 * 
	 * @param milliseconds Adds time in motion to the statistics
	 */
	public void addTimeInMotion(int milliseconds);

	/**
	 * 
	 * @param milliseconds Adds time in motion with no people loaded to the statistics
	 */
	public void addTimeInMotionEmpty(int milliseconds);

	/**
	 * 
	 * @return gets the name of the parameter
	 */
	public String getName();

	public int getAuslastung(int i);

}
