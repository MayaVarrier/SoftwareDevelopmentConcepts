package database;

public class OrderException extends Exception {
	
	int orderNumber = 0;

	//Constructor with parameters
	public OrderException(int orderNumber) {
		super();
		this.orderNumber = orderNumber;
	}
	
	public String getMessage() {
		String str = new String("Enter a valid input.");
		return str;
		}
	
	public int getReference() {
		return orderNumber;
	}
	

}
