package gui;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import logic.*;
import definition.*;
import gui.FrameMain;

/**
 * 
 * @author BFH-Boys
 *
 */
public class main {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) {
		runTestConfig();
		//runApplication();
	}
	
	
	public static void runTestConfig(){
		testGui();
	}
	
	public static void testGui(){
		try {
			Building building = new Tower(new Elevator(1, 3, 10, 1));
			building.addElevator(new Elevator(1, 3, Integer.MAX_VALUE, 3));
			building.addElevator(new Elevator(1, 3, Integer.MAX_VALUE,3));
			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));

//			
//			Building building = new Tower(new Elevator(1, 3, Integer.MAX_VALUE, 1));
//			building.addElevator(new Elevator(1, 3, Integer.MAX_VALUE, 3));
//			building.addElevator(new Elevator(1, 3, Integer.MAX_VALUE,3));
//			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
//			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
//			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
//			building.addElevator(new Elevator(-1, 6, Integer.MAX_VALUE,5));
			

			ElevatorController controller = new ElevatorController(building, PickUpFifoAlgorithm.class);
			controller.startController();

			Thread t = new Thread(new FrameMain(building, controller));
			t.start();
		} catch (Exception ex) {
			log4j.error(ex);
		}
	}

}
