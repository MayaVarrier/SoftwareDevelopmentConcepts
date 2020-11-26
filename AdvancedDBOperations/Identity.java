package database;

import java.util.Properties;

public class Identity {
	
	  public static void setIdentity(Properties prop) {
	      prop.setProperty("database", "csci3901");
	      prop.setProperty("user", "varrier");  // Replace with your CSID for bluenose
	      prop.setProperty("password", "B00847050"); // Replace with your Banner ID (which is your mysql password)	      
	 
	    }

}
