package exceptions;

import definition.Action;

public class MaxLevelActionException extends IllegalActionException {

	public MaxLevelActionException(Action action) {
		super(action);
	}

	@Override
	public String getMessage() {
		return "There is no elevator reaching the "
				+ getAction().getStartLevel() + " floor";
	}

}
