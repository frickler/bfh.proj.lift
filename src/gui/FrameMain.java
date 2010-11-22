package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import definition.Building;
import definition.Controller;
import definition.HorizontalTransporter;

/**
 * 
 * @author BFH-Boys
 * 
 */
public class FrameMain extends JFrame implements Runnable {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

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
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				isRunning = false;
				FrameMain.this.controller.stopController();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});

		this.setSize(1024, 800); // TODO
		this.setResizable(true);

		//a panel with everything (LevelPanel / ElevatorPanel) except the console
		JPanel panelMain = new JPanel();
		//contains one LevelPanel and an ElevatorPanel for each elevator
		panelMain.setLayout(new GridLayout(1, building.getElevators().size() + 1));
		panelMain.add(new LevelPanel(building, controller));

		this.getContentPane().setLayout(new BorderLayout());
		//the main panel is placed in the middle of this frame, the console on the bottom
		this.getContentPane().add(panelMain, BorderLayout.CENTER);
		this.getContentPane().add(new ConsolePanel(building, this), BorderLayout.SOUTH);

		float gradient = 0.8f;
		int oddEven = 1;
		for (HorizontalTransporter item : building.getElevators()) {
			ElevatorPanel ePanel = new ElevatorPanel(item, building, this, new Color(gradient, gradient, gradient));
			panelMain.add(ePanel);
			
			gradient -= (oddEven > 0) ? 0.1 : -0.1;
			oddEven *= -1;
			elevatorPanels.add(ePanel);
		}
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
		isRunning = true;
		while (isRunning) {
			for (ElevatorPanel p : elevatorPanels) {
				p.repaint();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log4j.error(e);
			}
		}

	}
}
