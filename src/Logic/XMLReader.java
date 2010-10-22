package Logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Interface.IAction;
import Interface.IBuilding;
import Interface.IElevator;
import Interface.IXMLReader;

public class XMLReader extends IXMLReader {

	private DocumentBuilder builder;
	private Document document;
	private List<IAction> actions;
	private IBuilding building;
	@Override
	public IBuilding GetBuilding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAction> GetActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readPath(String xmlPath) throws SAXException, IOException {
		document = builder.parse(xmlPath);
		processDocument();
	}
	
	private void processDocument(){
		if(document == null){
			return;
		}
		  NodeList nodes_i  = document.getDocumentElement().getChildNodes();
		  for (int i = 0; i < nodes_i.getLength(); i++) {
			   if(nodes_i.item(i).getNodeName() == "Building"){
				   processBuilding(nodes_i.item(i));
			  }else  if(nodes_i.item(i).getNodeName() == "Actions"){
				  processActions(nodes_i.item(i));
			  }
		  }
	}

	private void processActions(Node item) {
		actions = new ArrayList<IAction>();
		 NodeList nodes_i  = item.getChildNodes();
		 for (int i = 0; i < nodes_i.getLength(); i++) {
			  if(nodes_i.item(i).getNodeName() == "Action"){
				  IAction a = getAction(nodes_i.item(i));
				  if(a != null){
					  actions.add(a);
				  }
			  }
		  }
	}

	private IAction getAction(Node item) {
		try{
			if(item.hasAttributes()){
				NamedNodeMap m = item.getAttributes();
				if(IsInteger(m.getNamedItem("startLevel")) && IsInteger(m.getNamedItem("endLevel")) && IsInteger(m.getNamedItem("peopleAmount"))){			
					int startLevel = Integer.parseInt(m.getNamedItem("startLevel").toString());
					int endLevel = Integer.parseInt(m.getNamedItem("endLevel").toString());
					int peopleAmount = Integer.parseInt(m.getNamedItem("peopleAmount").toString());
					return new Action(startLevel,endLevel,peopleAmount);
				}
			}		
		}catch(Exception ex){
			
		}
		return null;
	}
	
	private IElevator getElevator(Node item) {
		try{
			if(item.hasAttributes()){
				NamedNodeMap m = item.getAttributes();
				if(IsInteger(m.getNamedItem("minLevel")) && IsInteger(m.getNamedItem("maxLevel")) && IsInteger(m.getNamedItem("maxPeople"))){			
					int minLevel = Integer.parseInt(m.getNamedItem("minLevel").toString());
					int maxLevel = Integer.parseInt(m.getNamedItem("maxLevel").toString());
					int maxPeople = Integer.parseInt(m.getNamedItem("maxPeople").toString());
					return new Elevator(minLevel,maxLevel,maxPeople);
				}
			}		
		}catch(Exception ex){			
		}
		return null;
	}

	private boolean IsInteger(Node namedItem) {
		try
		{
		   int zahl = Integer.parseInt(namedItem.toString());
		   return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
	}

	private void processBuilding(Node item) {
		 NodeList nodes_i  = item.getChildNodes();
		 for (int i = 0; i < nodes_i.getLength(); i++) {
			 try{ 
			 if(nodes_i.item(i).getNodeName() == "Elevator"){
				  IElevator e = getElevator(nodes_i.item(i));
				  if(e != null){
					  if(building == null){
						  building = new Building(e);
					  }else{
						  building.addElevator(e);
					  }
				  }
			  }
			 }catch(Exception ex){}
		  }
	}

	@Override
	public void readXMLStructure(String xmlStructure) throws SAXException, IOException {
		document = builder.parse(new ByteArrayInputStream(xmlStructure.getBytes()));	
		processDocument();
	}

}
