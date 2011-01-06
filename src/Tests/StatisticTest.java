package Tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import definition.Action;
import logic.ElevatorAction;
import logic.StatisticAction;

import org.junit.Test;

public class StatisticTest {

	
	@Test
	public void TestSummary() throws Exception {
		
		Action a = new ElevatorAction(2,12,3);
		Date date = new Date(System.currentTimeMillis());
		date = new Date(System.currentTimeMillis()+3*1000);
		a.setTimestampElevatorCalled(date);
		date = new Date(System.currentTimeMillis()+6*1000);		
		date = new Date(System.currentTimeMillis()+9*1000);
		a.setTimestampElevatorEntered(date);
		
		Action b = new ElevatorAction(2,12,3);
		date = new Date(System.currentTimeMillis());		
		date = new Date(System.currentTimeMillis()+10*1000);
		b.setTimestampElevatorCalled(date);
		date = new Date(System.currentTimeMillis()+20*1000);		
		date = new Date(System.currentTimeMillis()+30*1000);
		b.setTimestampElevatorEntered(date);
		
		Action c = new ElevatorAction(2,12,3);
		date = new Date(System.currentTimeMillis());		
		date = new Date(System.currentTimeMillis()+5*1000);
		c.setTimestampElevatorCalled(date);
		date = new Date(System.currentTimeMillis()+10*1000);		
		date = new Date(System.currentTimeMillis()+15*1000);
		c.setTimestampElevatorEntered(date);
			
		StatisticAction s = new StatisticAction();
		s.addAction(a);
		s.addAction(b);
		s.addAction(c);
		
		s.printStatisticToConsole();
	}
	
}
