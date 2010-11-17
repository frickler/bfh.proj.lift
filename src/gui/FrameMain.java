package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	 * @param building
	 *            the building that will be visualized in this frame
	 * @param controller
	 *            the controller used for this building
	 * @throws Exception
	 *             throws an exception if arguments are null
	 */
	public FrameMain(Building building, Controller controller) throws Exception {
		if (building == null)
			throw new Exception("building can not be null");

		if (controller == null)
			throw new Exception("controller can not be null");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");
		this.setVisible(true);
		
		this.setSize(1024, 800); // TODO
		this.setResizable(true);

		JPanel panelElevator = new JPanel();
		panelElevator.setLayout(new GridLayout(1, building.getElevators().size() + 1));
		panelElevator.setMinimumSize(new Dimension(this.getContentPane().getWidth(), this.getContentPane().getHeight() - 50));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelElevator, BorderLayout.CENTER);
		this.getContentPane().add(new ConsolePanel(building, this), BorderLayout.SOUTH);

		for (HorizontalTransporter item : building.getElevators()) {
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this);
			panelElevator.add(ePanel);

			elevatorPanels.add(ePanel);
		}

		// add a single LevelPanel and an ElevatorPanel for each elevator
		panelElevator.add(new LevelPanel(building, controller));
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			for (ElevatorPanel p : elevatorPanels) {
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
