package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.ElevatorAction;
import logic.Tower;
import logic.ElevatorController;
import logic.Elevator;
import logic.FiFoAlgorithm;

import org.junit.Test;

import definition.ActionObserver;
import definition.Action;
import definition.Building;
import definition.HorizontalTransporter;

public class ControllerTest {

	@Test
	public void controllerTest() throws Exception {
		
		final List<ElevatorAction> actions = new ArrayList<ElevatorAction>();
		
		// Create building
		Building building = new Tower(new Elevator(0, 25, 20, 0));
		// add two elevators
		building.addElevator(new Elevator(0, 10, 10, 0));
		building.addElevator(new Elevator(0, 10, 10, 0));
		building.addElevator(new Elevator(0, 10, 10, 0));
		building.addElevator(new Elevator(0, 10, 10, 0));
		// Create algorithm
		FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		ElevatorController c = new ElevatorController(building, fifo);
		
		c.addActionObserver(new ActionObserver() {
			
			@Override
			public void actionStarted(Elevator elevator, Action action) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionPerformed(Elevator elevator, Action action) {
				actions.remove(action);				
			}
		});
		
		System.out.println("Startcontroller");
		c.startController();
		
		int totalPeople = 0;
		Random gen = new Random( (int) (Math.random() * 10000));		
		for (int i = 0; i < 25; i++) {
			int start = gen.nextInt(15) + 1;
			int end = gen.nextInt(15) + 1;
			int people = gen.nextInt(5) + 1;
			ElevatorAction act = new ElevatorAction(start, end, people);
			c.performAction(act);
			totalPeople += people;
			actions.add(act);
			//System.out.println("start: " + start + " end: " + end + " people: " + people);
		}

		while (!actions.isEmpty()){
			Thread.sleep(100);
		}
		
		
		c.stopController();

		for (HorizontalTransporter lift : building.getElevators()) {
			Elevator l = (Elevator) lift;

			System.out.println(l.hashCode() + " " + l.toString());
		}
		System.out.println("Done. Transported people: " + totalPeople);
	}

}
