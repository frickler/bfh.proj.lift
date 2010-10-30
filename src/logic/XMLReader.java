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

import definition.IAction;
import definition.IBuilding;
import definition.IElevator;
import definition.IXMLReader;


public class XMLReader extends IXMLReader {

	private DocumentBuilder builder;
	private Document document;
	private List<IAction> actions;
	private IBuilding building;

	public XMLReader() {
		try {
			builder = new DocumentBuilderFactoryImpl().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IBuilding getBuilding() {
		return this.building;
	}

	@Override
	public List<IAction> getActions() {
		return this.actions;
	}

	@Override
	public void readPath(String xmlPath) throws SAXException, IOException {
		document = builder.parse(xmlPath);
		processDocument();
	}

	private void processDocument() {
		if (document == null) {
			return;
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

	private void processActions(Node item) {
		actions = new ArrayList<IAction>();
		NodeList nodes_i = item.getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			if (nodes_i.item(i).getNodeName() == "Action") {
				IAction a = getAction(nodes_i.item(i));
				if (a != null) {
					actions.add(a);
				}
			}
		}
	}

	private IAction getAction(Node item) {
		try {
			if (item.hasAttributes()) {
				NamedNodeMap m = item.getAttributes();
				int startLevel = Integer.parseInt(getValue("startLevel", m));
				int endLevel = Integer.parseInt(getValue("endLevel", m));
				int peopleAmount = Integer.parseInt(getValue("peopleAmount", m));
				return new Action(startLevel, endLevel, peopleAmount);
			}
		} catch (Exception ex) {
		}
		return null;
	}

	private IElevator getElevator(Node item) {
		try {
			if (item.hasAttributes()) {
				NamedNodeMap m = item.getAttributes();

				int minLevel = Integer.parseInt(getValue("minLevel", m));
				int maxLevel = Integer.parseInt(getValue("maxLevel", m));
				int maxPeople = Integer.parseInt(getValue("maxPeople", m));
				float currentLevel = Float.parseFloat(getValue("currentLevel",m));
				return new Elevator(minLevel, maxLevel, maxPeople, currentLevel);
			}
		} catch (Exception ex) {
		}
		return null;
	}

	private String getValue(String attributename, NamedNodeMap m)
			throws Exception {
		Attr a = (Attr) m.getNamedItem(attributename);
		if (a != null) {
			return a.getNodeValue();
		}
		throw new Exception("Attriubte with name " + attributename
				+ " not declated in xml");
	}

	private boolean isInteger(String check) {
		try {
			int zahl = Integer.parseInt(check);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	private void processBuilding(Node item) {
		NodeList nodes_i = item.getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			try {
				if (nodes_i.item(i).getNodeName() == "Elevator") {
					IElevator e = getElevator(nodes_i.item(i));
					if (e != null) {
						if (building == null) {
							building = new Building(e);							
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
