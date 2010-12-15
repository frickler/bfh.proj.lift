package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Controller;
import exceptions.IllegalActionException;

public class Simulation extends Thread {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private Date startTime;
	private Date endTime;
	private Controller elevatorController;
	private List<Action> actions = new ArrayList<Action>();
	private Boolean Running = false;
	private int actionsToDo = -1;
	private String result = "no simulation started yet";
	private int simulationSpeed;

	public Simulation(Controller eController) {
		this.elevatorController = eController;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

	public void addAction(List<Action> actions) throws IllegalActionException {
		for (Action a : actions) {
			addAction(a);
		}
	}

	private void startSimulation() {
		this.startTime = new Date(System.currentTimeMillis());
		this.actionsToDo = actions.size();
		this.result = "simulation is currently running.";
		setRunning(true);
	}

	public void stopSimulation() {
		this.endTime = new Date(System.currentTimeMillis());
		setRunning(false);
	}
	
	public String getResult(){
		return this.result;
	}

	@Override
	public void run() {
		startSimulation();
		while (isRunning()) {
			if (elevatorController.getDoneActions().size() != actionsToDo) {
				for (int i = 0; i < actions.size(); i++) {
					Boolean add = false;
					if (actions.get(i) instanceof DelayedElevatorAction) {

						DelayedElevatorAction di = (DelayedElevatorAction) actions
								.get(i);
						Calendar d = Calendar.getInstance();
						d.setTimeInMillis(System.currentTimeMillis());
						d.add(Calendar.SECOND, di.getDelayInSeconds()
								/ simulationSpeed);
						Calendar start = Calendar.getInstance();
						start.setTime(this.startTime);
						if (d.after(start)) {
							add = true;
						}
					} else {
						add = true;
					}
					if (add) {
						try {
							this.elevatorController.performAction(actions
									.get(i));
							log4j.debug(" Simulator: action add to controller: "
									+ actions.get(i).toString());
						} catch (IllegalActionException e) {
							e.printStackTrace();
						}
						actions.remove(i);
						i--;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			} else {
				stopSimulation();
				log4j.debug("Simulator stopped: all action are completed by the controller");
				int spaninseconds = (int)(this.endTime.getTime() - this.startTime.getTime()) / 1000;
				this.result = "It took "+spaninseconds+" seconds to complete all actions";
				log4j.debug(this.result);
			}
		}
	}

	public void setRunning(Boolean running) {
		Running = running;
	}

	public Boolean isRunning() {
		return Running;
	}

	public void generateActions(int amount) {

		int minLevel = elevatorController.getBuilding().getMinLevel();
		int maxLevel = elevatorController.getBuilding().getMaxLevel();

		Random gen = new Random((int) (Math.random() * 10000));

		for (int i = 0; i < amount; i++) {
			int startLevel = gen.nextInt(maxLevel - minLevel) + minLevel;
			int endLevel = gen.nextInt(maxLevel - minLevel) + minLevel;
			int pepoleAmount = gen.nextInt(10) + 1;
			int delay = gen.nextInt(40) / simulationSpeed;
			Action a = new DelayedElevatorAction(startLevel, endLevel,
					pepoleAmount, delay);
			actions.add(a);
			log4j.debug("Simulator: action generated: " + a.toString());
		}
	}

	public void clearActions() {
		log4j.debug(actions.size() + " actions cleared in simulator");
		actions.clear();
	}
}
