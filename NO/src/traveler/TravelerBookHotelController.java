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
	private List<Hotel> hotelList;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException
	{
		SharedState state = SharedState.getInstance();
		this.Traveler = (backend.Traveler) state.getUser();
		dbHandler = DBHandler.getInstance();
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

		hotelList = dbHandler.getAllHotels(); 

		ObservableList<String> hotelNames = FXCollections.observableArrayList();
		for (Hotel hotel : hotelList) {
			hotelNames.add(hotel.getName());
		}
		DropDownResponse.setItems(hotelNames);

		DropDownResponse.setOnAction(event -> updateHotelDetails());
	}



	private void updateHotelDetails() 
	{
		String selectedHotelName = (String) DropDownResponse.getValue();
		if (selectedHotelName != null) 
		{
			Hotel selectedHotel = findHotelByName(selectedHotelName);

			if (selectedHotel != null)
			{
				hotelName.setText(selectedHotel.getName());
				hotelLocation.setText(selectedHotel.getLocation());
				hotelRating.setText(String.valueOf(selectedHotel.getRating())); 
				hotelDescription.setText(selectedHotel.getDescription());

				ObservableList<Integer> roomNumbers = FXCollections.observableArrayList();
				for (Room room : selectedHotel.getRooms()) 
				{
					if (room.isAvailable()) 
					{
						roomNumbers.add(room.getRoomNum());
					}
				}
				RoomNumberComboBox.setItems(roomNumbers);

				RoomNumberComboBox.setOnAction(event -> updateRoomDetails(selectedHotel));
			}
		}
	}

	private Hotel findHotelByName(String hotelName) 
	{
		for (Hotel hotel : hotelList) {
			if (hotel.getName().equals(hotelName))
			{
				return hotel;
			}
		}
		return null;
	}

	private void updateRoomDetails(Hotel selectedHotel) 
	{
		Integer selectedRoomNumber = (Integer) RoomNumberComboBox.getValue();
		if (selectedRoomNumber != null)
		{
			Room selectedRoom = findRoomByNumber(selectedHotel, selectedRoomNumber);

			if (selectedRoom != null)
			{
				roomType.setText(selectedRoom.getType());
				roomPrice.setText(String.valueOf(selectedRoom.getPrice()));
				roomAvalibility.setText(selectedRoom.isAvailable() ? "Available" : "Not Available");
			}
		}
	}

	private Room findRoomByNumber(Hotel hotel, int roomNumber)
	{
		for (Room room : hotel.getRooms())
		{
			if (room.getRoomNum() == roomNumber)
			{
				return room;
			}
		}
		return null;
	}

	@FXML
	public void bookRoom(ActionEvent event) throws ClassNotFoundException, SQLException 
	{
		String selectedHotelName = DropDownResponse.getValue();
		Integer selectedRoomNumber = RoomNumberComboBox.getValue();
		LocalDate selectedDate = booking_date.getValue();

		if (selectedHotelName != null && selectedRoomNumber != null && selectedDate != null)
		{
			Hotel selectedHotel = findHotelByName(selectedHotelName);
			Room selectedRoom = findRoomByNumber(selectedHotel, selectedRoomNumber);

			if (selectedRoom != null)
			{
				Date bookingDate = Date.valueOf(selectedDate);

				boolean isBookingSuccessful = Traveler.addBookingHotel(selectedRoom.getRoomID(),selectedHotel.getHotelID(), bookingDate);

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

	private void showAlert(AlertType alertType, String title, String content) 
	{
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		SharedState state = SharedState.getInstance();
		this.Traveler = (backend.Traveler) state.getUser();
		dbHandler = DBHandler.getInstance();
		try
		{
			loadHotels();
		} 
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

}
