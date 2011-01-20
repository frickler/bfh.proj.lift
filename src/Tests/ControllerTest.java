package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import logic.Elevator;
import logic.ElevatorAction;
import logic.ElevatorController;
import logic.Tower;

import org.junit.Test;

import definition.Action;
import definition.Building;
import exceptions.MaxLevelActionException;
import exceptions.MinLevelActionException;

public class ControllerTest {
	
	/**
	 * Tests if an Action can be passed to the algorithm with illegal floors
	 * (a destination lower than the lowest floor in the bulding)
	 * 
	 * @throws Exception
	 */
	@Test
	public void highestActionTest() throws Exception {
		
		// Create building
		Building building = new Tower(new Elevator(0, 25, 5, 0));
		// add for elevators
		building.addElevator(new Elevator(5, 15, 5, 5));
		building.addElevator(new Elevator(0, 20, 5, 5));
		building.addElevator(new Elevator(1, 11, 5, 5));
		building.addElevator(new Elevator(0, 20, 5, 5));
		
		// Create algorithm
		//FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		ElevatorController c = new ElevatorController(building, "FiFoAlgorithm");
		
		// A valid action
		ElevatorAction act1 = new ElevatorAction(0, 15, 15);
		c.performAction(act1);
		// A invalid action with a invalid startLevel
		ElevatorAction act2 = new ElevatorAction(4, 10, 15);
		c.performAction(act2);
		
		// Assert the order of the actions
		assertEquals("Highest Action", act1, c.getActionWithHighestPriority());
		assertEquals("Highest Action", act2, c.getActionWithHighestPriority());
		
		// Perform a group of actions
		ElevatorAction actDown = new ElevatorAction(3,1,2);
		ElevatorAction act3 = new ElevatorAction(3,4,2);
		ElevatorAction act4 = new ElevatorAction(3,5,2);
		ElevatorAction act5 = new ElevatorAction(3,6,2);
		ElevatorAction act6 = new ElevatorAction(3,7,2);
		ElevatorAction act7 = new ElevatorAction(3,8,2);
		// add actions to controller
		c.performAction(actDown);
		c.performAction(act3);
		c.performAction(act4);
		c.performAction(act5);
		c.performAction(act6);
		c.performAction(act7);
		
		
		List<Action> action = c.getActions(3, 8, 7);
		assertTrue("4 actions in collection", action.size() == 4);		
		assertTrue("act3 in collection", action.contains(act3));
		assertEquals("act3 two people", 2, act3.getPeopleAmount());
		//  check action 4
		assertTrue("act4 in collection", action.contains(act4));
		assertEquals("act4 two people", 2, act4.getPeopleAmount());
		// check action 5
		assertTrue("act5 in collection", action.contains(act5));
		assertEquals("act5 two people", 2, act5.getPeopleAmount());
		// check action 6
		assertTrue("act6 in collection", action.contains(act6));
		assertEquals("act6 with one person", 1, act6.getPeopleAmount());	
		// check that action 7 is not in the collection
		assertTrue("act7 not in collection", !action.contains(act7));
		// check that the action which moves down is not in the collection
		assertTrue("actDown not in collection", !action.contains(actDown));
		// actDown should be next
		assertEquals("Controller should return actDown", actDown, c.getActionWithHighestPriority());
		// should return the rest of the splited action (act7)
		c.getActionWithHighestPriority();
		Action splited = c.getActionWithHighestPriority();
		System.out.println(splited);
		assertEquals("act7 should have 1 person", 1, splited.getPeopleAmount());
		
		c.getActionWithHighestPriority();
		
		
	}
	
	/**
	 * Tests if an Action can be passed to the algorithm with illegal floors
	 * (a destination lower than the lowest floor in the bulding)
	 * 
	 * @throws Exception
	 */
	@Test(expected= MaxLevelActionException.class)
	public void maxLevelTest() throws Exception {
		
		// Create building
		Building building = new Tower(new Elevator(0, 25, 20, 0));
		// add for elevators
		building.addElevator(new Elevator(5, 15, 10, 0));
		building.addElevator(new Elevator(-5, 20, 10, 0));
		building.addElevator(new Elevator(1, 11, 10, 0));
		building.addElevator(new Elevator(20, 40, 10, 0));
		
		// Create algorithm
		//FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		ElevatorController c = new ElevatorController(building, "FiFoAlgorithm");
		
		// A valid action
		ElevatorAction action = new ElevatorAction(0, 15, 15);
		c.performAction(action);
		// A invalid action with a invalid startLevel
		ElevatorAction act2 = new ElevatorAction(4, 1150, 15);
		c.performAction(act2);
		
	}

	/**
	 * Tests if an Action can be passed to the algorithm with illegal floors
	 * (a destination lower than the lowest floor in the bulding)
	 * 
	 * @throws Exception
	 */
	@Test(expected= MinLevelActionException.class)
	public void minLevelTest() throws Exception {
		
		// Create building
		Building building = new Tower(new Elevator(0, 25, 20, 0));
		// add for elevators
		building.addElevator(new Elevator(5, 15, 10, 0));
		building.addElevator(new Elevator(-5, 20, 10, 0));
		building.addElevator(new Elevator(1, 11, 10, 0));
		building.addElevator(new Elevator(20, 40, 10, 0));
		
		// Create algorithm
		//FiFoAlgorithm fifo = new FiFoAlgorithm(building);
		// Create controller
		ElevatorController c = new ElevatorController(building, "FiFoAlgorithm");
		
		// A valid action
		ElevatorAction action = new ElevatorAction(0, 15, 15);
		c.performAction(action);
		// A invalid action with a invalid startLevel
		ElevatorAction act2 = new ElevatorAction(-100, 15, 15);
		c.performAction(act2);	
	}

}
