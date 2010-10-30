package logic;

import java.util.Date;

import definition.IAction;
import definition.IActionListener;
import definition.IElevator;


public class Elevator extends IElevator {


	public Elevator(int minLevel, int maxLevel, int maxPeople, float currentLevel)
			throws Exception {
		super(minLevel, maxLevel, maxPeople, currentLevel);
	}

	@Override
	public void run() {	
		try {
			if(this.getCurrentAction() != null){
				notityActionStarted();
				runAction();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void runAction() throws Exception {
		
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
	

	private void Move(float sourceLevel, float targetLevel) throws InterruptedException {
		
		if(sourceLevel < targetLevel){ // go up
			for(double i = sourceLevel;i < targetLevel;i+=getStepSize()){
				Thread.sleep(getTimeForOneStep());
				setCurrentLevel((float) (Math.round(i*100)/100.0));
			}
		}
		if(sourceLevel > targetLevel){ // go down
			for(double i = sourceLevel;i > targetLevel;i-=getStepSize()){
				Thread.sleep(getTimeForOneStep());
				setCurrentLevel((float) (Math.round(i*100)/100.0));
			}
		}		
		
	}

	@Override
	protected void notifyActionDone() {
		for(IActionListener e : getObservers()){
			e.actionCompleted(this,getCurrentAction());
		}
		
	}

	@Override
	protected void actionDone() {
		notifyActionDone();
		try {
			setCurrentAction(null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	protected void notityActionStarted() {
		for(IActionListener e : getObservers()){
			e.actionStarted(this,getCurrentAction());
		}	
	}

	@Override
	public boolean isBusy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBusy(boolean isBusy) {
		// TODO Auto-generated method stub		
	}

}
