package Interface;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;



public abstract class IXMLReader {
	
	public abstract IBuilding GetBuilding();
	public abstract List<IAction> GetActions();
	
	public abstract void readPath(String xmlPath) throws SAXException, IOException;
	public abstract void readXMLStructure(String xmlStructure) throws SAXException, IOException;
	
}
