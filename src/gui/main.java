package gui;

import logic.*;
import definition.*;
import gui.FrameMain;

/**
 * 
 * @author BFH-Boys
 *
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testGui();
	}
	
	public static void testGui(){
		try {
			//TODO Auswahldialog für XML-Datei einblenden
			Building building = new Tower(new Elevator(1, 7, Integer.MAX_VALUE, 1));
			building.addElevator(new Elevator(1, 5, Integer.MAX_VALUE, 2));
			building.addElevator(new Elevator(-3, 3, Integer.MAX_VALUE, -2));

			ElevatorController controller = new ElevatorController(building, new FiFoAlgorithm(building));
			controller.startController();

			Thread t = new Thread(new FrameMain(building, controller));
			t.start();
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}
	}

}
