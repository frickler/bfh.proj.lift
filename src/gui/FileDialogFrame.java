package gui;

import java.awt.FileDialog;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.ElevatorActionXMLReader;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class FileDialogFrame extends JFrame {

	private static final long serialVersionUID = 6794649153700018626L;

	private static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");
	private FrameMain framemain;

	public FileDialogFrame(FrameMain fm) {

		this.framemain = fm;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FileDialog dialog = new FileDialog(this,
				"Select XML-configuration file");

		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);

		ElevatorActionXMLReader xmlReader = new ElevatorActionXMLReader();
		try {
			xmlReader.readPath(dialog.getDirectory() + dialog.getFile());
		} catch (SAXException e) {
			log4j.error(e);
			this.dispose();
		} catch (IOException e) {
			log4j.error(e);
			this.dispose();
		}
		
		int input = JOptionPane.showConfirmDialog(framemain,
				"Doyou wanna reset all statistics of the elevators and the actions?","Reset statistics",
				JOptionPane.YES_NO_OPTION);
		
		fm.startSimulation(xmlReader.getBuilding(),xmlReader.getActions(),(input == 0),xmlReader.getSimulationSpeed(), dialog.getDirectory());
	}
}
