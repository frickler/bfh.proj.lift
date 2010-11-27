package gui;

import java.awt.FileDialog;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import logic.ElevatorActionXMLReader;
import logic.ElevatorController;
import logic.FiFoAlgorithm;
import definition.Building;

public class FileDialogFrame extends JFrame {

	private static final long serialVersionUID = 6794649153700018626L;

	private static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	public FileDialogFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FileDialog dialog = new FileDialog(this, "Select XML-configuration file");
		
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

		Building building = xmlReader.getBuilding();
		
		if(building != null){
			this.dispose();
			try {
			ElevatorController controller = new ElevatorController(building, FiFoAlgorithm.class);
			controller.startController();

			
				Thread t = new Thread(new FrameMain(building, controller));
				t.start();
			} catch (Exception e) {
				log4j.error(e);
			}
		}
	}
}
