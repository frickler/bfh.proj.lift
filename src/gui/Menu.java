package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;

public class Menu {

	// Where the GUI is created:
	private JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	FrameMain framemain;

	public Menu(FrameMain fm) {
		// Create the menu bar.
		framemain = fm;
		menuBar = new JMenuBar();

		addMainMenu();

		addSimuationMenu();

		addEvaluationMenu();

		/*
		 * menuItem = new JMenuItem("Both text and icon", new
		 * ImageIcon("images/middle.gif")); menuItem.setMnemonic(KeyEvent.VK_B);
		 * menu.add(menuItem);
		 * 
		 * menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		 * menuItem.setMnemonic(KeyEvent.VK_D); menu.add(menuItem);
		 * 
		 * 
		 * 
		 * //a group of check box menu items menu.addSeparator(); cbMenuItem =
		 * new JCheckBoxMenuItem("A check box menu item");
		 * cbMenuItem.setMnemonic(KeyEvent.VK_C); menu.add(cbMenuItem);
		 * 
		 * cbMenuItem = new JCheckBoxMenuItem("Another one");
		 * cbMenuItem.setMnemonic(KeyEvent.VK_H); menu.add(cbMenuItem);
		 * 
		 * //a submenu menu.addSeparator(); submenu = new JMenu("A submenu");
		 * submenu.setMnemonic(KeyEvent.VK_S);
		 * 
		 * menuItem = new JMenuItem("An item in the submenu");
		 * menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_2,
		 * ActionEvent.ALT_MASK)); submenu.add(menuItem);
		 * 
		 * menuItem = new JMenuItem("Another item"); submenu.add(menuItem);
		 * menu.add(submenu);
		 * 
		 * //Build second menu in the menu bar. menu = new
		 * JMenu("Another Menu"); menu.setMnemonic(KeyEvent.VK_N);
		 * menu.getAccessibleContext().setAccessibleDescription(
		 * "This menu does nothing"); menuBar.add(menu);
		 */
	}

	private void addEvaluationMenu() {
		// Build the first menu.
		menu = new JMenu("Evaluation");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Elevators", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				framemain.doEvaluation(false, true);
			}
		});
		menu.add(menuItem);
		// a group of JMenuItems
		menuItem = new JMenuItem("Actions", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				framemain.doEvaluation(true, false);
			}
		});
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Reset Evaluation", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menu.add(menuItem);

		menuBar.add(menu);
	}

	private void addSimuationMenu() {
		// Build the first menu.
		menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_S);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Random Simulation", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int amount = 0;
					do {
						String input = JOptionPane.showInputDialog(framemain,
								"Enter action amount", "Action amount",
								JOptionPane.QUESTION_MESSAGE);
						try{
						amount = Integer.parseInt(input);
						}catch(Exception ex){}
					} while (amount <= 0);					
					framemain.startRandomSimulation(amount);				
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		// a group of JMenuItems
		menuItem = new JMenuItem("Load Simulation", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					new FileDialogFrame(framemain);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Stop Simulation", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					framemain.stopRandomSimulation();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		
		menuBar.add(menu);
	}

	private void addMainMenu() {
		// Build the first menu.
		menu = new JMenu("Main");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Elevator Configuration", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				ActionEvent.ALT_MASK));

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					ElevatorConfigPanel ecp = new ElevatorConfigPanel(framemain);
				} catch (Exception e1) {
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
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.ALT_MASK));
		submenu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Better Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.ALT_MASK));
		submenu.add(rbMenuItem);

		menu.add(submenu);
		menu.addSeparator();

		menuItem = new JMenuItem("About", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								framemain,
								"� 2010\nTom Tschiller, Krigu Feuz, Stef K�ser\nBerner Fachhochschule",
								"Elevator Simulation",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Shut Down", KeyEvent.VK_B);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framemain.quit();
			}
		});
		menu.add(menuItem);
	}

	public JMenuBar getMenuBar() {
		return this.menuBar;
	}
}
