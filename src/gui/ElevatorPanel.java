package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.io.Converters;

import definition.IBuilding;
import definition.ILiftable;


public class ElevatorPanel extends JPanel {
	public ElevatorPanel(ILiftable elevator, IBuilding building, JFrame frameMain){
		int pixelPerLevel = (int) Math.floor((float)frameMain.getContentPane().getHeight() / (building.getMaxLevel() - building.getMinLevel() + 1));

		//TODO: funktioniert so wahrscheinlich nicht auf Windows...
		ImageIcon icon = new ImageIcon("src/gui/icons/elevator.png");
		Image scaledImage = icon.getImage().getScaledInstance(pixelPerLevel, pixelPerLevel, 1);
		icon = new ImageIcon(scaledImage);

		int bottomPosition = frameMain.getContentPane().getHeight() - icon.getIconHeight();
		float relativeLevel = (float) elevator.getCurrentLevel() - building.getMinLevel();
		int verticalPosition = bottomPosition - (int) Math.floor(relativeLevel * pixelPerLevel); 
			
		this.setLayout(null);
		JLabel label = new JLabel(icon);
		label.setSize(icon.getIconWidth(), icon.getIconHeight());
		label.setLocation(0, verticalPosition);
		this.add(label);
	}
}
