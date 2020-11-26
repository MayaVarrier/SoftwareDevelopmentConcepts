package database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class Inventory implements inventoryControl {

	//Method to update the details of shipped orders
	@Override
	public void Ship_order(int orderNumber) throws OrderException {
		
		DBConnections db = new DBConnections();
		//New DB connection opened
		db.OpenConnection();
		
		 String shippedDate = "";
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		 Date date = new Date();  
		 
		 String currentDate = formatter.format(date); 
		 int ProductID = 0;
		 int Quantity = 0;
		 int UnitsInStock = 0;
		 int newUnits = 0;
		 HashMap<Integer,Integer> products = new HashMap<Integer,Integer> ();
		
		try {
			//Get the shipped date of the given order number
			String query1 = "SELECT ShippedDate from orders where OrderID = "+orderNumber+" ";
			db.resultSet = db.statement.executeQuery(query1);
			
			while(db.resultSet.next()) {
				shippedDate = db.resultSet.getString("ShippedDate");
			}
			//If shipped date hasn;t been updated, the order number doesn't exist
			if(shippedDate == "") {
				//Custom exception is thrown
				throw new OrderException(orderNumber);
			}
			else {
				//Shipped date is updated as current date
				String query2 = "Update orders set ShippedDate = '"+currentDate+"' where OrderID = "+orderNumber+" ";
				db.statement.executeUpdate(query2);
				
				String query3 = "Select ProductID, Quantity, UnitsInStock from orders natural join orderdetails natural join products where OrderID = "+orderNumber+";";
				db.resultSet = db.statement.executeQuery(query3);
				
				while(db.resultSet.next()) {
					ProductID = db.resultSet.getInt("ProductID");
					Quantity = db.resultSet.getInt("Quantity");
					UnitsInStock = db.resultSet.getInt("UnitsInStock");
					newUnits = UnitsInStock-Quantity;
					products.put(ProductID, newUnits);
				}
				
				ArrayList<Entry<Integer, Integer>> l = new ArrayList<>(products.entrySet());
				
				//Update UnitsInStock by reducing the units shipped
				for (Entry<Integer, Integer> entry : l) {
					String query4 = "Update products set UnitsInStock = "+entry.getValue()+" where ProductID = "+entry.getKey()+"";
					db.statement.executeUpdate(query4);
				}
				 products.clear();
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			//Close the db connection
			db.closeConnection();
		}

	}

	//Method to place an order to supplier
	@Override
	public int Issue_reorders(int year, int month, int day) {
		
		DBConnections db = new DBConnections();
		int SupplierID = 0;
		int ProductID = 0;
		int PurchaseOrderID = 0;  
		int supplierCount = 0;
		
		String currentDate = year+"-"+String.format("%02d", month)+"-"+ String.format("%02d", day);
		HashMap<Integer,Integer> purchaseOrder = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> purchaseOrderDetails = new HashMap<Integer,Integer>();
		ArrayList<Integer> SupplierIDs = new ArrayList<Integer>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<Integer> supIDs = new ArrayList<Integer>();
		
		try {
			//Open DB connection
			db.OpenConnection();
			
			//Retrieve the products to be reordered
			String query1 = "select ProductID, SupplierID, ProductName, UnitsOnOrder,Discontinued from products natural join suppliers where UnitsInStock < ReorderLevel and UnitsOnOrder < 1 and Discontinued < 1 order by SupplierID;";
			db.resultSet = db.statement.executeQuery(query1);
			
			while(db.resultSet.next()) {
				SupplierID = db.resultSet.getInt("SupplierID");
				ProductID = db.resultSet.getInt("ProductID");
				purchaseOrder.put(SupplierID, ProductID);
				supplierCount++;
			}
			ArrayList<Entry<Integer, Integer>> list = new ArrayList<>(purchaseOrder.entrySet());
			
			for (Entry<Integer, Integer> entry : list) {
				if(! SupplierIDs.contains(entry.getKey())){
				SupplierIDs.add(entry.getKey());
				}
			}
			
			//Retrieve the date ordered and supplier IDs of all the entries in purchaseorder table
			String query2 = "select DateOrdered, SupplierID from purchaseorder;";
			db.resultSet = db.statement.executeQuery(query2);
			
			while(db.resultSet.next()) {
				dates.add(db.resultSet.getString("DateOrdered"));
				supIDs.add(db.resultSet.getInt("SupplierID"));
			}
			
			for(Integer s : SupplierIDs) {
				//If for a supplier ID an order has been placed on a date, then do not order it again
				if(!(dates.contains(currentDate) && supIDs.contains(s))) {
				String query3 = "Insert into purchaseorder (SupplierID,DateOrdered) values ("+s+",'"+currentDate+"');";
				db.statement.executeUpdate(query3);
				}
				else {
					System.out.println("The order has already been placed.");
				}
			}
			
			String query4 = "select PurchaseOrderID, SupplierID from purchaseorder;";
			db.resultSet = db.statement.executeQuery(query4);
			
			while(db.resultSet.next()) {
				PurchaseOrderID = db.resultSet.getInt("PurchaseOrderID");
				SupplierID = db.resultSet.getInt("SupplierID");
				purchaseOrderDetails.put(PurchaseOrderID, SupplierID);
			}
			
			ArrayList<Integer> purchaseorderID = new ArrayList<Integer>();
			String query7 = "Select PurchaseOrderID from purchaseorderdetails;";
			db.resultSet = db.statement.executeQuery(query7);
			while(db.resultSet.next()) {
			purchaseorderID.add(db.resultSet.getInt("PurchaseOrderID"));
			}
			
			//Update the purchaseorderdetails table
			String query5 = "select PurchaseOrderID, ProductID, SupplierID, ProductName, UnitPrice,ReorderLevel,UnitsOnOrder from products natural join purchaseorder where UnitsInStock < ReorderLevel and UnitsOnOrder < 1 order by SupplierID;";
			db.resultSet = db.statement.executeQuery(query5);
			while(db.resultSet.next()) {
				int PId = db.resultSet.getInt("PurchaseOrderID");
				int ProdId = db.resultSet.getInt("ProductID");
				double ProdCost = db.resultSet.getDouble("UnitPrice");
				int UnitsOnOrder = db.resultSet.getInt("UnitsOnOrder");
				int Quantity = db.resultSet.getInt("ReorderLevel");
				if(Quantity == 0) {		
					Quantity = 5;
				}
				UnitsOnOrder = UnitsOnOrder + Quantity;
				double ProductCost = ProdCost - (0.15*ProdCost);
				
				if(! (purchaseorderID.contains(PId))) {
				String query6 = "Insert into purchaseorderdetails (PurchaseOrderID, ProductID, ProductCost, Quantity ) values ("+PId+", "+ProdId+", "+ProductCost+", "+Quantity+"  )";
				db.statement1.executeUpdate(query6);
				}
				
				//Update the products table by increasing the value of units of order with the quantity ordered. Change the ReOrdered flag to 1
				String query8 = "Update products set UnitsOnOrder = "+UnitsOnOrder+", ReOrdered = 1  where ProductID = "+ProdId+";";
				db.statement1.executeUpdate(query8);
			}
			purchaseorderID.clear();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//Close the connection
			db.closeConnection();
		}
		
		return supplierCount;
	}

	//Method to record if an order from supplier has been received
	@Override
	public void Receive_order(int internal_order_reference) throws OrderException {
		DBConnections db = new DBConnections();
		//Open the DB connection
		db.OpenConnection();
		int reference = 0;
		String DateDelivered = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = new Date();  
		String currentDate = formatter.format(date); 
		
		try {
			//Retrieve the purchaseorderID from purchaseorder table
			String query1 = "Select "+internal_order_reference+" from purchaseorder";
			db.resultSet = db.statement.executeQuery(query1);
			
			while(db.resultSet.next()) {
				reference = db.resultSet.getInt("PurchaseOrderID");
			}
			
			//If purchaseorderID is not present in the table, throw an exception
			if(reference == 0) {
				throw new OrderException(internal_order_reference);
			}
			
			//Get the delivery date of the given purchaseorderID
			String query2 = "Select DateDelivered from purchaseorder where PurchaseOrderID = "+internal_order_reference+";";
			db.resultSet = db.statement.executeQuery(query2);
			
			while(db.resultSet.next()) {
				DateDelivered = db.resultSet.getString("DateDelivered");
				
				//If date delivered is null, update it with today's date
				if(DateDelivered == null) {
					String query3 = "Update purchaseorder set DateDelivered = "+currentDate+" where PurchaseOrderID = "+internal_order_reference+";";
					db.statement.executeUpdate(query3);
				}
				else {
					System.out.println("The order has been received.");
				}
			}
			
			
			int Quantity = 0;
			int UnitsInStock = 0;
			
			//Calculate the value of UnitsInStock once the order from supplier has been delivered
			String query4 = "select ReorderLevel,UnitsInStock from products natural join purchaseorderdetails where PurchaseOrderID = "+internal_order_reference+";";
			db.resultSet = db.statement.executeQuery(query4);
			
			while(db.resultSet.next()) {
				Quantity = db.resultSet.getInt("ReorderLevel");
				if(Quantity == 0) {
					Quantity = 5;
				}
				UnitsInStock = db.resultSet.getInt("UnitsInStock");
				
				UnitsInStock = UnitsInStock - Quantity;
			}
			
			//Update the value of UnitsOnOrder,UnitsInStock and ReOrdered flag in products table 
			String query5 = "Update products natural join purchaseorderdetails set UnitsOnOrder = 0, ReOrdered = null, UnitsInStock = "+UnitsInStock+" where PurchaseOrderID = "+internal_order_reference+";";
			db.statement.executeUpdate(query5);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//Close the connection
			db.closeConnection();
		}
		

	}

}
