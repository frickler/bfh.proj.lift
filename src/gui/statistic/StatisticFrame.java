package gui.statistic;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logic.Simulation;
import logic.SimulationCompare;

import definition.Controller;

public class StatisticFrame extends JFrame {

	private static final long serialVersionUID = 8030178052210244256L;

	private ConsolePanel consolePanelElevators;
	private ConsolePanel consolePanelActions;
	private JPanel consolePanelSimuationCompare;
	private JPanel consolePanelSimuation;
	private Controller controller;
	private JTabbedPane tabbedPane;

	public StatisticFrame(Controller pcontroller) {
		this.setTitle("Wo ist mein Lift? - Statistik");
		controller = pcontroller;
		this.setSize(800, 600);
		this.setResizable(true);
		
		tabbedPane = new JTabbedPane();

		JPanel panelConsoleElevator = new JPanel();
		consolePanelElevators = new ElevatorStatisticPanel(controller);
		panelConsoleElevator.add(consolePanelElevators);
		tabbedPane.addTab("Statistik Lift", panelConsoleElevator);

		JPanel panelConsoleActions = new JPanel();
		consolePanelActions = new ActionStatisticPanel(controller);
		panelConsoleActions.add(consolePanelActions);
		tabbedPane.addTab("Statistik Actions", panelConsoleActions);
		
		this.add(tabbedPane);
	}

	public void doEvaluation(boolean bActions, boolean bElevator) {
		if(bElevator)
			tabbedPane.setSelectedIndex(0);
		if(bActions)
			tabbedPane.setSelectedIndex(1);

		this.setVisible(true);
	}

	public void compareSimulation(SimulationCompare sc) {
		try {
			
			String path = sc.saveCompareResult();
			JOptionPane.showMessageDialog(this,
					"Simulation comparison saved @ " + path);
			JPanel panelConsoleActions = new JPanel();
			consolePanelSimuationCompare = new SimulationStatisticPanel(path);
			panelConsoleActions.add(consolePanelSimuationCompare);
			tabbedPane.addTab("Simulationvergleich", consolePanelSimuationCompare);
			this.add(tabbedPane);
			tabbedPane.setSelectedIndex(2);
			this.setVisible(true);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showSimulationResult(Simulation s) {
		try {
			JPanel panelConsoleActions = new JPanel();
			consolePanelSimuation = new SimulationResultPanel(controller);
			panelConsoleActions.add(consolePanelSimuation);
			tabbedPane.addTab("Statistik Simulation", consolePanelSimuation);
			this.add(tabbedPane);		
			tabbedPane.setSelectedIndex(2);
			this.setVisible(true);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
