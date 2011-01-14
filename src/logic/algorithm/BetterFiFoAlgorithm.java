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
public class BetterFiFoAlgorithm extends Algorithm {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	/**
	 * 
	 * @param building
	 *            the algorithm should work with
	 * @param controller
	 *            holding the list of actions
	 */
	public BetterFiFoAlgorithm(Building building, Controller controller) {
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
				Elevator ele = getController().getClosestFreeElevator(action);
				if (ele != null) {
					ele.move(action);
					action = null;
					break;
				}
				// No elevator is free.. wait
				hold();
			}
			// No action is available.. wait
			hold();
		}
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
