package Tests;
import Interface.*;

public class TestActionListener extends IActionListener {

	public boolean actionDone = false;
	
	public void actionCompleted(IElevator e, IAction a) {	
		this.actionDone = true;
	}

	@Override
	public void actionStarted(IElevator e, IAction a) {
		this.actionDone = false;		
	}

}
