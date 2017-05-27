package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import view.MainController;

public class ZooManagement implements Runnable {
	private MainController mainController;
	private int year = 0;
	private int day = 0;
	private int hour = 0;
	private int step = 500;
	private double density = 1.0;
	private Zoo zoo;
	
	public ZooManagement(Zoo zoo, int hour, MainController mainController) {
		super();
		this.zoo = zoo;
		while(hour >= 365*24) {
			hour -= 365*24;
			this.year++;
		}
		while(hour >= 24) {
			hour -= 24;
			this.day++;
		}
		this.hour = hour;
		this.mainController = mainController;
	}

	@Override
	public void run() {
		try {
			while(true) {
				hour++;
				
				updateDensity();
				updateZoo();
				decreaseMoneyWithHourlyCosts();
				increaseMoneyWithVisitors();
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						mainController.updateTime(String.format("Y:%d D:%d H:%02d", year, day, hour));
						mainController.updateGUI();
						mainController.updatePopulationDensity(density);
					}
				});
				
				
				if(hour == 24) {
					day++;
					hour = 0;
				}
				if(day == 365)
					year++;
				Thread.sleep(step);
			}
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public void updateZoo() {
		zoo.increaseTimeInHour(1);
		
		//Every employee can regenerate their tiredness by 5 every hour
		//Thus every employee can care about 5 animals in an hour (in long term)
		//If he should care about more than 5, he won't be efficient
		//If he isn't efficient, he should be fired
		List<Employee> toBeFired = new ArrayList<>();
		for(Employee employee : zoo.getEmployees()) {
			if(employee.decreaseTire(5) <= 50)
				toBeFired.add(employee);
		}
		for(Employee employee : toBeFired)
			zoo.fireEmployee(employee);
		
		Random random = new Random();
		List<Animal> deadAnimals = new ArrayList<>();
		for(Building building : zoo.getBuildings()) {
			for(Animal animal : building.getAnimals()) {
				boolean isDead = false;
				
				//death by weakness (health reaches 0.0)
				if(!isDead && Double.compare(animal.decreaseHealth(1.0), 0.0) == 0) { 
					isDead = true;
				}
				
				//death by bad health (less than 25%)
				if(!isDead && animal.getHealth() <= 25.0) {
					if(Integer.compare(random.nextInt(new Double(animal.getHealth()).intValue()), 0) == 0) {
						isDead = true;
					}
				}
				
				//death by reaching maximum age
				//btw there is a small chance to live further (every hour increases the chance to die)
				if(!isDead && Integer.compare(animal.increaseAge(1), animal.getType().getMaxAge()) > 0) {
					if(random.nextDouble()/2 > 1.0/(animal.getAge()-animal.getType().getMaxAge())) { 
						isDead = true;
					}
				}
				
				//if the animal is dead, we should remove it
				if(isDead) {
					deadAnimals.add(animal);
				}
				
				//If there are employees, choose the less tired, who can care
				//about this animal. Thus the animal's health will increase,
				//as employee's tire do.
				List<Employee> employees = zoo.findEmployeeByValue(animal.getType().getValue());
				if(!employees.isEmpty()) {
					 Employee employee = employees.get(0);
					 animal.increaseHealth(1.02*(employee.getTire()/100.0));
					 employee.increaseTire(1);
				}
			}
		}
		for(Animal animal : deadAnimals) {
			for(Building building : zoo.getBuildings()) {
				if(building.getAnimals().contains(animal)) {
					if(mainController != null) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								mainController.deadAnimal(building, animal);
							}
						});
					}
					building.removeAnimal(animal);
				}
			}
		}
			
	}
	
	public double decreaseMoneyWithHourlyCosts() {
		double money = 0.0;
		//every animal has feeding cost, and other care fee
		for(Building building : zoo.getBuildings()) {
			for(Animal animal : building.getAnimals()) {
				money += animal.getType().getHourlyCost();
			}
		}
		//every employee has salary, pay it hourly
		for(Employee employee : zoo.getEmployees()) {
			money += employee.getType().getHourlyCost();
					
		}
		zoo.decreaseMoney(money);
		return money;
	}
	
	public double increaseMoneyWithVisitors() {
		Random r = new Random();
		double money = 0.0;
		try {
			for(Visitor visitor : zoo.getVisitors()) {
				double value = visitor.getMoney();
				value *= r.nextDouble();
				if(value > 0.0) {
					money += visitor.decreaseMoney(value);
				}
			}
		} catch (Exception ex) {
		}
		zoo.increaseMoney(money);
		return money;
	}

	private void updateDensity() {
		if(hour == 7)
			density = 0.10;
		else if(hour == 8)
			density = 0.25;
		else if(hour == 10)
			density = 0.80;
		else if(hour == 12)
			density = 0.50;
		else if(hour == 14)
			density = 1.00;
		else if(hour == 15)
			density = 0.80;
		else if(hour == 16)
			density = 0.60;
		else if(hour == 17)
			density = 0.40;
		else if(hour == 18)
			density = 0.20;
		else if(hour == 19)
			density = 0.10;
		else if(hour == 20)
			density = 0.05;
		else if(hour == 21)
			density = 0.01;
	}
}
