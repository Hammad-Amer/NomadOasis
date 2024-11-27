package admin;

import backend.Package;
import backend.Room;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import backend.DBHandler;
import backend.Hotel;
import backend.Item;
import backend.SharedState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private Admin Admin;

	@FXML
	public void initialize()
	{
		SharedState state = SharedState.getInstance();
		

		this.dbHandler=DBHandler.getInstance();
		this.Admin = (backend.Admin) state.getUser();
	}

	@FXML
	private Button admins_addhotel_button;

	@FXML
	private Button admins_additems_button;

	@FXML
	private Button admins_addpackages_button;
	
    @FXML
    private Button admins_removehotel_button1;

    @FXML
    private Button admins_removeitems_button1;

    @FXML
    private Button admins_removepackages_button1;
   
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
        controller.populateComboBoxWithItems(); 

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
    public void goToRemovePackage(ActionEvent event) throws IOException, SQLException {
     
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminRemovePackage.fxml"));
        Parent root = loader.load();
        
        AdminMainPageController controller = loader.getController();
        controller.populatePackagesComboBox();
 
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
    public void goToRemoveItem(ActionEvent event) throws IOException {
     
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminRemoveItem.fxml"));
        Parent root = loader.load();

        AdminMainPageController controller = loader.getController();
        controller.populateComboBoxWithRemItems(); 
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
        
        try {
            List<Hotel> hotels = Admin.hotels();
            for (Hotel hotel : hotels) 
            {
                selecthotelnamecombo.getItems().add(hotel.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean validateDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void handleAddHotel(ActionEvent event)
    {
        try {
            String name = hotelnametext.getText();
            String location = hotellocationtext.getText();
            String description = hoteldescriptiontext.getText();
            String rating1 =hotelratingtext.getText();
            
            if (name == null || location == null ||  description == null || rating1 == null) 
            {
                    showAlert(AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
                    return;
            }
            
            if(!validateDouble(rating1))
            {
            	showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid rating (e.g., 4.5).");
                return;
            }
            
            double rating = Double.parseDouble(rating1);
            
            if (Admin.addHotelToDB(name, location, description, rating))
            {
                showAlert(AlertType.INFORMATION, "Success", "Hotel added successfully!");
                loadHotelNames();
            } 
            else 
            {
                showAlert(AlertType.ERROR, "Error", "Failed to add hotel. Please try again.");
            }
        } 
        catch (NumberFormatException e)
        {
        	
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid rating.");
        }
    }

    @FXML
    private void handleAddRoom(ActionEvent event)
    {
        String selectedHotel = selecthotelnamecombo.getValue();
        String hotelID1 = String.valueOf(Admin.getHotelID(selectedHotel));
        String roomNum1 = roomnumbertext.getText();
        String type = roomtypetext.getValue(); 
        String price1 = roompricetext.getText();
        
        if (selectedHotel == null || hotelID1==null||roomNum1==null||type==null||price1==null) 
        {
            showAlert(AlertType.WARNING, "Selection Required", "Please Fill Out All The Fields");
            return;
        }
        
        if(!validateInt(hotelID1))
        {
        	showAlert(AlertType.ERROR, "Invalid Input", "HotelID shouild be an Integer");
        	return;
        }
        if(!validateInt(roomNum1))
        {
        	showAlert(AlertType.ERROR, "Invalid Input", "RoomNumber shouild be an Integer");
        	return;
        }
        if(!validateInt(price1))
        {
        	showAlert(AlertType.ERROR, "Invalid Input", "Price shouild be an Integer");
        	return;
        }
        
        int hotelID = Admin.getHotelID(selectedHotel);
        int roomNum = Integer.parseInt(roomnumbertext.getText());
        int price = Integer.parseInt(roompricetext.getText());
        
        
        

        try 
        {
        	
            if (Admin.addRoomToDB(hotelID, roomNum, type, price))
            {
                showAlert(AlertType.INFORMATION, "Success", "Room added successfully!");
            }
            else 
            {
                showAlert(AlertType.ERROR, "Error", "Failed to add room. Please try again.");
            }
        }
        catch (NumberFormatException e) 
        {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numbers for room number and price.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message)
    {
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
    
    public void populateComboBoxWithItems() 
    {
    	 if (Admin_additems_combobox == null) 
    	 {
             System.out.println("ChoiceBox not initialized");
             return;
         }
    	
        List<Item> items = Admin.getAllItems(); 
        Admin_additems_combobox.getItems().clear();

        for (Item item : items) 
        {
            Admin_additems_combobox.getItems().add(item.getName());
        }
    }
    
    public void displaySelectedItemDetails() 
    {
        String selectedItem = Admin_additems_combobox.getValue();

        if (selectedItem == null || selectedItem.isEmpty())
        {
            System.out.println("No item selected.");
            return;
        }

        List<Item> items = Admin.getAllItems();

        for (Item item : items)
        {
            if (item.getName().equals(selectedItem)) 
            {
                Admin_additems_Nametext.setText(item.getName());
                Admin_additems_Pricetext.setText(String.valueOf(item.getPrice()));
                Admin_additems_Stocktext.setText(String.valueOf(item.getQuantity()));
                return;
            }
        }

        System.out.println("Item not found in the database.");
    }
    
    @FXML
    void handleAddItemStock(ActionEvent event) 
    {
        String selectedItem = Admin_additems_combobox.getValue();
        System.out.println(selectedItem);
        if (selectedItem == null || selectedItem.isEmpty())
        {
            System.out.println("No item selected.");
            return;
        }

        String amountText = Admin_additems_amounttext.getText();
        
        if (amountText == null || amountText.isEmpty()) 
        {
            System.out.println("Amount is empty.");
            return;
        }

        try {
            int amountToAdd = Integer.parseInt(amountText);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Quantity Update");
            alert.setHeaderText("Are you sure you want to update the quantity of this item?");
            alert.setContentText("Item: " + selectedItem + "\nAmount to Add: " + amountToAdd);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
            	
              Admin.updateItemQuantity(selectedItem, amountToAdd);

                displaySelectedItemDetails();
            }
            else
            {
                System.out.println("Update canceled.");
            }
            
        }
        catch (NumberFormatException e) 
        {
        	showAlert(AlertType.ERROR, "Invalid Input", "Amount should be a number");
        	return;
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
    private void handleAddPackage() 
    {
        if (AddPackage_Name.getText().trim().isEmpty() || 
            AddPackage_Destinations.getText().trim().isEmpty() || 
            AddPackage_Duration.getText().trim().isEmpty() || 
            AddPackage_Description.getText().trim().isEmpty() || 
            AddPackage_Price.getText().trim().isEmpty()) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Input Error");
            errorAlert.setHeaderText("Missing Information");
            errorAlert.setContentText("Please fill in all the fields before adding the package.");
            errorAlert.showAndWait();
            return; 
        }

        try {
            String name = AddPackage_Name.getText().trim();
            String destinations = AddPackage_Destinations.getText().trim();
            String destinationFormatted = destinations.replace("\n", ", "); 
            int duration = Integer.parseInt(AddPackage_Duration.getText().trim());
            String description = AddPackage_Description.getText().trim();
            int price = Integer.parseInt(AddPackage_Price.getText().trim());

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Add New Package");
            confirmationAlert.setContentText("Are you sure you want to add this package?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
        
                boolean success = Admin.addPackageToDatabase(name, destinationFormatted, duration, description, price);

                if (success) 
                {
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
                    
                    
                } 
                else 
                {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Database Error");
                    failureAlert.setHeaderText("Failed to Add Package");
                    failureAlert.setContentText("There was an error while adding the package. Please try again.");
                    failureAlert.showAndWait();
                }
            } 
        } 
        catch (NumberFormatException e) 
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Input Error");
            errorAlert.setHeaderText("Invalid Data");
            errorAlert.setContentText("Please ensure that 'Duration' and 'Price' are valid numbers.");
            errorAlert.showAndWait();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Unexpected Error");
            errorAlert.setContentText("An unexpected error occurred: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public void goToMainPagefromAddPackage(ActionEvent event) throws IOException
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
    
    
    private boolean validateInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    
    //Admin remove packages
    @FXML
    private TextArea Admin_RemovePackage_Description;

    @FXML
    private TextArea Admin_RemovePackage_Destinations;

    @FXML
    private Text Admin_RemovePackage_Durationtext;

    @FXML
    private Text Admin_RemovePackage_Nametext;

    @FXML
    private Text Admin_RemovePackage_Pricetext;

    @FXML
    private ComboBox<String> RemovePackage_combobox;

    @FXML
    private Button RemovePackage_remove_button;

    @FXML
    private Button RemovePakckage_back_button;

    public void goToMainPagefromRemovePackage(ActionEvent event) throws IOException
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
    
    public void populatePackagesComboBox() throws SQLException {
        List<String> packageNames = dbHandler.getAllPackageNames();
		RemovePackage_combobox.getItems().clear();
		RemovePackage_combobox.getItems().addAll(packageNames);
    }
    
    public void displayPackageDetails() throws SQLException {
        String selectedPackage = RemovePackage_combobox.getValue();
        if (selectedPackage != null) {
            Package packageDetails = dbHandler.getPackageDetails(selectedPackage);
			if (packageDetails != null) {
			    Admin_RemovePackage_Nametext.setText(packageDetails.getName());
			    Admin_RemovePackage_Durationtext.setText(packageDetails.getDuration() + " days");
			    Admin_RemovePackage_Pricetext.setText(  packageDetails.getPrice()+ "Rs.");
			    Admin_RemovePackage_Description.setText(packageDetails.getDescription());

			    // Show destinations, each on a new line
			    Admin_RemovePackage_Destinations.setText(
			        String.join("\n", packageDetails.getDestination().split(","))
			    );
			}
        }
    }
    
    public void removeSelectedPackage(ActionEvent event) {
        String selectedPackage = RemovePackage_combobox.getValue();
        if (selectedPackage != null) {
            // Show confirmation alert
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Package Removal");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to remove the package: " + selectedPackage + "?");

            // Wait for user response
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed removal
                try {
                    boolean success = Admin.removePackage(selectedPackage);
                    if (success) {
                        // Show success alert
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Package Removed");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("The package \"" + selectedPackage + "\" has been removed successfully.");
                        successAlert.showAndWait();

                        // Refresh the combo box and clear fields
                        populatePackagesComboBox();
                        clearFields();
                    } else {
                        // Show error alert
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Removal Failed");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to remove the package. Please try again.");
                        errorAlert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Show error alert for SQL exception
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("An error occurred while removing the package.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Show warning alert if no package is selected
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("No Package Selected");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Please select a package to remove.");
            warningAlert.showAndWait();
        }
    }


    // Clear all fields
    private void clearFields() {
        Admin_RemovePackage_Nametext.setText("");
        Admin_RemovePackage_Durationtext.setText("");
        Admin_RemovePackage_Pricetext.setText("");
        Admin_RemovePackage_Description.setText("");
        Admin_RemovePackage_Destinations.setText("");
    }
    
    
    ////////////////////////////////////////////
    /// remove items
    
    @FXML
    private Button Admin_Removeitems_button;

    @FXML
    private Text Admin_removeitems_Nametext;

    @FXML
    private Text Admin_removeitems_Pricetext;

    @FXML
    private Text Admin_removeitems_Stocktext;

    @FXML
    private TextField Admin_removeitems_amounttext;

    @FXML
    private ComboBox<String> Admin_removeitems_combobox;

    @FXML
    private Button removeItem_back_button;

    @FXML
    void goBackAdminMainfromRemItems(ActionEvent event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminMainPage.fxml"));
		Parent root = loader.load();

		

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/admin/AdminMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    public void populateComboBoxWithRemItems() 
    {
    	 if (Admin_removeitems_combobox == null) 
    	 {
             System.out.println("ChoiceBox not initialized");
             return;
         }
    	
        List<Item> items = Admin.getAllItems(); 
        Admin_removeitems_combobox.getItems().clear();

        for (Item item : items) 
        {
        	Admin_removeitems_combobox.getItems().add(item.getName());
        }
    }
    
    public void displaySelectedRemItemDetails() 
    {
        String selectedItem = Admin_removeitems_combobox.getValue();

        if (selectedItem == null || selectedItem.isEmpty())
        {
          
            return;
        }

        List<Item> items = Admin.getAllItems();

        for (Item item : items)
        {
            if (item.getName().equals(selectedItem)) 
            {
            	Admin_removeitems_Nametext.setText(item.getName());
            	Admin_removeitems_Pricetext.setText(String.valueOf(item.getPrice()));
            	Admin_removeitems_Stocktext.setText(String.valueOf(item.getQuantity()));
                return;
            }
        }

        System.out.println("Item not found in the database.");
    }
    
    
    public void handleRemoveItemStock() {
        String selectedItem = Admin_removeitems_combobox.getValue();
        if (selectedItem == null || selectedItem.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Item Selected", "Please select an item to remove stock.");
            return;
        }

        String amountText = Admin_removeitems_amounttext.getText();
        if (amountText == null || amountText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Amount", "Please enter a valid amount to remove.");
            return;
        }

        int amountToRemove;
        try {
            amountToRemove = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a numeric value for the amount.");
            return;
        }

        try {
            List<Item> items = dbHandler.getAllItems();
            for (Item item : items) {
                if (item.getName().equals(selectedItem)) {
                    int currentStock = item.getQuantity();

                    if (amountToRemove < 1) {
                        showAlert(Alert.AlertType.WARNING, "Invalid Amount", "Amount must be at least 1.");
                        return;
                    }

                    if (amountToRemove > currentStock) {
                        showAlert(Alert.AlertType.WARNING, "Insufficient Stock", "The amount exceeds current stock. Current stock: " + currentStock);
                        return;
                    }

                    // Confirm removal
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirm Stock Removal");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to remove " + amountToRemove + " from the stock of " + selectedItem + "?");

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Update stock in database
                        boolean updated = Admin.updateItemStock(item.getItemid(), currentStock - amountToRemove);
                        if (updated) {
                            showAlert(Alert.AlertType.INFORMATION, "Stock Removed", amountToRemove + " units of " + selectedItem + " have been removed successfully.");
                            populateComboBoxWithRemItems();
                            Admin_removeitems_amounttext.clear();
                        } else {
                            showError("Database Error", "Failed to update stock in the database.");
                        }
                    }
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database Error", "An error occurred while accessing the database.");
        }
    }



    private void showError(String title, String message) 
    {
        showAlert(Alert.AlertType.ERROR, title, message);
    }
    
    ///////////////////////////////
    @FXML
    private Button removehotelbutton;

    @FXML
    private Button removeboombutton;

    @FXML
    private ComboBox<String> selectremovehotelnamecombo;

    @FXML
    private ComboBox<Integer> selectremoveroomnamecombo;
    
	private List<Hotel> hotelList;
    
    public void loadHotels() throws ClassNotFoundException, SQLException 
	{

		hotelList = dbHandler.getAllHotels(); 

		ObservableList<String> hotelNames = FXCollections.observableArrayList();
		for (Hotel hotel : hotelList) {
			hotelNames.add(hotel.getName());
		}
		selectremovehotelnamecombo.setItems(hotelNames);

		selectremovehotelnamecombo.setOnAction(event -> loadRooms());
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
    private void loadRooms() 
	{
		String selectedHotelName = (String) selectremovehotelnamecombo.getValue();
		if (selectedHotelName != null) 
		{
			Hotel selectedHotel = findHotelByName(selectedHotelName);

			if (selectedHotel != null)
			{
				ObservableList<Integer> roomNumbers = FXCollections.observableArrayList();
				for (Room room : selectedHotel.getRooms()) 
				{
					if (room.isAvailable()) 
					{
						roomNumbers.add(room.getRoomNum());
					}
				}
				selectremoveroomnamecombo.setItems(roomNumbers);

			//	selectremovehotelnamecombo.setOnAction(event -> updateRoomDetails(selectedHotel));
			}
		}
	}
    
    @FXML
    void handleRemoveHotel(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
    	String hotelName=(String) selectremovehotelnamecombo.getValue();
    	Hotel RemoveHotel =  findHotelByName(hotelName);
    	if(Admin.removeHotelDB(RemoveHotel))
    	{
    		showAlert(AlertType.INFORMATION, "Success", "Hotel Removed successfully!");
    		selectremoveroomnamecombo.setItems(null);
    		loadHotels();
    	}
    	else 
    	{
    		showError("Database Error", "An Error Occured while trying to delete the Hotel");
    	}
    }
    @FXML
    void handleRemoveRoom(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
    	int RoomNum= selectremoveroomnamecombo.getValue();
    	String hotelName=(String) selectremovehotelnamecombo.getValue();
    	Hotel RemoveHotel =  findHotelByName(hotelName);
    	
    	
    	if(Admin.removeRoomDB(RemoveHotel,RoomNum))
    	{
    		showAlert(AlertType.INFORMATION, "Success", "Hotel Room Removed successfully!");
    		selectremoveroomnamecombo.setItems(null);
    		loadHotels();
    		loadRooms();
    	}
    	else 
    	{
    		showError("Database Error", "An Error Occured while trying to delete the Room");
    	}
    }
    
    @FXML
    public void goToRemoveHotel(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminRemoveHotel.fxml"));
		Parent root = loader.load();

		AdminMainPageController controller = loader.getController();
		controller.loadHotels();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/admin/AdminMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
