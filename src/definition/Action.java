package definition;

import java.util.Date;

/**
 * An action is a movement from a level X to another Y 
 * where X <> Y with a certain amount of people. It also
 * has a timestamp with the beginning and the end of an
 * action to offer some calculations.
 *  
 * @author BFH-Boys
 *
 */
public abstract class Action {
	// where the lift is called
	private int startLevel;
	// where the people want to go
	private int endLevel;
	// how many people want to go into the lift
	private int peopleAmount;
	// when the action was put in the controllers data structure
	private Date timestampCreated;
	// when the elevator starts processing the action
	private Date timestampStarted;
	// when the action is done
	private Date timestampEnded;
	// when the people enter the elevator
	private Date timestampPeopleLoaded;

	/**
	 * 
	 * @param startLevel
	 *            Level where the button is pressed and the people want to enter
	 * @param endLevel
	 *            Destination
	 * @param pepoleAmount
	 *            Amount of people moving from startLevel to endLevel
	 */
	public Action(int startLevel, int endLevel, int peopleAmount) {
		this.startLevel = startLevel;
		this.endLevel = endLevel;
		this.peopleAmount = peopleAmount;
	}

	/**
	 * 
	 * @return Startlevel Level where the button is pressed and the people want
	 *         to enter
	 */
	public int getStartLevel() {
		return startLevel;
	}
	
	
	/**
	 * 
	 * @return when the people entered the elevator
	 */
	protected Date getTimestampPeopleLoaded() {
		return timestampPeopleLoaded;
	}

	/**
	 * 
	 * @param when the people enter the elevator
	 */
	protected void setTimestampPeopleLoaded(Date timestampPeopleLoaded) {
		this.timestampPeopleLoaded = timestampPeopleLoaded;
	}

	/**
	 * 
	 * @return The destination level
	 */
	public int getEndLevel() {
		return endLevel;
	}

	/**
	 * 
	 * @return Amount of people going from {@link #getStartLevel StartLevel} to {@link #getEndLevel EndLevel}
	 */
	public int getPeopleAmount() {
		return peopleAmount;
	}

	/**
	 * Sets the amount of people
	 * @param peopleAmount amount
	 */
	public void setPeopleAmount(int peopleAmount){
		this.peopleAmount = peopleAmount;
	}

	/**
	 * 
	 * @param timestampCreated
	 *            Timestamp in milliseconds when the action was put in the {@link Controller} datastructure	 
	 */
	public void setTimestampCreated(Date timestampCreated) {
		this.timestampCreated = timestampCreated;
	}

	/**
	 * 
	 * @return Timestamp in milliseconds when the action was put in the {@link Controller} datastructureF
	 */
	public Date getTimestampCreated() {
		return timestampCreated;
	}

	/**
	 * 
	 * @param Sets the timestamp (in milliseconds) when the action is done processing
	 */
	public void setTimestampEnded(Date timestampEnded) {
		this.timestampEnded = timestampEnded;
	}

	/**
	 * 
	 * @return Gets the timestamp (in milliseconds) when the action is done processing
	 */
	public Date getTimestampEnded() {
		return timestampEnded;
	}

	/**
	 * 
	 * @param Sets the timestamp (in milliseconds) when the action was started processing
	 */
	public void setTimestampStarted(Date timestampStarted) {
		this.timestampStarted = timestampStarted;
	}

	/**
	 * 
	 * @return Returns the timestamp (in milliseconds) when the action was started processing
	 */
	public Date getTimestampStarted() {
		return timestampStarted;
	}

}
