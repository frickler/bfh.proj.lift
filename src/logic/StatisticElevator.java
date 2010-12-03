package logic;

import java.util.ArrayList;
import java.util.List;

import definition.HorizontalTransporter;
import definition.Statistic;


public class StatisticElevator  extends Statistic  {

	private ArrayList<HorizontalTransporter> elevators = new ArrayList<HorizontalTransporter>();
	
	public void addElevator(List<HorizontalTransporter> list){
		elevators.addAll(list);
	}
	
	public void addElevator(HorizontalTransporter elevator){
		elevators.add(elevator);
	}
	
	public void removeElevator(HorizontalTransporter elevator){
		elevators.remove(elevator);
	}
	
	@Override
	public String getStatistic() {
		
		String text = getTitle("Elevators summary");
		int i = 0;
		for(HorizontalTransporter e : elevators){
			i++;
			text +=	getElevatorValue(e,i);
		}
		String summary = getSubTitle("Summary");
		try {
			
			summary += getFormattedText("=>Total driven levels", "levels", getSummaryOf(Attriubte.DrivenLevel));
			summary += getFormattedText("=>Total driven levels empty", "levels", getSummaryOf(Attriubte.DrivenLevelEmpty));
			summary += getFormattedText("=>Total time in motion", "seconds", getSummaryOf(Attriubte.TimeInMotion));
			summary += getFormattedText("=>Total time in motion empty", "seconds", getSummaryOf(Attriubte.TimeInMotionEmpty));
			summary += getFormattedText("=>Total transported people", "people", getSummaryOf(Attriubte.TrasportedPepole));
		} catch (Exception e1) {
			e1.printStackTrace();
			summary += e1.getMessage();
		}
		return text+summary;
	}
	
	private String getFormattedText(String title,String entity, int[] summary){
		
		String sText = "\n";
		sText += title;
		sText += "\nMinimum: "+summary[0]+" "+entity+". ";
		sText += "\tMaximum: "+summary[1]+" "+entity+". ";
		sText += "\tAverage: "+summary[2]+" "+entity+". ";
		sText += "\tSum: "+summary[3]+" "+entity+". \n";
		return sText;
	}
	
	
	public enum Attriubte { TrasportedPepole, DrivenLevel, DrivenLevelEmpty, TimeInMotion,TimeInMotionEmpty};
	
public int[] getSummaryOf(Attriubte a) throws Exception{
		
		int[] returns = new int[4];
		
		int max = -1;
		int min = 99999999;
		int sum = 0;
		
		for(HorizontalTransporter e : elevators){
			int value = (int)(getValue(e,a));
			if(value > max){
				max = value;
			}
			if(value < min){
				min = value;
			}
		sum += value;
		}
		
		returns[0] = min;
		returns[1] = max;
		returns[2] = (int)(sum / this.elevators.size());
		returns[3] = (int)sum;
		return returns;
	}
	
	

	private int getValue(HorizontalTransporter e, Attriubte a) throws Exception {
		switch(a){
		case TrasportedPepole:
			return e.getTransportedPeople();
		case DrivenLevel:
			return e.getDrivenLevels();
		case DrivenLevelEmpty:
			return e.getDrivenLevelsEmpty();
		case TimeInMotion:
			return (int)(e.getTimeInMotion()/1000);
		case TimeInMotionEmpty:
			return (int)(e.getTimeInMotionEmpty()/1000);	
		}
		throw new Exception("Actions datetype not defined");
}

	private String getElevatorValue(HorizontalTransporter e,int i) {
		String text = getSubTitle("Elevator "+i);
		text += "Driven Levels: "+e.getDrivenLevels()+"\t\tTotalTime: "+e.getTimeInMotion();
		int percent = 0;
		if(e.getDrivenLevels() != 0){
			percent = Math.round(((float)e.getDrivenLevelsEmpty())/((float)e.getDrivenLevels())*100);
		}		
		text += "\nDriven Levels empty: "+e.getDrivenLevelsEmpty()+"\tTotalTime: "+e.getTimeInMotionEmpty()+"\tPercent: "+percent+"%";
		text +=  "\nTransported People: "+e.getTransportedPeople();
		text +=  "\n";
		 return text;
	}	
}
