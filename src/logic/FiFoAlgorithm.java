package logic;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Algorithm;
import definition.Building;
import definition.HorizontalTransporter;

public class FiFoAlgorithm extends Algorithm {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private Queue<Action> queue = new LinkedList<Action>();

	public FiFoAlgorithm(Building building) {
		super(building);
	}

	@Override
	public void performAction(Action action) {
		queue.add(action);
		log4j.debug("adding action. queue.size: " + queue.size() + " action: " + action);
	}

	/**
	 * Do not call this method directly! Call start() instead.
	 */
	@Override
	public void run() {		
		setRunning(true);
		while (isRunning()) {
			while (!queue.isEmpty()) {
				for (HorizontalTransporter i : getBuilding().getElevators()) {
					Action action = queue.peek();
					if (action == null) {
						break;
					}
					Elevator ele = (Elevator) i;
					if (!i.isBusy()) {
						ele.move(action);
						// remove element from queue
						queue.poll();
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