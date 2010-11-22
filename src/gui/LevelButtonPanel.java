package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import logic.ElevatorAction;

import definition.Action;
import definition.Building;
import definition.Controller;
import exceptions.IllegalActionException;

/**
 * 
 * @author BFH-Boys
 *
 */
public class LevelButtonPanel extends JPanel {
	private static final long serialVersionUID = 5928452566660481029L;

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

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
			try {
				controller.performAction(action);
			} catch (IllegalActionException e1) {
				log4j.error(e1);
			}
		}
	}
}
