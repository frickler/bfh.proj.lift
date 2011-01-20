package gui.statistic;

import java.awt.Graphics;


import definition.Controller;

public class SimulationResultPanel extends ConsolePanel {

	private static final long serialVersionUID = -6633228998418781167L;

	public SimulationResultPanel(Controller controller) {
		super(controller);
	}

	public void paint(Graphics g) {
		clearText();
		if (controller.getSimulation() == null) {
			addTextNewLine("no simulation started");
		} else {
			try {
				addTextNewLine(controller.getSimulation().getResult());
			} catch (Exception e) {
				addTextNewLine(e.getStackTrace().toString());
			}

		}

	}
}