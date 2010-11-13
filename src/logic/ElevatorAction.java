package logic;

import java.util.Date;

import definition.Action;

public class ElevatorAction extends Action {

	/**
	 * 
	 * @param startLevel
	 * @param endLevel
	 * @param pepoleAmount
	 */
	public ElevatorAction(int startLevel, int endLevel, int pepoleAmount) {
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
