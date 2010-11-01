package gui;

import java.lang.String;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import definition.IBuilding;
import definition.IController;


public class LevelPanel extends JPanel {
	public LevelPanel(IBuilding building, IController controller) {
		// create a GridLayout with the number of levels
		this.setLayout(new GridLayout(building.getMaxLevel()
				- building.getMinLevel() + 1, 1));

		// for each level from top to bottom create a label with the level
		// number and a LevelButtonPanel
		for (int i = building.getMaxLevel(); i >= building.getMinLevel(); i--) {
			JPanel panelLevel = new JPanel();
			panelLevel.setLayout(new GridLayout(2, 1));
			panelLevel.add(new JLabel("level: " + String.valueOf(i)));
			panelLevel.add(new LevelButtonPanel(i, building, controller));
			this.add(panelLevel);
		}
	}
}
