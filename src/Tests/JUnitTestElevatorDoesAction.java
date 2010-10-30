package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Interface.*;
import Logic.*;

public class JUnitTestElevatorDoesAction {

	//@Test
	public void TestElevator() throws Exception {

		int currentLevel = 10;
		int targetLevel = 4;
		int startLevel = 12;
		
		System.out.println("Start in Level "+currentLevel+". get pepole in level "+startLevel+" and bring dem to level "+targetLevel);
		
		IAction a = new Action(startLevel, targetLevel, 5);
		IElevator e = new Elevator(0, 40, 6, currentLevel);
		Thread th = new Thread(e);
		TestActionListener l = new TestActionListener();
		e.setCurrentAction(a);
		e.addObservers(l);
		th.start();

		System.out.println("Elevator is in Level: ");
		while (e.isRunning()) {
			System.out.print(" "+e.getCurrentLevel());
			if (l.actionDone) {
				throw new Exception("action cannot be done at this time");
			}
			Thread.sleep(1000);
		}
		assertFalse(e.isRunning());
		assertTrue(l.actionDone);
		
		System.out.println("Elevator is in Level:" + e.getCurrentLevel());
		System.out.println("end");

	}
	
	@Test
	public void TestElevatorTwoActions() throws Exception {

		int currentLevel = 10; //(lift)
		
		int targetLevel = 11;
		int startLevel = 12;
		
		System.out.println("Start in Level "+currentLevel+". get pepole in level "+startLevel+" and bring dem to level "+targetLevel);
		
		IAction a = new Action(startLevel, targetLevel, 5);
		IElevator e = new Elevator(0, 40, 6, currentLevel);
		Thread th = new Thread(e);
		TestActionListener l = new TestActionListener();
		e.setCurrentAction(a);
		e.addObservers(l);
		th.start();
		System.out.println("Elevator is in Level: ");
		while (e.isRunning()) {
			System.out.print(" "+e.getCurrentLevel());
			if (l.actionDone) {
				throw new Exception("action cannot be done at this time");
			}
			Thread.sleep(1000);
		}
		assertFalse(e.isRunning());
		assertTrue(l.actionDone);
		
		System.out.println("Elevator is in End-Level:" + e.getCurrentLevel());

		targetLevel = 9;
		startLevel = 10;
		currentLevel = (int) e.getCurrentLevel();
		
		System.out.println("Start in Level "+currentLevel+". get pepole in level "+startLevel+" and bring dem to level "+targetLevel);
		
		/* tut noch nicht so richtig.
		 */
		IAction a2 = new Action(startLevel, targetLevel, 5);
		e.setCurrentAction(a);
		e.addObservers(l);
		th.run();
		Thread.sleep(1000);
		System.out.println("2. Elevator is in Level: ");
		while (e.isRunning()) {
			System.out.print(" "+e.getCurrentLevel());
			if (l.actionDone) {
				throw new Exception("2. action cannot be done at this time");
			}
			Thread.sleep(1000);
		}
		assertFalse(e.isRunning());
		assertTrue(l.actionDone);
		
		
		System.out.println("2. Elevator is in End-Level:" + e.getCurrentLevel());
		
	}

}
