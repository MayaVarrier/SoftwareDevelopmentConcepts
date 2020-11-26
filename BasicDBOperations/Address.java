package northwind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//POJO class to store the variables of Address
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

	@XmlElement(name = "street")
	private String Street = "";
	@XmlElement(name = "city")
	private String City = "";
	@XmlElement(name = "region")
	private String Region = "";
	@XmlElement(name = "postalcode")
	private String PostalCode = "";
	@XmlElement(name = "country")
	private String Country = "";
	
	//Getters and setters for all the variables
	
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	
	//Parameterized constructor for the class
	public Address(String street, String city, String region, String postalCode, String country) {
		super();
		Street = street;
		City = city;
		Region = region;
		PostalCode = postalCode;
		Country = country;
	}
	
	
}
