package definition;

import java.util.Date;

/**
 * 
 * @author BFH-Boys
 *
 */
public abstract class Action {
	// where the lift is called
	private int startLevel;
	// where the pepole want to go
	private int endLevel;
	// how many people want to go into the lift
	private int peopleAmount;
	// when the action was entered
	private Date timestampEntered;
	// when the elevator goes to the Level where the elevator was ordered
	private Date timestampStarted;
	// when the elevator arrived at the startLevel
	private Date timestampPeopleLoaded;
	// when the elevator arrived at the endLevel
	private Date timestampEnded;

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
	 * @return Destination
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
	 * 
	 * @param timestampEntered
	 *            Timestamp in milliseconds
	 * @deprecated          Get peopleAmount (>0) instead and read getTimestampStarted
	 */
	public void setTimestampEntered(Date timestampEntered) {
		this.timestampEntered = timestampEntered;
	}

	/**
	 * @deprecated
	 * @return
	 */
	public Date getTimestampEntered() {
		return timestampEntered;
	}

	/**
	 * 
	 * @param timestampEnded Sets the timestamp in milliseconds when the action is done processing
	 */
	public void setTimestampEnded(Date timestampEnded) {
		this.timestampEnded = timestampEnded;
	}

	/**
	 * 
	 * @return Gets the timestamp in milliseconds when the action is done processing
	 */
	public Date getTimestampEnded() {
		return timestampEnded;
	}

	/**
	 * 
	 * @param timestampStarted Sets the timestamp in milliseconds when the action was started processing
	 */
	public void setTimestampStarted(Date timestampStarted) {
		this.timestampStarted = timestampStarted;
	}

	/**
	 * 
	 * @return Returns the timestamp in milliseconds when the action was started processing
	 */
	public Date getTimestampStarted() {
		return timestampStarted;
	}

	/**
	 * @deprecated
	 * @param timestampPeopleLoaded
	 */
	public void setTimestampPeopleLoaded(Date timestampPeopleLoaded) {
		this.timestampPeopleLoaded = timestampPeopleLoaded;
	}

	/**
	 * @deprecated
	 * @return
	 */
	public Date getTimestampPeopleLoaded() {
		return timestampPeopleLoaded;
	}
}
