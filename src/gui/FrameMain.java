package gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import definition.IBuilding;
import definition.IElevator;
import definition.ILiftable;


public class FrameMain extends JFrame implements Runnable {
	public FrameMain(IBuilding building) throws Exception {
		if (building == null)
			throw new Exception("building can not be null");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");
		this.setVisible(true);

		this.setSize(1024, 800); // TODO
		this.setResizable(false);

		//add a single LevelPanel and an ElevatorPanel for each elevator 
		this.setLayout(new GridLayout(1, building.getElevators().size() + 1));
		this.add(new LevelPanel(building));
		for(ILiftable item : building.getElevators())
		{
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this);
			this.getContentPane().add(ePanel);
		}
	}
	
	@Override
	public void run() {
		while(true)
		{
			//TODO: funktioniert noch nicht...
			this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
