package gui;

import java.awt.GridLayout;
import javax.swing.JFrame;

import definition.IBuilding;
import definition.IElevator;


public class FrameMain extends JFrame {
	public FrameMain(IBuilding building) throws Exception {
		if (building == null)
			throw new Exception("building can not be null");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");

		this.setSize(1024, 800); // TODO
		this.setResizable(false);

		//add a single LevelPanel and an ElevatorPanel for each elevator 
		this.setLayout(new GridLayout(1, building.getElevators().size() + 1));
		this.add(new LevelPanel(building));
		for(IElevator item : building.getElevators())
		{
			ElevatorPanel ePanel = new ElevatorPanel(item);
			this.add(ePanel);
		}

		this.setVisible(true);
	}
}
