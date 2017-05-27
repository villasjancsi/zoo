package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Zoo {
	private double money;
	private List<Building> buildings = new ArrayList<>();
	private List<Visitor> visitors = new ArrayList<>();
	private List<Employee> employees = new ArrayList<>();
	private double ticketPrice;
	private int timeInHour;

	public Zoo() {}
	
	public Zoo(double money, double ticketPrice, int timeInHour, List<Building> buildings) {
		super();
		this.money = money;
		this.buildings = buildings;
		this.timeInHour = timeInHour;
		this.setTicketPrice(ticketPrice);
	}
	
	public double getMoney() {
		return money;
	}
	public double setMoney(double money) {
		this.money = money;
		return this.money;
	}
	public List<Building> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
	public void addBuilding(Building b) {
		this.buildings.add(b);
	}
	
	public double decreaseMoney(double value) {
		money -= value;
		return money;
	}
	
	public double increaseMoney(double value) {
		money += value;
		return money;
	}
	
	public int getTimeInHour() {
		return timeInHour;
	}

	public void setTimeInHour(int timeInHour) {
		this.timeInHour = timeInHour;
	}
	
	public void increaseTimeInHour(int timeInHour) {
		this.timeInHour += timeInHour;
	}

	public List<Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<Visitor> visitors) {
		this.visitors = visitors;
	}
	
	public void addVisitor(Visitor visitor) {
		this.visitors.add(visitor);
	}
	
	public void removeVisitor(int index){
		this.visitors.remove(index);
	}
	
	public void clearVisitors() {
		this.visitors.clear();
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public List<Employee> findEmployeeByValue(int value) {
		return employees.stream()
				.filter(p -> p.getType().getValue() == value)
				.sorted((c1, c2) -> Integer.compare(c2.getTire(), c1.getTire()))
				.collect(Collectors.toList());
	}
	
	public void hireEmployee(Employee employee) {
		employees.add(employee);
	}
	
	public void fireEmployee(Employee employee) {
		employees.remove(employee);
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public double getPopularity() {
		double popularity = 0.0;
		int animalCount = 0, zooCapacity = 0;
		int[] animalsByValue = new int[5];
		for(Building building : buildings) {
			animalCount += building.getAnimalCount();
			zooCapacity += building.getType().getMaxAnimals();
			for(Animal animal : building.getAnimals()) {
				animalsByValue[animal.getType().getValue()-1]++;
			}
		}
		int min = animalCount, max = 0;
		for(int i = 0; i < 5; i++) {
			if(animalsByValue[i] < min)
				min = animalsByValue[i];
			if(animalsByValue[i] > max)
				max = animalsByValue[i];
		}
		if(animalCount == 0 || zooCapacity == 0)
			return 0.0;
		popularity = 0.6 * ((animalCount-(max-min))/((double)animalCount));
		popularity += 0.1 * ((double)getMaxValue()/5);
		popularity += 0.3 * ((double)animalCount/zooCapacity);
		return popularity;
	}
	
	public int getMaxValue() {
		int maxValue = 0;
		for(Building building : buildings) {
			for(Animal animal : building.getAnimals()) {
				if(animal.getType().getValue() > maxValue)
					maxValue = animal.getType().getValue();
			}
		}
		return maxValue;
	}
	
	public boolean hasEnoughMoney(double value) {
		return money >= value;
	}
}
