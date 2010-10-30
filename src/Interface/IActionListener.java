package Interface;

public abstract class IActionListener {
	public abstract void actionCompleted(IElevator e,IAction a);
	public abstract void actionStarted(IElevator e,IAction a);
}
