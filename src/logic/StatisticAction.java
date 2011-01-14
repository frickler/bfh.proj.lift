package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import definition.Action;
import definition.Statistic;

public class StatisticAction extends Statistic {
	
	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	/**
	 * List of all actions which are considered at the evaluation
	 */
	private List<Action> actions = new ArrayList<Action>();

	/**
	 * Adds action for the evaluation
	 * @param pactions
	 */
	public void addAction(List<Action> pactions) {
		actions.addAll(pactions);
	}

	/**
	 * Adds actions for the evaluation
	 * @param action
	 */
	public void addAction(Action action) {
		actions.add(action);
	}

	/**
	 * Removes a added action
	 * @param action
	 */
	public void removeAction(Action action) {
		actions.remove(action);
	}
    /**
     * Prints the Statistic Results to the Console
     * @throws Exception
     */
	public void printStatisticToConsole() throws Exception {
		System.out.println(getStatistic());
	}

	/**
	 * Returns a string with the formatted statistic restult containg Wartezeit auf den Lift, Fahrzeit, Gesamtzeit
	 */
	public String getStatistic() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(getTitle(actions.size() + " Actions evaluated"));
			sb.append(getFormattedText("Wartezeit auf den Lift",
					getTimespan(DateTypes.Called, DateTypes.Entered)));
			sb.append(getFormattedText("Fahrzeit",
					getTimespan(DateTypes.Entered, DateTypes.Left)));
			sb.append(getFormattedText("Gesamtzeit",
					getTimespan(DateTypes.Called, DateTypes.Left)));
		} catch (Exception e) {
			sb.append("Error doing Evaluation: " + e.getMessage() + ":"
					+ e.getClass() + ":" + e.getStackTrace());
		}
		return sb.toString();
	}



	/**
	 * Possible datetypes which were measured 
	 */
	public enum DateTypes {
		Called, Entered, Left
	};

	/**
	 * Get the Minimum, Maximum and Average timeSpan between the dates
	 * defined over the paramter @from and @to
	 * @param from Start of timespan
	 * @param to End of timespan
	 * @return Minimum, Maximum and Average in this order
	 */
	public int[] getTimespan(DateTypes from, DateTypes to) {

		int[] returns = new int[3];

		int max = -1;
		int min = 99999999;
		int sum = 0;

		for (Action ia : this.actions) {			
			log4j.debug("from " + from + " to " + to);
			log4j.debug("From: " + getDateTime(ia, to).getTime());
			log4j.debug("To: " + getDateTime(ia, from).getTime());
			int spaninseconds = (int) (getDateTime(ia, to).getTime() - getDateTime(
					ia, from).getTime());
			if (spaninseconds > max) {
				max = spaninseconds;
			}
			if (spaninseconds < min) {
				min = spaninseconds;
			}
			sum += spaninseconds;
		}

		returns[0] = min;
		returns[1] = max;
		returns[2] = (int) sum / this.actions.size();

		return returns;
	}

	/**
	 * Gets the Timestamp @typ of the action @ia
	 * @param ia the action
	 * @param typ the date typ
	 * @return
	 */
	private Date getDateTime(Action ia, DateTypes typ) {

		switch (typ) {
		case Left:
			return ia.getTimestampElevatorLeft();
		case Called:
			return ia.getTimestampElevatorCalled();
		case Entered:
			return ia.getTimestampElevatorEntered();
		}
		return null;
		// throw new Exception("Actions datetype not defined");
	}


	public org.w3c.dom.Element getXMLStatistic(Document doc) {
		org.w3c.dom.Element n = doc.createElement("Actions");
		n.setAttribute("total", this.actions.size()+"");	
		org.w3c.dom.Element m1 = getMeasureElement(doc.createElement("Measure"),"Wartezeit auf den Lift",
				getTimespan(DateTypes.Called, DateTypes.Entered));
		org.w3c.dom.Element m2 = getMeasureElement(doc.createElement("Measure"),"Fahrzeit",
				getTimespan(DateTypes.Entered, DateTypes.Left));
		org.w3c.dom.Element m3 = getMeasureElement(doc.createElement("Measure"),"Gesamtzeit",
				getTimespan(DateTypes.Called, DateTypes.Left));
		n.appendChild(m1);
		n.appendChild(m2);
		n.appendChild(m3);
		
		return n;
	}

	
}
