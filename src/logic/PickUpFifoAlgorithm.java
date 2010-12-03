package logic;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.VerticalTransporter;

/**
 * This algorithm is similar to the FifoAlgorithm, with the slight difference
 * that people with the same start level can enter the elevator as well. So
 * people who arrive late in a floor can profite from a person who pressed the
 * elevator button erlier. TODO Better description
 * 
 * @author krigu
 * 
 */
public class PickUpFifoAlgorithm extends Algorithm {

	public PickUpFifoAlgorithm(Building building, Controller controller) {
		super(building, controller);
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		setRunning(true);
		while (isRunning()) {
			Action action = getController().getActionWithHighestPriority();
			while (action != null) {
				for (VerticalTransporter i : getBuilding().getElevators()) {

					Elevator ele = (Elevator) i;
					// Look for a non-busy elevator with a fitting range
					// (MinLevel & MaxLevel)
					if (!i.isBusy()
							&& ele.getMinLevel() <= action.getStartLevel()
							&& ele.getMaxLevel() >= action.getStartLevel()
							&& ele.getMinLevel() <= action.getEndLevel()
							&& ele.getMaxLevel() >= action.getEndLevel()) {
						ele.move(action);
						action = null;
						break;
					}
				}
				// No elevator is free.. wait
				hold();
			}
			// No action is available.. wait
			hold();
		}

	}

	private void hold() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

}
