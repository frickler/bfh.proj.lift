package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import logic.Elevator;
import logic.Tower;

import definition.Building;
import definition.Controller;

public class Menu {

	//Where the GUI is created:
	private JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	Building b;
	Controller controller;
	public Menu(Building building,Controller c){
	//Create the menu bar.
		building = b;
		controller = c;
	menuBar = new JMenuBar();

	//Build the first menu.
	menu = new JMenu("MittagsMenü");
	menu.setMnemonic(KeyEvent.VK_M);
	menu.getAccessibleContext().setAccessibleDescription(
	        "The only menu in this program that has menu items");
	menuBar.add(menu);

	//a group of JMenuItems
	menuItem = new JMenuItem("Elevator Configuration",
	                         KeyEvent.VK_L);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_2, ActionEvent.ALT_MASK));
	
	
	menuItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
			ElevatorConfigPanel c = new ElevatorConfigPanel(b);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		});
	
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "This doesn't really do anything");
	menu.add(menuItem);
		
	submenu = new JMenu("Select Algorithmus");
	submenu.setMnemonic(KeyEvent.VK_A);

	rbMenuItem = new JRadioButtonMenuItem("FiFo Algorithm");
	rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_5, ActionEvent.ALT_MASK));
	submenu.add(rbMenuItem);

	rbMenuItem = new JRadioButtonMenuItem("Better Algorithm");
	rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_6, ActionEvent.ALT_MASK));
	submenu.add(rbMenuItem);
	
	menu.add(submenu);
	menu.addSeparator();

	menuItem = new JMenuItem("About",
	                         KeyEvent.VK_O);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_3, ActionEvent.ALT_MASK));
	
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "This doesn't really do anything");
	menu.add(menuItem);
	
	
	menuItem = new JMenuItem("Shut Down",
	                         KeyEvent.VK_B);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_4, ActionEvent.ALT_MASK));
	
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "This doesn't really do anything");
	menu.add(menuItem);
	
	
	//Build the first menu.
	menu = new JMenu("Simulation");
	menu.setMnemonic(KeyEvent.VK_M);
	menu.getAccessibleContext().setAccessibleDescription(
	        "The only menu in this program that has menu items");
	menuBar.add(menu);
	
	//a group of JMenuItems
	menuItem = new JMenuItem("Random Simulation",
	                         KeyEvent.VK_S);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_1, ActionEvent.ALT_MASK));
	menu.add(menuItem);
	//a group of JMenuItems
	menuItem = new JMenuItem("Load Simulation",
	                         KeyEvent.VK_S);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_1, ActionEvent.ALT_MASK));
	menuItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				new FileDialogFrame(controller);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		});
	menu.add(menuItem);
	
	
	
	//a group of JMenuItems
	menuItem = new JMenuItem("Reset Simulation",
	                         KeyEvent.VK_S);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_1, ActionEvent.ALT_MASK));
	
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "This doesn't really do anything");
	menu.add(menuItem);
	
	menuBar.add(menu);
	
	/*
	menuItem = new JMenuItem("Both text and icon",
	                         new ImageIcon("images/middle.gif"));
	menuItem.setMnemonic(KeyEvent.VK_B);
	menu.add(menuItem);

	menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
	menuItem.setMnemonic(KeyEvent.VK_D);
	menu.add(menuItem);



	//a group of check box menu items
	menu.addSeparator();
	cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
	cbMenuItem.setMnemonic(KeyEvent.VK_C);
	menu.add(cbMenuItem);

	cbMenuItem = new JCheckBoxMenuItem("Another one");
	cbMenuItem.setMnemonic(KeyEvent.VK_H);
	menu.add(cbMenuItem);

	//a submenu
	menu.addSeparator();
	submenu = new JMenu("A submenu");
	submenu.setMnemonic(KeyEvent.VK_S);

	menuItem = new JMenuItem("An item in the submenu");
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_2, ActionEvent.ALT_MASK));
	submenu.add(menuItem);

	menuItem = new JMenuItem("Another item");
	submenu.add(menuItem);
	menu.add(submenu);

	//Build second menu in the menu bar.
	menu = new JMenu("Another Menu");
	menu.setMnemonic(KeyEvent.VK_N);
	menu.getAccessibleContext().setAccessibleDescription(
	        "This menu does nothing");
	menuBar.add(menu);
*/
	}
	
	public JMenuBar getMenuBar(){
		return this.menuBar;
	}
}
