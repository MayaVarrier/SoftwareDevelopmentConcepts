package northwind;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

//POJO class to store the variables of CustomerInfo class
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerPOJO {
	@XmlElement(name = "customer_name")
	private String ContactName = "";
	@XmlElement(name = "address")
	private Address address;	
	@XmlElement(name = "num_orders")
	private int NumberOfOrders = 0;
	@XmlElement(name = "order_value")
	private double total = 0;
	
	//Getters and Setters for all the variables
	public String getContactName() {
		return ContactName;
	}
	public void setContactName(String contactName) {
		ContactName = contactName;
	}
	
	public int getNumberOfOrders() {
		return NumberOfOrders;
	}
	public void setNumberOfOrders(int numberOfOrders) {
		NumberOfOrders = numberOfOrders;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	//Parameterized constructor for the class
	public CustomerPOJO(String contactName, Address address, int numberOfOrders, double total) {
		super();
		ContactName = contactName;
		this.address = address;
		NumberOfOrders = numberOfOrders;
		this.total = total;
	}
	
	public CustomerPOJO() {
	}
	
}
