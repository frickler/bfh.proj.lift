package gui;

import gui.statistic.ConsolePanel;
import gui.statistic.StatisticFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import logic.Elevator;
import logic.ElevatorActionXMLReader;
import logic.Simulation;
import logic.SimulationCompare;
import logic.StatisticAction;
import logic.StatisticElevator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

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

		if (tower != null) {
			Building old = controller.getBuilding();
			int size = old.getElevators().size();
			for (VerticalTransporter e : tower.getElevators()) {
				controller.addElevator((Elevator) e);
			}
			for (int i = 0; i < size; i++) {
				controller.removeElevator(0);
			}

		}
		controller.startSimulation(path, simulationSpeed, actions);
	}

	public void addElevator(Elevator e) {
		controller.addElevator(e);
	}

	public void removeElevator(int removeId) {
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
			consolePanel.addTextNewLine(s.getResult());
		}
	}

	public void saveSimulationResult() {

		Simulation s = controller.getSimulation();
		if (s != null) {

			try {

				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element root = s.setXMLResult(doc);

				StatisticAction sa = new StatisticAction();
				sa.addAction(controller.getDoneActions());
				Element actions = sa.getXMLStatistic(doc);
				root.appendChild(actions);

				StatisticElevator se = new StatisticElevator();
				se.addElevator(controller.getBuilding().getElevators());
				Element elevators = se.getXMLStatistic(doc);
				root.appendChild(elevators);
				doc.appendChild(root);
				SimpleDateFormat dateformat = new SimpleDateFormat(
						"yyyy-MM-dd_hh_mm_ss");

				String pathName = s.getPath() + dateformat.format(new Date())
						+ "_SimulationResult.xml";

				FileWriter fstream = new FileWriter(pathName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(xmlToString(root));
				out.close();
				JOptionPane.showConfirmDialog(this,
						"Simulation result saved @ " + pathName);
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	public String xmlToString(Element node) {
		try {
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void compareSimulations() throws Exception {

		int selectedFiles = -1;

		while (selectedFiles < 2) {

			//todo nicht nur für kaeserst
			JFileChooser fc = new JFileChooser("C:\\Users\\kaeserst\\Documents\\My Dropbox\\bfh\\Projekt1_7301\\Lift_feuzc1_kases1_chiller12");
			fc.setName("Select XML-Result file");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setFileFilter(new CustomFileFilter("xml"));
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				if (fc.getSelectedFiles().length > 1) {
					selectedFiles = fc.getSelectedFiles().length;
					JFileChooser fcXSLT = new JFileChooser(fc.getSelectedFiles()[0].getParent());
					fcXSLT.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fcXSLT.setName("Select XSL-Transformation file");
					fcXSLT.setFileFilter(new CustomFileFilter("xsl"));
					fcXSLT.setMultiSelectionEnabled(false);
					returnVal = fcXSLT.showOpenDialog(this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						SimulationCompare sc = new SimulationCompare(
								fc.getCurrentDirectory(),
								fc.getSelectedFiles(), fcXSLT.getSelectedFile());
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
