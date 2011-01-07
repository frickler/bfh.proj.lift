package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import definition.Building;
import definition.VerticalTransporter;

/**
 * 
 * @author BFH-Boys
 * 
 */
public class ElevatorPanel extends JPanel {

	private static final long serialVersionUID = 3356838193898117742L;
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");
	private VerticalTransporter elevator;
	private Building building;
	private ImageIcon icon;
	private JLabel jiconlabel;
	private JPanel background;
	private int pixelPerLevel;
	private int displayedPeople = 0;
	private JLabel infolabel;

	/**
	 * @param elevator
	 *            elevator that will be visualized in this panel
	 * @param building
	 *            building that contains the elevator
	 * @param frameMain
	 *            the main JFrame
	 */
	public ElevatorPanel(VerticalTransporter elevator, Building building,
			JFrame frameMain, Color backGroundColor) {
		this.elevator = elevator;
		this.building = building;
		icon = new ImageIcon();
		jiconlabel = new JLabel(icon);
		background = new JPanel();
		this.add(jiconlabel);
		
		infolabel = new JLabel();
		Font f = new Font(Font.SANS_SERIF,Font.PLAIN,16);
		infolabel.setFont(f);
		infolabel.setVerticalTextPosition(JLabel.TOP);
		infolabel.setBounds(0, 0, 200, 180);	
		this.setLayout(null);
		this.add(infolabel);
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
	public void rearrageImage(int width, int height) {
		ImageIcon iconTemp = GetImageIcon();
		Image scaledImage = iconTemp.getImage().getScaledInstance(width,
				height, 1);
		icon.setImage(scaledImage);
		jiconlabel.setSize(icon.getIconWidth(), icon.getIconHeight());
		//log4j.debug("rescaled elevator icon to height:" + icon.getIconHeight() + " width:" + icon.getIconWidth());
	}

	private ImageIcon GetImageIcon() {
		// TODO Auto-generated method stub
		int i = elevator.getCurrentPeople();
		if(i>5){
			
		//return new ImageIcon("src/gui/icons/elevator_more.png");
		return new ImageIcon(ClassLoader.getSystemResource("gui/icons/elevator_more.png"));

		}else{
			if(i <= 0){
				//return new ImageIcon("src/gui/icons/elevator_0.png");
				return new ImageIcon(ClassLoader.getSystemResource("gui/icons/elevator_0.png"));
				
			}
			if(i != 1){
				//return new ImageIcon("src/gui/icons/elevator_"+i+".png");
				return new ImageIcon(ClassLoader.getSystemResource("gui/icons/elevator_"+i+".png"));
			}else{		
					int rand = new Random((int) (Math.random() * 10000)).nextInt(30);
					if(rand == 26){
						//return new ImageIcon("src/gui/icons/elevator_easteregg.png");
						return new ImageIcon(ClassLoader.getSystemResource("gui/icons/elevator_easteregg.png"));
					}else{
						//return new ImageIcon("src/gui/icons/elevator_"+i+".png");
						return new ImageIcon(ClassLoader.getSystemResource("gui/icons/elevator_"+i+".png"));
					}
			}
		}
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
		if (pixelPerLevel != icon.getIconHeight() || displayedPeople != elevator.getCurrentPeople()) {
			this.displayedPeople = elevator.getCurrentPeople();					
			rearrageImage(pixelPerLevel, pixelPerLevel);
		}
		String sInfo = "<html><h3>Elevator "+elevator.getIdentityNumber()+"</h3><h4>";
		sInfo += "People LoadLevel: "+elevator.getCurrentPeople()+" of "+elevator.getMaxPeople();
		sInfo += "<br/>Speed max: "+elevator.getMaxSpeed();
		sInfo += "<br/>Speed current: "+(double) Math.round(elevator.getCurrentSpeed()*100)/100;
		sInfo += "<br/>Acceleration: "+elevator.getAcceleration();		
		sInfo += "<br/>Position: "+elevator.getCurrentLevel()+ "(L) "+(double) Math.round(elevator.getCurrentPosition()*100)/100;
		sInfo += "<br/>LevelRange: "+elevator.getMinLevel()+" to "+elevator.getMaxLevel();
		sInfo += "<br/>Levels driven: "+elevator.getDrivenLevels()+" empty: "+elevator.getDrivenLevelsEmpty();
		sInfo += "<br/>TimeInMotion: "+Math.round(elevator.getTimeInMotion()/1000)+" empty: "+Math.round(elevator.getTimeInMotionEmpty()/1000);
		sInfo +=  "</h4></html>";
				
		infolabel.setText(sInfo);
		
		int bottomPosition = this.getHeight() - icon.getIconHeight();

		// zero-based level of the current elevator
		float relativeLevel = (float) elevator.getCurrentPosition()
				- building.getMinLevel();
		int verticalPosition = bottomPosition
				- (int) Math.floor(relativeLevel * pixelPerLevel);

		// the elevator icon is centered within the panel
		int horizontalPosition = this.getWidth() / 2 - icon.getIconWidth() / 2;
		jiconlabel.setLocation(horizontalPosition, verticalPosition);
	}
}
