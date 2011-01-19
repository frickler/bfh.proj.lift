package gui;

import gui.statistic.StatisticFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Elevator;
import logic.Helper;
import logic.Simulation;
import logic.SimulationCompare;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

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
	private JPanel panelElevatorsLevels;
	private static final long serialVersionUID = -6897288117049912593L;
	private List<ElevatorPanel> elevatorPanels = new ArrayList<ElevatorPanel>();
	private boolean repaintElevatorsPanel = true;

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

		this.building = building;
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Wo ist mein Lift?");

		JMenuBar menubar = new Menu(this, controller).getMenuBar();
		this.setJMenuBar(menubar);

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

		if (repaintElevatorsPanel) {
			repaintElevatorsPanel = false;
			panelElevatorsLevels.removeAll();

			// if the building contains more than 10 buildings, other panels are
			// used to display elevator target level
			if (building.getMaxLevel() - building.getMinLevel() + 1 > 10) {
				panelElevatorsLevels.add(new SkyscraperLevelPanel(building,
						controller));
			} else {
				panelElevatorsLevels.add(new LevelPanel(building, controller));
			}
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

			// TODO: Hack, damit das Frame neu gezeichnet wird, repaint()
			// funktioniert nicht.
			this.setSize(getWidth(), getHeight() - 1);
			this.setSize(getWidth(), getHeight() + 1);
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

	public void startSimulation(Building tower, List<Action> actions,
			boolean resetEvaluation, int simulationSpeed, String path) {
		if (resetEvaluation) {
			resetEvaluations();
		}

		controller.stopController();

		if (tower != null) {
			Building old = controller.getBuilding();
			int size = old.getElevators().size();
			for (VerticalTransporter e : tower.getElevators()) {
				addElevator((Elevator) e);
			}
			for (int i = 0; i < size; i++) {
				removeElevator(0);
			}

		}
		controller.startSimulation(path, simulationSpeed, actions);
	}

	public void addElevator(Elevator e) {
		repaintElevatorsPanel = true;
		controller.addElevator(e);
	}

	public void removeElevator(int removeId) {
		repaintElevatorsPanel = true;
		controller.removeElevator(removeId);
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
		return controller.getSimluationSpeed();
	}

	public void setSimulationSpeed(int speed) {
		controller.getSimulation().setSimulationSpeed(speed);
	}

	public void resetEvaluations() {
		controller.resetDoneActions();
		controller.resetLiftEvaluation();
	}

	public void clearActions() {
		controller.resetActions();
	}

	public void showSimulationResult() {
		Simulation s = controller.getSimulation();
		if (s != null) {
			StatisticFrame frm = new StatisticFrame(controller);
			frm.showSimulationResult(s);
		}
	}

	public void saveSimulationResult() {

		Simulation s = controller.getSimulation();
		if (s != null) {

			try {

				Element root = s.getXMLSimulation();
				SimpleDateFormat dateformat = new SimpleDateFormat(
						"yyyy-MM-dd_HH_mm_ss");

				String pathName = s.getPath() + dateformat.format(new Date())
						+ "_SimulationResult.xml";

				FileWriter fstream = new FileWriter(pathName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(Helper.xmlToString(root));
				out.close();
				JOptionPane.showMessageDialog(this,
						"Simulation result saved @ " + pathName);
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	public void compareSimulations() throws Exception {

		int selectedFiles = -1;

		while (selectedFiles < 2) {

			// todo nicht nur fuer kaeserst
			JFileChooser fc = new JFileChooser(
					"C:\\Users\\kaeserst\\Documents\\My Dropbox\\bfh\\Projekt1_7301\\Lift_feuzc1_kases1_chiller12");
			fc.setName("Select XML-Result file");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setFileFilter(new CustomFileFilter("xml"));
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				if (fc.getSelectedFiles().length > 1) {
					selectedFiles = fc.getSelectedFiles().length;
					JFileChooser fcXSLT = new JFileChooser(fc
							.getSelectedFiles()[0].getParent());
					fcXSLT.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fcXSLT.setName("Select XSL-Transformation file");
					fcXSLT.setFileFilter(new CustomFileFilter("xsl"));
					fcXSLT.setMultiSelectionEnabled(false);
					returnVal = fcXSLT.showOpenDialog(this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						SimulationCompare sc = new SimulationCompare(fc
								.getCurrentDirectory(), fc.getSelectedFiles(),
								fcXSLT.getSelectedFile());
						StatisticFrame frm = new StatisticFrame(controller);
						frm.compareSimulation(sc);
					} else {
						break;
					}
				} else {
					JOptionPane.showConfirmDialog(this,
							"Please select at least two files to compare them");
				}

			} else {
				break;
			}

		}
	}

}
