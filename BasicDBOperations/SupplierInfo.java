package northwind;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierInfo {
	String CompanyName = "";
	String Address = "";
	String City = "";
	String Region = "";
	String PostalCode = "";
	String Country = "";
	int Quantity = 0;
	double TotalCost = 0.0;
	ArrayList<SupplierPOJO> suppliers = new ArrayList<SupplierPOJO>();
	
	//Takes the Supplier details from the database and stores them in an ArrayList
	public ArrayList<SupplierPOJO> SupplierInformation( String startdate, String enddate) {
		
		DBConnections db = new DBConnections();
		try {
			//Database connection is opened
			db.OpenConnection();
			//Query to get the supplier name, address, number of products sold of that supplier and their product value
			String query1 = "select SupplierID, CompanyName, Address, City, Region, PostalCode, Country, Quantity, s.UnitPrice, (Quantity * s.UnitPrice) as TotalCost from (select SupplierID, Quantity, p.UnitPrice from (select ProductID, Quantity, UnitPrice from (select OrderID from orders where OrderDate between '"+startdate+"' and '"+enddate+"') as o join orderdetails using (OrderID)) as p join products using (ProductID)) as s join suppliers using (SupplierID) ;";
			//Store the value in a resultset
			db.resultSet = db.statement.executeQuery(query1);
			
			//Iterate over the resultset and store the value to the variables
			while(db.resultSet.next()) {
				CompanyName = db.resultSet.getString("CompanyName");
				Address = db.resultSet.getString("Address");
				City = db.resultSet.getString("City");
				Region = db.resultSet.getString("Region");
				PostalCode = db.resultSet.getString("PostalCode");
				Country = db.resultSet.getString("Country");
				Quantity = db.resultSet.getInt("Quantity");
				TotalCost = db.resultSet.getDouble("TotalCost");
				
				//Pass the variables to form the address to the Address class
				Address address = new Address(Address, City, Region, PostalCode, Country);
				//Create an object of the SupplierPOJO class and pass all information to it
				SupplierPOJO supplier = new SupplierPOJO(CompanyName, address, Quantity, TotalCost);
				//Store the object to an ArrayList
				suppliers.add(supplier);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//Close the database connection
			db.closeConnection();
		}
		return suppliers;
		
	}
	
}
