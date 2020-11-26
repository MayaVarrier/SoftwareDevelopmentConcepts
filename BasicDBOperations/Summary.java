package northwind;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.sql.*;


//POJO class to store the details of Customers, Products, and Suppliers as Lists
@XmlRootElement(name = "year_end_summary")
@XmlAccessorType(XmlAccessType.FIELD)
public class Summary {
	@XmlElement(name = "year")
	YearPOJO year;
	@XmlElementWrapper(name = "customer_list")
	@XmlElement(name = "customer")
	ArrayList<CustomerPOJO> customers;          
	@XmlElementWrapper(name = "product_list")
	@XmlElement(name = "product")
	ArrayList<ProductPOJO> products;            
	@XmlElementWrapper(name = "supplier_list")
	@XmlElement(name = "supplier")
	ArrayList<SupplierPOJO> supplier;           
	



	//Parametrized constructor for the class
	public Summary(YearPOJO year, ArrayList<CustomerPOJO> customers, ArrayList<ProductPOJO> products,
			ArrayList<SupplierPOJO> supplier) {
		super();
		this.year = year;
		this.customers = customers;
		this.products = products;
		this.supplier = supplier;
	}

	public Summary() {
	}

	
	//Getters and setters for all the variables
	public YearPOJO getYear() {
		return year;
	}

	public void setYear(YearPOJO year) {
		this.year = year;
	}

	public ArrayList<CustomerPOJO> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<CustomerPOJO> customers) {
		this.customers = customers;
	}

	public ArrayList<ProductPOJO> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductPOJO> products) {
		this.products = products;
	}

	public ArrayList<SupplierPOJO> getSupplier() {
		return supplier;
	}

	public void setSupplier(ArrayList<SupplierPOJO> supplier) {
		this.supplier = supplier;
	}
	
	

}
