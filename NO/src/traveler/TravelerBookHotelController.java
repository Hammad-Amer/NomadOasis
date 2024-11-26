package traveler;
import backend.Traveler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import backend.Booking;
import backend.DBHandler;
import backend.Hotel;
import backend.Room;
import backend.SharedState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TravelerBookHotelController implements Initializable
{

	private Traveler Traveler;
	private DBHandler dbHandler;
	private Connection connection;
	private List<Hotel> hotelList;

	  @FXML
		 public void initialize() throws ClassNotFoundException, SQLException {
		        // Retrieve the shared data
		        SharedState state = SharedState.getInstance();
		        this.connection = state.getConnection();
		        this.Traveler = (backend.Traveler) state.getUser();
		        loadHotels();
		
		    }
	  
	
	@FXML
	private Button goback_Main;

	@FXML
	private ComboBox<String> DropDownResponse;

	@FXML
	private Button bookroom;

	@FXML
	private Text hotelName;

	@FXML
	private ComboBox<Integer> RoomNumberComboBox;

    @FXML
    private DatePicker booking_date;
    
	@FXML
	private Text hotelLocation;

	@FXML
	private Text hotelRating;

	@FXML
	private Text hotelDescription;

	@FXML
	private Text roomType;

	@FXML
	private Text roomPrice;

	@FXML
	private Text roomAvalibility;

	@FXML
	public void goBackTravelerMain(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
		Parent root = loader.load();

		
		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

	}

	public void loadHotels() throws ClassNotFoundException, SQLException 
	{
		
			dbHandler = new DBHandler(connection);
	
		hotelList = dbHandler.getAllHotels();  // Get hotels from the database

		// Populate the DropDownResponse with hotel names
		ObservableList<String> hotelNames = FXCollections.observableArrayList();
		for (Hotel hotel : hotelList) {
			hotelNames.add(hotel.getName());
		}
		DropDownResponse.setItems(hotelNames);

		// Add listener to update hotel details when a hotel is selected
		DropDownResponse.setOnAction(event -> updateHotelDetails());
	}

	// Method to update hotel details and available rooms when a hotel is selected
	private void updateHotelDetails() {
		String selectedHotelName = (String) DropDownResponse.getValue();
		if (selectedHotelName != null) {
			Hotel selectedHotel = findHotelByName(selectedHotelName);

			if (selectedHotel != null) {
				// Display hotel details
				hotelName.setText(selectedHotel.getName());
				hotelLocation.setText(selectedHotel.getLocation());
				hotelRating.setText(String.valueOf(selectedHotel.getRating())); 
				hotelDescription.setText(selectedHotel.getDescription());

				// Populate RoomNumberComboBox with room numbers from the selected hotel
				ObservableList<Integer> roomNumbers = FXCollections.observableArrayList();
				for (Room room : selectedHotel.getRooms()) {
					if (room.isAvailable()) {
						roomNumbers.add(room.getRoomNum());
					}
				}
				RoomNumberComboBox.setItems(roomNumbers);

				// Add listener to update room details when a room is selected
				RoomNumberComboBox.setOnAction(event -> updateRoomDetails(selectedHotel));
			}
		}
	}

	// Method to find hotel by name
	private Hotel findHotelByName(String hotelName) {
		for (Hotel hotel : hotelList) {
			if (hotel.getName().equals(hotelName)) {
				return hotel;
			}
		}
		return null;
	}

	// Method to update room details when a room is selected
	private void updateRoomDetails(Hotel selectedHotel) {
		Integer selectedRoomNumber = (Integer) RoomNumberComboBox.getValue();
		if (selectedRoomNumber != null) {
			Room selectedRoom = findRoomByNumber(selectedHotel, selectedRoomNumber);

			if (selectedRoom != null) {
				roomType.setText(selectedRoom.getType());
				roomPrice.setText(String.valueOf(selectedRoom.getPrice()));
				roomAvalibility.setText(selectedRoom.isAvailable() ? "Available" : "Not Available");
			}
		}
	}

	// Method to find a room by its number in the selected hotel
	private Room findRoomByNumber(Hotel hotel, int roomNumber) {
		for (Room room : hotel.getRooms()) {
			if (room.getRoomNum() == roomNumber) {
				return room;
			}
		}
		return null;
	}

	@FXML
	public void bookRoom(ActionEvent event) throws ClassNotFoundException, SQLException {
	    String selectedHotelName = DropDownResponse.getValue();
	    Integer selectedRoomNumber = RoomNumberComboBox.getValue();
	    LocalDate selectedDate = booking_date.getValue();

	    // Check if all required fields are selected
	    if (selectedHotelName != null && selectedRoomNumber != null && selectedDate != null)
	    {
	        Hotel selectedHotel = findHotelByName(selectedHotelName);
	        Room selectedRoom = findRoomByNumber(selectedHotel, selectedRoomNumber);

	        if (selectedRoom != null)
	        {
	            // Create a booking object
	            Date bookingDate = Date.valueOf(selectedDate);
	         
	    			dbHandler = new DBHandler(connection);
	    	
	    	
	            
	            Booking booking = new Booking(Integer.parseInt(Traveler.getUserid()), selectedRoom.getRoomID(),selectedHotel.getHotelID(), bookingDate, dbHandler);

	            // Call the booking method from the Booking class to add the booking to the database
	            boolean isBookingSuccessful = booking.addBooking();

	            // Display success or failure alerts
	            if (isBookingSuccessful) 
	            {
	                showAlert(AlertType.INFORMATION, "Booking Successful", "Your room has been successfully booked!");
	            } 
	            else 
	            {
	                showAlert(AlertType.ERROR, "Booking Failed", "There was an issue with your booking. Please try again.");
	            }
	        } 
	        else 
	        {
	            showAlert(AlertType.WARNING, "Room Not Available", "The selected room is not available. Please choose another room.");
	        }
	    } else
	    {
	        showAlert(AlertType.WARNING, "Incomplete Information", "Please select a hotel, room, and booking date.");
	    }
	
	}

	// Helper method to show alerts
	private void showAlert(AlertType alertType, String title, String content) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        SharedState state = SharedState.getInstance();
        this.connection = state.getConnection();
        this.Traveler = (backend.Traveler) state.getUser();
        try {
			loadHotels();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
