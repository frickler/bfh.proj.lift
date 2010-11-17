package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import definition.Building;
import definition.HorizontalTransporter;


/**
 * 
 * @author BFH-Boys
 *
 */
public class ElevatorPanel extends JPanel {

	private static final long serialVersionUID = 3356838193898117742L;
	private HorizontalTransporter elevator;
	private Building building;
	private JLabel label;
	
	private int bottomPosition;
	private int pixelPerLevel;
	
	/**
	 * @param elevator elevator that will be visualized in this panel
	 * @param building building that contains the elevator
	 * @param frameMain the main JFrame
	 */
	public ElevatorPanel(HorizontalTransporter elevator, Building building, JFrame frameMain){
		this.elevator = elevator;
		this.building = building;

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
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g){
		super.paint(g);

		//zero-based level of the current elevator
		float relativeLevel = (float) elevator.getCurrentLevel() - building.getMinLevel();
		int verticalPosition = bottomPosition - (int) Math.floor(relativeLevel * pixelPerLevel); 

		label.setLocation(0, verticalPosition);
	}
}
