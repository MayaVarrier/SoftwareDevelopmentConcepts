package northwind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class ProductPOJO {
	@XmlElement(name = "category_name")
	private String CategoryName;
	@XmlElement(name = "product")
	private ProductDetailsPOJO proddetails;
	
	
	public ProductPOJO(){
		
	}


	public ProductPOJO(String categoryName, ProductDetailsPOJO proddetails) {
		super();
		CategoryName = categoryName;
		this.proddetails = proddetails;
	}


	public String getCategoryName() {
		return CategoryName;
	}


	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}


	public ProductDetailsPOJO getProddetails() {
		return proddetails;
	}


	public void setProddetails(ProductDetailsPOJO proddetails) {
		this.proddetails = proddetails;
	}
	
	

}
