package logic;

import java.util.Date;

import definition.IAction;
import definition.ILiftable;

public class Movement extends Thread {

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
			action.setTimestampStarted(new Date(System.currentTimeMillis()));
			move(elevator.getCurrentLevel(), action.getEndLevel());
			action.setTimestampEnded(new Date(System.currentTimeMillis()));
			// update statistics
			elevator.moved(action);
		} finally {
			elevator.setBusy(false);
		}
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
