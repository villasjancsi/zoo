import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import javafx.geometry.Point2D;
import model.*;

public class UnitTest {

	private Zoo zoo = new Zoo(2000.0, 1.0, 1567, new ArrayList<Building>());
	
	@Test
	public void assertDaoAcceptableAnimals() {
		List<String> list = new ArrayList<String>();
		list.add("apple");
		list.add("peanut");
		list.add("carrot");
		assertEquals(list, new DAO().acceptableAnimals("apple", "peanut", "carrot"));
	}
	
	@Test
	public void assertDaoReadXml() {
		if(new File("gamesave.xml").exists()) {
			try {
				new DAO().readGameSave("gamesave.xml");
			} catch(ParserConfigurationException | SAXException | IOException e) {
				fail("Shouldn't have got Exception");
			}
		} else {
			try {
				new DAO().readGameSave("appletree.xml");
				fail("Should have got IOException");
			} catch(ParserConfigurationException | SAXException | IOException e) {}
		}
	}
		
	@Test
	public void assertZooDecreaseMoney() {
		double money = zoo.getMoney();
		assertEquals(new Double(money-=500), new Double(zoo.decreaseMoney(500)));
		assertEquals(new Double(money-=0.1), new Double(zoo.decreaseMoney(0.1)));
	}
	
	@Test
	public void assertZooIncreaseMoney() {
		double money = zoo.getMoney();
		assertEquals(new Double(money+=10123), new Double(zoo.increaseMoney(10123)));
		assertEquals(new Double(money+=0.25), new Double(zoo.increaseMoney(0.25)));
	}
	
	@Test
	public void assertZoofindEmployeeByValue() {
		assertEquals(new ArrayList<Employee>(), zoo.findEmployeeByValue(-1));
		assertEquals(new ArrayList<Employee>(), zoo.findEmployeeByValue(1));
		assertEquals(new ArrayList<Employee>(), zoo.findEmployeeByValue(3));
		
		List<Employee> list = new ArrayList<Employee>();
		Employee emp = new Employee(new EmployeeType("Name", 10.0, 3, 20.0, 100.0, 100.0, ""), 100);
		zoo.hireEmployee(emp);
		list.add(emp);

		assertEquals(new ArrayList<Employee>(), zoo.findEmployeeByValue(-1));
		assertEquals(new ArrayList<Employee>(), zoo.findEmployeeByValue(1));
		assertEquals(list, zoo.findEmployeeByValue(3));
	}
	
	@Test
	public void assertZooGetPopularity() {
		assertEquals(new Double(0.0), new Double(zoo.getPopularity()));
	}
	
	@Test
	public void assertZooGetMaxValue() {
		zoo.getBuildings().clear();
		assertEquals(0, zoo.getMaxValue());
		
		zoo.addBuilding(new Building(new BuildingType("", 1.0, 1, null, 100, 100, 100, 100, ""),100,100));
		zoo.getBuildings().get(0).addAnimal(new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, "")));
		assertEquals(3, zoo.getMaxValue());
	}
	
	@Test
	public void assertZooHasEnoughMoney() {
		assertTrue(zoo.hasEnoughMoney(zoo.getMoney()));
		assertTrue(zoo.hasEnoughMoney(zoo.getMoney()-1.0));
		assertFalse(zoo.hasEnoughMoney(zoo.getMoney()+1));
	}
	
	@Test
	public void assertServiceGetBuildingLocation() {
		Service.initialize();
		assertEquals(new Point2D(85.0, 39.0), Service.getBuildingLocation(100.0, 100.0));
		assertEquals(new Point2D(85.0, 39.0), Service.getBuildingLocation(80.0, 10.0));
		assertEquals(new Point2D(85.0, 39.0), Service.getBuildingLocation(85.0, 39.0));
	}
	
	@Test
	public void assertServiceNumberFormatter() {
		assertEquals("0,39", Service.numberFormatter(0.39));
		assertEquals("123,15", Service.numberFormatter(123.15159));
		assertEquals("123,16", Service.numberFormatter(123.155));
	}
	
	@Test
	public void assertServiceMoneyFormatter() {
		assertEquals("$0,39", Service.moneyFormatter(0.39));
		assertEquals("$123,15", Service.moneyFormatter(123.15159));
		assertEquals("$123,16", Service.moneyFormatter(123.155));
	}
	
	@Test
	public void assertAnimalIncreaseHealth() {
		Animal animal = new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, ""), 100, 50.0);

		assertEquals(new Double(50.0), new Double(animal.increaseHealth(0)));
		assertEquals(new Double(100.0), new Double(animal.increaseHealth(500)));
		assertEquals(new Double(animal.getHealth()), new Double(animal.increaseHealth(-500)));
	}
	
	@Test
	public void assertAnimalDecreaseHealth() {
		Animal animal = new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, ""), 100, 50.0);

		assertEquals(new Double(50.0), new Double(animal.decreaseHealth(0)));
		assertEquals(new Double(0.0), new Double(animal.decreaseHealth(500)));
		assertEquals(new Double(animal.getHealth()), new Double(animal.decreaseHealth(-500)));
	}
	
	@Test
	public void assertEmployeeIncreaseTire() {
		Employee emp = new Employee(new EmployeeType("Name", 10.0, 3, 20.0, 100.0, 100.0, ""), 50);

		assertEquals(50, emp.increaseTire(0));
		assertEquals(0, emp.increaseTire(500));
		assertEquals(emp.getTire(), emp.increaseTire(-500));
	}

	@Test
	public void assertEmployeeDecreaseTire() {
		Employee emp = new Employee(new EmployeeType("Name", 10.0, 3, 20.0, 100.0, 100.0, ""), 50);

		assertEquals(50, emp.decreaseTire(0));
		assertEquals(100, emp.decreaseTire(500));
		assertEquals(emp.getTire(), emp.decreaseTire(-500));
	}
	
	@Test
	public void assertVisitorDecreaseMoney() {
		Visitor visitor = new Visitor(10.0);

		assertEquals(new Double(10.0), new Double(visitor.decreaseMoney(-30.0)));
		assertEquals(new Double(5.0), new Double(visitor.decreaseMoney(5.0)));
		assertEquals(new Double(5.0), new Double(visitor.decreaseMoney(100.0)));
	}
	
	@Test
	public void assertZooManagementUpdateZoo() {
		ZooManagement zooManagement = new ZooManagement(zoo, 366*24 + 1, null);
		
		zoo.getBuildings().clear();		
		zoo.addBuilding(new Building(new BuildingType("", 1.0, 3, null, 100, 100, 100, 100, ""),100,100));
		
		/*
		 * Fire employee if necessary
		 */
		zoo.getEmployees().clear();
		Employee emp = new Employee(new EmployeeType("Name", 10.0, 3, 20.0, 100.0, 100.0, ""), 50);
		zoo.hireEmployee(emp);
		ArrayList<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		zooManagement.updateZoo();
		assertEquals(list, zoo.getEmployees());

		emp.setTire(45);
		zooManagement.updateZoo();
		assertEquals(new ArrayList<Employee>(), zoo.getEmployees());
		
		
		/*
		 * Dead animals
		 */

		//everything is ok
		zoo.getBuildings().get(0).addAnimal(new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, ""), 80, 100.0));
		zooManagement.updateZoo();
		assertNotEquals(new ArrayList<Animal>(), zoo.getBuildings().get(0).getAnimals());

		zoo.getBuildings().get(0).getAnimals().clear();
		
		//max age reached
		zoo.getBuildings().get(0).addAnimal(new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, ""), 150, 100.0));
		zooManagement.updateZoo();
		assertEquals(new ArrayList<Animal>(), zoo.getBuildings().get(0).getAnimals());

		//health critical
		zoo.getBuildings().get(0).addAnimal(new Animal(new AnimalType("", 100, 100, 100, 3, 100, 100, ""), 80, 0.0));
		zooManagement.updateZoo();
		assertEquals(new ArrayList<Animal>(), zoo.getBuildings().get(0).getAnimals());
	}
	
	@Test
	public void assertZooManagementDecreaseMoney() {
		ZooManagement zooManagement = new ZooManagement(zoo, 366*24 + 1, null);
		
		zoo.getBuildings().clear();		
		zoo.getEmployees().clear();
		zoo.addBuilding(new Building(new BuildingType("", 1.0, 3, null, 100, 100, 100, 100, ""),100,100));
		zoo.getBuildings().get(0).addAnimal(new Animal(new AnimalType("", 100, 50.0, 100, 3, 100, 100, ""), 80, 100.0));
		Employee emp = new Employee(new EmployeeType("Name", 10.0, 3, 10.0, 100.0, 100.0, ""), 50);
		zoo.hireEmployee(emp);
		
		assertEquals(new Double(50.0+10.0), new Double(zooManagement.decreaseMoneyWithHourlyCosts()));
	}

}
