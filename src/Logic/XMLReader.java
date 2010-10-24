package Logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.xml.internal.bind.v2.model.core.MaybeElement;

import Interface.IAction;
import Interface.IBuilding;
import Interface.IElevator;
import Interface.IXMLReader;

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
				int startLevel = 0;
				int endLevel = 0;
				int peopleAmount = 0;
				for (int i = 0; i < m.getLength(); i++) {
					Attr att = (Attr) m.item(i);

					if (att.getName() == "startLevel"
							&& IsInteger(att.getValue())) {
						startLevel = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					if (att.getName() == "endLevel"
							&& IsInteger(att.getValue())) {
						endLevel = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					if (att.getName() == "peopleAmount"
							&& IsInteger(att.getValue())) {
						peopleAmount = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					return new Action(startLevel, endLevel, peopleAmount);
				}
			}
		} catch (Exception ex) {
		}
		return null;
	}

	private IElevator getElevator(Node item) {
		try {
			if (item.hasAttributes()) {
				NamedNodeMap m = item.getAttributes();
				int minLevel = 0;
				int maxLevel = 0;
				int maxPeople = 0;
				float currentLevel = 0;
				for (int i = 0; i < m.getLength(); i++) {
					Attr att = (Attr) m.item(i);

					if (att.getName() == "minLevel"
							&& IsInteger(att.getValue())) {
						minLevel = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					if (att.getName() == "maxLevel"
							&& IsInteger(att.getValue())) {
						maxLevel = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					if (att.getName() == "maxPeople"
							&& IsInteger(att.getValue())) {
						maxPeople = Integer.parseInt(att.getValue());
					} else {
						return null;
					}
					if (att.getName() == "currentLevel"
							&& IsInteger(att.getValue())) {
						currentLevel = Integer.parseInt(att.getValue());
					} else {
						return null;
					}

					if (minLevel >= maxLevel) {
						return null;
					} else {
						return new Elevator(minLevel, maxLevel, maxPeople,
								currentLevel);
					}
				}
			}
		} catch (Exception ex) {
		}
		return null;
	}

	private boolean IsInteger(String check) {
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
4	}
}
