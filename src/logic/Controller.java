package logic;

import java.util.HashSet;
import java.util.Set;

import definition.IAction;
import definition.IActionListener;
import definition.IAlgorithm;
import definition.IBuilding;
import definition.IController;

public class Controller implements IController{

	private Set<IActionListener> listeners = new HashSet<IActionListener>();
	// Building attached to the controller
	private IBuilding building;
	private IAlgorithm algorithm;
		
	/**
	 * Constructor for the controller. Each controller is
	 * responsible for exactly one building.
	 * 
	 * @param building The attached building
	 */
	public Controller(IBuilding building, IAlgorithm algorithm) {
		super();
		this.building = building;
		this.algorithm = algorithm;
	}

	/**
	 * 
	 * @return The attached building
	 */
	public IBuilding getBuilding() {
		return building;
	}
	
	@Override
	public void addActionListener(IActionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeActionListener(IActionListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void performAction(IAction action) {
		// ToDo: Etagen checken, Personen, etc.
		algorithm.performAction(action);		
	}

	@Override
	public void startController() {
		algorithm.run();
		
	}

	@Override
	public void stopController() {
		// ToDo: Implementation
		
	}

}
