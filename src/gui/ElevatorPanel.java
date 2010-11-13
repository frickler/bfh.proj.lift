package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import definition.Building;
import definition.HorizontalTransporter;


public class ElevatorPanel extends JPanel {
	private HorizontalTransporter elevator;
	private Building building;
	private JFrame frameMain;
	private JLabel label;
	
	private int bottomPosition;
	private int pixelPerLevel;
	
	public ElevatorPanel(HorizontalTransporter elevator, Building building, JFrame frameMain){
		this.elevator = elevator;
		this.building = building;
		this.frameMain = frameMain;

		pixelPerLevel = (int) Math.floor((float)frameMain.getContentPane().getHeight() / (building.getMaxLevel() - building.getMinLevel() + 1));

		//TODO: funktioniert so wahrscheinlich nicht auf Windows...
		ImageIcon iconTemp = new ImageIcon("src/gui/icons/elevator.png");
		Image scaledImage = iconTemp.getImage().getScaledInstance(pixelPerLevel, pixelPerLevel, 1);
		ImageIcon icon = new ImageIcon(scaledImage);

		this.setLayout(null);
		label = new JLabel(icon);
		label.setSize(icon.getIconWidth(), icon.getIconHeight());
		bottomPosition = frameMain.getContentPane().getHeight() - icon.getIconHeight();
		this.add(label);
	}
	public void paint(Graphics g){
		super.paint(g);

		//zero-based level of the current elevator
		float relativeLevel = (float) elevator.getCurrentLevel() - building.getMinLevel();
		int verticalPosition = bottomPosition - (int) Math.floor(relativeLevel * pixelPerLevel); 

		label.setLocation(0, verticalPosition);
	}
}
