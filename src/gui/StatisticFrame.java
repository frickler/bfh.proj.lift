package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import definition.Controller;

public class StatisticFrame extends JFrame {

	private static final long serialVersionUID = 8030178052210244256L;

	private ConsolePanel consolePanelElevators;
	private ConsolePanel consolePanelActions;
	
	private JTabbedPane tabbedPane;

	public StatisticFrame(Controller controller) {
		this.setTitle("Wo ist mein Lift? - Statistik");

		this.setSize(800, 600); // TODO
		this.setResizable(true);
		
		tabbedPane = new JTabbedPane();

		JPanel panelConsoleElevator = new JPanel();
		consolePanelElevators = new ActionStatisticPanel(controller);
		panelConsoleElevator.add(consolePanelElevators);
		tabbedPane.addTab("Statistik Lift", panelConsoleElevator);

		JPanel panelConsoleActions = new JPanel();
		consolePanelActions = new ElevatorStatisticPanel(controller);
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
}
