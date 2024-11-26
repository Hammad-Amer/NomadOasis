package backend;

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
}
