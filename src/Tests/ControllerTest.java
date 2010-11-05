package Tests;

import java.util.Random;

import logic.Action;
import logic.Building;
import logic.Controller;
import logic.Elevator;
import logic.FiFoAlgorithm;

import org.junit.Test;

import definition.IBuilding;
import definition.ILiftable;

public class ControllerTest {

	@Test
	public void controllerTest() throws Exception {
		// Create building
		IBuilding building = new Building(new Elevator(0, 25, 20, 0));
		// add two elevators
		building.addElevator(new Elevator(0, 10, 10, 0));
		building.addElevator(new Elevator(0, 10, 10, 0));
		// Create algorithm
		FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		Controller c = new Controller(building, fifo);
		
		System.out.println("Startcontroller");
		c.startController();
		
//		// Create action
//		Action act1 = new Action(5, 10, 5);
//		act1.setTimestampEnded(new Date());
//		c.performAction(act1);
		

//		Action act2 = new Action(2, 12, 3);
//		act2.setTimestampEnded(new Date());
//		c.performAction(act2);
//
//		Action act3 = new Action(8, 3, 3);
//		act3.setTimestampEnded(new Date());
//		c.performAction(act3);
//
//		Action act4 = new Action(8, 1, 5);
//		act4.setTimestampEnded(new Date());
//		c.performAction(act4);
//
//		Action act5 = new Action(8, 1, 5);
//		act5.setTimestampEnded(new Date());
//		c.performAction(act5);
		int totalPeople = 0;
		Random gen = new Random( (int) (Math.random() * 10000));		
		for (int i = 0; i < 15; i++) {
			int start = gen.nextInt(15) + 1;
			int end = gen.nextInt(15) + 1;
			int people = gen.nextInt(5) + 1;
			Action act = new Action(start, end, people);
			c.performAction(act);
			totalPeople += people;
			//System.out.println("start: " + start + " end: " + end + " people: " + people);
		}

		Thread.sleep(30000);
		c.stopController();

		for (ILiftable lift : building.getElevators()) {
			Elevator l = (Elevator) lift;

			System.out.println(l.hashCode() + " " + l.toString());
		}
		System.out.println("Done. Transported people: " + totalPeople);
	}

}
