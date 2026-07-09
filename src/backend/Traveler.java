package backend;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
	public void clearcartBuy()
	{
		Cart CTEMP=new Cart();
		CTEMP.clearcartBuy(Integer.parseInt(getUserid()));
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
	
	public void addBookingPackage(String name,int quantity) throws NumberFormatException, SQLException
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
	
	/////////////////////////////////////////////////////////////
	public boolean updateProfile(String  updatedUsername, String updatedEmail)
	{
		DBHandler dbhandler=DBHandler.getInstance();
		return dbhandler.updateTravelerData(getUserid(), updatedUsername, updatedEmail);
	}
	public boolean updateProfile(String  updatedUsername, String updatedEmail,String pass)
	{
		DBHandler dbhandler=DBHandler.getInstance();
		return dbhandler.updateTravelerData(getUserid(), updatedUsername, updatedEmail, pass);
	}
	
	public Traveler getTravelerInfo()
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.fetchTravelerData(getUserid());
	}
	/////////////////////////////////////////////////////////////
	
	public String addQueryToDB(String queryContent) throws ClassNotFoundException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.insertQuery(this.getUserid(), queryContent);
	}
	
	public List<String> getLog() throws SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getTravelerLogs(getUserid());
	}
	
	/////////////////////////////////////////////////////////////
	public boolean addBookingHotel(int roomID,int hotelID,Date selectedDate) throws ClassNotFoundException, SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		Booking booking = new Booking(Integer.parseInt(getUserid()), roomID, hotelID, selectedDate);
		
		return  booking.addBooking();
	}
	////////////////////////////////////////////////////////////
	
	public List<Integer> retunQueryIds()
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getQueryIDs(getUserid());
	}
	public Query getQueryDetails(Integer qID)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getQueryDetails(qID);
	}
	public boolean validateUsername1(String uname) 
	{
		DBHandler dbhandler = DBHandler.getInstance();
		return dbhandler.validateTravelerUsername(uname);
	}
	
	
}
