package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import definition.Building;
import definition.Controller;
import definition.HorizontalTransporter;


/**
 * 
 * @author BFH-Boys
 *
 */
public class FrameMain extends JFrame implements Runnable {
	private static final long serialVersionUID = -6897288117049912593L;
	private List<ElevatorPanel> elevatorPanels = new ArrayList<ElevatorPanel>();
	
	/**
	 * @param building the building that will be visualized in this frame
	 * @param controller the controller used for this building
	 * @throws Exception throws an exception if arguments are null 
	 */
	public FrameMain(Building building, Controller controller) throws Exception {
		if (building == null)
			throw new Exception("building can not be null");

		if(controller == null)
			throw new Exception("controller can not be null");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");
		this.setVisible(true);

		this.setSize(1024, 800); // TODO
		this.setResizable(false);

		//add a single LevelPanel and an ElevatorPanel for each elevator 
		this.getContentPane().setLayout(new GridLayout(1, building.getElevators().size() + 1));
		this.getContentPane().add(new LevelPanel(building, controller));
		for(HorizontalTransporter item : building.getElevators())
		{
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this);
			this.getContentPane().add(ePanel);
			elevatorPanels.add(ePanel);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g){
		super.paint(g);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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
