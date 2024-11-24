package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import backend.DBHandler;
import backend.Hotel;
import backend.SharedState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminMainPageController
{
	private DBHandler dbHandler;
	private Connection connection;
	private String AdminID;

	@FXML
	public void initialize()
	{
		SharedState state = SharedState.getInstance();
		this.dbHandler=new DBHandler();
		this.connection = state.getConnection();
		this.AdminID = state.getTravelerID();
	}

	@FXML
	private Button admins_addhotel_button;

	@FXML
	private Button admins_additems_button;

	@FXML
	private Button admins_addpackages_button;
	

    @FXML
    void goToAddHotel(ActionEvent event) throws IOException
    {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminAddHotel.fxml"));
		Parent root = loader.load();

		AdminMainPageController controller = loader.getController();
		controller.loadHotelNames();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/admin/AdminMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    
    
    
    
    
    
      /////////////////////////////////////////////////////////////////////////////
     /////////////////////////addhotelpagethingys/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    
    
    @FXML
    private Button addhotelbutton;

    @FXML
    private Button addroombutton;
    
    @FXML
    private TextField hotelnametext;

    @FXML
    private TextField hotellocationtext;

    @FXML
    private TextField hotelratingtext;

    @FXML
    private TextField hoteldescriptiontext;

    @FXML
    private ComboBox<String> selecthotelnamecombo;

    @FXML
    private TextField roomnumbertext;

    @FXML
    private ComboBox<String> roomtypetext;

    @FXML
    private TextField roompricetext;
    
    @FXML
    private Button backadminmain;

    @FXML
    void goBackAdminMain(ActionEvent event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminMainPage.fxml"));
		Parent root = loader.load();

		AdminMainPageController controller = loader.getController();
		//controller.setConsultantID(travelerID); 
		//controller.setDBHandler(dbHandler);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/admin/AdminMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    
    private void loadHotelNames() 
    {
    	roomtypetext.getItems().clear();
    	roomtypetext.getItems().addAll("Luxury", "Single", "Double", "Suite");
        selecthotelnamecombo.getItems().clear();
        dbHandler=new DBHandler(connection);
        try {
            List<Hotel> hotels = dbHandler.getAllHotels();
            for (Hotel hotel : hotels) 
            {
                selecthotelnamecombo.getItems().add(hotel.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddHotel(ActionEvent event) {
        try {
            String name = hotelnametext.getText();
            String location = hotellocationtext.getText();
            String description = hoteldescriptiontext.getText();
            double rating = Double.parseDouble(hotelratingtext.getText());

            if (dbHandler.addHotel(name, location, description, rating)) {
                showAlert(AlertType.INFORMATION, "Success", "Hotel added successfully!");
                loadHotelNames();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to add hotel. Please try again.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid rating.");
        }
    }

    @FXML
    private void handleAddRoom(ActionEvent event) {
        String selectedHotel = selecthotelnamecombo.getValue();
        if (selectedHotel == null || selectedHotel.isEmpty()) {
            showAlert(AlertType.WARNING, "Selection Required", "Please select a hotel.");
            return;
        }

        try {
            int hotelID = dbHandler.getHotelIDByName(selectedHotel);
            int roomNum = Integer.parseInt(roomnumbertext.getText());
            String type = roomtypetext.getValue(); 
            if (type == null) {
                showAlert(AlertType.WARNING, "Selection Required", "Please select a room type.");
                return;
            }
            int price = Integer.parseInt(roompricetext.getText());

            if (dbHandler.addRoom(hotelID, roomNum, type, price)) {
                showAlert(AlertType.INFORMATION, "Success", "Room added successfully!");
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to add room. Please try again.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numbers for room number and price.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
}
