package exceptions;

import definition.Action;

public class MinLevelActionException extends IllegalActionException {

	public MinLevelActionException(Action action) {
		super(action);
	}

	@Override
	public String getMessage() {
		return "There is no elevator reaching the "
				+ getAction().getStartLevel() + " floor";
	}

}
