package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import logic.Action;

import definition.IAction;
import definition.IBuilding;


public class LevelButtonPanel extends JPanel {
	public LevelButtonPanel(int levelIndex, IBuilding building) {
		this.setLayout(new GridLayout(1, 2));

		JButton buttonUp = new JButton("up");
		JButton buttonDown = new JButton("down");

		if (levelIndex == building.getMinLevel())
			buttonDown.setEnabled(false);

		if (levelIndex == building.getMaxLevel())
			buttonUp.setEnabled(false);

		buttonUp.addActionListener(new ActionCmdTargetFloor(new Action(levelIndex, levelIndex + 1, 0)));
		buttonDown.addActionListener(new ActionCmdTargetFloor(new Action(levelIndex, levelIndex - 1, 0)));

		this.add(buttonDown);
		this.add(buttonUp);
	}

	private class ActionCmdTargetFloor implements ActionListener {
		private IAction action;

		public ActionCmdTargetFloor(IAction action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: implement action
			System.out.println("getStartLevel(): "
					+ this.action.getStartLevel() + " getEndLevel(): "
					+ this.action.getEndLevel());
		}
	}
}
