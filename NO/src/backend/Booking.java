package backend;

import java.sql.Date;
import java.sql.SQLException;

public class Booking {
    	
    private int bookingID;
    private int travelerID;
    private int roomID;
    private int hotelID;
    private Date bookingDate;
    private DBHandler dbHandler;

    // Constructor
    public Booking(int travelerID, int roomID, int hotelID, java.sql.Date bookingDate, DBHandler dbHandler) {
        this.travelerID = travelerID;
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.bookingDate = bookingDate;
        this.dbHandler = dbHandler; 
    }

    // Method to add a booking
    public boolean addBooking() throws ClassNotFoundException, SQLException {
        return dbHandler.addBookingToDatabase(this.travelerID, this.roomID, this.hotelID, this.bookingDate);
    }

    // Method to remove a booking
    public boolean removeBooking(int bookingID) {
        return dbHandler.removeBookingFromDatabase(bookingID);
    }
}