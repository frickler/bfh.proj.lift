package logic;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import definition.Action;
import definition.ActionObserver;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.HorizontalTransporter;
import exceptions.IllegalActionException;
import exceptions.MaxLevelActionException;
import exceptions.MinLevelActionException;

public class ElevatorController implements Controller {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// Building attached to the controller
	private Building building;
	private Algorithm algorithm;
	private List<Action> actions;
	private Simulation simulation;

	/**
	 * Constructor for the controller. Each controller is responsible for
	 * exactly one building.
	 * 
	 * @param building
	 *            The attached building
	 */
	public ElevatorController(Building building,
			Class<? extends Algorithm> clazz) throws Exception {
		super();
		this.actions = new LinkedList<Action>();
		this.building = building;

		// Create new instance of algorithm using reflections
		// to ensure that each controller always has a algorithm
		// which processes the data
		Class[] args = new Class[] { Building.class, Controller.class };
		Constructor con = clazz.getConstructor(args);

		this.algorithm = (Algorithm) con.newInstance(building, this);
	}

	/**
	 * 
	 * @return The attached building
	 */
	public Building getBuilding() {
		return building;
	}

	@Override
	public void performAction(Action action) throws IllegalActionException {
		if (action.getStartLevel() < building.getMinLevel()) {
			throw new MinLevelActionException(action);
		}
		if (action.getEndLevel() > building.getMaxLevel()) {
			throw new MaxLevelActionException(action);
		}
		// set the current timestamp to the action
		action.setTimestampCreated(new Date());

		// add to datastructure
		synchronized (actions) {
			this.actions.add(action);
		}
	}

	@Override
	public void startController() {
		Thread t = new Thread(algorithm);
		t.start();

	}

	@Override
	public void stopController() {
		algorithm.stop();

	}

	/**
	 * Adds an actionListener to all elevators
	 */
	public void addActionObserver(ActionObserver observer) {
		for (HorizontalTransporter elevator : building.getElevators()) {
			elevator.addActionObserver(observer);
		}

	}

	/**
	 * Removes an actionListener from all elevators
	 */
	public void deleteActionObserver(ActionObserver observer) {
		for (HorizontalTransporter elevator : building.getElevators()) {
			elevator.deleteActionObserver(observer);
		}

	}

	@Override
	public void performActions(List<Action> actions)
			throws IllegalActionException {
		for (Action a : actions) {
			performAction(a);
		}
	}

	/**
	 * 
	 */
	@Override
	public Action getActionWithHighestPriority() {
		if (actions.isEmpty()) {
			return null;
		}
		return actions.remove(0);
	}

	@Override
	public List<Action> getActions(int startLevel, int endlevel, int maxPerson) {
		List<Action> actionsOfLevel = new LinkedList<Action>();
		boolean up = startLevel < endlevel;
		int remainingCapacity = maxPerson;

		Action splitedAction = null;
		synchronized (actionsOfLevel) {
			for (Action action : actions) {
				// break if the elevator is full
				if (remainingCapacity == 0) {
					break;
				}
				// ensure that action comes from the same startLevel
				if (action.getStartLevel() == startLevel) {
					// ensure that action goes in the same directory as the
					// elevator
					if ((action.getStartLevel() < action.getEndLevel()) == up) {
						// ensure that the elevator is not overloaded
						if (action.getPeopleAmount() > remainingCapacity) {
							// Split action so that only a few people move
							splitedAction = new ElevatorAction(
									action.getStartLevel(),
									action.getEndLevel(),
									action.getPeopleAmount()
											- remainingCapacity);
							action.setPeopleAmount(remainingCapacity);
						}
						// Descend the free spaces in the elevator
						remainingCapacity -= action.getPeopleAmount();
						actionsOfLevel.add(action);
					}
				}
			}
			// Remove actions which are processed
			this.actions.removeAll(actionsOfLevel);
		}
		if (splitedAction != null) {
			try {
				this.performAction(splitedAction);
			} catch (IllegalActionException e) {
				log4j.error(e.getMessage(), e);
			}
		}

		return actionsOfLevel;
	}

	/**
	 * 
	 */
	public boolean removeElevator(int removeId) {
		try {
			building.removeElevator(building.getElevators().get(removeId));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Action> getDoneActions() {
		// TODO List of done actions (krigu)
		List<Action> list = new ArrayList<Action>();

		// hardcode test actions use done actions
		Action a = new ElevatorAction(2, 12, 3);
		Date date = new Date(System.currentTimeMillis());
		a.setTimestampCreated(date);
		date = new Date(System.currentTimeMillis() + 3 * 1000);
		a.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis() + 6 * 1000);
		a.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis() + 9 * 1000);
		a.setTimestampEnded(date);

		Action b = new ElevatorAction(2, 12, 3);
		date = new Date(System.currentTimeMillis());
		b.setTimestampCreated(date);
		date = new Date(System.currentTimeMillis() + 10 * 1000);
		b.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis() + 20 * 1000);
		b.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis() + 30 * 1000);
		b.setTimestampEnded(date);

		Action c = new ElevatorAction(2, 12, 3);
		date = new Date(System.currentTimeMillis());
		c.setTimestampCreated(date);
		date = new Date(System.currentTimeMillis() + 5 * 1000);
		c.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis() + 10 * 1000);
		c.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis() + 15 * 1000);
		c.setTimestampEnded(date);

		list.add(a);
		list.add(b);
		list.add(c);

		return list;
	}

	@Override
	public void addElevator(Elevator e) {
		building.addElevator(e);
	}

	@Override
	public void setBuilding(Building b) {
		building = b;
	}

	@Override
	public void startRandomSimulation(int amount) {
		simulation = new Simulation(this);
		simulation.generateActions(amount);
		simulation.start();
	}

	@Override
	public void stopRandomSimulation() {
		simulation.stopSimulation();
		simulation.clearActions();
		algorithm.stop();
	}

	@Override
	public void startSimulation(List<Action> actions) {
		try {
			simulation = new Simulation(this);
			simulation.addAction(actions);
			simulation.start();
		} catch (IllegalActionException e) {
			e.printStackTrace();
		}
	}
}
