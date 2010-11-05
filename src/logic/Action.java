package logic;

import java.util.Date;

import definition.IAction;

public class Action extends IAction {

	/**
	 * 
	 * @param startLevel
	 * @param endLevel
	 * @param pepoleAmount
	 */
	public Action(int startLevel, int endLevel, int pepoleAmount) {
		super(startLevel, endLevel, pepoleAmount);
		this.setTimestampStarted(new Date());		
	}

	@Override
	public String toString() {
		return "Action [getStartLevel()=" + getStartLevel()
				+ ", getEndLevel()=" + getEndLevel() + ", getPeopleAmount()="
				+ getPeopleAmount() + ", hashCode()=" + hashCode() + "]";
	}

}
