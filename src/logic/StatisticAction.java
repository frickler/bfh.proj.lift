package logic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import definition.Action;
import definition.Statistic;

public class StatisticAction extends Statistic {

	
	private List<Action> actions = new ArrayList<Action>();
	
	public void addAction(List<Action> pactions){
		actions.addAll(pactions);
	}
	
	public void addAction(Action action){
		actions.add(action);
	}
	
	public void removeAction(Action action){
		actions.remove(action);
	}
	
	public void printStatisticToConsole() throws Exception{
		
		consolePrint("Einteffen in der Queue bis Auftrag ausgef�hrt ist:",getEnteredToEnded(DateTypes.Created,DateTypes.Ended));
		consolePrint("Eintreffen in der Etage bis zur Zieletage:",getEnteredToEnded(DateTypes.Loaded,DateTypes.Ended));
		consolePrint("Einteffen in der Queue bis zur Aus�hrung:",getEnteredToEnded(DateTypes.Loaded,DateTypes.Ended));
	}
	
	
	private void consolePrint(String title, int[] summary) {
			System.out.println("");
			System.out.println(title);
			System.out.println("Minimum: "+summary[0]+" seconds.");
			System.out.println("Maximum: "+summary[1]+" seconds.");
			System.out.println("Average: "+summary[2]+" seconds.");
	}
	

	public String getStatistic(){
		String sText = "";
		try {
		sText += getTitle(actions.size()+" Actions evaluated");
		sText += getFormattedText("Einteffen in der Queue bis Auftrag ausgefuehrt ist:",getEnteredToEnded(DateTypes.Created,DateTypes.Ended));
		sText += getFormattedText("Eintreffen in der Etage bis zur Zieletage:",getEnteredToEnded(DateTypes.Loaded,DateTypes.Ended));
		sText += getFormattedText("Einteffen in der Queue bis zur Ausfuehrung:",getEnteredToEnded(DateTypes.Loaded,DateTypes.Ended));
		} catch (Exception e) {
				sText = "Error doing Evaluation: "+e.getMessage();
		}
		return sText;
	}
	
	private String getFormattedText(String title, int[] summary){
		
		String sText = "\n";
		sText += title;
		sText += "\nMinimum: "+summary[0]+" seconds.";
		sText += "\nMaximum: "+summary[1]+" seconds.";
		sText += "\nAverage: "+summary[2]+" seconds.\n";
		return sText;
	}


	public enum DateTypes { Created, Ended, Started, Loaded };
	
	public int[] getEnteredToEnded(DateTypes from,DateTypes to) throws Exception{
		
		int[] returns = new int[3];
		
		int max = -1;
		int min = 99999999;
		int sum = 0;
		
		for(Action ia : this.actions){
			int spaninseconds = (int)((getDateTime(ia,to).getTime()-getDateTime(ia,from).getTime()) / 1000);
			if(spaninseconds > max){
				max = spaninseconds;
			}
			if(spaninseconds < min){
				min = spaninseconds;
			}
		sum += spaninseconds;
		}
		
		returns[0] = min;
		returns[1] = max;
		returns[2] = (int)sum / this.actions.size();
		
		return returns;
	}
	
	private Date getDateTime(Action ia, DateTypes typ) throws Exception {
			
		switch(typ){
		case Started:
			return ia.getTimestampStarted();
		case Loaded:
			return ia.getTimestampPeopleLoaded();
		case Created:
			return ia.getTimestampCreated();
		case Ended:
			return ia.getTimestampEnded();
		}
		throw new Exception("Actions datetype not defined");
	}

	public void printStatisticToFile(String sFilePath){
		
	}
}