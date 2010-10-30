package definition;

import java.util.ArrayList;
import java.util.List;

public abstract class IBuilding {

	// all elevators of the building
	private List<ILiftable> elevators;
	
	public IBuilding(ILiftable elevator) throws Exception{
		elevators = new ArrayList<ILiftable>();		
		elevators.add(elevator);
	}
	
	public void addElevator(ILiftable elevator) throws Exception{
		if(elevator == null){
			throw new Exception("elevator object is empty");
		}
		elevators.add(elevator);
	}
	
	public void removeElevator(ILiftable elevator){
		elevators.remove(elevator);
	}
	
	public List<ILiftable> getElevators(){
		return elevators;
	}
	
	public int getMinLevel(){
		int minLevel = 100000;
		for(ILiftable e : elevators){
			if(e.getMinLevel() < minLevel){
				minLevel = e.getMinLevel();
			}
		}
		return minLevel;
	}
	
	public int getMaxLevel(){
		int maxLevel = 0;
		for(ILiftable e : elevators){
			if(e.getMaxLevel() > maxLevel){
				maxLevel = e.getMaxLevel();
			}
		}
		return maxLevel;
	}	
}
