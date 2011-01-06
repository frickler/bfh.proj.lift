package definition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

/**
 * An action is a movement from a level X to another Y where X <> Y with a
 * certain amount of people. It also has a timestamp with the beginning and the
 * end of an action to offer some calculations.
 * 
 * @author BFH-Boys
 * 
 */
public abstract class Action implements ActionObservable {

	// List of action observers
	private List<ActionObserver> observers;
	// where the lift is called
	private int startLevel;
	// where the people want to go
	private int endLevel;
	// how many people want to go into the lift
	private int peopleAmount;
	// when the action was put in the controllers data structure
	// the person has pressed the button to call the elevator
	private Date timestampElevatorCalled;
	// the elevator arrived at the startLevel the people enter the elevator
	private Date timestampElevatorEntered;
	// when the action is done the people had left the elevator
	private Date timestampElevatorLeft;


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
		observers = new ArrayList<ActionObserver>();
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
	 * @return The destination level
	 */
	public int getEndLevel() {
		return endLevel;
	}

	/**
	 * 
	 * @return Amount of people going from {@link #getStartLevel StartLevel} to
	 *         {@link #getEndLevel EndLevel}
	 */
	public int getPeopleAmount() {
		return peopleAmount;
	}

	/**
	 * Sets the amount of people
	 * 
	 * @param peopleAmount
	 *            amount
	 */
	public void setPeopleAmount(int peopleAmount) {
		this.peopleAmount = peopleAmount;
		notifyObserversActionPeopleLoaded();
	}

	/**
	 * 
	 * @param timestampCreated
	 *            Timestamp in milliseconds when the action was put in the
	 *            {@link Controller} datastructure
	 */
	public void setTimestampElevatorCalled(Date timestampElevatorCalled) {
		this.timestampElevatorCalled = timestampElevatorCalled;
	}

	/**
	 * 
	 * @return Timestamp in milliseconds when the action was put in the
	 *         {@link Controller} datastructureF
	 */
	public Date getTimestampElevatorCalled() {
		return timestampElevatorCalled;
	}

	/**
	 * 
	 * @param Sets
	 *            the timestamp (in milliseconds) when the action is done
	 *            processing
	 */
	public void setTimestampElevatorLeft(Date timestampElevatorLeft) {
		this.timestampElevatorLeft = timestampElevatorLeft;
		notifyObserversActionPerformed();
	}

	/**
	 * 
	 * @return Gets the timestamp (in milliseconds) when the action is done
	 *         processing
	 */
	public Date getTimestampElevatorLeft() {
		return timestampElevatorLeft;
	}

	/**
	 * 
	 * @param Sets
	 *            the timestamp (in milliseconds) when the action was started
	 *            processing
	 */
	public void setTimestampElevatorEntered(Date timestampElevatorEntered) {
		this.timestampElevatorEntered = timestampElevatorEntered;
		notifyObserversActionStarted();
	}

	/**
	 * 
	 * @return Returns the timestamp (in milliseconds) when the action was
	 *         started processing
	 */
	public Date getTimestampElevatorEntered() {
		return timestampElevatorEntered;
	}

	@Override
	public void addActionObserver(ActionObserver observer) {
		observers.add(observer);

	}

	@Override
	public void deleteActionObserver(ActionObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserversActionStarted() {
		for (ActionObserver observer : observers) {
			observer.actionStarted(this);
		}
	}

	@Override
	public void notifyObserversActionPerformed() {
		for (ActionObserver observer : observers) {
			observer.actionPerformed(this);
		}
	}

	@Override
	public void notifyObserversActionPeopleLoaded() {
		for (ActionObserver observer : observers) {
			observer.actionPeopleLoaded(this);
		}

	}

	public String toXML() {
		// TODO Auto-generated method stub
		return "<Action startLevel=\""+startLevel+"\" endLevel=\""+endLevel+"\" peopleAmount=\""+peopleAmount+"\" />";
	}

}
