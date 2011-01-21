package logic.algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import logic.AlgorithmListener;
import logic.Elevator;
import logic.Movement;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.Direction;
import definition.VerticalTransporter;

/**
 * This algorithm is similar to the PickupFifo Algorithm. But instead of the
 * first elevator available the closest gets selected to perform an action
 * 
 * 
 * @author BFH-Boys
 * 
 */
public class EveryLevelPickUpAlgorithm extends Algorithm {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");
	

	public EveryLevelPickUpAlgorithm(Building building, Controller controller) {
		super(building, controller);
		log4j.debug("EveryLevelPickUpAlgorithm");
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

					ele.move(acts, action.getStartLevel(), dir,new AlgorithmListener() {
						
						
						public List<Action> actionPerformed(int currentLevel, int minmaxLevel, int remaningCapacity) {
							
							return getController().getActions(currentLevel,minmaxLevel,remaningCapacity);
						}
					});

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
	/*
	@Override
	public List<Action> getActions(int currentLevel, int minmaxLevel, int remaningCapacity) {		
		List<Action> acts = 
		return acts;
	}
	*/

}
