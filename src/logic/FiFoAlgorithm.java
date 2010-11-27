package logic;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.Controller;
import definition.HorizontalTransporter;

public class FiFoAlgorithm extends Algorithm {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	

	public FiFoAlgorithm(Building building, Controller controller) {
		super(building, controller);
	}

	/**
	 * Do not call this method directly!
	 */
	@Override
	public void run() {
		setRunning(true);
		while (isRunning()) {
			Action action = getController().getActionWithHighestPriority(); 
			if (action != null) {
				for (HorizontalTransporter i : getBuilding().getElevators()) {

					Elevator ele = (Elevator) i;
					// Look for a non-busy elevator with a fitting range
					// (MinLevel & MaxLevel)
					if (!i.isBusy()
							&& ele.getMinLevel() <= action.getStartLevel()
							&& ele.getMaxLevel() >= action.getStartLevel()
							&& ele.getMinLevel() <= action.getEndLevel()
							&& ele.getMaxLevel() >= action.getEndLevel()) {
						ele.move(action);
						break;
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}

		}

	}

}
