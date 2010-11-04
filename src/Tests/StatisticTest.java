package Tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import definition.IAction;
import logic.Action;
import logic.Statistic;

import org.junit.Test;

public class StatisticTest {

	
	@Test
	public void TestSummary() throws Exception {
		
		IAction a = new Action(2,12,3);
		Date date = new Date(System.currentTimeMillis());
		a.setTimestampEntered(date);
		date = new Date(System.currentTimeMillis()+3*1000);
		a.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis()+6*1000);
		a.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis()+9*1000);
		a.setTimestampEnded(date);
		
		IAction b = new Action(2,12,3);
		date = new Date(System.currentTimeMillis());
		b.setTimestampEntered(date);
		date = new Date(System.currentTimeMillis()+10*1000);
		b.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis()+20*1000);
		b.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis()+30*1000);
		b.setTimestampEnded(date);
		
		IAction c = new Action(2,12,3);
		date = new Date(System.currentTimeMillis());
		c.setTimestampEntered(date);
		date = new Date(System.currentTimeMillis()+5*1000);
		c.setTimestampStarted(date);
		date = new Date(System.currentTimeMillis()+10*1000);
		c.setTimestampPeopleLoaded(date);
		date = new Date(System.currentTimeMillis()+15*1000);
		c.setTimestampEnded(date);
			
		Statistic s = new Statistic();
		s.addAction(a);
		s.addAction(b);
		s.addAction(c);
		
		s.printStatisticToConsole();
	}
	
}
