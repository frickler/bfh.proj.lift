package logic;

import java.util.ArrayList;
import java.util.List;

import definition.Action;
import definition.ActionObserver;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.HorizontalTransporter;

public class ElevatorController implements Controller {

	// Building attached to the controller
	private Building building;
	private Algorithm algorithm;

	/**
	 * Constructor for the controller. Each controller is responsible for
	 * exactly one building.
	 * 
	 * @param building
	 *            The attached building
	 */
	public ElevatorController(Building building, Algorithm algorithm) {
		super();
		this.building = building;
		this.algorithm = algorithm;
	}

	/**
	 * 
	 * @return The attached building
	 */
	public Building getBuilding() {
		return building;
	}

	@Override
	public void performAction(Action action) {
		// ToDo: Etagen checken, Personen, etc.
		algorithm.performAction(action);
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

}
