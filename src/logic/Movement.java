package logic;

import java.util.Date;

import org.apache.log4j.Logger;

import definition.IAction;
import definition.ILiftable;

public class Movement extends Thread {
		
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private ILiftable elevator;
	private IAction action;

	public Movement(ILiftable elevator, IAction action) {
		super();
		this.elevator = elevator;
		this.action = action;
	}

	@Override
	public void run() {
		// wait until the elevator is not busy any more
		while (elevator.isBusy()) {
			try {
				Thread.sleep(100);				
			} catch (InterruptedException e) {
			}

		}
		try {
			elevator.setBusy(true);
												
			// Move elevator to startLevel
			if (elevator.getCurrentLevel() != action.getStartLevel()){
				log4j.debug("Moving empty elevator " + elevator.hashCode() + " from " + elevator.getCurrentLevel() + " to " + action.getStartLevel() + " action: " + action.hashCode());
				moveToStart();
			}
			log4j.debug("Moving elevator " + elevator.hashCode() + " from " + elevator.getCurrentLevel() + " to " + action.getEndLevel() + " action: " + action.hashCode());
			action.setTimestampStarted(new Date(System.currentTimeMillis()));
			move(elevator.getCurrentLevel(), action.getEndLevel());
			action.setTimestampEnded(new Date(System.currentTimeMillis()));
			// update statistics
			elevator.moved(action);
		} finally {
			elevator.setBusy(false);
		}
	}

	private void moveToStart() {
		move(elevator.getCurrentLevel(),action.getStartLevel());
		Action moveTo = new Action(elevator.getCurrentLevel(), action.getStartLevel(), 0);
		moveTo.setTimestampStarted(new Date());
		move(elevator.getCurrentLevel(),action.getStartLevel());
		moveTo.setTimestampEnded(new Date());
		elevator.moved(moveTo);
	}

	private void move(int sourceLevel, int targetLevel) {

		int levels = Math.abs(targetLevel - sourceLevel);

		for (int i = 0; i < levels; i++) {
			// ToDo: Update elevators current level
			try {
				Thread.sleep(elevator.getTimeForOneLevel());
			} catch (InterruptedException e) { }
		}		

	}

}
