package model;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private BuildingType type;
	private double layoutX;
	private double layoutY;
    private double posX;
    private double posY;
	private List<Animal> animals = new ArrayList<>();
	
	public Building() {
		
	}
	
	public Building(BuildingType type, double layoutX, double layoutY) {
		super();
		this.setType(type);
		this.setLayoutX(layoutX);
		this.setLayoutY(layoutY);
		this.setPosX(layoutX + type.getStartX());
		this.setPosY(layoutY + type.getStartY());
	}
	
	public List<Animal> addAnimal(Animal animal) {
		animals.add(animal);
		return animals;
	}
	
	public List<Animal> removeAnimal(Animal animal) {
		animals.remove(animal);
		return animals;
	}
	
	public List<Animal> getAnimals() {
		return animals;
	}
	
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public BuildingType getType() {
		return type;
	}
	public void setType(BuildingType type) {
		this.type = type;
	}

	public double getLayoutY() {
		return layoutY;
	}

	public void setLayoutY(double layoutY) {
		this.layoutY = layoutY;
	}

	public double getLayoutX() {
		return layoutX;
	}

	public void setLayoutX(double layoutX) {
		this.layoutX = layoutX;
	}
	public int getAnimalCount() {
		return animals.size();
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
}
