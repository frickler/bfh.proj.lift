package logic;

import java.util.LinkedList;
import java.util.Queue;

import definition.IAction;
import definition.IAlgorithm;
import definition.IBuilding;
import definition.ILiftable;

public class FiFoAlgorithm extends IAlgorithm{
	
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
		while (!queue.isEmpty()){			
			for (ILiftable i : getBuilding().getElevators()){
				IAction action = queue.poll();
				if (action == null){ break; }
				SimpleElevator ele = (SimpleElevator) i;
				if (!i.isBusy()){		
					moveElevator(ele);
				}				
			}			
		}
		
	}

	@Override
	protected void moveElevator(ILiftable lift) {
		lift.setBusy(true);
		
	}

}
