package gui;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.XMLSimulationReader;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class FileDialogFrame extends JFrame {

	private static final long serialVersionUID = 6794649153700018626L;

	private static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");
	private FrameMain framemain;

	public FileDialogFrame(FrameMain fm) {
		File selectedFile = null;
		this.framemain = fm;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFileChooser fc = new JFileChooser(
				"C:\\Users\\kaeserst\\Documents\\My Dropbox\\bfh\\Projekt1_7301\\Lift_feuzc1_kases1_chiller12");
		fc.setName("Select XML-configuration file");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new CustomFileFilter("xml"));
		fc.setMultiSelectionEnabled(true);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFile = fc.getSelectedFile();
			XMLSimulationReader xmlReader = new XMLSimulationReader();
			try {
				xmlReader.readPath(selectedFile.getAbsolutePath());
			} catch (SAXException e) {
				log4j.error(e);
				this.dispose();
			} catch (IOException e) {
				log4j.error(e);
				this.dispose();
			}

			int input = JOptionPane
					.showConfirmDialog(
							framemain,
							"Do you wanna reset all statistics of the elevators and the actions?",
							"Reset statistics", JOptionPane.YES_NO_OPTION);

			fm.startSimulation(xmlReader.getBuilding(), xmlReader.getActions(),
					(input == 0), xmlReader.getSimulationSpeed(),
					selectedFile.getParent());
			
		}
	}
}
