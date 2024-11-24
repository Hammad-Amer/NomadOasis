package backend;
import java.sql.Connection;
import java.sql.SQLException;

import backend.DBHandler;
import backend.Item;
public class Cart {
	private int CartID;
	
	public String ItemtoCart(Item item,Traveler T1,Connection conn) throws SQLException
	{	
		
		DBHandler DB=new DBHandler(conn);
		return DB.AddItemtoCart(item.getItemid(), item.getQuantity(), T1.getTravelerid());
	}

}
