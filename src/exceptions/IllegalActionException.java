package exceptions;

import definition.Action;

public abstract class IllegalActionException extends Exception {

	private Action action;

	protected Action getAction() {
		return action;
	}

	public IllegalActionException(Action action) {
		super();
		this.action = action;
	}

}
