package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import definition.IBuilding;
import definition.IController;
import definition.ILiftable;


public class FrameMain extends JFrame implements Runnable {
	private IBuilding building;
	private IController controller;
	private List<ElevatorPanel> elevatorPanels = new ArrayList<ElevatorPanel>();
	
	public FrameMain(IBuilding building, IController controller) throws Exception {
		if (building == null)
			throw new Exception("building can not be null");
		
		this.building = building;
		this.controller = controller;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");
		this.setVisible(true);

		this.setSize(1024, 800); // TODO
		this.setResizable(false);

		//add a single LevelPanel and an ElevatorPanel for each elevator 
		this.getContentPane().setLayout(new GridLayout(1, building.getElevators().size() + 1));
		this.getContentPane().add(new LevelPanel(building, controller));
		for(ILiftable item : building.getElevators())
		{
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this);
			this.getContentPane().add(ePanel);
			elevatorPanels.add(ePanel);
		}
	}
	public void paint(Graphics g){
		super.paint(g);
	}
	
	@Override
	public void run() {
		while(true)
		{
			for(ElevatorPanel p : elevatorPanels)
			{
				p.repaint();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
