package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import definition.Building;
import definition.HorizontalTransporter;

/**
 * 
 * @author BFH-Boys
 * 
 */
public class ElevatorPanel extends JPanel {

	private static final long serialVersionUID = 3356838193898117742L;
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");
	private HorizontalTransporter elevator;
	private Building building;
	private ImageIcon icon;
	private JLabel label;
	private JPanel background;

	private int pixelPerLevel;

	/**
	 * @param elevator
	 *            elevator that will be visualized in this panel
	 * @param building
	 *            building that contains the elevator
	 * @param frameMain
	 *            the main JFrame
	 */
	public ElevatorPanel(HorizontalTransporter elevator, Building building,
			JFrame frameMain, Color backGroundColor) {
		this.elevator = elevator;
		this.building = building;
		icon = new ImageIcon();
		label = new JLabel(icon);
		background = new JPanel();

		this.setLayout(null);
		this.add(label);

		background.setBackground(backGroundColor);
		this.add(background);
	}

	/**
	 * rescales the elevator icon
	 * 
	 * @param width
	 *            new width
	 * @param height
	 *            new height
	 */
	public void rescaleImage(int width, int height) {
		ImageIcon iconTemp = new ImageIcon("src/gui/icons/elevator.png");
		Image scaledImage = iconTemp.getImage().getScaledInstance(width,
				height, 1);
		icon.setImage(scaledImage);
		label.setSize(icon.getIconWidth(), icon.getIconHeight());
		log4j.debug("rescaled elevator icon to height:" + icon.getIconHeight() + " width:" + icon.getIconWidth());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		pixelPerLevel = (int) Math.floor((float) this.getHeight()
				/ (building.getMaxLevel() - building.getMinLevel() + 1));

		background.setBounds(
				0, 
				pixelPerLevel * ((building.getMaxLevel() - elevator.getMaxLevel())), 
				this.getWidth(), 
				pixelPerLevel * (elevator.getMaxLevel() - elevator.getMinLevel() + 1)
				);

		// if icon height isn't as expected the image will be rescaled
		if (pixelPerLevel != icon.getIconHeight()) {
			rescaleImage(pixelPerLevel, pixelPerLevel);
		}

		int bottomPosition = this.getHeight() - icon.getIconHeight();

		// zero-based level of the current elevator
		float relativeLevel = (float) elevator.getCurrentPosition()
				- building.getMinLevel();
		int verticalPosition = bottomPosition
				- (int) Math.floor(relativeLevel * pixelPerLevel);

		// the elevator icon is centered within the panel
		int horizontalPosition = this.getWidth() / 2 - icon.getIconWidth() / 2;
		label.setLocation(horizontalPosition, verticalPosition);
	}
}
