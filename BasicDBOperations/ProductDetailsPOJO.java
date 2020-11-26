package northwind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//POJO class to store the variables of ProductInfo class
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDetailsPOJO {

	@XmlElement(name = "product_name")
	private String ProductName;
	@XmlElement(name = "supplier_name")
	private String CompanyName;
	@XmlElement(name = "units_sold")
	private int Quantity;
	@XmlElement(name = "sale_value")
	private double TotalCost;
	
	//Getters and Setters for all the variables
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
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
	
	//Parameterized constructor for the class
	public ProductDetailsPOJO(String productName, String companyName, int quantity, double totalCost) {
		super();
		ProductName = productName;
		CompanyName = companyName;
		Quantity = quantity;
		TotalCost = totalCost;
	}
	public ProductDetailsPOJO() {
		
	}
	
	
	
	
}
