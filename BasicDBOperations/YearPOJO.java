package northwind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//POJO class to store the variables of Year
@XmlAccessorType(XmlAccessType.FIELD)
public class YearPOJO {

	@XmlElement(name = "start_date")
	private String startdate;
	@XmlElement(name = "end_date")
	private String enddate;
	
	//Getters and setters for all the variables
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	//Parameterized constructor for the class
	public YearPOJO(String startdate, String enddate) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
	}
	
	public YearPOJO() {
		
	}
	
}
