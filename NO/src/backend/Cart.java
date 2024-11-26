package backend;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import backend.DBHandler;
import backend.Item;
public class Cart {
	private int CartID;
	
	public String ItemtoCart(Item item,Traveler T1) throws SQLException
	{	
		
		DBHandler DB= DBHandler.getInstance();
		return DB.AddItemtoCart(item.getItemid(), item.getQuantity(), Integer.parseInt(T1.getUserid()));
	}

	
	public void clearcart(int TravelerID)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		 dbHandler.clearCart(TravelerID);
	}

	public List<Item> getCartItems(int travelerID) 
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getCartItems(travelerID);
	}
	
	public int getCartID() {
		return CartID;
	}


	public void setCartID(int cartID) {
		CartID = cartID;
	}
}
