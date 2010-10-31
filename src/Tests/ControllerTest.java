package Tests;

import java.util.Date;

import logic.Action;
import logic.Building;
import logic.Controller;
import logic.FiFoAlgorithm;
import logic.Elevator;

import org.junit.Test;

import definition.IBuilding;
import definition.ILiftable;


public class ControllerTest {
	
	@Test
	public void controllerTest() throws Exception{
		// Create building
		IBuilding building = new Building(new Elevator(0, 25, 20, 0));
		// add two elevators
		building.addElevator(new Elevator(0, 10, 10, 0));		
		building.addElevator(new Elevator(0, 10, 10, 0));
		// Create algorithm
		FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		Controller c = new Controller(building,fifo);
		
		// Create action
		Action a = new Action(5,10,5);		
		a.setTimestampEnded(new Date());
		c.performAction(a);
		
		System.out.println("Startcontroller");
		c.startController();
		System.out.println("/Startcontroller");
		Action b = new Action(2,12,3);		
		b.setTimestampEnded(new Date());
		c.performAction(b);		
		
		c.stopController();
		
		Thread.sleep(5000);
		
		for (ILiftable lift : building.getElevators()){
			Elevator l = (Elevator) lift;
			
			System.out.println( l.toString() );			
		}
	}

}
