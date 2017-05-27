package model;

public class EmployeeType {
	private String name;
	private double price;
	private int value;
	private double hourlyCost;
	private double width;
	private double height;
	private String pictureUrl;
	
	
	public EmployeeType(String name, double price, int value, double hourlyCost, double width, double height, String pictureUrl) {
		super();
		this.name = name;
		this.price = price;
		this.value = value;
		this.setHourlyCost(hourlyCost);
		this.width = width;
		this.height = height;
		this.pictureUrl = pictureUrl;
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


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
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


	public double getHourlyCost() {
		return hourlyCost;
	}


	public void setHourlyCost(double hourlyCost) {
		this.hourlyCost = hourlyCost;
	}
	
	
}
