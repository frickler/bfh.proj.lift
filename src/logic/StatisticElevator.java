package logic;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import definition.VerticalTransporter;
import definition.Statistic;

public class StatisticElevator extends Statistic {

	private int totalTime = -1;
	
	private ArrayList<VerticalTransporter> elevators = new ArrayList<VerticalTransporter>();

	/**
	 * Adds elevator for the statistic
	 * @param list
	 */
	public void addElevator(List<VerticalTransporter> list) {
		elevators.addAll(list);
	}

	public void setTotalSimuationTime(int milliSeconds){
		this.totalTime = milliSeconds;
	}
	
	/**
	 * Adds elevators for the statistics
	 * @param elevator
	 */
	public void addElevator(VerticalTransporter elevator) {
		elevators.add(elevator);
	}

	/**
	 * Removes an added elevator form the statistic
	 * @param elevator
	 */
	public void removeElevator(VerticalTransporter elevator) {
		elevators.remove(elevator);
	}

	@Override
	public String getStatistic() {
		StringBuilder text = new StringBuilder();
		text.append(getTitle("Elevators summary"));
		for (VerticalTransporter e : elevators) {
			text.append(getElevatorValue(e));
		}
		StringBuilder summary = new StringBuilder();
		summary.append(getSubTitle("Summary"));
		try {

			summary.append(getFormattedText("=>Total driven levels", 
					getSummaryOf(Attriubte.DrivenLevel),"levels"));
			
			summary.append(getFormattedText("=>Total driven levels filled,", 
					getSummaryOf(Attriubte.DrivenLevelFilled),"levels"));
			
			summary.append(getFormattedText("=>Total driven levels empty",
					getSummaryOf(Attriubte.DrivenLevelEmpty), "levels"));
			
			summary.append(getFormattedText("=>Total time in motion (ms)", 
					getSummaryOf(Attriubte.TimeInMotion),"seconds"));
			
			summary.append(getFormattedText("=>Total time in motion empty (ms)",
					getSummaryOf(Attriubte.TimeInMotionEmpty), "seconds"));
			
			summary.append(getFormattedText("=>Total transported people (ms)", 
					getSummaryOf(Attriubte.TrasportedPepole),
					"people"));
			if(this.totalTime > 0){
			summary.append(getFormattedText("=>Auslastung (%)", 
					getSummaryOf(Attriubte.Utalization),
					"%"));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			summary.append(e1.getMessage());
		}
		text.append(summary.toString());
		return text.toString();
	}

	/**
	 * Elements of the measure
	 * @author kaeserst
	 *
	 */
	public enum Attriubte {
		TrasportedPepole, DrivenLevel,DrivenLevelFilled, DrivenLevelEmpty, TimeInMotion,TimeInMotionFilled, TimeInMotionEmpty, TimeStillStand, Utalization
	};
/**
 * Gets a summary (min,max,avg,sum) of the attribute @a
 * @param a
 * @return
 * @throws Exception
 */
	public int[] getSummaryOf(Attriubte a) throws Exception {

		int[] returns = new int[4];

		int max = -1;
		int min = 99999999;
		int sum = 0;

		for (VerticalTransporter e : elevators) {
			int value = (int) (getValue(e, a));
			if (value > max) {
				max = value;
			}
			if (value < min) {
				min = value;
			}
			sum += value;
		}

		returns[0] = min;
		returns[1] = max;
		returns[2] = (int) (sum / this.elevators.size());
		returns[3] = (int) sum;
		return returns;
	}

	/**
	 * returns the value of the Attribute @a of the elevator @e
	 * @param e
	 * @param a
	 * @return
	 * @throws Exception
	 */
	private int getValue(VerticalTransporter e, Attriubte a) throws Exception {
		switch (a) {
		case TrasportedPepole:
			return e.getTransportedPeople();
		case DrivenLevel:
			return e.getDrivenLevels();
		case DrivenLevelFilled:
			return e.getDrivenLevels()-e.getDrivenLevelsEmpty();	
		case DrivenLevelEmpty:
			return e.getDrivenLevelsEmpty();
		case TimeInMotion:
			return (int) (e.getTimeInMotion());
		case TimeInMotionFilled:
			return (int) (e.getTimeInMotion() - e.getTimeInMotionEmpty());	
		case TimeInMotionEmpty:
			return (int) (e.getTimeInMotionEmpty());
		case TimeStillStand:
			return (int) (e.getTimeStillStand(this.totalTime));
		case Utalization:
			return (int) (e.getUtilization(this.totalTime));
		}
		throw new Exception("Actions datetype not defined");
	}

	/**
	 * Get the current values of the elevator for the statistic
	 * @param e
	 * @return
	 */
	private String getElevatorValue(VerticalTransporter e) {
		String text = getSubTitle("Elevator " + e.getIdentityNumber());
		text += "Driven Levels: " + e.getDrivenLevels() + "\t\tTotalTime: "
				+ e.getTimeInMotion();
		int percent = 0;
		if (e.getDrivenLevels() != 0) {
			percent = Math.round(((float) e.getDrivenLevelsEmpty())
					/ ((float) e.getDrivenLevels()) * 100);
		}
		text += "\nDriven Levels empty: " + e.getDrivenLevelsEmpty()
				+ "\tTotalTime: " + e.getTimeInMotionEmpty() + "\tPercent: "
				+ percent + "%";
		text += "\nTransported People: " + e.getTransportedPeople();
		text += "\n";
		return text;
	}

	@Override
	public Element getXMLStatistic(Document doc) {

		org.w3c.dom.Element n = doc.createElement("Elevators");
		try {
			n.setAttribute("total", this.elevators.size() + "");

			org.w3c.dom.Element sum = doc.createElement("Summary");

			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Total gefahrene Stockwerke",getSummaryOf(Attriubte.DrivenLevel)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Total gefahrene Stockwerke beladen",getSummaryOf(Attriubte.DrivenLevelFilled)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Stockwerke leer gefahren",getSummaryOf(Attriubte.DrivenLevelEmpty)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Totale Bewegungszeit (ms)",getSummaryOf(Attriubte.TimeInMotion)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Totale Bewegungszeit beladen (ms)",getSummaryOf(Attriubte.TimeInMotionFilled)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Total Bewegungszeit unbeladen (ms)",getSummaryOf(Attriubte.TimeInMotionEmpty)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Stillstandzeit (ms)",getSummaryOf(Attriubte.TimeStillStand)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Auslastung %",getSummaryOf(Attriubte.Utalization)));
			
			sum.appendChild(getMeasureElement(doc.createElement("Measure"),
					"Transportierte Personen",getSummaryOf(Attriubte.TrasportedPepole)));
			
			n.appendChild(sum);
			
			for(VerticalTransporter e : elevators){
				n.appendChild(e.getXML(doc.createElement("Elevator"),this.totalTime));
			}

		} catch (Exception e1) {

		}
		return n;
	}
}
