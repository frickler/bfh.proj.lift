package logic;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import definition.IAction;
import definition.IAlgorithm;
import definition.IBuilding;
import definition.ILiftable;

public class FiFoAlgorithm extends IAlgorithm {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private Queue<IAction> queue = new LinkedList<IAction>();

	public FiFoAlgorithm(IBuilding building) {
		super(building);
	}

	@Override
	public void performAction(IAction action) {
		queue.add(action);
		log4j.debug("adding action. queue.size: " + queue.size() + " action: " + action);
	}

	@Override
	/**
	 * ToDo: Thread save
	 */
	public void run() {
		setRunning(true);
		while (isRunning()) {
			while (!queue.isEmpty()) {
				for (ILiftable i : getBuilding().getElevators()) {
					IAction action = queue.peek();
					if (action == null) {
						break;
					}
					Elevator ele = (Elevator) i;
					if (!i.isBusy()) {
						new Movement(ele, action).start();
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
