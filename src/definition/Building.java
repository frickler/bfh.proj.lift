package definition;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author BFH-Boys
 *
 */
public abstract class Building {

	// all elevators of the building
	private List<VerticalTransporter> transporters;

	/**
	 * 
	 * @param elevator
	 *            Each building needs at least one elevator
	 * @throws Exception
	 *             If the elevator-argument is null, an exception will be thrown
	 */
	public Building(VerticalTransporter elevator)
			throws IllegalArgumentException {
		transporters = new ArrayList<VerticalTransporter>();
		if (elevator == null) {
			throw new IllegalArgumentException("elevator object is empty");
		}
		transporters.add(elevator);
	}

	/**
	 * 
	 * @param elevator
	 *            Add an elevator the the building
	 * @throws IllegalArgumentException
	 *             An exception gets thrown when die elevator is null
	 */
	public void addElevator(VerticalTransporter elevator)
			throws IllegalArgumentException {
		if (elevator == null) {
			throw new IllegalArgumentException("elevator object is empty");
		}
		transporters.add(elevator);
	}

	/**
	 * 
	 * @param elevator
	 *            Elevator to remove from the building
	 * @throws Exception 
	 */
	public void removeElevator(VerticalTransporter elevator) throws Exception {
		if(transporters.size() != 1)
		transporters.remove(elevator);
		else
			throw new Exception("cannot remove the last elevator, do you really wanna use the stairs?");
	}

	/**
	 * 
	 * @return Returns a list of all elevators in this building
	 */
	public List<VerticalTransporter> getElevators() {
		return transporters;
	}

	/**
	 * 
	 * @return the minimal reachable level by an elevator (lowest floor)
	 */
	public int getMinLevel() {
		int minLevel = 100000;
		for (VerticalTransporter e : transporters) {
			if (e.getMinLevel() < minLevel) {
				minLevel = e.getMinLevel();
			}
		}
		return minLevel;
	}

	/**
	 * 
	 * @return the maximal reachable level by an elevator (highest floor)
	 */
	public int getMaxLevel() {
		int maxLevel = 0;
		for (VerticalTransporter e : transporters) {
			if (e.getMaxLevel() > maxLevel) {
				maxLevel = e.getMaxLevel();
			}
		}
		return maxLevel;
	}

	public void setSimulationSpeed(int speed) {
		for(VerticalTransporter e : transporters){
			e.setSimulationSpeed(speed);
		}
	}
}
