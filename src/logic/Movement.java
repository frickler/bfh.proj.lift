package logic;

import java.util.Date;

import org.apache.log4j.Logger;

import definition.Action;
import definition.HorizontalTransporter;
import definition.MovementObserver;
import definition.MovementObserverable;

public class Movement extends Thread implements MovementObserverable {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private HorizontalTransporter elevator;
	private Action action;
	private MovementObserver movedObserver;
	// Variable to indicate if the stopMovement-Method is called
	private boolean move;

	public Movement(HorizontalTransporter elevator, Action action,
			MovementObserver movedObserver) {
		super();
		move = true;
		this.elevator = elevator;
		this.action = action;
		this.movedObserver = movedObserver;

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

		log4j.debug("Moving elevator " + elevator.hashCode() + " with "
				+ action.getPeopleAmount() + " people from "
				+ action.getStartLevel() + " to " + action.getEndLevel()
				+ " action: " + action.hashCode());
		action.setTimestampStarted(new Date(System.currentTimeMillis()));
		loadPeople();
		move(elevator.getCurrentLevel(), action.getEndLevel());
		loadPeople();
		action.setTimestampEnded(new Date(System.currentTimeMillis()));

		// update statistics
		movedObserver.moved(this, action);
	}

	/**
	 * @ToDo: Überprüfen
	 */
	private void loadPeople() {
		log4j.debug("People Amount:" + action.getPeopleAmount());
		if (action.getPeopleAmount() > 0) {
			try {
				Thread.sleep(action.getPeopleAmount() * 2000);
			} catch (InterruptedException e) {
			}
		}

	}

	/**
	 * @param sourceLevel
	 * @param targetLevel
	 */
	private void move(int sourceLevel, int targetLevel) {

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
			movedObserver.stepDone(this, action, (sign * currentSpeed) / 100);

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {

			}
		}
	}

	public void stopMovement() {
		this.move = false;
	}

}
