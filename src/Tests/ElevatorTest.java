package Tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import logic.Elevator;
import logic.ElevatorAction;

import org.junit.Test;

import definition.Action;
import definition.ActionObserver;
import definition.VerticalTransporter;
import exceptions.IllegalRangeException;
import exceptions.IllegalStartLevelException;

public class ElevatorTest {

	/**
	 * Tests if a invalid Range (MinLevel > MaxLevel) throws an
	 * IllegalRangeException
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalRangeException.class)
	public void rangeExceptionTest() throws Exception {
		new Elevator(10, 0, 10, 0);
	}

	/**
	 * Tests if a invalid startLevel (startLevel > MaxLevel or startLevel <
	 * Minlevel) throws an IllegalStartLevelException
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalStartLevelException.class)
	public void startLevelExceptionTest() throws Exception {
		new Elevator(0, 100, 10, -1);
	}

	@Test
	public void constructorTest() throws Exception {
		Elevator e = new Elevator(-10, 100, 10, 50);

		assertEquals("MaxLevel", 100, e.getMaxLevel());
		assertEquals("MinLevel", -10, e.getMinLevel());
		assertEquals("Current level", 50, e.getCurrentLevel());
		assertEquals("Moved levels", 0, e.getDrivenLevels());
		assertEquals("Moved levels empty", 0, e.getDrivenLevelsEmpty());
		assertEquals("Moved persons", 0, e.getTransportedPeople());

	}

	/**
	 * Tests if the elevators statistics are updated after a move
	 * 
	 * @throws Exception
	 */
	@Test
	public void movementTest() throws Exception {

		final int currentLevel = 10;
		final int targetLevel = 4;
		final int startLevel = 12;

		System.out.println("Start in Level " + currentLevel
				+ ". get pepole in level " + startLevel
				+ " and bring them to level " + targetLevel);

		final Action a = new ElevatorAction(startLevel, targetLevel, 5);
		final VerticalTransporter e = new Elevator(0, 40, 6, currentLevel);

		a.setTimestampElevatorEntered(new Date(System.currentTimeMillis()));
		e.move(a);

		a.addActionObserver(new ActionObserver() {

			@Override
			public void actionStarted(Action action) {
				assertEquals(a, action);
			}

			@Override
			public void actionPerformed(Action action) {
				assertEquals("Current level", targetLevel, e.getCurrentLevel());
				assertEquals("Moved levels", e.getDrivenLevels(), startLevel
						- currentLevel + (startLevel - targetLevel));
				assertEquals("Moved levels empty", e.getDrivenLevelsEmpty(),
						startLevel - currentLevel);
				assertEquals("Moved persons", e.getTransportedPeople(), 5);
			}

			@Override
			public void actionPeopleLoaded(Action action) {
				

			}
		});

	}

}
