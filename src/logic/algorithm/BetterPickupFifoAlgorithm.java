package logic.algorithm;

import java.util.List;

import logic.Elevator;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.Direction;

/**
 * This algorithm is similar to the PickupFifo Algorithm. But instead of the
 * first elevator available the closest gets selected to perform an action
 * 
 * 
 * @author BFH-Boys
 * 
 */
public class BetterPickupFifoAlgorithm extends Algorithm {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	public BetterPickupFifoAlgorithm(Building building, Controller controller) {
		super(building, controller);
		log4j.debug("BetterPickupFifoAlgorithm");
	}

	/**
	 * Starts the processing of the actions
	 */
	@Override
	public void run() {
		log4j.debug(" run() called!");
		setRunning(true);
		log4j.debug("should run now: "+this.isRunning());
		while (isRunning()) {
			Action action = getController().getActionWithHighestPriority();
			while (action != null && isRunning()) {
				Elevator ele = getController().getClosestFreeElevator(action);
				if (ele != null) {
					Direction dir = Direction.UP;
					if (action.getStartLevel() > action.getEndLevel()) {
						dir = Direction.DOWN;
					}
					checkCombination(ele, action);
					List<Action> acts = getController().getActions(
							action.getStartLevel(), action.getEndLevel(),
							ele.getMaxPeople() - action.getPeopleAmount());
					acts.add(action);
					log4j.debug("Pickup Action Size" + acts.size());

					ele.move(acts, action.getStartLevel(), dir);

					action = null;
					break;
					
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
	 * Waits for 100 milliseconds to continue
	 */
	private void hold() {
		try {
			Thread.sleep(100); // todo too looong?
		} catch (InterruptedException e) {
		}
	}

}
