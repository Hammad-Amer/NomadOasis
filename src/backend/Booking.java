package backend;

import java.sql.Date;
import java.sql.SQLException;

public class Booking {
    	
    private int bookingID;
    private int travelerID;
    private int roomID;
    private int hotelID;
    private Date bookingDate;

    public Booking(int travelerID, int roomID, int hotelID, java.sql.Date bookingDate) {
        this.travelerID = travelerID;
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.bookingDate = bookingDate;
    }

    public boolean addBooking() throws ClassNotFoundException, SQLException
    {
    	DBHandler dbHandler = DBHandler.getInstance();
        return dbHandler.addBookingToDatabase(this.travelerID, this.roomID, this.hotelID, this.bookingDate);
    }

    public boolean removeBooking(int bookingID)
    {
    	DBHandler dbHandler = DBHandler.getInstance();
        return dbHandler.removeBookingFromDatabase(bookingID);
    }
}