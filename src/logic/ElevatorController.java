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
import definition.VerticalTransporter;
import exceptions.IllegalActionException;
import exceptions.MaxLevelActionException;
import exceptions.MinLevelActionException;

public class ElevatorController implements Controller {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	// Building attached to the controller2144398321443983
	private Building building;
	private Algorithm algorithm;
	private List<Action> actions;
	private List<Action> doneActions;
	private Simulation simulation;
	private int simulationSpeed = 1;

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
		this.doneActions = new ArrayList<Action>();
		this.building = building;
		this.simulation = new Simulation(this);
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

		action.addActionObserver(new ActionObserver() {

			@Override
			public void actionStarted(Action action) {
				// TODO Auto-generated method stub

			}

			@Override
			public void actionPerformed(Action action) {
				doneActions.add(action);

			}

			@Override
			public void actionPeopleLoaded(Action action) {
				// TODO Auto-generated method stub

			}
		});

		log4j.debug("Adding action " + action);

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
		return doneActions;
	}

	@Override
	public void addDoneAction(Action a) {
		this.actions.add(a);
	}

	@Override
	// TODO Clone list
	public void addElevator(Elevator e) {
		e.setSimulationSpeed(this.simulationSpeed);
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
	public void startSimulation(String path,int speed,List<Action> actions) {
		try {
			simulationSpeed = speed;
			simulation = new Simulation(this);
			simulation.setSimulationSpeed(this.simulationSpeed);
			simulation.setPath(path);
			building.setSimulationSpeed(simulationSpeed);
			simulation.addAction(actions);
			simulation.start();
		} catch (IllegalActionException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see definition.Controller#setSimulationSpeed(int)
	 * sets the speed only if valid 0 < speed <= 100
	 */
	public void setSimulationSpeed(int speed) {

		if (speed > 0 && speed <= 100) {
			simulationSpeed = speed;
			if (simulation != null) {
				simulation.setSimulationSpeed(speed);
			}
			building.setSimulationSpeed(speed);
		}

	}

	/*
	 * Reset the statistics of each elevator
	 */
	public void resetLiftEvaluation() {
		for (VerticalTransporter t : building.getElevators()) {
			t.resetStatistics();
		}
	}

	/*
	 * Clears the list where all done actions are stored for evaluation
	 */
	public void resetDoneActions() {
		doneActions.clear();
	}

	/*
	 * Clears all actions with aren't perfom yet
	 */
	public void resetActions() {
		actions.clear();
	}


	@Override
	public Simulation getSimulation() {
		return simulation;
	}

	@Override
	public int getSimluationSpeed() {
		return this.simulationSpeed;
	}

	@Override
	public String getAlgorithmName() {
		if(this.algorithm != null)
		return algorithm.getClass().toString();
		return "";
	}

	@Override
	public int getTodoActionsAmount() {
		// TODO Auto-generated method stub
		return this.actions.size();
	}
}
