package logic;

import org.apache.log4j.Logger;

import definition.MovementObserver;
import definition.MovementObserverable;
import definition.PeopleLoadedObserver;
import definition.PeopleLoadedObserverable;
import definition.VerticalTransporter;

public class Movement extends Thread implements MovementObserverable {

	// Time for one person to enter/exit the elevator in milliseconds
	private final int TIME_TO_EXIT = 10000;
	// Time for one level
	private final int TICK_TIME = 400;

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

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

	public Movement(Elevator elevator, int startLevel, int endLevel,
			int simulationSpeed, MovementObserver movementObserver) {
		this(elevator, startLevel, endLevel, 0, 0, simulationSpeed,
				movementObserver, null);
	}

	@Override
	public void run() {
		// wait until the elevator is not busy any more
		// while (elevator.isBusy()) {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		//
		// }

		log4j.debug("Moving elevator " + elevator.hashCode() + " from "
				+ startLevel + " to " + endLevel);

		// action.setTimestampStarted(new Date(System.currentTimeMillis()));
		// loadPeople();
		loadPeople();
		move();

		// update statistics
		movedObserver.moved(this);
	}

	/**
	 * @ToDo: ueberpruefen
	 */
	private void loadPeople() {

		log4j.debug("LoadPeople - In: " + peopleIn + " Out: " + peopleOut);

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
	 * @param sourceLevel
	 * @param targetLevel
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
			} catch (InterruptedException e) {

			}
		}
		movedObserver.updateSpeed(0);
	}

	public void stopMovement() {
		this.move = false;
	}

}
