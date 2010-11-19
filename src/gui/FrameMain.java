package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

	private boolean isRunning;	
	private Controller controller;

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

		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				isRunning = false;
				FrameMain.this.controller.stopController();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		this.setSize(1024, 800); // TODO
		this.setResizable(false);

		this.setVisible(true);

		JPanel panelElevator = new JPanel();
		panelElevator.setLayout(new GridLayout(1, building.getElevators().size() + 1));
		panelElevator.setMinimumSize(new Dimension(this.getContentPane().getWidth(), this.getContentPane().getHeight() - 50));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelElevator, BorderLayout.CENTER);
		this.getContentPane().add(new ConsolePanel(building, this), BorderLayout.SOUTH);

		// add a single LevelPanel and an ElevatorPanel for each elevator
		this.getContentPane().setLayout(
				new GridLayout(1, building.getElevators().size() + 1));

		this.getContentPane().add(new LevelPanel(building, controller));

		for (HorizontalTransporter item : building.getElevators()) {
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this);
			this.getContentPane().add(ePanel);

			elevatorPanels.add(ePanel);
		}
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
		isRunning = true;
		while (isRunning) {
			for (ElevatorPanel p : elevatorPanels) {
				p.repaint();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
