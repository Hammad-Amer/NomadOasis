package backend;

import java.sql.SQLException;
import java.util.List;

public class Admin  extends User{

	public Admin(String email, String username, String password, String cnic, String gender, String dob) 
	{
		super(email, username, password, cnic, gender, dob);

	}

	public Admin() 
	{
		
	}
	
	
	
	public List<Item> getAllItems()
	{
		Item Itemp=new Item();
		return Itemp.getAllItems();
	}
	
	public void updateItemQuantity(String selectedItem, int amountToAdd)
	{
		Item Itemp=new Item();
		Itemp.updateItemQuantity(selectedItem, amountToAdd);
	} 
	
	public boolean addPackageToDatabase(String name, String destination, int duration, String description, int price)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		Package newPackage=new Package(name,destination,duration,description,price);
		return dbHandler.addPackageToDatabase(newPackage);
	}
	
	/////////////////////////////////////////////
	
	public List<Hotel> hotels() throws SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getAllHotels();
	}
	
	public boolean addHotelToDB(String name, String location, String description, double rating)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.addHotel(name, location, description, rating);
	}
	public boolean addRoomToDB(int hotelID, int roomNum, String type, int price)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.addRoom(hotelID, roomNum, type, price);
	}
	public int getHotelID(String name)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getHotelIDByName(name);
	}
	
	 public boolean updateItemStock(int itemid, int stock) throws SQLException
	 {
		 Item Itemp=new Item();
			return Itemp.updateItemStock(itemid, stock);
	 }
	 
	 public boolean removePackage(String selectedPackage) throws SQLException
	 {
		 Package Ptemp=new Package(selectedPackage);
		 return Ptemp.removePackage();
		 
	 }
	
}
