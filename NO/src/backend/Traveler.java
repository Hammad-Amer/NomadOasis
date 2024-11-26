package backend;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import backend.DBHandler;
import backend.Cart;
import backend.Package;
public class Traveler extends User{

	

	public Traveler(String email, String username, String password, String cnic, String gender, String dob) {
		super(email, username, password, cnic, gender, dob);
		
	}

	public Traveler() {
		
	}

	
	public void clearcart()
	{
		Cart CTEMP=new Cart();
		CTEMP.clearcart(Integer.parseInt(getUserid()));
	}
	
	public List<Item> getCartItems()
	{
		Cart CTEMP=new Cart();
		return CTEMP.getCartItems(Integer.parseInt(getUserid()));
	}
	
	public boolean addtravelertoDB()
	{
	
		boolean flag;
		DBHandler dbhandler=DBHandler.getInstance();

		flag=dbhandler.InsertingTravelerTODB(getEmail(), getUsername(), getPassword(), getCnic(), getGender(), getDob());
	
		return flag;
		
	}
	
	public void addBookingPackage(String name,int quantity)
	{
		Package Ptemp=new Package();
		Ptemp.addBookingPackage(Integer.parseInt(getUserid()), name, quantity);
	}


	public ArrayList<Package> getBookedPackages()
	{
		Package Ptemp=new Package();
		return Ptemp.getBookedPackages(Integer.parseInt(getUserid()));
	}
	
	public boolean cancelBooking(int packageID)
	{
		Package Ptemp=new Package();
		return Ptemp.cancelBooking(Integer.parseInt(getUserid()),packageID);
	}
}
