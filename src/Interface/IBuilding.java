package Interface;

import java.util.ArrayList;
import java.util.List;

public abstract class IBuilding {

	// all elevators of the building
	private List<IElevator> elevators;
	
	public IBuilding(IElevator elevator) throws Exception{
		elevators = new ArrayList<IElevator>();
		addElevator(elevator);
	}
	
	public void addElevator(IElevator elevator) throws Exception{
		if(elevator == null){
			throw new Exception("elevator object is empty");
		}
		elevators.add(elevator);
	}
	
	public void removeElevator(IElevator elevator){
		elevators.remove(elevator);
	}
	
	public List<IElevator> getElevators(){
		return elevators;
	}
	
	public int getMinLevel(){
		int minLevel = 100000;
		for(IElevator e : elevators){
			if(e.getMinLevel() < minLevel){
				minLevel = e.getMinLevel();
			}
		}
		return minLevel;
	}
	
	public int getMaxLevel(){
		int maxLevel = 0;
		for(IElevator e : elevators){
			if(e.getMaxLevel() > maxLevel){
				maxLevel = e.getMaxLevel();
			}
		}
		return maxLevel;
	}

	public List<IElevator> getElevators(){
		return this.elevators;
	}
	
}
