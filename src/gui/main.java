package gui;

import logic.*;
import definition.*;
import gui.FrameMain;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	
	public static void testGui(){
		try {
			IBuilding building = new Building(new Elevator(1, 5, Integer.MAX_VALUE));
			building.addElevator(new Elevator(1, 5, Integer.MAX_VALUE));
			building.addElevator(new Elevator(-3, 10, Integer.MAX_VALUE));

			FrameMain frame = new FrameMain(building);
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}
	}

}
