package gui.statistic;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import logic.Helper;

import org.apache.log4j.Logger;

public class SimulationStatisticPanel extends JPanel {

	private static final long serialVersionUID = -6633228998418781167L;
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	 JLabel tp = new JLabel();
	 JScrollPane js = new JScrollPane();
	  
	public SimulationStatisticPanel(String url) {
		tp.setBounds(0, 0, 600, 800);
		js.setBounds(0, 0, 400, 400);
		 js.getViewport().add(tp);
		 this.add(js);
		 setFile(url);
	}
	
	public void setFile(String urlpath){					  
			  try {
				  String html = readFile(urlpath);
				  //todo hack do otherwise
				  
				  tp.setText(Helper.convertToHtml(html));
			    } 
			  catch (Exception e) {
			    e.printStackTrace();
			   }
		}
	
	private String readFile(String pathName) throws Exception {
		byte[] buffer = new byte[(int) new File(pathName).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(pathName));
		f.read(buffer);
		return new String(buffer);

	}
}
