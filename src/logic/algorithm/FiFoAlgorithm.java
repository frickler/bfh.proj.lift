package logic.algorithm;

import logic.Elevator;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.VerticalTransporter;

/**
 * This algorithm is a simple FIFO implementation. Each elevator gets the action
 * with the highest priority and delivers the people to the according level.
 * 
 * @author BFH-Boys
 * 
 */
public class FiFoAlgorithm extends Algorithm {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	/**
	 * 
	 * @param building the algorithm should work with
	 * @param controller holding the list of actions
	 */
	public FiFoAlgorithm(Building building, Controller controller) {
		super(building, controller);
	}

	/**
	 * Starts the processing of the actions
	 */
	@Override
	public void run() {
		setRunning(true);
		while (isRunning()) {
			Action action = getController().getActionWithHighestPriority();
			while (action != null) {
				for (VerticalTransporter i : getBuilding().getElevators()) {

					Elevator ele = (Elevator) i;
					// Look for a non-busy elevator with a fitting range (MinLevel & MaxLevel)
					if (ele.canPerformAction(action)) {
						checkCombination(ele, action);
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
		log4j.debug(" run() left!");
		this.isEnded = true;
	}

	/**
	 * Waits for 100 milliseconds
	 */
	private void hold() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

}
