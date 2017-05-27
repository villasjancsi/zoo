package model;

public class Animal {

	private AnimalType type;
	private int age;
	private double health;
	
	public Animal() {
		
	}
	
	public Animal(AnimalType type, int age, double health) {
		super();
		this.setType(type);
		this.setAge(age);
		this.setHealth(health);
	}
	
	public Animal(AnimalType type) {
		super();
		this.setType(type);
		this.setAge(0);
		this.setHealth(100.0);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public int increaseAge(int value) {
		this.age += value;
		return age;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public double decreaseHealth(double value) {
		if(value < 0.0)
			return this.health;
		this.health -= value;
		if(this.health < 0.0)
			this.health = 0.0;
		return health;
	}
	
	public double increaseHealth(double value) {
		if(value < 0.0)
			return this.health;
		this.health += value;
		if(this.health > 100.0)
			this.health = 100.0;
		return health;
	}

	public AnimalType getType() {
		return type;
	}

	public void setType(AnimalType type) {
		this.type = type;
	}
}
