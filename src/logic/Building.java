package logic;

import definition.IBuilding;
import definition.ILiftable;

public class Building extends IBuilding {

	public Building(ILiftable elevator) throws Exception {
		super(elevator);
	}

}
