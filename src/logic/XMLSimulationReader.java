package logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

import definition.Action;
import definition.Building;
import definition.VerticalTransporter;
import definition.XMLReader;

public class XMLSimulationReader extends XMLReader {

	private DocumentBuilder builder;
	private Document document;
	private List<Action> actions;
	private Building building;
	private int simulationSpeed = 0;

	/**
	 * Initialize the XMLSimulationReader by creating a new DocumentBuilder
	 */
	public XMLSimulationReader() {
		try {
			builder = new DocumentBuilderFactoryImpl().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return new simulationspeed
	 */
	public int getSimulationSpeed() {
		return this.simulationSpeed;
	}

	@Override
	public Building getBuilding() {
		return this.building;
	}

	@Override
	public List<Action> getActions() {
		return this.actions;
	}

	@Override
	public void readPath(String xmlPath) throws SAXException, IOException {
		document = builder.parse(xmlPath);
		processDocument();
	}

	/**
	 * Processes the read document and fills the actions list and creates the building
	 */
	private void processDocument() {
		if (document == null) {
			return;
		}

		NamedNodeMap m = document.getDocumentElement().getAttributes();
		try {
			this.simulationSpeed = Integer.parseInt(getValue("simulationSpeed",
					m));
		} catch (Exception e) {
		}
		NodeList nodes_i = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			if (nodes_i.item(i).getNodeName() == "Building") {
				processBuilding(nodes_i.item(i));
			} else if (nodes_i.item(i).getNodeName() == "Actions") {
				processActions(nodes_i.item(i));
			}
		}
	}

	/**
	 * Check for all Actions in the node @item, if found add them to the 
	 * internal actionlist
	 * @param item
	 */
	private void processActions(Node item) {
		actions = new ArrayList<Action>();
		NodeList nodes_i = item.getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			if (nodes_i.item(i).getNodeName() == "Action") {
				Action a = getAction(nodes_i.item(i));
				if (a != null) {
					actions.add(a);
				}
			}
		}
	}

	/**
	 * Gets the action out of the Node
	 * @param item
	 * @return the new created action
	 */
	private Action getAction(Node item) {
		try {
			if (item.hasAttributes()) {
				NamedNodeMap m = item.getAttributes();
				int startLevel = Integer.parseInt(getValue("startLevel", m));
				int endLevel = Integer.parseInt(getValue("endLevel", m));
				int peopleAmount = Integer
						.parseInt(getValue("peopleAmount", m));
				try {
					int delay = Integer.parseInt(getValue("delay", m));
					return new DelayedElevatorAction(startLevel, endLevel,
							peopleAmount, delay);
				} catch (Exception ex) {
					return new ElevatorAction(startLevel, endLevel,
							peopleAmount);
				}
			}
		} catch (Exception ex) {
			System.out.print("Error creating action " + ex.getMessage());
		}
		return null;
	}

	/**
	 * Gets the elevator out of the Node
	 * @param item
	 * @return the new created elevator
	 */
	private VerticalTransporter getElevator(Node item) {
		try {
			if (item.hasAttributes()) {
				NamedNodeMap m = item.getAttributes();

				int minLevel = Integer.parseInt(getValue("minLevel", m));
				int maxLevel = Integer.parseInt(getValue("maxLevel", m));
				int maxPeople = Integer.parseInt(getValue("maxPeople", m));
				int currentLevel = Integer
						.parseInt(getValue("currentLevel", m));
				float maxSpeed = Float.parseFloat(getValue("maxSpeed", m));
				float acceleration = Float.parseFloat(getValue("acceleration",
						m));
				return new Elevator(minLevel, maxLevel, maxPeople,
						currentLevel, maxSpeed, acceleration);
			}
		} catch (Exception ex) {
			System.out.println("Elevator not created" + ex.getMessage());
		}
		return null;
	}

	/**
	 * Gets the value out of the NamedNodeMap which contains all attributes
	 * @param attributename attriubte to search
	 * @param m NamedNodeMap containing all attributes
	 * @return Value of the atrribute
	 * @throws Exception if attribute not found
	 */
	private String getValue(String attributename, NamedNodeMap m)
			throws Exception {
		Attr a = (Attr) m.getNamedItem(attributename);
		if (a != null) {
			return a.getNodeValue();
		}
		throw new Exception("Attriubte with name " + attributename
				+ " not declated in xml");
	}


	/**
	 * Processes the Building node and looking for Elevators
	 * @param item
	 */
	private void processBuilding(Node item) {
		NodeList nodes_i = item.getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			try {
				if (nodes_i.item(i).getNodeName() == "Elevator") {
					VerticalTransporter e = getElevator(nodes_i.item(i));
					if (e != null) {
						if (building == null) {
							building = new Tower(e);
						} else {
							building.addElevator(e);
						}
					}
				}
			} catch (Exception ex) {
			}
		}
	}

	@Override
	public void readXMLStructure(String xmlStructure) throws SAXException,
			IOException {
		document = builder.parse(new ByteArrayInputStream(xmlStructure
				.getBytes()));
		processDocument();
	}
}
