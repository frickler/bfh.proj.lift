package logic;

import org.apache.log4j.Logger;

import definition.MovementObserver;
import definition.PeopleLoadedObserver;
import definition.VerticalTransporter;

/**
 * A Movement represents a motion. There can be different action involved in
 * this motion. <br>
 * An example:
 * <ul>
 * <li>Action1 with StartLevel 1 and EndLevel 10</li>
 * <li>Action2 with StartLevel 1 and EndLevel 5</li>
 * </ul>
 * Lets assume the elevator is now in level 8. So there will be 3 movement
 * generated:
 * <ol>
 * <li>Movement: Move elevator from level 8 to level 1 (move to start)</li>
 * <li>Movement: Move elevator from level 1 to level 5</li>
 * <li>Movement: Move elevator from level 4 to level 10</li>
 * </ol>
 * So a movement is not the same as an action, but they are related. From a
 * movement, multiple actions can profit.
 * 
 * 
 * @author BFH-Boys
 * 
 */
public class Movement extends Thread {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// Time for one person to enter/exit the elevator in milliseconds
	private final int TIME_TO_EXIT = 10000;
	// Time for one level
	private final int TICK_TIME = 400;

	private VerticalTransporter elevator;
	private MovementObserver movedObserver;
	private PeopleLoadedObserver peopleLoadedObserver;
	private int startLevel;
	private int endLevel;
	private int simulationSpeed = 10;
	// Amount of people enter the elevator
	private int peopleIn;
	// Amount of people exiting the elevator
	private int peopleOut;
	// Variable to indicate if the stopMovement-Method is called
	private boolean move;

	/**
	 * 
	 * @param elevator
	 *            Elevator who will be moved
	 * @param startLevel
	 *            Startlevel of this movement
	 * @param endLevel
	 *            Endlevel of this movement
	 * @param peopleIn
	 *            Amount of people enter the elevator in the startlevel
	 * @param peopleOut
	 *            Amount of people exit the elevator in the startlevel
	 * @param simulationSpeed
	 *            Speed of the simulation. Affects both the time to enter/exit
	 *            and the movement speed
	 * @param movedObserver
	 *            This observer gets called after each step/movement the
	 *            elevator does (callback)
	 * @param peopleLoadedObserver
	 *            This observer gets called after people enter/exit the elevator
	 */
	public Movement(VerticalTransporter elevator, int startLevel, int endLevel,
			int peopleIn, int peopleOut, int simulationSpeed,
			MovementObserver movedObserver,
			PeopleLoadedObserver peopleLoadedObserver) {
		super();
		move = true;
		this.startLevel = startLevel;
		this.endLevel = endLevel;
		this.elevator = elevator;
		this.movedObserver = movedObserver;
		this.peopleIn = peopleIn;
		this.peopleOut = peopleOut;
		this.peopleLoadedObserver = peopleLoadedObserver;
		this.simulationSpeed = simulationSpeed;
	}

	/**
	 * 
	 * @param elevator
	 *            Elevator who will be moved
	 * @param startLevel
	 *            Startlevel of this movement
	 * @param endLevel
	 *            Endlevel of this movement
	 * @param simulationSpeed
	 *            Speed of the simulation. Affects both the time to enter/exit
	 *            and the movement speed
	 * @param movementObserver
	 *            This observer gets called after each step/movement the
	 *            elevator does (callback)
	 */
	public Movement(Elevator elevator, int startLevel, int endLevel,
			int simulationSpeed, MovementObserver movementObserver) {
		this(elevator, startLevel, endLevel, 0, 0, simulationSpeed,
				movementObserver, null);
	}

	/**
	 * Starts loading the people and moving the elevator
	 */
	@Override
	public void run() {

		log4j.debug("Moving " + elevator.getName() + " from " + startLevel
				+ " to " + endLevel);
		loadPeople();
		move();

		// update statistics
		movedObserver.moved(this);
	}

	/**
	 * Loads and unloads the people in one level. This takes a certain amount of
	 * time
	 */
	private void loadPeople() {

		log4j.debug(elevator.getName() + " LoadPeople - In: " + peopleIn
				+ " Out: " + peopleOut);

		for (int i = 0; i < peopleOut; i++) {
			try {
				Thread.sleep((int) (TIME_TO_EXIT / simulationSpeed));
			} catch (InterruptedException e) {
			}
			if (peopleLoadedObserver != null) {
				peopleLoadedObserver.peopleLoaded(elevator, -1);
			}
		}

		for (int i = 0; i < peopleIn; i++) {
			try {
				Thread.sleep((int) (TIME_TO_EXIT / simulationSpeed));
			} catch (InterruptedException e) {
			}
			if (peopleLoadedObserver != null) {
				peopleLoadedObserver.peopleLoaded(elevator, +1);
			}
		}

	}

	/**
	 * Moves the elevator from the sourceLevel to the targetLevel
	 */
	private void move() {
		int sourceLevel = startLevel;
		int targetLevel = endLevel;

		int totalDistance = Math.abs(targetLevel - sourceLevel) * 100;
		// double distanceAcceleration = (elevator.getMaxSpeed() * elevator
		// .getMaxSpeed()) / (2 * elevator.getAcceleration());
		double currentSpeed = 0;
		double milage = 0.1;

		double sign = (double) 1;
		if (targetLevel < sourceLevel) {
			sign *= -1;
		}

		while (milage <= totalDistance) {

			// StopMovement Method was called
			if (!move) {
				return;
			}

			double distanceToBreak = (currentSpeed * currentSpeed)
					/ (2 * elevator.getAcceleration());

			if ((totalDistance - milage) <= distanceToBreak) {
				// Elevator slows down
				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
						* (totalDistance - milage));
			} else {
				// Elevator speeds up
				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
						* milage);
				currentSpeed = (currentSpeed > elevator.getMaxSpeed()) ? elevator
						.getMaxSpeed() : currentSpeed;
			}
			movedObserver.updateSpeed(currentSpeed);

			milage += currentSpeed;
			// log4j.debug((sign * currentSpeed) / 100);
			movedObserver.stepDone(this, ((sign * currentSpeed) / 100));

			try {
				Thread.sleep((int) (TICK_TIME / simulationSpeed));
				elevator.addTimeInMotion(TICK_TIME);
				if (!elevator.isLoaded()) {
					elevator.addTimeInMotionEmpty(TICK_TIME);
				}

			} catch (InterruptedException e) {

			}
		}
		movedObserver.updateSpeed(0);
	}

	/**
	 * Stops processing this movement
	 */
	public void stopMovement() {
		this.move = false;
	}

}
