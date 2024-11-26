package admin;

import backend.Package;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import backend.DBHandler;
import backend.Hotel;
import backend.Item;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.LoginSignupController;
import backend.Admin;
public class AdminMainPageController
{
	private DBHandler dbHandler;
	private Connection connection;
	private Admin Admin;

	@FXML
	public void initialize()
	{
		SharedState state = SharedState.getInstance();
		
		this.connection = state.getConnection();
		this.dbHandler=new DBHandler(connection);
		this.Admin = (backend.Admin) state.getUser();
	}

	@FXML
	private Button admins_addhotel_button;

	@FXML
	private Button admins_additems_button;

	@FXML
	private Button admins_addpackages_button;
	
   
    @FXML
    public void goToAddHotel(ActionEvent event) throws IOException
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
    
    @FXML
    public void goToAddItemStock(ActionEvent event) throws IOException {
     
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminAddItems.fxml"));
        Parent root = loader.load();

        
        AdminMainPageController controller = loader.getController();
        controller.populateComboBoxWithItems(); // Populate the ComboBox with items

        controller.Admin_additems_combobox.setOnAction(e -> controller.displaySelectedItemDetails());
        // Set up the new scene
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nomad Oasis");
        stage.show();

        // Close the current stage
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void goToAddPackage(ActionEvent event) throws IOException {
     
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminAddPackage.fxml"));
        Parent root = loader.load();

        // Set up the new scene
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nomad Oasis");
        stage.show();

        // Close the current stage
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    @FXML
    private Button logout;

    
    @FXML
    public 	void logOut(ActionEvent event) throws IOException, ClassNotFoundException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/LoginSignup.fxml"));
		Parent root = loader.load();

		LoginSignupController controller = loader.getController();
		controller.setConnection(connection);
		
		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/user/LoginSignup.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
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
    
    ////////////////////////////////////////////////////////////////////////////
    //	Admin Add item
    ////////////////////////////////////////////////////
    

    @FXML
    private Button AddItem_back_button;
    
    @FXML
    private Text Admin_additems_Nametext;

    @FXML
    private Text Admin_additems_Pricetext;

    @FXML
    private Text Admin_additems_Stocktext;

    @FXML
    private Button Admin_additems_addbutton;

    @FXML
    private TextField Admin_additems_amounttext;

    @FXML
    private ComboBox<String> Admin_additems_combobox;
    
    public void populateComboBoxWithItems() {
    	 if (Admin_additems_combobox == null) {
             System.out.println("ChoiceBox not initialized");
             return;
         }
    	
        List<Item> items = dbHandler.getAllItems(); // Fetch items from the database
        Admin_additems_combobox.getItems().clear();

        for (Item item : items) {
            Admin_additems_combobox.getItems().add(item.getName());
        }
    }
    
    private void displaySelectedItemDetails() {
        // Get the selected item
        String selectedItem = Admin_additems_combobox.getValue();

        if (selectedItem == null || selectedItem.isEmpty()) {
            System.out.println("No item selected.");
            return;
        }

        // Fetch all items from the database
        List<Item> items = dbHandler.getAllItems();

        // Find the item by name
        for (Item item : items) {
            if (item.getName().equals(selectedItem)) {
                // Set the text fields with the item's details
                Admin_additems_Nametext.setText(item.getName());
                Admin_additems_Pricetext.setText(String.valueOf(item.getPrice()));
                Admin_additems_Stocktext.setText(String.valueOf(item.getQuantity()));
                return;
            }
        }

        // If the item was not found
        System.out.println("Item not found in the database.");
    }
    
    @FXML
    void handleAddItemStock(ActionEvent event) {
        // Get the selected item from ComboBox
        String selectedItem = Admin_additems_combobox.getValue();
        System.out.println(selectedItem);
        if (selectedItem == null || selectedItem.isEmpty()) {
            System.out.println("No item selected.");
            return;
        }

        // Get the amount to add from the TextField
        String amountText = Admin_additems_amounttext.getText();
        
        if (amountText == null || amountText.isEmpty()) {
            System.out.println("Amount is empty.");
            return;
        }

        try {
            // Parse the amount to add
            int amountToAdd = Integer.parseInt(amountText);

            // Create an Alert for confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Quantity Update");
            alert.setHeaderText("Are you sure you want to update the quantity of this item?");
            alert.setContentText("Item: " + selectedItem + "\nAmount to Add: " + amountToAdd);
            
            // Show the alert and wait for a response
            Optional<ButtonType> result = alert.showAndWait();
            
            // If the user clicks "Yes", update the item
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Update the item in the database
                dbHandler.updateItemQuantity(selectedItem, amountToAdd);

                // After updating, refresh the displayed item details
                displaySelectedItemDetails();
            } else {
                System.out.println("Update canceled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered: " + e.getMessage());
        }
    }

    public void goBackAdminMainfromADDitems(ActionEvent event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminMainPage.fxml"));
		Parent root = loader.load();

	
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    
    //////////////////////////////////////////////////
    //Admin Add packages
    /////////////////////////////////////////////////
    
    @FXML
    private Button AddPakckage_back_button;
    
    @FXML
    private Button AddPackage_Add_button;

    @FXML
    private TextArea AddPackage_Description;

    @FXML
    private TextArea AddPackage_Destinations;

    @FXML
    private TextField AddPackage_Duration;

    @FXML
    private TextField AddPackage_Name;

    @FXML
    private TextField AddPackage_Price;
    
    @FXML
    private void handleAddPackage() {
        // Check if all fields are filled
        if (AddPackage_Name.getText().trim().isEmpty() || 
            AddPackage_Destinations.getText().trim().isEmpty() || 
            AddPackage_Duration.getText().trim().isEmpty() || 
            AddPackage_Description.getText().trim().isEmpty() || 
            AddPackage_Price.getText().trim().isEmpty()) {

            // Show error alert if any field is empty
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Input Error");
            errorAlert.setHeaderText("Missing Information");
            errorAlert.setContentText("Please fill in all the fields before adding the package.");
            errorAlert.showAndWait();
            return; // Exit method if fields are incomplete
        }

        try {
            // Collect data from UI
            String name = AddPackage_Name.getText().trim();
            String destinations = AddPackage_Destinations.getText().trim();
            String destinationFormatted = destinations.replace("\n", ", "); // Replace newlines with commas
            int duration = Integer.parseInt(AddPackage_Duration.getText().trim());
            String description = AddPackage_Description.getText().trim();
            int price = Integer.parseInt(AddPackage_Price.getText().trim());

            // Confirmation alert
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Add New Package");
            confirmationAlert.setContentText("Are you sure you want to add this package?");

            // Wait for user response
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Create package object
                Package newPackage = new Package(name, destinationFormatted, duration, description, price);

                // Send package to the DB handler
                boolean success = dbHandler.addPackageToDatabase(newPackage);

                // Show feedback
                if (success) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText("Package Added");
                    successAlert.setContentText("The package was successfully added to the database.");
                    successAlert.showAndWait();
                    
                    AddPackage_Description.clear();
                    AddPackage_Destinations.clear();
                    AddPackage_Duration.clear();
                    AddPackage_Name.clear();
                    AddPackage_Price.clear();
                    
                    
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Database Error");
                    failureAlert.setHeaderText("Failed to Add Package");
                    failureAlert.setContentText("There was an error while adding the package. Please try again.");
                    failureAlert.showAndWait();
                }
            } // If "Cancel" is pressed, do nothing
        } catch (NumberFormatException e) {
            // Show error alert for invalid input (non-integer for duration/price)
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Input Error");
            errorAlert.setHeaderText("Invalid Data");
            errorAlert.setContentText("Please ensure that 'Duration' and 'Price' are valid numbers.");
            errorAlert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Generic error handling
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Unexpected Error");
            errorAlert.setContentText("An unexpected error occurred: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public void goToMainPagefromAddPackage(ActionEvent event) throws IOException {
        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminMainPage.fxml"));
        Parent root = loader.load();

        // Set up the new scene
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nomad Oasis");
        stage.show();

        // Close the current stage
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    

}
