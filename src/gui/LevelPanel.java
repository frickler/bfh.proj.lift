package gui;

import java.lang.String;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import definition.Building;
import definition.Controller;


/**
 * 
 * @author BFH-Boys
 *
 */
public class LevelPanel extends JPanel {
	private static final long serialVersionUID = 7151223026491488177L;

	/**
	 * @param building the building this instance is created for
	 * @param controller the controller used for this building
	 */
	public LevelPanel(Building building, Controller controller) {
		// create a GridLayout with the number of levels
		this.setLayout(new GridLayout(building.getMaxLevel()
				- building.getMinLevel() + 1, 1));

		// for each level from top to bottom create a label with the level
		// number and a LevelButtonPanel
		for (int i = building.getMaxLevel(); i >= building.getMinLevel(); i--) {
			JPanel panelLevelMain = new JPanel();
			panelLevelMain.setLayout(new GridLayout(2, 1));
			
			JPanel panelLevelAndPersons = new JPanel();
			panelLevelAndPersons.add(new JLabel("level: " + String.valueOf(i) + " / persons: "));
			
			JComboBox comboBoxPersons = new JComboBox();
			for(int persons = 1; persons < 10; persons++){
				comboBoxPersons.addItem(persons);
			}
			panelLevelAndPersons.add(comboBoxPersons);
			
			panelLevelMain.add(panelLevelAndPersons);
			panelLevelMain.add(new LevelButtonPanel(i, building, controller, comboBoxPersons));
			this.add(panelLevelMain);
		}
		this.validate();
	}
}
