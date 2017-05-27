package model;

public class Visitor {
	private double money;

	public Visitor(double money) {
		super();
		this.money = money;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	public double decreaseMoney(double value) {
		if(value < 0)
			return this.money;
		if(value > money)
			value = money;
		this.money -= value;
		return value;
	}
	
}
