package gui.statistic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import definition.Controller;

/**
 * 
 * @author BFH-Boys
 *
 */
public class ConsolePanel extends JPanel {

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");
	protected Controller controller;
	protected TextArea textAreaConsole;
	private static final long serialVersionUID = -5903280586820362452L;

	public ConsolePanel(Controller controller){
		this.controller = controller;
		
		textAreaConsole = new TextArea("", 30, 90, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textAreaConsole.setBackground(Color.WHITE);
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
		
	public void paint(Graphics g)
	{
	}
}
