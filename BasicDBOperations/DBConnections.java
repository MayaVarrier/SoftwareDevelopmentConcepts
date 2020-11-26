package northwind;

import java.sql.*;
import java.util.Properties;

public class DBConnections {
	
    	// Variables for the connections and the queries.
        Connection connect = null;     
        Statement statement = null;     
        ResultSet resultSet = null;    

        //To establish a connection with the database
        public void OpenConnection() {
        	 Properties identity = new Properties();  
        	//Username, password and the database name are stored here 
             Identity me = new Identity();       

             String user;
             String password;
             String dbName;

             // Get the info for logging into the database.

             me.setIdentity( identity );               
             user = identity.getProperty("user");
             password = identity.getProperty("password");
             dbName = identity.getProperty("database");

             try {
            	 //Load the MySQL Driver
                 Class.forName("com.mysql.jdbc.Driver");

                 // Connection to the DB
                 connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", user, password);

                 //Create an instance of statement that can be used to send queries to the database
                 statement = connect.createStatement();

                 // Choose a database to use
                 statement.executeQuery("use " + dbName + ";");


             } catch (Exception e) {
                 System.out.println(e.getMessage());
             }
        }
        	
        //Close the connection to the database    
        public void closeConnection(){
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }  
    
	
        }
