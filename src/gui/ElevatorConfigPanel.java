package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Elevator;
import logic.Tower;

import org.apache.log4j.Logger;

import definition.Building;
import definition.Controller;

public class ElevatorConfigPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator.gui");

	Building b;
	JTextField txtmaxspeed, txtmaxlevel, txtminlevel, txtstartlevel,
			txtmaxpeople, txtacceleration, txtcolumnindex;
	JLabel lblErrorAdd, lblErrorRemove;

	public ElevatorConfigPanel(Building building) throws Exception {
		this.b = building;
		this.setResizable(true);
		this.setLayout(null);

		int line = 20;

		JLabel label = new JLabel();
		label.setText("add new elevator");
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		label.setFont(f);
		label.setBounds(50, line, 150, 20);
		this.add(label);
		line += 30;

		label = new JLabel();
		label.setText("minlevel");
		label.setBounds(50, line, 100, 20);
		this.add(label);

		txtminlevel = new JTextField(3);
		txtminlevel.setText("0");
		txtminlevel.setBounds(150, line, 20, 20);
		this.add(txtminlevel);

		label = new JLabel();
		label.setText("maxlevel");
		label.setBounds(200, line, 100, 20);
		this.add(label);

		txtmaxlevel = new JTextField(3);
		txtmaxlevel.setText("4");
		txtmaxlevel.setBounds(300, line, 20, 20);
		this.add(txtmaxlevel);

		line += 30;

		label = new JLabel();
		label.setText("startlevel");
		label.setBounds(50, line, 100, 20);
		this.add(label);

		txtstartlevel = new JTextField(3);
		txtstartlevel.setText("2");
		txtstartlevel.setBounds(150, line, 20, 20);
		this.add(txtstartlevel);

		label = new JLabel();
		label.setText("maxpeople");
		label.setBounds(200, line, 100, 20);
		this.add(label);

		txtmaxpeople = new JTextField(3);
		txtmaxpeople.setText("6");
		txtmaxpeople.setBounds(300, line, 20, 20);
		this.add(txtmaxpeople);

		line += 30;

		label = new JLabel();
		label.setText("maxspeed");
		label.setBounds(50, line, 100, 20);
		this.add(label);

		txtmaxspeed = new JTextField(3);
		txtmaxspeed.setBounds(150, line, 20, 20);
		txtmaxspeed.setText("20");
		this.add(txtmaxspeed);

		label = new JLabel();
		label.setText("acceleration");
		label.setBounds(200, line, 100, 20);
		this.add(label);

		txtacceleration = new JTextField(3);
		txtacceleration.setText("2");
		txtacceleration.setBounds(300, line, 20, 20);
		this.add(txtacceleration);

		line += 30;

		lblErrorAdd = new JLabel();
		lblErrorAdd.setBounds(50, line, 120, 40);
		this.add(lblErrorAdd);

		JButton b = new JButton();
		b.setText("add elevator");
		b.setBounds(170, line, 150, 40);

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addElevator();
			}
		});
		this.add(b);
		// remove elevator

		line += 80;

		label = new JLabel();
		label.setText("remove elevator");
		label.setFont(f);
		label.setBounds(50, line, 150, 20);
		this.add(label);
		line += 30;

		label = new JLabel();
		label.setText("column index");
		label.setBounds(50, line, 100, 20);
		this.add(label);

		txtcolumnindex = new JTextField(3);
		txtcolumnindex.setBounds(150, line, 20, 20);
		this.add(txtcolumnindex);

		line += 30;

		lblErrorRemove = new JLabel();
		lblErrorRemove.setBounds(50, line, 120, 40);
		this.add(lblErrorRemove);

		b = new JButton();
		b.setText("remove elevator");
		b.setBounds(170, line, 150, 40);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				removeElevator();
			}
		});
		this.add(b);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Elevator Configuration");

		line += 100;
		this.setSize(370, line);
		this.setVisible(true);
	}

	private void close() {
		this.close();
	}

	private void addElevator() {

		Elevator e = null;
		try {
			float maxSpeed = Float.parseFloat(txtmaxspeed.getText());
			int minLevel = Integer.parseInt(txtminlevel.getText());
			int maxLevel = Integer.parseInt(txtmaxlevel.getText());
			int maxPeople = Integer.parseInt(txtmaxpeople.getText());
			float acceleration = Float.parseFloat(txtacceleration.getText());
			int startLevel = Integer.parseInt(txtstartlevel.getText());

			e = new Elevator(minLevel, maxLevel, maxPeople, startLevel,
					maxSpeed, acceleration);

		} catch (Exception ex) {
			lblErrorAdd.setText("parameter invalid");
		}
		if (e != null) {
			this.b.addElevator(e);
			lblErrorAdd.setText("elevator added");
		} else {
			lblErrorAdd.setText("parameter invalid");
		}

	}

	private void removeElevator() {

		try {
			int removeId = Integer.parseInt(txtcolumnindex.getText());
			this.b.getElevators().remove(removeId);
			lblErrorRemove.setText("elevator removed");
		} catch (Exception ex) {
			lblErrorRemove.setText("column index invalid");
		}
	}
}
