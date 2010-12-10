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
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import logic.Elevator;
import logic.StatisticAction;
import logic.StatisticElevator;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Building;
import definition.Controller;
import definition.VerticalTransporter;

/**
 * 
 * @author BFH-Boys
 * 
 */
public class FrameMain extends JFrame implements Runnable {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	private boolean isRunning;
	private Building building;
	private Controller controller;
	private ConsolePanel consolePanel;
	private JPanel panelElevatorsLevels;
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

		Menu m = new Menu(this);
		JMenuBar menubar = m.getMenuBar();
		this.setJMenuBar(menubar);

		if (building == null)
			throw new Exception("building can not be null");

		if (controller == null)
			throw new Exception("controller can not be null");

		this.building = building;
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");

		addWindowListenerToFrame();

		this.setSize(1680, 1024); // TODO
		this.setResizable(true);

		// a panel with everything (LevelPanel / ElevatorPanel) except the
		// console
		panelElevatorsLevels = new JPanel();

		this.getContentPane().setLayout(new BorderLayout());
		// the main panel is placed in the middle of this frame, the console on
		// the bottom
		this.getContentPane().add(panelElevatorsLevels, BorderLayout.CENTER);
		consolePanel = new ConsolePanel(building, this);
		this.getContentPane().add(consolePanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	private void addWindowListenerToFrame() {
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// contains one LevelPanel and an ElevatorPanel for each elevator
		panelElevatorsLevels.setLayout(new GridLayout(1, building
				.getElevators().size() + 1));

		if (elevatorPanels.size() != building.getElevators().size()) {
			panelElevatorsLevels.removeAll();

			panelElevatorsLevels.add(new LevelPanel(building, controller));
			elevatorPanels = new ArrayList<ElevatorPanel>();

			float gradient = 0.8f;
			int oddEven = 1;
			for (VerticalTransporter item : building.getElevators()) {
				ElevatorPanel ePanel = new ElevatorPanel(item, building, this,
						new Color(gradient, gradient, gradient));
				panelElevatorsLevels.add(ePanel);

				gradient -= (oddEven > 0) ? 0.1 : -0.1;
				oddEven *= -1;
				elevatorPanels.add(ePanel);
			}
			
			//TODO: Hack, damit das Frame neu gezeichnet wird, repaint() funktioniert nicht.
			this.setSize(getWidth(), getHeight()-1);
			this.setSize(getWidth(), getHeight()+1);
		}
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
			this.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log4j.error(e);
			}
		}

	}

	public void startSimulation(Building building, List<Action> actions,boolean resetEvaluation) {
		if(resetEvaluation){
			resetEvaluations();
		}
		if (building != null) {
			controller.setBuilding(building);
		}
		controller.startSimulation(actions);
	}

	public void addElevator(Elevator e) {
		controller.addElevator(e);
	}

	public void removeElevator(int removeId) {
		controller.removeElevator(removeId);
	}

	public void doEvaluation(boolean bActions, boolean bElevator) {

		if (bActions) {
			StatisticAction s = new StatisticAction();
			s.addAction(controller.getDoneActions());
			consolePanel.addText(s.getStatistic());
		}
		if (bElevator) {
			StatisticElevator s = new StatisticElevator();
			s.addElevator(controller.getBuilding().getElevators());
			consolePanel.addText(s.getStatistic());
		}
	}

	public void quit() {
		System.exit(NORMAL);
	}

	public void startRandomSimulation(int amountAction) {
		controller.startRandomSimulation(amountAction);
	}

	public void stopRandomSimulation() {
		controller.stopRandomSimulation();
	}

	public int getSimulationSpeed() {
		return controller.getSimulationSpeed();
	}

	public void setSimulationSpeed(int speed) {
		controller.setSimulationSpeed(speed);
	}

	public void resetEvaluations() {
		controller.resetDoneActions();
		controller.resetLiftEvaluation();
	}

	public void clearActions() {
		
		
	}
}
