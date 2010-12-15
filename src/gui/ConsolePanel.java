package gui;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import definition.Building;

/**
 * 
 * @author BFH-Boys
 *
 */
public class ConsolePanel extends JPanel {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");
	TextArea textAreaConsole;
	private static final long serialVersionUID = -5903280586820362452L;

	public ConsolePanel(Building building, JFrame frameMain){
		textAreaConsole = new TextArea("", 6, 120, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textAreaConsole.setBackground(Color.WHITE);
		textAreaConsole.setSize(this.getSize());
		this.add(textAreaConsole);
	}
	public void addText(String text){
		textAreaConsole.append(text);
	}
	public void addTextNewLine(String text){
		addText("\n"+text);
	}
	
	public void setText(String text){
		clearText();
		addText(text);
	}
	
	public void clearText(){
		textAreaConsole.setText("");
	}
}
