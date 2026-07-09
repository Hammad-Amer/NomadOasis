package backend;

public class Room
{
	private int roomID;
	private int hotelID; 
	private int roomNum; 
	private String type; 
	private int price;
	private boolean available;

	public Room(int roomID, int hotelID, int roomNum, String type, int price, boolean available) {
		this.roomID = roomID;
		this.hotelID = hotelID;
		this.roomNum = roomNum;
		this.type = type;
		this.price = price;
		this.available = available;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}


	public boolean checkAvailability(String dates) {

		return available;
	}

	public void book(String dates) 
	{
		if (checkAvailability(dates))
		{
			available = false;
			System.out.println("Room booked for dates: " + dates);
		} 
		else 
		{
			System.out.println("Room not available for dates: " + dates);
		}
	}
}
