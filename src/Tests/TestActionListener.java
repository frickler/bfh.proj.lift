package Tests;
import definition.*;

public class TestActionListener implements IActionListener {

	public boolean actionDone = false;
	
	public void actionCompleted(IElevator e, IAction a) {	
		this.actionDone = true;
	}

	@Override
	public void actionStarted(IElevator e, IAction a) {
		this.actionDone = false;		
	}

}
