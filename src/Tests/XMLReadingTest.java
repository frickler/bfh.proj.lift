package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import logic.ElevatorActionXMLReader;

import org.junit.Test;

import definition.Action;
import definition.Building;


public class XMLReadingTest {

	@Test
	public void TestBuilding() {
		String strXML = getXMLString();
		ElevatorActionXMLReader reader = new ElevatorActionXMLReader();

		try {
			reader.readXMLStructure(strXML);

			Building b = reader.getBuilding();
			assertNotNull(b);
			assertEquals(3, b.getElevators().size());
			assertEquals(1, b.getElevators().get(0).getMinLevel());
			assertEquals(2, b.getElevators().get(0).getMaxLevel());
			assertEquals(4, b.getElevators().get(0).getMaxPeople());

			assertEquals(-2, b.getElevators().get(2).getMinLevel());
			assertEquals(3, b.getElevators().get(2).getMaxLevel());
			assertEquals(6, b.getElevators().get(2).getMaxPeople());

		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void TestActions() {
		String strXML = getXMLString();
		ElevatorActionXMLReader reader = new ElevatorActionXMLReader();

		try {
			reader.readXMLStructure(strXML);

			List<Action> a = reader.getActions();
			assertNotNull(a);
			assertEquals(3, a.size());
			assertEquals(1, a.get(0).getStartLevel());
			assertEquals(5, a.get(0).getEndLevel());
			assertEquals(3, a.get(0).getPeopleAmount());

			assertEquals(2, a.get(2).getStartLevel());
			assertEquals(3, a.get(2).getEndLevel());
			assertEquals(4, a.get(2).getPeopleAmount());

		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	private String getXMLString() {

		String sXML = "<?xml version=\"1.0\" ?>"
				+ "<ElevatorSettings>"
				+ "<Building>"
				+ "<Elevator minLevel=\"1\" maxLevel=\"2\" maxPeople=\"4\" currentLevel=\"4\" />"
				+ "<Elevator minLevel=\"0\" maxLevel=\"10\" maxPeople=\"8\" currentLevel=\"1\" />"
				+ "<Elevator minLevel=\"-2\" maxLevel=\"3\" maxPeople=\"6\" currentLevel=\"0\" />"
				+ "</Building>"
				+ "<Actions>"
				+ "<Action startLevel=\"1\" endLevel=\"5\" peopleAmount=\"3\" />"
				+ "<Action startLevel=\"6\" endLevel=\"4\" peopleAmount=\"1\" />"
				+ "<Action startLevel=\"2\" endLevel=\"3\" peopleAmount=\"4\" />"
				+ "</Actions>" + "</ElevatorSettings>";
		return sXML;
	}
}