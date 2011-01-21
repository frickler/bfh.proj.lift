package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import logic.ElevatorAction;

import definition.Action;
import definition.Building;
import definition.Controller;
import exceptions.IllegalActionException;

public class SkyscraperLevelPanel extends JPanel {
	private static final long serialVersionUID = -8329798040055837972L;

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	private Controller controller;
	private JComboBox comboBoxPersons;
	private JComboBox comboBoxStartLevel;
	private JComboBox comboBoxEndLevel;

	/**
	 * @param building
	 *            the building this instance is created for
	 * @param controller
	 *            the controller used for this building
	 */
	public SkyscraperLevelPanel(Building building, Controller controller) {
		this.controller = controller;

		comboBoxStartLevel = new JComboBox();
		comboBoxEndLevel = new JComboBox();

		for (int i = building.getMinLevel(); i <= building.getMaxLevel(); i++) {
			comboBoxStartLevel.addItem(i);
			comboBoxEndLevel.addItem(i);
		}

		this.add(new JLabel("Start level: "));
		this.add(comboBoxStartLevel);
		this.add(getNewLineLabel());
		this.add(new JLabel("End level: "));
		this.add(comboBoxEndLevel);
		this.add(getNewLineLabel());
		this.add(new JLabel("persons: "));
		comboBoxPersons = new JComboBox();

		for (int persons = 1; persons < 10; persons++) {
			comboBoxPersons.addItem(persons);
		}
		this.add(comboBoxPersons);
		this.add(getNewLineLabel());

		JButton buttonGo = new JButton();
		buttonGo.setText("go");
		buttonGo.addActionListener(new ActionCmdTargetFloor());
		this.add(buttonGo);

		this.validate();
	}

	private JLabel getNewLineLabel() {
		JLabel br = new JLabel();
		br.setPreferredSize(new Dimension(500, 1));
		return br;
	}

	private class ActionCmdTargetFloor implements ActionListener {

		public ActionCmdTargetFloor() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Action a = new ElevatorAction(Integer
						.valueOf(comboBoxStartLevel.getSelectedItem()
								.toString()), Integer.valueOf(comboBoxEndLevel
						.getSelectedItem().toString()), Integer
						.valueOf(comboBoxPersons.getSelectedItem().toString()));
				controller.performAction(a);
			} catch (IllegalActionException e1) {
				log4j.error(e1);
			}
		}
	}
}
