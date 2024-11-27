package backend;

import java.sql.SQLException;
import java.util.ArrayList;

public class Package {
    private int id;
    private String name;
    private String destination;
    private int duration;
    private String description;
    private int price;

    public Package(int id, String name, String destination, int duration, String description, int price) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.duration = duration;
        this.description = description;
        this.price = price;
    }

    public Package( String name, String destination, int duration, String description, int price) {
        this.name = name;
        this.destination = destination;
        this.duration = duration;
        this.description = description;
        this.price = price;
    }
    
    public Package() {

    }

    public Package(String name) {

    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	void addBookingPackage(int travellerid, String PackageName, int quantity) throws SQLException
	{
		DBHandler dbhandler=DBHandler.getInstance();
		dbhandler.addBookingPackage(travellerid, PackageName, quantity);
	}
	
	public ArrayList<Package> getBookedPackages(int travelerid)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getBookedPackages(travelerid);
	}
	
	public int getPackagePrice(int packageID)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.getPackagePrice(packageID);
	}
	
	public boolean cancelBooking(int travelerid,int packageID)
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.cancelBooking(travelerid, packageID);
		
	}
	
	public boolean removePackage(String selectedPackage) throws SQLException
	{
		DBHandler dbHandler=DBHandler.getInstance();
		return dbHandler.removePackage(selectedPackage);
	}
}
