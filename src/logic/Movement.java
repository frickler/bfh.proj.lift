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
		move(elevator.getCurrentLevel(), action.getEndLevel());
		action.setTimestampEnded(new Date(System.currentTimeMillis()));
		// update statistics
		movedObserver.moved(this, action);
	}

	/**
	 * Bug: Wenn die Strecke kürzer ist als die Beschleunigung, wird als Startwert 
	 * die maximale Geschwindigkeit genommen
	 * @param sourceLevel
	 * @param targetLevel
	 */
	private void move(int sourceLevel, int targetLevel) {
		
		int totalDistance = Math.abs(targetLevel - sourceLevel) * 100;
		//double distanceAcceleration = (elevator.getMaxSpeed() * elevator
		//		.getMaxSpeed()) / (2 * elevator.getAcceleration());
		double currentSpeed = 0;
		double milage = 0.1;

		double sign = (double) 1;
		if (targetLevel < sourceLevel) {
			sign *= -1;
		}

		while (milage <= totalDistance){
			
			// StopMovement Method was called
			if (!move) {
				return;
			}
			
			double distanceToBreak = (currentSpeed *  currentSpeed) / (2 * elevator.getAcceleration());
					
			if ((totalDistance - milage) <= distanceToBreak) {
				// Elevator slows down
				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
						* (totalDistance - milage));
			} else {			
				// Elevator speeds up
				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
						* milage);
				currentSpeed = (currentSpeed > elevator.getMaxSpeed()) ? elevator.getMaxSpeed() : currentSpeed;
			}
			milage += currentSpeed;
			log4j.debug((sign * currentSpeed) / 100);
			movedObserver.stepDone(this, action, (sign * currentSpeed) / 100);
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {

			}
		}	
		 
//		int totalDistance = Math.abs(targetLevel - sourceLevel) * 100;
//		double distanceAcceleration = (elevator.getMaxSpeed() * elevator
//				.getMaxSpeed()) / (2 * elevator.getAcceleration());
//		double currentSpeed = 0;
//		double milage = 0.1;
//
//		double sign = (double) 1;
//		if (targetLevel < sourceLevel) {
//			sign *= -1;
//		}
//
//		while (milage <= totalDistance){
//			
//			// StopMovement Method was called
//			if (!move) {
//				return;
//			}
//					
//			if ((totalDistance - milage) <= distanceAcceleration) {
//				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
//						* (totalDistance - milage));
//			} else if (milage <= distanceAcceleration) {				
//				currentSpeed = Math.sqrt(2 * elevator.getAcceleration()
//						* milage);
//
//			} else {
//				currentSpeed = elevator.getMaxSpeed();
//			}
//			milage += currentSpeed;
//			log4j.debug((sign * currentSpeed) / 100);
//			movedObserver.stepDone(this, action, (sign * currentSpeed) / 100);
//			
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//
//			}
//		}		

//		boolean up = targetLevel < sourceLevel;
//
//		int distance = Math.abs(targetLevel - sourceLevel) * 100;
//		float distanceAcceleration = (elevator.getMaxSpeed() * elevator.getMaxSpeed()) / 2 * elevator.getAcceleration();
//		float currentSpeed = 0;
//		int time = 0;
//		
//		log4j.debug("Distance:" + distanceAcceleration);
//
//		double stepSize = (double) 0.02;
//		if (up) {
//			stepSize *= -1;
//		}
//
//		log4j.debug("StepSize: " + stepSize);
//
//		for (int i = 0; i < distance; i++) {
//			// StopMovement Method was called
//			if (!move) {
//				return;
//			}
//			currentSpeed = elevator.getAcceleration() * time;
//			log4j.debug("CurrentSpeed" + currentSpeed);
//			
//			if (currentSpeed > elevator.getMaxSpeed()){
//				currentSpeed = elevator.getMaxSpeed();				
//			}
//			
//			movedObserver.stepDone(this, action, stepSize);
//
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//
//			}
//		}
	}

	// @Override
	// public void addMovedObserver(MovementObserver observer) {
	// movedObservers.add(observer);
	//
	// }
	//
	// @Override
	// public void deleteMovedObserver(MovementObserver observer) {
	// movedObservers.remove(observer);
	// }
	//
	// @Override
	// public void notifyObservers(Action action) {
	// for (MovementObserver observer : movedObservers) {
	// observer.moved(this, action);
	// }
	// }

	public void stopMovement() {
		this.move = false;
	}

}
