package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import logic.ElevatorAction;

import definition.Action;
import definition.Building;
import definition.Controller;


public class LevelButtonPanel extends JPanel {
	private Controller controller;
	
	public LevelButtonPanel(int levelIndex, Building building, Controller controller) {
		this.controller = controller;
		
		this.setLayout(new GridLayout(1, 2));

		JButton buttonUp = new JButton("up");
		JButton buttonDown = new JButton("down");

		if (levelIndex == building.getMinLevel())
			buttonDown.setEnabled(false);

		if (levelIndex == building.getMaxLevel())
			buttonUp.setEnabled(false);

		buttonUp.addActionListener(new ActionCmdTargetFloor(new ElevatorAction(levelIndex, levelIndex + 1, 0)));
		buttonDown.addActionListener(new ActionCmdTargetFloor(new ElevatorAction(levelIndex, levelIndex - 1, 0)));

		this.add(buttonDown);
		this.add(buttonUp);
	}

	private class ActionCmdTargetFloor implements ActionListener {
		private Action action;

		public ActionCmdTargetFloor(Action action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.performAction(action);
			//TODO: implement action
			System.out.println("getStartLevel(): "
					+ this.action.getStartLevel() + " getEndLevel(): "
					+ this.action.getEndLevel());
		}
	}
}
