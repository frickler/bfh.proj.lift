package definition;

public interface ILiftable {
	
	public int getMinLevel();
	public int getMaxLevel();
	public int getMaxPeople();
	
	public boolean isBusy();
	public void setBusy(boolean isBusy);
		

}
