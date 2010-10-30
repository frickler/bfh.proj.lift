package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import definition.IElevator;


public class ElevatorPanel extends JPanel {
	public ElevatorPanel(IElevator elevator){
		//TODO
		this.add(new JLabel(elevator.toString()));
	}
}
