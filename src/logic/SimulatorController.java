package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import definition.Action;
import definition.Controller;
import definition.HorizontalTransporter;

public class SimulatorController implements Controller, Runnable {

	private Date startTime;
	private ElevatorController elevatorController;
	private List<Action> actions = new ArrayList<Action>();
	private Boolean Running = false;

	public SimulatorController(ElevatorController eController) {
		this.elevatorController = eController;
	}

	@Override
	public void performAction(Action action) {
		this.actions.add(action);
	}

	@Override
	public void startController() {
		this.startTime = new Date(System.currentTimeMillis());
	}

	@Override
	public void stopController() {
		setRunning(false);
	}

	@Override
	public void run() {
		setRunning(true);
		while (isRunning()) {
			for (int i = 0; i < actions.size(); i++) {
				Boolean add = false;
				if (actions.get(i) instanceof DelayedElevatorAction) {

					DelayedElevatorAction di = (DelayedElevatorAction) actions
							.get(i);
					Calendar d = Calendar.getInstance();
					d.setTimeInMillis(System.currentTimeMillis());
					d.add(Calendar.SECOND, di.getDelayInSeconds());
					Calendar start = Calendar.getInstance();
					start.setTime(this.startTime);
					if (d.after(start)) {
						add = true;
					}
				} else {
					add = true;
				}
				if (add) {
					this.elevatorController.performAction(actions.get(i));
					actions.remove(i);
					i--;
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setRunning(Boolean running) {
		Running = running;
	}

	public Boolean isRunning() {
		return Running;
	}

}
