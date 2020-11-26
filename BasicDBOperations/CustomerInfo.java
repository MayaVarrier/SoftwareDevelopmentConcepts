package northwind;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerInfo {
	 
	 String ContactName = "";
     String Street = "";	 
     String City = "";	 
     String Region = "";	 
     String PostalCode = "";	 
     String Country = "";	 
     int NumberOfOrders = 0;
     double TotalCost = 0.0;
     String Address = "";
     ArrayList<CustomerPOJO> customers = new ArrayList<CustomerPOJO>();
    
     //Takes the Customer details from the database and stores them in an ArrayList
	public ArrayList<CustomerPOJO> CustomerInformation( String startdate, String enddate) {
		
		DBConnections db = new DBConnections();
		try {
			//Database connection is opened
			db.OpenConnection();
			//Query to get the customer name, address, number of orders, and the order value
			 String query1 = "select CustomerID, count(distinct OrderID) as NumberOfOrders, ContactName, Address, City, Region, PostalCode, Country, sum(UnitPrice*Quantity) as TotalCost from orders natural join customers natural join orderdetails where orders.OrderDate between ' "+startdate+" ' and ' "+enddate+" ' group by CustomerID;";
	        //Store the values in the resultset 
			 db.resultSet = db.statement.executeQuery(query1);
			
			//Iterate over the resultset and store the values to the variables
			while(db.resultSet.next()) {
				if(db.resultSet.getString("ContactName") != null) {
					ContactName = db.resultSet.getString("ContactName");
				 }
				 if(db.resultSet.getString("Address") != null) {	 
	        	 Street = db.resultSet.getString("Address");
				 }
				 if(db.resultSet.getString("City") != null) {
	        	 City = db.resultSet.getString("City");
				 }
				 if(db.resultSet.getString("Region") != null) {
	        	 Region = db.resultSet.getString("Region");
				 }
				 if(db.resultSet.getString("PostalCode") != null) {
	        	 PostalCode = db.resultSet.getString("PostalCode");
				 }
				 if(db.resultSet.getString("Country") != null) {
	        	 Country = db.resultSet.getString("Country");
				 }
				 NumberOfOrders = db.resultSet.getInt("NumberOfOrders");
				 TotalCost = db.resultSet.getDouble("TotalCost");
				 
				 //Pass the variables to form the address to the Address class
				 Address address = new Address (Street, City,Region, PostalCode, Country);
				 //Create an object of the CustomerPOJO class and pass all information to it
				 CustomerPOJO cp = new CustomerPOJO(ContactName, address, NumberOfOrders, TotalCost);
				 //Store the object to an ArrayList
				 customers.add(cp);
				 
			}		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//Close the database connection
			db.closeConnection();
		}
		
		return customers;
	}

}
