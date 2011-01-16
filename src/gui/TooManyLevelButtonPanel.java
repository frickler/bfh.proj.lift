package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.ElevatorAction;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Building;
import definition.Controller;
import exceptions.IllegalActionException;

public class TooManyLevelButtonPanel extends JPanel {
	private static final long serialVersionUID = 6441994390401100193L;

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	private Controller controller;
	private JComboBox comboBoxPersons;
	private JComboBox comboBoxLevels;

	/**
	 * @param levelIndex the level of that instance
	 * @param building the building that contains this level 
	 * @param controller the controller used for this building
	 * @param comboBoxPersons 
	 */
	public TooManyLevelButtonPanel(int levelIndex, Building building,
			Controller controller, JComboBox comboBoxPersons) {
		this.controller = controller;
		this.comboBoxPersons = comboBoxPersons;

		this.add(new JLabel("target level: "));

		comboBoxLevels = new JComboBox();

		for (int i = building.getMinLevel(); i <= building.getMaxLevel(); i++) {
			if (i != levelIndex) {
				comboBoxLevels.addItem(i);
			}
		}
		this.add(comboBoxLevels);
		
		JButton buttonGo = new JButton();
		buttonGo.setText("go");
		buttonGo.addActionListener(new ActionCmdTargetFloor(levelIndex));
		
		this.add(buttonGo);
	}

	private class ActionCmdTargetFloor implements ActionListener {
		private int startLevel;

		public ActionCmdTargetFloor(int startLevel) {
			this.startLevel = startLevel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Action a = new ElevatorAction(startLevel, Integer.valueOf(comboBoxLevels.getSelectedItem().toString()), Integer.valueOf(comboBoxPersons.getSelectedItem().toString()));
				controller.performAction(a);
			} catch (IllegalActionException e1) {
				log4j.error(e1);
			}
		}
	}
}
