package Logic;

import Interface.IBuilding;
import Interface.IElevator;

public class Building extends IBuilding {

	public Building(IElevator elevator) throws Exception {
		super(elevator);
	}

}
