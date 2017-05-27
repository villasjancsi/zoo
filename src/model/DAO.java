package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DAO {
	private Map<String, AnimalType> animalTypes = new HashMap<>();
	private Map<String, BuildingType> buildingTypes = new HashMap<>(); 
	private Map<String, EmployeeType> employeeTypes = new HashMap<>();
	private Zoo zoo;
	
	public DAO() {
		super();

		setAnimalTypes();
		setBuildingTypes();
		setEmployeeTypes();
		
		zoo = new Zoo(2000.0, 1.0, 0, new ArrayList<>());
		try {
			readGameSave("gamesave.xml");
		} catch (ParserConfigurationException | SAXException | IOException e) {	}
	}
	
	private void setAnimalTypes() {
		//key, value(name, maxAge, hourlyCost, price, value, width, height, picUrl)
		/*animalTypes.put("Bunny", new AnimalType("Bunny", 7*365*24, 5.0, 30.0, 1, 40.0, 40.0, DAO.class.getResource("../resources/bunny.png").toString()));
		animalTypes.put("Monkey", new AnimalType("Monkey", 20*365*24, 10.0, 50.0, 2, 50.0, 60.0, DAO.class.getResource("../resources/monkey.png").toString()));
		animalTypes.put("Bear", new AnimalType("Bear", 20*365*24, 20.0, 100.0, 3, 130.0, 130.0, DAO.class.getResource("../resources/bear.png").toString()));
		animalTypes.put("Penguin", new AnimalType("Penguin", 20*365*24, 20.0, 500.0, 3, 55.0, 55.0, DAO.class.getResource("../resources/penguin.png").toString()));*/
		
		//				key							name		lifespan	hC	  Cost 		Val	Width	Height	Pic. Url
		animalTypes.put("Cow", 		new AnimalType("Cow", 		20*365*24, 10.0, 100.0, 	1, 	80.0, 	80.0, 	DAO.class.getResource("../resources/cow.png").toString()));
		animalTypes.put("Goat", 	new AnimalType("Goat", 		24*365*24, 10.0, 100.0, 	1,  80.0, 	80.0, 	DAO.class.getResource("../resources/goat.png").toString()));
		animalTypes.put("Pig", 		new AnimalType("Pig", 		10*365*24, 10.0, 100.0, 	1,  80.0, 	80.0, 	DAO.class.getResource("../resources/pig.png").toString()));
		animalTypes.put("Squirrel", new AnimalType("Squirrel", 	10*365*24, 5.0,  200.0, 	1,  60.0, 	60.0, 	DAO.class.getResource("../resources/squirrel.png").toString()));
		animalTypes.put("Frog", 	new AnimalType("Frog", 		10*365*24, 5.0,  100.0, 	1,  60.0, 	60.0, 	DAO.class.getResource("../resources/frog.png").toString()));
		
		animalTypes.put("Tortoise",	new AnimalType("Tortoise", 	200*365*24,30.0, 500.0, 	2,  80.0, 	80.0, 	DAO.class.getResource("../resources/tortoise.png").toString()));
		animalTypes.put("Monkey", 	new AnimalType("Monkey", 	25*365*24, 20.0, 350.0, 	2,  80.0, 	80.0, 	DAO.class.getResource("../resources/monkey.png").toString()));
		animalTypes.put("Owl",	 	new AnimalType("Owl", 		4*365*24,  10.0, 350.0, 	2,  60.0, 	60.0, 	DAO.class.getResource("../resources/owl.png").toString()));
		animalTypes.put("Zebra", 	new AnimalType("Zebra", 	30*365*24, 20.0, 400.0, 	2,  80.0, 	80.0, 	DAO.class.getResource("../resources/zebra.png").toString()));
		
		animalTypes.put("Bear",	 	new AnimalType("Bear", 		20*365*24, 30.0, 1000.0, 	3,  110.0, 	110.0, 	DAO.class.getResource("../resources/bear.png").toString()));
		animalTypes.put("Gnu", 		new AnimalType("Gnu", 		30*365*24, 30.0, 3000.0, 	3,  80.0, 	80.0, 	DAO.class.getResource("../resources/gnu.png").toString()));
		animalTypes.put("Seal",	 	new AnimalType("Seal", 		15*365*24, 30.0, 3000.0, 	3,  80.0, 	80.0, 	DAO.class.getResource("../resources/seal.png").toString()));
		animalTypes.put("Deer", 	new AnimalType("Deer", 		12*365*24, 30.0, 2000.0, 	3,  110.0, 	110.0, 	DAO.class.getResource("../resources/deer.png").toString()));
		animalTypes.put("Elephant", new AnimalType("Elephant", 	60*365*24, 30.0, 5000.0, 	3,  110.0, 	110.0, 	DAO.class.getResource("../resources/elephant.png").toString()))
		;
		animalTypes.put("Hippo", 	new AnimalType("Hippo", 	40*365*24, 50.0, 8000.0, 	4,  110.0, 	110.0, 	DAO.class.getResource("../resources/hippo.png").toString()));
		animalTypes.put("Koala", 	new AnimalType("Koala", 	10*365*24, 50.0, 5000.0, 	4,  60.0, 	60.0, 	DAO.class.getResource("../resources/koala.png").toString()));
		animalTypes.put("Leopard", 	new AnimalType("Leopard", 	13*365*24, 50.0, 10000.0, 	4,  80.0, 	80.0, 	DAO.class.getResource("../resources/leopard.png").toString()));
		
		animalTypes.put("Polarbear",new AnimalType("Polarbear", 20*365*24, 100.0, 15000.0, 	5,  110.0, 	110.0, 	DAO.class.getResource("../resources/polarbear.png").toString()));
		animalTypes.put("Lion", 	new AnimalType("Lion", 		13*365*24, 100.0, 15000.0, 	5,  80.0, 	80.0, 	DAO.class.getResource("../resources/lion.png").toString()));


		
	}
	
	private void setBuildingTypes() {
		buildingTypes.put(
				"B_Corral", 
				new BuildingType("B_Corral", 
									200.0, 
									3,
									acceptableAnimals("Cow", "Elephant", "Goat", "Pig", "Zebra"),
									350.0,
									175.0,
									50.0,
									30.0,
									DAO.class.getResource("../resources/corral.png").toString()));
		
		buildingTypes.put("B_Small_cage",
				new BuildingType("B_Small_cage", 
									500.0, 
									3, 
									acceptableAnimals("Koala", "Monkey", "Owl", "Squirrel"), 
									350.0, 
									175.0, 
									50.0,
									30.0,
									DAO.class.getResource("../resources/small_cage.png").toString()));
		
		buildingTypes.put("B_Cage", 
				new BuildingType("B_Cage", 
									1000.0, 
									3, 
									acceptableAnimals("Leopard", "Lion", "Bear", "Gnu"), 
									350.0, 
									175.0, 
									50.0,
									30.0,
									DAO.class.getResource("../resources/cage.png").toString()));
		
		buildingTypes.put("B_Icy_corral",
				new BuildingType("B_Icy_corral", 
									5000.0, 
									3, 
									acceptableAnimals("PolarBear", "Seal", "Deer"), 
									350.0, 
									175.0, 
									50.0,
									30.0,
									DAO.class.getResource("../resources/icy_corral.png").toString()));
		
		buildingTypes.put("B_Water_corral",
				new BuildingType("B_Water_corral", 
									5000.0, 
									3, 
									acceptableAnimals("Bear", "Hippo", "Frog", "Seal", "Tortoise"), 
									350.0, 
									175.0, 
									50.0,
									30.0,
									DAO.class.getResource("../resources/water_corral.png").toString()));
	}
	
	private void setEmployeeTypes() {
		//public EmployeeType(String name, double price, int value, double hourlyCost, double width, double height, String pictureUrl) {
		employeeTypes.put("E_AnimalCare01", new EmployeeType(
								"E_AnimalCare01",
								100.0,
								1,
								10.0,
								50.0,
								50.0,
								DAO.class.getResource("../resources/employee.png").toString()));
		
		employeeTypes.put("E_AnimalCare02", new EmployeeType(
								"E_AnimalCare02",
								250.0,
								2,
								15.0,
								50.0,
								50.0,
								DAO.class.getResource("../resources/employee.png").toString()));
		
		employeeTypes.put("E_AnimalCare03", new EmployeeType(
								"E_AnimalCare03",
								500.0,
								3,
								20.0,
								50.0,
								50.0,
								DAO.class.getResource("../resources/employee.png").toString()));
		
		employeeTypes.put("E_AnimalCare04", new EmployeeType(
								"E_AnimalCare04",
								1000.0,
								4,
								35.0,
								50.0,
								50.0,
								DAO.class.getResource("../resources/employee.png").toString()));
		
		employeeTypes.put("E_AnimalCare05", new EmployeeType(
								"E_AnimalCare05",
								5000.0,
								5,
								50.0,
								50.0,
								50.0,
								DAO.class.getResource("../resources/employee.png").toString()));
	}
	
	public List<String> acceptableAnimals(String... animalNames) {
		List<String> ret = new ArrayList<String>();
		for(String name : animalNames) {
			ret.add(name);
		}
		return ret;
	}
	
	public Map<String, AnimalType> getAnimalTypesMap() {
		return animalTypes;
	}
	
	public List<AnimalType> getAnimalTypesList() {
		return animalTypes.values()
				.stream()
				.sorted((c1, c2) -> Integer.compare(c1.getValue(), c2.getValue()))
				.sorted((c1, c2) -> c1.getValue() == c2.getValue() ? Double.compare(c1.getPrice(), c2.getPrice()) : 0)
				.collect(Collectors.toList());
	}
	
	public Map<String, BuildingType> getBuildingTypesMap() {
		return buildingTypes;
	}
	
	public List<BuildingType> getBuildingTypesList() {
		return buildingTypes.values()
				.stream()
				.sorted((c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()))
				.collect(Collectors.toList());
	}
	
	public Map<String, EmployeeType> getEmployeeTypesMap() {
		return employeeTypes;
	}
	
	public List<EmployeeType> getEmployeeTypesList() {
		return employeeTypes.values()
				.stream()
				.sorted((c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()))
				.collect(Collectors.toList());
	}
	
	public void readGameSave(String fileName) throws ParserConfigurationException, SAXException, IOException {
		if(!(new File(fileName).exists()))
			throw new IOException();
		
		InputStream xmlFile = new FileInputStream(fileName);
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);

		
		Element rootElement = (Element)document.getElementsByTagName("zoo").item(0);
		
		zoo.setMoney(Double.valueOf(rootElement.getAttribute("money")));
		zoo.setTicketPrice(Double.valueOf(rootElement.getAttribute("ticketPrice")));
		zoo.setTimeInHour(Integer.valueOf(rootElement.getAttribute("time")));
		
		NodeList buildingNodeList = rootElement.getElementsByTagName("building");
		for(int buldingIndex = 0; buldingIndex < buildingNodeList.getLength(); buldingIndex++) {
			Node node = buildingNodeList.item(buldingIndex);
			if(node.getNodeType() == Node.ELEMENT_NODE) {				
				Element buildingElement = (Element)node;
				BuildingType buildingType = buildingTypes.get(buildingElement.getAttribute("typeName"));

				Building building = new Building(buildingType, 
						Double.valueOf(buildingElement.getAttribute("layoutX")),
						Double.valueOf(buildingElement.getAttribute("layoutY")));
				
				NodeList animalNodeList = buildingElement.getElementsByTagName("animal");
				for(int animalIndex = 0; animalIndex < animalNodeList.getLength(); animalIndex++) {
					Element animalElement = (Element)animalNodeList.item(animalIndex);
					Animal animal = new Animal();
					animal.setType(animalTypes.get(animalElement.getElementsByTagName("typeName").item(0).getTextContent()));
					animal.setAge(Integer.valueOf(animalElement.getElementsByTagName("age").item(0).getTextContent()));
					animal.setHealth(Double.valueOf(animalElement.getElementsByTagName("health").item(0).getTextContent()));
					building.addAnimal(animal);
				}
				zoo.addBuilding(building);
			}
		}
		
		NodeList employeeNodeList = rootElement.getElementsByTagName("employee");
		for(int employeeIndex = 0; employeeIndex < employeeNodeList.getLength(); employeeIndex++) {
			Node node = employeeNodeList.item(employeeIndex);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element employeeElement = (Element)node;
				Employee employee = new Employee();
				EmployeeType employeeType = employeeTypes.get(employeeElement.getElementsByTagName("typeName").item(0).getTextContent());
				
				employee.setType(employeeType);
				employee.setTire(Integer.valueOf(employeeElement.getElementsByTagName("tire").item(0).getTextContent()));
				
				zoo.hireEmployee(employee);
			}
		}
	}
	
	public void writeGameSave() throws TransformerException, ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		Element zooElement = document.createElement("zoo");
		document.appendChild(zooElement);
		zooElement.setAttribute("money", Double.toString(zoo.getMoney()));
		zooElement.setAttribute("ticketPrice", Double.toString(zoo.getTicketPrice()));
		zooElement.setAttribute("time", Integer.toString(zoo.getTimeInHour()));
		
		Element buildingsElement = document.createElement("buildings");
		zooElement.appendChild(buildingsElement);
		
		for (Building building : zoo.getBuildings()) {
			Element buildingElement = document.createElement("building");
			buildingElement.setAttribute("typeName", building.getType().getName());
			buildingElement.setAttribute("layoutX", Double.toString(building.getLayoutX()));
			buildingElement.setAttribute("layoutY", Double.toString(building.getLayoutY()));
			buildingsElement.appendChild(buildingElement);
			
			Element animalsElement = document.createElement("animals");
			buildingElement.appendChild(animalsElement);
			
			for(Animal animal : building.getAnimals()) {
				Element animalElement = document.createElement("animal");
				animalsElement.appendChild(animalElement);

				Element typeNameElement = document.createElement("typeName");
				typeNameElement.appendChild(document.createTextNode(animal.getType().getName()));
				animalElement.appendChild(typeNameElement);
				
				Element ageElement = document.createElement("age");
				ageElement.appendChild(document.createTextNode(Integer.toString(animal.getAge())));
				animalElement.appendChild(ageElement);
				
				Element healthElement = document.createElement("health");
				healthElement.appendChild(document.createTextNode(Double.toString(animal.getHealth())));
				animalElement.appendChild(healthElement);
				
			}
		}
		
		Element employeesElement = document.createElement("employees");
		zooElement.appendChild(employeesElement);
		
		for (Employee employee : zoo.getEmployees()) {
			Element employeeElement = document.createElement("employee");
			
			Element typeNameElement = document.createElement("typeName");
			typeNameElement.appendChild(document.createTextNode(employee.getType().getName()));
			employeeElement.appendChild(typeNameElement);
			
			Element tireElement = document.createElement("tire");
			tireElement.appendChild(document.createTextNode(Integer.toString(employee.getTire())));
			employeeElement.appendChild(tireElement);
			
			employeesElement.appendChild(employeeElement);
			
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File("gamesave.xml"));
		
		transformer.transform(source, result);
	}

	public Zoo getZoo() {
		return zoo;
	}

	public void setZoo(Zoo zoo) {
		this.zoo = zoo;
	}
}
