package model;

import java.util.ArrayList;
import java.util.List;

public class BuildingType {
	private String name;
	private double price;
	private double width;
	private double height;
	private int maxAnimals;
	private String pictureUrl;
	private List<String> validAnimalTypes;
	private double startX;
	private double startY;
	
	public BuildingType(String name, double price, int maxAnimals, List<String> validAnimalTypes, double width, double height, double startX, double startY, String pictureUrl) {
		super();
		this.setName(name);
		this.setPrice(price);
		this.setMaxAnimals(maxAnimals);
		if(validAnimalTypes == null)
			setValidAnimalTypes(new ArrayList<String>());
		else
			this.setValidAnimalTypes(validAnimalTypes);
		this.setWidth(width);
		this.setHeight(height);
		this.setPictureUrl(pictureUrl);
		this.setStartX(startX);
		this.setStartY(startY);
	}
	
	public boolean canAcceptAnimal(AnimalType at) {
		if(validAnimalTypes.size() == 0)
			return true;
		if(validAnimalTypes.contains(at.getName()))
			return true;
		return false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public int getMaxAnimals() {
		return maxAnimals;
	}

	public void setMaxAnimals(int maxAnimals) {
		this.maxAnimals = maxAnimals;
	}

	public List<String> getValidAnimalTypes() {
		return validAnimalTypes;
	}

	public void setValidAnimalTypes(List<String> validAnimalTypes) {
		this.validAnimalTypes = validAnimalTypes;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}
	
	
}
