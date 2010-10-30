package definition;

import java.util.Date;

public abstract class IAction {
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
	
	
	public IAction(int startLevel,int endLevel,int pepoleAmount) {
		setStartLevel(startLevel);
		setEndLevel(endLevel);
		setPeopleAmount(pepoleAmount);
	}
	
	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setEndLevel(int endLevel) {
		this.endLevel = endLevel;
	}

	public int getEndLevel() {
		return endLevel;
	}

	public void setPeopleAmount(int peopleAmount) {
		this.peopleAmount = peopleAmount;
	}

	public int getPeopleAmount() {
		return peopleAmount;
	}

	public void setTimestampEntered(Date timestampEntered) {
		this.timestampEntered = timestampEntered;
	}

	public Date getTimestampEntered() {
		return timestampEntered;
	}

	public void setTimestampEnded(Date timestampEnded) {
		this.timestampEnded = timestampEnded;
	}

	public Date getTimestampEnded() {
		return timestampEnded;
	}

	public void setTimestampStarted(Date timestampStarted) {
		this.timestampStarted = timestampStarted;
	}

	public Date getTimestampStarted() {
		return timestampStarted;
	}

	public void setTimestampPeopleLoaded(Date timestampPeopleLoaded) {
		this.timestampPeopleLoaded = timestampPeopleLoaded;
	}

	public Date getTimestampPeopleLoaded() {
		return timestampPeopleLoaded;
	}
}
