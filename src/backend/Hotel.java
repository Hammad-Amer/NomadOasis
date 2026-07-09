package backend;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int hotelID;
    private String name;
    private String location;
    private String description;
    private int rating;
    private List<Room> rooms;

    public Hotel(int hotelID, String name, String location, String des,int rat) {
        this.hotelID = hotelID;
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.setDescription(des);
        this.setRating(rat);
    }
    public int getHotelID() {
        return hotelID;
    }
    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }
    public List<Room> viewRooms() {
        return rooms;
    }
    public boolean bookRoom(int roomID, String dates)
    {
        for (Room room : rooms) 
        {
            if (room.getRoomID() == roomID && room.isAvailable())
            {
                room.book(dates);
                System.out.println("Room number " + room.getRoomNum() + " booked.");
                return true;
            }
        }
        for (Room room : rooms) 
        {
            if (room.getRoomID() == roomID)
            {
                System.out.println("Room number " + room.getRoomNum() + " is not available.");
                return false;
            }
        }
        System.out.println("Room is not available.");
        return false;
    }
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}

