package definition;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

/**
 * 
 * @author BFH-Boys
 * 
 */
public abstract class XMLReader {

	/**
	 * 
	 * @return A Building
	 */
	public abstract Building getBuilding();

	/**
	 * 
	 * @return A list of actions to perform
	 */
	public abstract List<Action> getActions();

	/**
	 * 
	 * @param xmlPath
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract void readPath(String xmlPath) throws SAXException,
			IOException;

	/**
	 * 
	 * @param xmlStructure
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract void readXMLStructure(String xmlStructure)
			throws SAXException, IOException;

}
