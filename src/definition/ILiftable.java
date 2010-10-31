package definition;

/**
 * @todo: add comments
 * @author 
 *
 */
public interface ILiftable {
	
	public int getMinLevel();
	public int getMaxLevel();
	public int getMaxPeople();
	public int getCurrentLevel();	
	public int getTransportedPeople();
	public int getDrivenLevels();
	public int getDrivenLevelsEmpty();
	public float getTimeInMotion();
	public float getTimeInMotionEmpty();
	
	/**
	 * 
	 * @return The time in milliseconds for a liftable to pass one level
	 */
	public int getTimeForOneLevel();
	
	/**
	 * indicates if a liftable object is busy (moving)
	 * @return
	 */
	public boolean isBusy();
	public void setBusy(boolean isBusy);
	
	public void moved(IAction action);
}
