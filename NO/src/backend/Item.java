package backend;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import backend.Cart;

public class Item {
	private int itemid;
    private String name;
    private int price;
    private int quantity;

    public Item(int itemid)
    {
    	this.itemid=itemid;
    }
    
    public Item()
    {

    }
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
	public int getItemStock() throws SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getItemStock(itemid);
	}
	
	public List<Item> getAllItems()
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getAllItems(); 
	}
	
	public void updateItemQuantity(String selectedItem, int amountToAdd)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		 dbHandler.updateItemQuantity(selectedItem, amountToAdd);
	}
	
	public boolean updateItemStock(int itemid, int stock) throws SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.updateItemStock(itemid,stock);
	}
	 
}
