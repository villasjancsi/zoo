package model;

public class Employee {
	private EmployeeType type;
	private int tire;
	
	public Employee() {
		
	}
	
	public Employee(EmployeeType employeeType, int tire) {
		this.type = employeeType;
		this.tire = tire;
	}
	
	public EmployeeType getType() {
		return type;
	}
	public void setType(EmployeeType employeeType) {
		this.type = employeeType;
	}
	public int getTire() {
		return tire;
	}
	public void setTire(int tire) {
		this.tire = tire;
	}
	public int increaseTire(int tire) {
		if(tire < 0)
			return this.tire;
		this.tire -= tire;
		if(this.tire < 0)
			this.tire = 0;
		return this.tire;
	}
	public int decreaseTire(int tire) {
		if(tire < 0)
			return this.tire;
		this.tire += tire;
		if(this.tire > 100)
			this.tire = 100;
		return this.tire;
	}
}
