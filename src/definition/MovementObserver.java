package definition;

import logic.Movement;

/**
 * 
 * @author BFH-Boys
 * 
 */
public interface MovementObserver {

	/**
	 * This method gets called when the elevator has moved.
	 * 
	 * @param object
	 *            Movement which processed the action
	 * @param action
	 *            Processed action (or a part of it)
	 */
	public void moved(MovementObserverable object);
	
	/**
	 * 
	 * @param movement	 
	 * @param stepSize
	 */
	public void stepDone(Movement movement, double stepSize);

	void updateSpeed(double speed);
}
