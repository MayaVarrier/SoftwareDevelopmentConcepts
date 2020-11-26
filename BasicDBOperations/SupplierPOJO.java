package northwind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//POJO class to store the variables of SupplierInfo class
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierPOJO {
	@XmlElement(name = "supplier_name")
	String CompanyName;
	@XmlElement(name = "address")
	private Address address;
	@XmlElement(name = "num_products")
	int Quantity;
	@XmlElement(name = "product_value")
	double TotalCost;
	
	public SupplierPOJO() {
		
	}
	
	//Parameterized constructor for the class
	public SupplierPOJO(String companyName, Address address, int quantity, double totalCost) {
		super();
		CompanyName = companyName;
		this.address = address;
		Quantity = quantity;
		TotalCost = totalCost;
	}

	//Getters and Setters for all the variables
	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public double getTotalCost() {
		return TotalCost;
	}

	public void setTotalCost(double totalCost) {
		TotalCost = totalCost;
	}
	
	
	
	
	
	

}
