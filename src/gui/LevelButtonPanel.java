package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JComboBox comboBoxPersons;

	/**
	 * @param levelIndex the level of that instance
	 * @param building the building that contains this level 
	 * @param controller the controller used for this building
	 * @param comboBoxPersons 
	 */
	public LevelButtonPanel(int levelIndex, Building building,
			Controller controller, JComboBox comboBoxPersons) {
		this.controller = controller;
		this.comboBoxPersons = comboBoxPersons;

		this.setLayout(new GridLayout(1, building.getMaxLevel()
				- building.getMinLevel() + 1));

		for (int i = building.getMinLevel(); i <= building.getMaxLevel(); i++) {
			JButton buttonLevel = new JButton();
			buttonLevel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			buttonLevel.setText(String.valueOf(i));
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
				Action a = new ElevatorAction(action.getStartLevel(), action.getEndLevel(), Integer.valueOf(comboBoxPersons.getSelectedItem().toString()));
				controller.performAction(a);
			} catch (IllegalActionException e1) {
				log4j.error(e1);
			}
		}
	}
}
