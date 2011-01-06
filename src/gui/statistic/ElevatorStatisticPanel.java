package gui.statistic;

import java.awt.Graphics;

import logic.StatisticElevator;
import definition.Controller;

public class ElevatorStatisticPanel extends ConsolePanel {

	private static final long serialVersionUID = -2187771047384226697L;

	public ElevatorStatisticPanel(Controller controller) {
		super(controller);
	}

	public void paint(Graphics g) {
		clearText();
		StatisticElevator s = new StatisticElevator();
		s.addElevator(controller.getBuilding().getElevators());
		addText(s.getStatistic());
	}
}
