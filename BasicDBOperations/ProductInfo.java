package northwind;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class ProductInfo {
	String CategoryName = "";
	String ProductName = "";
	String CompanyName = "";
	int Quantity = 0;
	double TotalCost = 0.0;
	ArrayList<ProductPOJO> products = new ArrayList<ProductPOJO>();
	
	//Takes the Product details from the database and stores them in an ArrayList
	public ArrayList<ProductPOJO> ProductInformation(String startdate, String enddate) {
		
		DBConnections db = new DBConnections();
		try {
			//Database connection is opened
			db.OpenConnection();
			//Query to get the category name, product name, supplier name, units sold and sale value
			String query1 = "SELECT CompanyName, CategoryName, v.SupplierID, v.ProductName, v.ProductID, v.CategoryID, v.Quantity, v.UnitPrice, (v.Quantity*v.UnitPrice) as TotalCost FROM (SELECT CompanyName, SupplierID, ProductName, ProductID, UnitsOnOrder, QuantityPerUnit, CategoryID, Quantity, UnitPrice FROM (SELECT ProductID, SupplierID, ProductName, UnitsOnOrder, QuantityPerUnit, CategoryID, Quantity, n.UnitPrice FROM (SELECT  ProductID, UnitPrice, Quantity FROM  (SELECT OrderID FROM orders WHERE OrderDate BETWEEN  '"+startdate+"' and '"+enddate+"') AS m JOIN orderdetails USING (OrderID)) AS n JOIN products USING (ProductID)) AS k JOIN suppliers USING (SupplierID)) as v join categories using (CategoryID) group BY ProductID;";
			//Store the values in a resultset
			db.resultSet = db.statement.executeQuery(query1);
			
			//Iterate over the resultset and store the values to the variables
			while(db.resultSet.next()) {
				CategoryName = db.resultSet.getString("CategoryName");
				ProductName = db.resultSet.getString("ProductName");
				CompanyName = db.resultSet.getString("CompanyName");
				Quantity = db.resultSet.getInt("Quantity");
				TotalCost = db.resultSet.getDouble("TotalCost");
				
				//Pass the details to ProductDetailsPOJO to from the product details
				ProductDetailsPOJO pd = new ProductDetailsPOJO(ProductName, CompanyName, Quantity, TotalCost);
				//Create an object of the ProductPOJO class and pass all information to it
				ProductPOJO prod = new ProductPOJO(CategoryName, pd);
				//Store the object to an ArrayList
				products.add(prod);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//Close the database connection
			db.closeConnection();
		}
		
		return products;
	}
	
}
