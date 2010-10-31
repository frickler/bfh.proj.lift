package logic;

import java.util.LinkedList;
import java.util.Queue;

import definition.IAction;
import definition.IAlgorithm;
import definition.IBuilding;
import definition.ILiftable;

public class FiFoAlgorithm extends IAlgorithm {

	private Queue<IAction> queue = new LinkedList<IAction>();

	public FiFoAlgorithm(IBuilding building) {
		super(building);
	}

	@Override
	public void performAction(IAction action) {
		queue.add(action);
		System.out.println(action.toString());
	}

	@Override
	public void run() {
		setRunning(true);		
		while (isRunning()) {		
			System.out.println("isRunning");
			while (!queue.isEmpty()) {
				for (ILiftable i : getBuilding().getElevators()) {
					IAction action = queue.poll();
					if (action == null) {
						break;
					}
					Elevator ele = (Elevator) i;
					if (!i.isBusy()) {
						if (ele.getCurrentLevel() != action.getStartLevel()) {
							// Move elevator to startlevel
							new Movement(ele, new Action(
									(int) ele.getCurrentLevel(),
									action.getStartLevel(), 0)).start();
							// Move elevator to endlevel
							new Movement(ele, action).start();
						}

					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {	}
			
		}

	}

}
