package definition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Abstract class which provides the minimal methods for a Statistic
 * 
 * @author BFH-Boys
 */
public abstract class Statistic {

	private String titlebar = "======================";
	private String subtitlebar = "----------------------";

	/**
	 * Returns a formated title
	 * 
	 * @param text
	 *            Text for the title
	 * @return Title Returns a formated title
	 */
	protected String getTitle(String text) {
		return "\n" + titlebar + text + titlebar + "\n";
	}

	/**
	 * Formats the statistic result. the default entity is seconds
	 * @param title of the measure
	 * @param summary result of the measure
	 * @return string of the summary
	 */
	public String getFormattedText(String title, int[] summary) {		
		return getFormattedText(title,summary,"seconds");
	}
	
	/**
	 * Formats the statistic result
	 * @param title title of the measure
	 * @param summary result of the measure
	 * @param entity the unit of the measure
	 * @return formatted summary
	 */
	public String getFormattedText(String title, int[] summary,String entity) {
		StringBuilder text = new StringBuilder();
		text.append("\n");
		text.append(title);
		text.append("\nMinimum: " + summary[0] + " "+entity);
		text.append("\nMaximum: " + summary[1] + " "+entity);
		text.append("\nAverage: " + summary[2] + " "+entity);
		if(summary.length == 4){
			text.append("\tSum: " + summary[3] + " " + entity + ". \n");
		}
		text.append("\n");
		
		return text.toString();
	}

	/**
	 * Returns a formated title
	 * 
	 * @param text
	 *            Text for the subtitle
	 * @return Title Returns a formated title
	 */
	protected String getSubTitle(String text) {
		return "\n" + subtitlebar + text + subtitlebar + "\n";
	}

	/**
	 * 
	 * @return Returns a summary of the statistics
	 */
	public abstract String getStatistic();

	/**
	 * Adds the results of the measure to a new node of the Document @doc
	 * 
	 * @param doc
	 *            of wich the new node is created
	 * @return the new NodeElement
	 */
	public abstract Element getXMLStatistic(Document doc);

	/**
	 * Adds the Measure (Min, Max, Avg) to the element @e as attributes
	 * 
	 * @param e
	 *            Element to add the attributes
	 * @param string
	 *            Name of the measure
	 * @param results
	 *            the results of the measure
	 * @return the dom element of the meausre
	 */
	public org.w3c.dom.Element getMeasureElement(org.w3c.dom.Element e,
			String string, int[] results) {
		e.setTextContent(string);
		e.setAttribute("min", results[0] + "");
		e.setAttribute("max", results[1] + "");
		e.setAttribute("avg", results[2] + "");
		return e;
	}
}
