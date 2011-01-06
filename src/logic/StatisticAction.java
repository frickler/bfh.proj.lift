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

	private List<Action> actions = new ArrayList<Action>();

	public void addAction(List<Action> pactions) {
		actions.addAll(pactions);
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public void removeAction(Action action) {
		actions.remove(action);
	}

	public void printStatisticToConsole() throws Exception {

		consolePrint("Wartezeit auf den Lift",
				getTimespan(DateTypes.Called, DateTypes.Entered));
		consolePrint("Fahrzeit",
				getTimespan(DateTypes.Entered, DateTypes.Left));
		consolePrint("Gesamtzeit",
				getTimespan(DateTypes.Called, DateTypes.Left));
	}

	private void consolePrint(String title, int[] summary) {
		System.out.println("");
		System.out.println(title);
		System.out.println("Minimum: " + summary[0] + " seconds.");
		System.out.println("Maximum: " + summary[1] + " seconds.");
		System.out.println("Average: " + summary[2] + " seconds.");
	}

	public String getStatistic() {
		String sText = "";
		try {
			sText += getTitle(actions.size() + " Actions evaluated");
			sText += getFormattedText("Wartezeit auf den Lift",
					getTimespan(DateTypes.Called, DateTypes.Entered));
			sText += getFormattedText("Fahrzeit",
					getTimespan(DateTypes.Entered, DateTypes.Left));
			sText += getFormattedText("Gesamtzeit",
					getTimespan(DateTypes.Called, DateTypes.Left));
		} catch (Exception e) {
			sText = "Error doing Evaluation: " + e.getMessage() + ":"
					+ e.getClass() + ":" + e.getStackTrace();
		}
		return sText;
	}

	private String getFormattedText(String title, int[] summary) {

		String sText = "\n";
		sText += title;
		sText += "\nMinimum: " + summary[0] + " seconds.";
		sText += "\nMaximum: " + summary[1] + " seconds.";
		sText += "\nAverage: " + summary[2] + " seconds.\n";
		return sText;
	}

	public enum DateTypes {
		Called, Entered, Left
	};

	public int[] getTimespan(DateTypes from, DateTypes to) {

		int[] returns = new int[3];

		int max = -1;
		int min = 99999999;
		int sum = 0;

		for (Action ia : this.actions) {			
			log4j.debug("from " + from + " to " + to);
			log4j.debug("From: " + getDateTime(ia, to).getTime());
			log4j.debug("To: " + getDateTime(ia, from).getTime());
			int spaninseconds = (int) ((getDateTime(ia, to).getTime() - getDateTime(
					ia, from).getTime()) / 1000);
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

	public void printStatisticToFile(String sFilePath) {

	}

	public org.w3c.dom.Element getXMLStatistic(Document doc) {
		org.w3c.dom.Element n = doc.createElement("Actions");
		n.setAttribute("total", this.actions.size()+"");	
		org.w3c.dom.Element m1 = GetMeasure(doc.createElement("Measure"),"Wartezeit auf den Lift",
				getTimespan(DateTypes.Called, DateTypes.Entered));
		org.w3c.dom.Element m2 = GetMeasure(doc.createElement("Measure"),"Fahrzeit",
				getTimespan(DateTypes.Entered, DateTypes.Left));
		org.w3c.dom.Element m3 = GetMeasure(doc.createElement("Measure"),"Gesamtzeit",
				getTimespan(DateTypes.Called, DateTypes.Left));
		n.appendChild(m1);
		n.appendChild(m2);
		n.appendChild(m3);
		
		return n;
	}

	private org.w3c.dom.Element GetMeasure(org.w3c.dom.Element e,
			String string, int[] results) {	
		e.setTextContent(string);
		e.setAttribute("min", results[0]+"");
		e.setAttribute("max", results[1]+"");
		e.setAttribute("avg", results[2]+"");
		return e;
	}
}
