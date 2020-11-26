package database;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Inventory inv = new Inventory();
		Scanner sc = new Scanner(System.in);
		
		int orderNumber = 0;
		int referenceNumber = 0;
		int year = 0;
		int month = 0;
		int day = 0;

		System.out.println("Enter year (YYYY) : ");
		year = sc.nextInt();
		System.out.println("Enter month (MM) : ");
		month = sc.nextInt();
		System.out.println("Enter day (DD) : ");
		day = sc.nextInt();
		inv.Issue_reorders(year,month,day);
		
		/*try {
			System.out.println("Enter an order number : ");
			orderNumber = sc.nextInt();
			inv.Ship_order(orderNumber);
		} catch (OrderException e) {
			System.out.println(e.getMessage() +"\n" + "Reference number : " +e.getReference());
		}*/

		try {
			System.out.println("Enter the reference number : ");
			referenceNumber = sc.nextInt();
			inv.Receive_order(referenceNumber);
		} catch (OrderException e) {
			System.out.println(e.getMessage() +"\n" + "Reference number : " +e.getReference());
		}
		
	}

}
