package logic;

import java.io.Console;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

import org.apache.log4j.Logger;

import definition.Action;
import definition.Controller;
import exceptions.IllegalActionException;

public class Simulation extends Thread {

	// Logger
	static Logger log4j = Logger.getLogger("ch.bfh.proj1.elevator");

	private Date startTime;
	private Date endTime;
	private Controller elevatorController;
	private List<Action> actions = new ArrayList<Action>();
	private Boolean Running = false;
	private int actionsToDo = -1;
	private String result = "no simulation started yet";
	private int simulationSpeed = 1;
	private String path;

	public Simulation(Controller eController) {
		this.elevatorController = eController;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

	public void addAction(List<Action> actions) throws IllegalActionException {
		for (Action a : actions) {
			addAction(a);
		}
	}

	private void startSimulation() {
		this.startTime = new Date(System.currentTimeMillis());
		this.actionsToDo = actions.size();
		this.result = "simulation is currently running.";
		setRunning(true);
	}

	public void stopSimulation() {
		this.endTime = new Date(System.currentTimeMillis());
		setRunning(false);
	}

	public String getResult() {
		return this.result;
	}

	@Override
	public void run() {
		startSimulation();
		while (isRunning()) {
			if (elevatorController.isBusy() || elevatorController.getTodoActionsAmount()>0 || actions.size() > 0) {
				for (int i = 0; i < actions.size(); i++) {
					log4j.debug("Action to give the controller "+actions.size());
					Boolean add = false;
					if (actions.get(i) instanceof DelayedElevatorAction) {

						DelayedElevatorAction di = (DelayedElevatorAction) actions
								.get(i);
						Calendar addTime = Calendar.getInstance();
						addTime.setTime(this.startTime);
						addTime.add(Calendar.SECOND, di.getDelayInSeconds()
								/ simulationSpeed);
						Calendar currentTime = Calendar.getInstance();
						currentTime.setTimeInMillis(System.currentTimeMillis());
						
						SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
						
						log4j.debug("Compare (ActionAddTime):"+dateformat.format(addTime.getTime())+" to CurrentTime():"+dateformat.format(currentTime.getTime()));
						if (currentTime.after(addTime)) {
							add = true;
						}
					} else {
						add = true;
					}
					if (add) {
						try {
							this.elevatorController.performAction(actions
									.get(i));
							log4j.debug(" Simulator: action add to controller: "
									+ actions.get(i).toString());
						} catch (IllegalActionException e) {
							e.printStackTrace();
						}
						actions.remove(i);
						i--;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			} else {
				stopSimulation();
				log4j.debug("Simulator stopped: all action are completed by the controller");
				
				this.result = "Started @ " + this.startTime.toString()
						+ " and ended @" + this.endTime.toString();
				this.result += "\nIt took " + getDurationInMilliSeconds()
						+ " miliseconds to complete all actions";
				this.result += "\nSimulation speed was: " + simulationSpeed
						+ " in real time the simulation would have taken: "
						+ getRealTimeDurationFormatted();
				log4j.debug(this.result);
			}
		}
	}

	private int getDurationInMilliSeconds() {
		return (int) (this.endTime.getTime() - this.startTime.getTime());
	}

	public void setRunning(Boolean running) {
		Running = running;
	}

	public Boolean isRunning() {
		return Running;
	}

	public void generateActions(int amount) {

		int minLevel = elevatorController.getBuilding().getMinLevel();
		int maxLevel = elevatorController.getBuilding().getMaxLevel();

		Random gen = new Random((int) (Math.random() * 10000));
		String all = "";
		for (int i = 0; i < amount; i++) {
			int startLevel = gen.nextInt(maxLevel - minLevel) + minLevel;
			int endLevel = startLevel;
			while (endLevel == startLevel)
				endLevel = gen.nextInt(maxLevel - minLevel) + minLevel;

			int pepoleAmount = gen.nextInt(10) + 1;
			int delay = gen.nextInt(40) / simulationSpeed;

			Action a = new DelayedElevatorAction(startLevel, endLevel,
					pepoleAmount, delay);
			actions.add(a);
			all += a.toXML() + "\n";
			log4j.debug("Simulator: action generated: " + a.toString());
		}
		// System.out.println(all);
	}

	public void clearActions() {
		log4j.debug(actions.size() + " actions cleared in simulator");
		actions.clear();
	}

	public void setSimulationSpeed(int speed) {
		this.simulationSpeed = speed;
	}

	public void setPath(String ppath) {
		this.path = ppath;
	}

	public String getPath() {

		return this.path;
	}

	public Element setXMLResult(Document doc)
			throws ParserConfigurationException {
		Element n = doc.createElement("simluation");
		n.setAttribute("startDate", startTime.toString());
		n.setAttribute("endDate", endTime.toString());
		n.setAttribute("durationRealTime", getRealTimeDuration() + "");
		n.setAttribute("durationRealTimeFormatted", getRealTimeDurationFormatted() + "");
		n.setAttribute("durationInMiliSec", getDurationInMilliSeconds() + "");
		n.setAttribute("simluationSpeed", this.simulationSpeed + "");
		n.setAttribute("algorithm", elevatorController.getAlgorithmName() + "");
		return n;
	}

	private String getRealTimeDurationFormatted() {
		int timeSec = getRealTimeDuration()/1000;

		if(timeSec > 3600*24){ // grösser als ein tag
			return timeSec / 3600 +" hour(s)";
		}
		
		if(timeSec > 3600){ // grösser als eine stunde
			return timeSec / 60 +" minute(s)";
		}
		
		// grösser als eine minute
			return timeSec +" seconds(s)";
	}

	private int getRealTimeDuration() {
		return this.getDurationInMilliSeconds()*simulationSpeed;
	}

	public Element getXMLSimulation() throws ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = setXMLResult(doc);

		StatisticAction sa = new StatisticAction();
		sa.addAction(elevatorController.getDoneActions());
		Element actions = sa.getXMLStatistic(doc);
		root.appendChild(actions);

		StatisticElevator se = new StatisticElevator();
		se.setTotalSimuationTime(getRealTimeDuration());
		se.addElevator(elevatorController.getBuilding().getElevators());
		Element elevators = se.getXMLStatistic(doc);
		root.appendChild(elevators);
		doc.appendChild(root);
		return root;
	}
}
