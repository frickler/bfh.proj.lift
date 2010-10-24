package Logic;

import java.util.Date;

import Interface.IAction;
import Interface.IElevator;

public class Elevator extends IElevator {


	public Elevator(int minLevel, int maxLevel, int maxPeople, float currentLevel)
			throws Exception {
		super(minLevel, maxLevel, maxPeople, currentLevel);
	}

	@Override
	public void run() {
		

		// TODO Auto-generated method stub
	}

	@Override
	public void runAction() throws Exception {
		
		if(this.getCurrentAction()==null){
			throw new Exception("no action defined");
		}
		
		IAction a = this.getCurrentAction();
		
		// get to the level where the button was pressed.
		a.setTimestampStarted(new Date(System.currentTimeMillis()));
		Move(getCurrentLevel(),a.getStartLevel());
		// get to the level where the people wants to go.
		a.setTimestampPeopleLoaded(new Date(System.currentTimeMillis()));
		Move(getCurrentLevel(),a.getEndLevel());
		a.setTimestampEnded(new Date(System.currentTimeMillis()));
		actionDone();
	}
	

	private void Move(double sourceLevel, double targetLevel) throws InterruptedException {
		
		if(sourceLevel < targetLevel){ // go up
			for(double i = sourceLevel;i != targetLevel;i+=getStepSize()){
				Thread.sleep(getTimeForOneStep());
			}
		}
		if(sourceLevel > targetLevel){ // go down
			for(double i = sourceLevel;i != targetLevel;i-=getStepSize()){
				Thread.sleep(getTimeForOneStep());
			}
		}		
	}

	@Override
	protected void actionDone() {
		// TODO Notify the controller;		
	}

}
