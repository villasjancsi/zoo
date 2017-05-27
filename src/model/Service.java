package model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import javafx.geometry.Point2D;
import view.MainController;

public final class Service {
	private static MainController mainController;
	private static DAO dao;
	private static Zoo zoo;
	private static Thread zooManagementThread;
	private static List<Point2D> locations = new ArrayList<Point2D>();
	
	public static double ratio = 1f;
	
	public static DAO initialize() {
		//DAO contains Types and reads the current save from XML
		dao = new DAO();
		//the session's changes will be stored in Zoo
		zoo = dao.getZoo();

		locations.add(new Point2D(	85.0, 	39.0));
		locations.add(new Point2D(	85.0, 	314.0));
		locations.add(new Point2D(	85.0, 	583.0));

		locations.add(new Point2D(	540.0, 	39.0));
		locations.add(new Point2D(	540.0, 	314.0));
		locations.add(new Point2D(	540.0, 	583.0));

		locations.add(new Point2D(	1023.0,	39.0));
		locations.add(new Point2D(	1023.0,	314.0));
		locations.add(new Point2D(	1023.0,	583.0));

		locations.add(new Point2D(	1488.0,	39.0));
		locations.add(new Point2D(	1488.0, 314.0));
		locations.add(new Point2D(	1488.0, 583.0));
		
		zooManagementThread = new Thread(new ZooManagement(zoo, zoo.getTimeInHour(), mainController));
		zooManagementThread.start();
		return dao;
	}
	
	public static Point2D getBuildingLocation(double x, double y) {
		Point2D ret = locations.get(0);
		double diff = Math.abs(ret.getX()-x) + Math.abs(ret.getY()-y);
		for(Point2D point : locations) {
			if(Math.abs(point.getX()-x) + Math.abs(point.getY()-y) < diff) {
				ret = point;
				diff = Math.abs(point.getX()-x) + Math.abs(point.getY()-y);
			}
		}
		return ret;
	}
	
	public static void close() {
		//occurs on closing the application
		//shut the other thread, and save the game
		zooManagementThread.interrupt();
		try {
			dao.writeGameSave();
		} catch (TransformerException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static String numberFormatter(double value) {
		return String.format("%.02f", value);
	}
	
	public static String moneyFormatter(double value) {
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		sb.append(numberFormatter(value));
		return sb.toString();
	}
	
	
	
	public static BuildingType getBuildingType(String type) {
		return dao.getBuildingTypesMap().get(type);
	}
	
	public static AnimalType getAnimalType(String type) {
		return dao.getAnimalTypesMap().get(type);
	}
	
	public static EmployeeType getEmployeeType(String type) {
		return dao.getEmployeeTypesMap().get(type);
	}
	
	public static List<Building> getBuildings() {
		return zoo.getBuildings();
	}

	public static MainController getMainController() {
		return mainController;
	}

	public static void setMainController(MainController mainController) {
		Service.mainController = mainController;
	}

	public static Zoo getZoo() {
		return zoo;
	}

	public static void setZoo(Zoo zoo) {
		Service.zoo = zoo;
	}

	public static Thread getZooManagementThread() {
		return zooManagementThread;
	}

	public static void setZooManagementThread(Thread zooManagementThread) {
		Service.zooManagementThread = zooManagementThread;
	}
	
	
	
}
