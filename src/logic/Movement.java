package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import definition.Action;
import definition.HorizontalTransporter;
import definition.MovementObserver;
import definition.MovementObserverable;

public class Movement extends Thread implements MovementObserverable {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private HorizontalTransporter elevator;
	private Action action;
	private List<MovementObserver> movedObservers;
	// Variable to indicate if the stopMovement-Method is called
	private boolean move;

	public Movement(HorizontalTransporter elevator, Action action,
			MovementObserver movedObserver) {
		super();
		move = true;
		movedObservers = new ArrayList<MovementObserver>();
		this.elevator = elevator;
		this.action = action;
		addMovedObserver(movedObserver);

	}

	@Override
	public void run() {
		// wait until the elevator is not busy any more
//		while (elevator.isBusy()) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//			}
//
//		}

		log4j.debug("Moving elevator " + elevator.hashCode() 
				+ " with " + action.getPeopleAmount() + " people from "
				+ action.getStartLevel() + " to " + action.getEndLevel()
				+ " action: " + action.hashCode());
		action.setTimestampStarted(new Date(System.currentTimeMillis()));
		move(elevator.getCurrentLevel(), action.getEndLevel());
		action.setTimestampEnded(new Date(System.currentTimeMillis()));
		// update statistics
		notifyObservers(action);
	}

	private void move(int sourceLevel, int targetLevel) {

		int levels = Math.abs(targetLevel - sourceLevel);

		for (int i = 0; i < levels; i++) {
			// StopMovement Method was called
			if (!move) {
				return;
			}
			// ToDo: Update elevators current level
			try {
				Thread.sleep(elevator.getTimeForOneLevel());
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	public void addMovedObserver(MovementObserver observer) {
		movedObservers.add(observer);

	}

	@Override
	public void deleteMovedObserver(MovementObserver observer) {
		movedObservers.remove(observer);
	}

	@Override
	public void notifyObservers(Action action) {
		for (MovementObserver observer : movedObservers) {
			observer.moved(this, action);
		}
	}

	public void stopMovement() {
		this.move = false;
	}

}
