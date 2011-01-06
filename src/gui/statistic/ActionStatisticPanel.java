package gui.statistic;

import java.awt.Graphics;

import logic.StatisticAction;
import definition.Controller;

public class ActionStatisticPanel extends ConsolePanel {

	private static final long serialVersionUID = -6633228998418781167L;

	public ActionStatisticPanel(Controller controller) {
		super(controller);
	}

	public void paint(Graphics g) {
		clearText();
		StatisticAction s = new StatisticAction();
		s.addAction(controller.getDoneActions());
		addTextNewLine("Todo actions: "+controller.getTodoActionsAmount());
		addTextNewLine("one actions: "+controller.getDoneActions().size());
		addTextNewLine(s.getStatistic());
		
	}
}
