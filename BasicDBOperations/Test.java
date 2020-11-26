package northwind;

import java.io.File;
import java.util.Scanner;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Test {

	//Invoke all the classes
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		CustomerInfo ci = new  CustomerInfo();
		ProductInfo pi = new ProductInfo();
		SupplierInfo si = new SupplierInfo();
		YearPOJO year = new YearPOJO();
		
		Summary su = new Summary();
		xml xml = new xml();
		String startdate;
		String enddate;
		String filename;
		
		//Take the Start Date from the user
		System.out.println("Enter start date : ");
		startdate = sc.nextLine();
		year.setStartdate(startdate);
		
		//Take the End Date from the user
		System.out.println("Enter end date : ");
		enddate = sc.nextLine();
		year.setEnddate(enddate);
		
		//Take the filename from the user
		System.out.println("Enter filename : ");
		filename = sc.nextLine();
		
		sc.close();
		
		//Set the start date and end date
		su.setYear(year);
		//Call the CustomerInfo class and store the ArrayList of objects in the ArrayList in Summary class
		su.setCustomers(ci.CustomerInformation(startdate, enddate));
		//Call the ProductInfo class and store the ArrayList of objects in the ArrayList in Summary class
		su.setProducts(pi.ProductInformation(startdate, enddate));
		//Call the SupplierInfo class and store the ArrayList of objects in the ArrayList in Summary class
		su.setSupplier(si.SupplierInformation(startdate, enddate));
		
		  try {

				//File file = new File("C:\\Users\\hp\\Documents\\MACS Dalhousie\\SDC\\Assignment 5\\file.xml");
				//Create a file of the given filename
			    File file = new File(filename);
				JAXBContext jaxbContext = JAXBContext.newInstance(Summary.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// Output generated
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				//Output written to the given file
				jaxbMarshaller.marshal(su, file);
				jaxbMarshaller.marshal(su, System.out);

			      } catch (JAXBException e) {
				e.printStackTrace();
			      }		
		
	}

}
