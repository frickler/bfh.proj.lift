package definition;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;



public abstract class IXMLReader {
	
	public abstract IBuilding getBuilding();
	public abstract List<IAction> getActions();
	
	public abstract void readPath(String xmlPath) throws SAXException, IOException;
	public abstract void readXMLStructure(String xmlStructure) throws SAXException, IOException;
	
}
