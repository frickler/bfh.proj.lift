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

/**
 * 
 * @author BFH-Boys
 *
 */
public class LevelButtonPanel extends JPanel {
	private static final long serialVersionUID = 5928452566660481029L;

	private Controller controller;

	/**
	 * @param levelIndex the level of that instance
	 * @param building the building that contains this level 
	 * @param controller the controller used for this building
	 */
	public LevelButtonPanel(int levelIndex, Building building,
			Controller controller) {
		this.controller = controller;

		this.setLayout(new GridLayout(1, building.getMaxLevel()
				- building.getMinLevel() + 1));

		for (int i = building.getMinLevel(); i <= building.getMaxLevel(); i++) {
			JButton buttonLevel = new JButton(String.valueOf(i));
			if (i != levelIndex) {
				buttonLevel.addActionListener(new ActionCmdTargetFloor(
						new ElevatorAction(levelIndex, i, 1)));
			} else {
				buttonLevel.setEnabled(false);
			}
			this.add(buttonLevel);
		}
	}

	private class ActionCmdTargetFloor implements ActionListener {
		private Action action;

		public ActionCmdTargetFloor(Action action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.performAction(action);
			// TODO: implement action
			System.out.println("getStartLevel(): "
					+ this.action.getStartLevel() + " getEndLevel(): "
					+ this.action.getEndLevel());
		}
	}
}
