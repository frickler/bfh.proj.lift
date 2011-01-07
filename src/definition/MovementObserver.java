package definition;

import logic.Movement;

/**
 * This interface is useful if an elevator wants to 
 * get some callback-Events from a movement
 * 
 * @author BFH-Boys
 * 
 */
public interface MovementObserver {

	/**
	 * This method gets called when the elevator has moved.
	 * 
	 * @param movement
	 *            Movement which processed the action
	 */
	public void moved(Movement movement);

	/**
	 * This method gets called when the movement calculated a new position.
	 * Useful for the elevator to update his current position
	 * 
	 * @param movement
	 *            Movement which calculated the new position
	 * @param stepSize
	 *            Size of the step delta of the former position. Can be positive
	 *            (elevator moving up) or negative (elevator moving down)
	 */
	public void stepDone(Movement movement, double stepSize);

	/**
	 * This method gets called if the speed of the movement changes
	 * 
	 * @param speed Current speed
	 */
	void updateSpeed(double speed);
}
