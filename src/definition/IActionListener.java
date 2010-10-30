package definition;

public interface IActionListener {
	public abstract void actionCompleted(IElevator e,IAction a);
	public abstract void actionStarted(IElevator e,IAction a);
}
