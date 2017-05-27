package model;

public class AnimalType {
	private String name;
	private int maxAge;
	private double hourlyCost;
	private double price;
	private int value;
	private double width;
	private double height;
	private String pictureUrl;
	
	public AnimalType(String name, int maxAge, double hourlyCost, double price, int value, double width, double height, String pictureUrl) {
		super();
		this.setName(name);
		this.setMaxAge(maxAge);
		this.setHourlyCost(hourlyCost);
		this.setPrice(price);
		this.setValue(value);
		this.setWidth(width);
		this.setHeight(height);
		this.setPictureUrl(pictureUrl);
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

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public double getHourlyCost() {
		return hourlyCost;
	}

	public void setHourlyCost(double hourlyCost) {
		this.hourlyCost = hourlyCost;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
