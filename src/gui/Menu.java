package gui;

import gui.statistic.StatisticFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;

import definition.Controller;

public class Menu {

	// Where the GUI is created:
	private JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	Controller controller;
	FrameMain framemain;
	StatisticFrame statisticFrame;

	public Menu(FrameMain fm, Controller controller) {
		// Create the menu bar.
		framemain = fm;
		this.controller = controller;
		menuBar = new JMenuBar();

		addMainMenu();
		addSimuationMenu();
		addEvaluationMenu();

	}

	private void addEvaluationMenu() {
		// Build the first menu.
		menu = new JMenu("Statistics");
		statisticFrame = new StatisticFrame(controller);
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Elevators", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statisticFrame.doEvaluation(false, true);
			}
		});
		menu.add(menuItem);
		// a group of JMenuItems
		menuItem = new JMenuItem("Actions", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statisticFrame.doEvaluation(true, false);
			}
		});
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Simulation", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					framemain.showSimulationResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Reset Statistics", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				framemain.resetEvaluations();
			}
		});
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
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int amount = 0;
					do {
						String input = JOptionPane.showInputDialog(framemain,
								"Enter action amount", "Action amount",
								JOptionPane.QUESTION_MESSAGE);
						try {
							amount = Integer.parseInt(input);
						} catch (Exception ex) {
						}
					} while (amount <= 0);
					framemain.startRandomSimulation(amount);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		// a group of JMenuItems
		menuItem = new JMenuItem("Import Simulation", KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
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
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
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

		// a group of JMenuItems
		menuItem = new JMenuItem("Save Simulation Result", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					framemain.saveSimulationResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Compare Simulations", KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));

		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					framemain.compareSimulations();
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
		menuItem = new JMenuItem("Elevator Configuration", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
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

		// a group of JMenuItems
		menuItem = new JMenuItem("Clear Actions", KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					framemain.clearActions();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menu.add(menuItem);

		// a group of JMenuItems
		menuItem = new JMenuItem("Simulation Speed", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int amount = 0;
					int currentSpeed = framemain.getSimulationSpeed();
					do {

						String input = JOptionPane.showInputDialog(framemain,
								"Enter simulation speed (1-100)\nCurrent speed is "
										+ currentSpeed, "Simulation speed",
								JOptionPane.QUESTION_MESSAGE);
						try {
							amount = Integer.parseInt(input);
						} catch (Exception ex) {
						}
					} while (amount <= 0 || amount > 100);
					framemain.setSimulationSpeed(amount);
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

		// Make sure only one algorithm is selected
		ButtonGroup btnGroup = new ButtonGroup();

		rbMenuItem = new JRadioButtonMenuItem("FiFo Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.ALT_MASK));
		btnGroup.add(rbMenuItem);
		submenu.add(rbMenuItem);
		rbMenuItem.setSelected(controller.getAlgorithmName().equalsIgnoreCase(
				"FifoAlgorithm"));
		rbMenuItem.addActionListener(new AlgorithmChangeActionListener(
				"FifoAlgorithm"));

		rbMenuItem = new JRadioButtonMenuItem("Better FiFo Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.ALT_MASK));
		btnGroup.add(rbMenuItem);
		submenu.add(rbMenuItem);
		rbMenuItem.setSelected(controller.getAlgorithmName().equalsIgnoreCase(
				"BetterFifoAlgorithm"));
		rbMenuItem.addActionListener(new AlgorithmChangeActionListener(
				"BetterFifoAlgorithm"));

		rbMenuItem = new JRadioButtonMenuItem("Pickup FiFo Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7,
				ActionEvent.ALT_MASK));
		btnGroup.add(rbMenuItem);
		submenu.add(rbMenuItem);
		rbMenuItem.setSelected(controller.getAlgorithmName().equalsIgnoreCase(
				"PickupFifoAlgorithm"));
		rbMenuItem.addActionListener(new AlgorithmChangeActionListener(
				"PickupFifoAlgorithm"));

		rbMenuItem = new JRadioButtonMenuItem("Better Pickup FiFo Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8,
				ActionEvent.ALT_MASK));
		btnGroup.add(rbMenuItem);
		submenu.add(rbMenuItem);
		rbMenuItem.setSelected(controller.getAlgorithmName().equalsIgnoreCase(
				"BetterPickupFifoAlgorithm"));
		rbMenuItem.addActionListener(new AlgorithmChangeActionListener(
				"BetterPickupFifoAlgorithm"));
		
		rbMenuItem = new JRadioButtonMenuItem("EveryLevel Pickup Algorithm");
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9,
				ActionEvent.ALT_MASK));
		btnGroup.add(rbMenuItem);
		submenu.add(rbMenuItem);
		rbMenuItem.setSelected(controller.getAlgorithmName().equalsIgnoreCase(
				"EveryLevelPickUpAlgorithm"));
		rbMenuItem.addActionListener(new AlgorithmChangeActionListener(
				"EveryLevelPickUpAlgorithm"));
		

		menu.add(submenu);
		menu.addSeparator();

		menuItem = new JMenuItem("About", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
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

		menuItem = new JMenuItem("Shut Down", KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
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

	private class AlgorithmChangeActionListener implements ActionListener {

		private String algorithmName;

		public AlgorithmChangeActionListener(String algorithmName) {
			super();
			this.algorithmName = algorithmName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.setAlgorithmName(algorithmName);
		}

	}
}
