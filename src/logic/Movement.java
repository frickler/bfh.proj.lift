package logic;

import java.util.Date;

import org.apache.log4j.Logger;

import definition.Action;
import definition.VerticalTransporter;
import definition.MovementObserver;
import definition.MovementObserverable;

public class Movement extends Thread implements MovementObserverable {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private VerticalTransporter elevator;
	private MovementObserver movedObserver;
	private int startLevel;
	private int endLevel;
	private int simulationSpeed = 1;
	// Amount of people moving in/out
	private int peopleInOut;
	// Variable to indicate if the stopMovement-Method is called
	private boolean move;

	public Movement(VerticalTransporter elevator, int startLevel, int endLevel,
			MovementObserver movedObserver) {
		this(elevator, startLevel, endLevel, 0,1, movedObserver);

	}

	public Movement(VerticalTransporter elevator, int startLevel, int endLevel,
			int peopleInOut, int simulationSpeed, MovementObserver movedObserver) {
		super();
		move = true;
		this.startLevel = startLevel;
		this.endLevel = endLevel;
		this.elevator = elevator;
		this.movedObserver = movedObserver;
		this.peopleInOut = peopleInOut;

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
		if (peopleInOut > 0) {
			try {
				Thread.sleep(peopleInOut * 1000 / simulationSpeed);
			} catch (InterruptedException e) {
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
			milage += currentSpeed;
			// log4j.debug((sign * currentSpeed) / 100);
			movedObserver.stepDone(this, (sign * currentSpeed) / 100);

			try {
				Thread.sleep(20/simulationSpeed);
			} catch (InterruptedException e) {

			}
		}
	}

	public void stopMovement() {
		this.move = false;
	}

}
