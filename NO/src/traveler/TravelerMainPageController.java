package traveler;
import backend.SharedState;
import backend.Traveler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import backend.Item;
import backend.Cart;
import backend.DBHandler;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import user.LoginSignupController;
import backend.Package;

public class TravelerMainPageController implements Initializable{

	private Traveler Traveler;
	
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	
	}
	


	 @FXML
	 public void initialize() {
	        // Retrieve the shared data
	        SharedState state = SharedState.getInstance();
	        this.connection = state.getConnection();
	        this.Traveler =  (backend.Traveler) state.getUser();

	
	    }


	/////////////////////////////////////////////////////////
	@FXML
    private Button traveler_book_hotels;

    @FXML
    private Button traveler_contact_us;

    @FXML
    private Button traveler_update_profile;

    @FXML
    private Button traveler_vew_packages;

    @FXML
    private Button traveler_view_destinitions;

    @FXML
    private Button traveler_view_history;

    @FXML
    private Button traveler_view_items;

    @FXML
   	private Button bookinghotel;

   	@FXML
   	private Button Edit_Profile;

   	@FXML
   	private Button viewLog;

   	@FXML
   	private Button Contact_Us;
    
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
    
   	
    public void GotoViewDestinationPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == traveler_view_destinitions)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    public void GotoViewItemStore(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == traveler_view_items)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/itemShop.fxml"));
    	
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    public void GotoViewPackages(ActionEvent event) throws IOException {
        if (event.getSource() == traveler_vew_packages) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewPackages.fxml"));
            Parent root = loader.load();

            // Ensure the controller is properly set
            TravelerMainPageController controller = loader.getController();

            // Populate the ChoiceBox (move this logic from initialize to here)
            controller.loadPackages();

            // Set up the new scene and stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nomad Oasis");
            stage.show();

            // Close the current stage
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }

    
    
    
    //////////////////////////////////////////////////////////////
    
    @FXML
    private Button VD_Chitral;
    public void GoBackToChitralPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_Chitral)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/Chitral.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    @FXML
    private Button Chitral_back_button;
    
    public void GoBackToVDfromChitral(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Chitral_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    
    @FXML
    private Button VD_FairyMeadows;
    public void GoBackToFairyMeadowsPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_FairyMeadows)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/FairyMeadows.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button FairyMeadows_back_button;
    public void GoBackToVDfromFairyMeadows(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == FairyMeadows_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    @FXML
    private Button VD_HunzaValley;
    public void GoBackToHunzaPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_HunzaValley)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/HunzaValley.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    @FXML
    private Button HUnzaVally_back_button;
    public void GoBackToVDfromHunza(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == HUnzaVally_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    @FXML
    private Button VD_SkarduValley;
    public void GoBackToSkarduPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_SkarduValley)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/SkarduValley.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button Skardu_back_button;
    public void GoBackToVDfromSkardu(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Skardu_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button VD_SwatValley;
    public void GoBackToSwatPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_SwatValley)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/SwatValley.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button Swat_back_button;
    public void GoBackToVDfromSwat(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Swat_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button VD_WadiShogran;
    public void GoBackToShogranPage(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == VD_WadiShogran)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/WadiShogran.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button WadiShogran_back_button;
    public void GoBackToVDfromShogran(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == WadiShogran_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewDesitinations.fxml"));
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    @FXML
    private Button back_button_VD;
    
   
    
    public void GoBackToMainPageFromVD(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == back_button_VD)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
    		TravelerMainPageController controller = new TravelerMainPageController(); // Ensure this is the correct instance


    		Parent root = loader.load();
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    
    
	////////////////////////////////////////////////////////
    //Traveler item store controller class
    ////////////////////////////////////////////////////

    @FXML
    private Button IS_Back_button;

    @FXML
    private AnchorPane IS_Beanies_grid;

    @FXML
    private Button IS_CART_button;

    @FXML
    private GridPane IS_Camping_grid;

    @FXML
    private Button IS_Clothing_button;

    @FXML
    private Button IS_Essential_button;

    @FXML
    private Text IS_FIrstAID_text;

    @FXML
    private Button IS_Hikinggear_button;

    @FXML
    private Text IS_JAcket_text;



    @FXML
    private GridPane IS_Jacket_grid;

    @FXML
    private Button IS_Jacket_minus;

    @FXML
    private Button IS_Jacket_plus;

    @FXML
    private Text IS_SHoes_quantity;

   @FXML
    private Button IS_Shoes_Add;

    @FXML
    private Button IS_Shoes_minus;

    @FXML
    private Button IS_Shoes_plus;

    @FXML
    private Text IS_Shoes_text;

    @FXML
    private GridPane IS_TOrch_grid;


    @FXML
    private Button IS_Torch_minus;

    @FXML
    private Button IS_Torch_plus;



    @FXML
    private GridPane IS_bacgpack_grid;

    @FXML
    private Button IS_bacgpack_minus;

    @FXML
    private Button IS_bacgpack_plus;

    @FXML
    private Text IS_bacgpack_quantity;

    @FXML
    private Text IS_bacgpack_text;

 

    @FXML
    private GridPane IS_beanies_grid;

    @FXML
    private Button IS_beanies_minus;

    @FXML
    private Button IS_beanies_plus;

    @FXML
    private Text IS_beanies_quantity;



    @FXML
    private Button IS_camping_minus;

    @FXML
    private Button IS_camping_plus;

    @FXML
    private Text IS_camping_quantity;

    @FXML
    private Text IS_camping_text;


    @FXML
    private GridPane IS_gloves_grid;

    @FXML
    private Button IS_gloves_minus;

    @FXML
    private Button IS_gloves_plus;

    @FXML
    private Text IS_gloves_quantity;

    @FXML
    private Text IS_jacket_quantity;

    @FXML
    private Text IS_power_text;



    @FXML
    private GridPane IS_powerbank_grid;

    @FXML
    private Button IS_powerbank_minus;

    @FXML
    private GridPane IS_Shoes_grid;
    @FXML
    private GridPane IS_FIrstAID_grid;
   
    @FXML
    private Button IS_powerbank_plus;

    @FXML
    private Text IS_torch_quantity;

    @FXML
    private Text IS_torch_text;

    @FXML
    private Text IS_gloves_text;
    
    @FXML
    private Text IS_beanie_text;
    
    @FXML
    private Button IS_FirsatAid_plus;
    @FXML
    private Button IS_FirstAID_minus;

    
    @FXML
    private Text IS_FIrstAID_quantity;
   
    
    @FXML
    private Text is_powerbank_quantity;

    public void ShowClothing(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == IS_Clothing_button)
    	{
    		IS_powerbank_grid.setVisible(false);
    		IS_gloves_grid.setVisible(true);
    		IS_beanies_grid.setVisible(true);
    		IS_bacgpack_grid.setVisible(false);
    		IS_TOrch_grid.setVisible(false);
    		IS_Jacket_grid.setVisible(true);
    		IS_Camping_grid.setVisible(false);
    		IS_Shoes_grid.setVisible(false);
    		IS_FIrstAID_grid.setVisible(false);
    		
    		IS_FIrstAID_text.setVisible(false);
    		IS_torch_text.setVisible(false);
    		IS_camping_text.setVisible(false);
    		IS_power_text.setVisible(false);
    		IS_bacgpack_text.setVisible(false);
    		IS_Shoes_text.setVisible(false);
    		IS_JAcket_text.setVisible(true);
    		IS_gloves_text.setVisible(true);
    		IS_beanie_text.setVisible(true);
    		
    	}
    	
    }
    public void showHikingstuff(ActionEvent event) throws IOException
    {
    	if (event.getSource() == IS_Hikinggear_button)
    	{
    		IS_powerbank_grid.setVisible(false);
    		IS_gloves_grid.setVisible(false);
    		IS_beanies_grid.setVisible(false);
    		IS_bacgpack_grid.setVisible(true);
    		IS_TOrch_grid.setVisible(false);
    		IS_Jacket_grid.setVisible(false);
    		IS_Camping_grid.setVisible(true);
    		IS_Shoes_grid.setVisible(true);
    		IS_FIrstAID_grid.setVisible(false);
    		
    		IS_FIrstAID_text.setVisible(false);
    		IS_torch_text.setVisible(false);
    		IS_camping_text.setVisible(true);
    		IS_power_text.setVisible(false);
    		IS_bacgpack_text.setVisible(true);
    		IS_Shoes_text.setVisible(true);
    		IS_JAcket_text.setVisible(false);
    		IS_gloves_text.setVisible(false);
    		IS_beanie_text.setVisible(false);
    	}
    	
    }
    public void showEssential(ActionEvent event) throws IOException
    {
    	if (event.getSource() == IS_Essential_button)
    	{
    		IS_powerbank_grid.setVisible(true);
    		IS_gloves_grid.setVisible(false);
    		IS_beanies_grid.setVisible(false);
    		IS_bacgpack_grid.setVisible(false);
    		IS_TOrch_grid.setVisible(true);
    		IS_Jacket_grid.setVisible(false);
    		IS_Camping_grid.setVisible(false);
    		IS_Shoes_grid.setVisible(false);
    		IS_FIrstAID_grid.setVisible(true);
    		
    		IS_FIrstAID_text.setVisible(true);
    		IS_torch_text.setVisible(true);
    		IS_camping_text.setVisible(false);
    		IS_power_text.setVisible(true);
    		IS_bacgpack_text.setVisible(false);
    		IS_Shoes_text.setVisible(false);
    		IS_JAcket_text.setVisible(false);
    		IS_gloves_text.setVisible(false);
    		IS_beanie_text.setVisible(false);
    	}
    	
    }
    
    @FXML
    private void goBackToTravelerMainfromIS(ActionEvent event) throws IOException, SQLException {
        // Check if the cart has items
        DBHandler dbHandler = new DBHandler(getConnection());
        int travelerID = Integer.parseInt(Traveler.getUserid());

        if (!dbHandler.isCartEmpty(travelerID)) { // Assuming isCartEmpty checks cart content for the traveler
            // Show confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cart Confirmation");
            alert.setHeaderText("You have items in your cart!");
            alert.setContentText("If you go back, your cart will be emptied. Do you want to proceed?");
            
            // Wait for user's decision
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed to proceed, clear the cart
                dbHandler.clearCart(travelerID);

                // Load main page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
                Parent root = loader.load();

                // Display the main page
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Nomad Oasis");
                stage.show();

                // Close the current window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            }
        } else {
            // If the cart is empty, simply go back to the main page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
            Parent root = loader.load();

            // Display the main page
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nomad Oasis");
            stage.show();

            // Close the current window
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }


   
  
 // Shoes Plus and Minus
    public void ShoesHandlePlusButtonClick() throws SQLException {

    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_SHoes_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(1); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_SHoes_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

   

	public void ShoesHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_SHoes_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_SHoes_quantity.setText(String.valueOf(quantity));
        }
    }

    // Gloves Plus and Minus
    public void GlovesHandlePlusButtonClick() throws SQLException {
        
        
       	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_gloves_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(5); 
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_gloves_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void GlovesHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_gloves_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_gloves_quantity.setText(String.valueOf(quantity));
        }
    }

    // Camping Plus and Minus
    public void CampingHandlePlusButtonClick() throws SQLException {
    
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_camping_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(3); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_camping_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void CampingHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_camping_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_camping_quantity.setText(String.valueOf(quantity));
        }
    }

    // Beanies Plus and Minus
    public void BeaniesHandlePlusButtonClick() throws SQLException {
 
       
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_beanies_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(6); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_beanies_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void BeaniesHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_beanies_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_beanies_quantity.setText(String.valueOf(quantity));
        }
    }

    // Backpack Plus and Minus
    public void BackpackHandlePlusButtonClick() throws SQLException {
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_bacgpack_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(2); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_bacgpack_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void BackpackHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_bacgpack_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_bacgpack_quantity.setText(String.valueOf(quantity));
        }
    }

    // Torch Plus and Minus
    public void TorchHandlePlusButtonClick() throws SQLException {
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_torch_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(8); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_torch_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void TorchHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_torch_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_torch_quantity.setText(String.valueOf(quantity));
        }
    }

    // Powerbank Plus and Minus
    public void PowerbankHandlePlusButtonClick() throws SQLException {
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(is_powerbank_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(9); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            is_powerbank_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void PowerbankHandleMinusButtonClick() {
        int quantity = Integer.parseInt(is_powerbank_quantity.getText());
        if (quantity > 0) {
            quantity--;
            is_powerbank_quantity.setText(String.valueOf(quantity));
        }
    }

    // Jacket Plus and Minus
    public void JacketHandlePlusButtonClick() throws SQLException {
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_jacket_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(4); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_jacket_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void JacketHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_jacket_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_jacket_quantity.setText(String.valueOf(quantity));
        }
    }

    // First Aid Plus and Minus
    public void FirstAidHandlePlusButtonClick() throws SQLException {
        
    	DBHandler dbHandler = new DBHandler(getConnection());
        int currentQuantity = Integer.parseInt(IS_FIrstAID_quantity.getText());
       
        int maxStock = dbHandler.getItemStock(7); // Assume itemID = 1 for shoes
        if (currentQuantity < maxStock) {
            currentQuantity++;
            IS_FIrstAID_quantity.setText(String.valueOf(currentQuantity));
        } else {
            showAlert("Stock Limit Reached", "You cannot add more items than available in stock.");
        }
    }

    public void FirstAidHandleMinusButtonClick() {
        int quantity = Integer.parseInt(IS_FIrstAID_quantity.getText());
        if (quantity > 0) {
            quantity--;
            IS_FIrstAID_quantity.setText(String.valueOf(quantity));
        }
    }
 
    public void CartButtoncItemStore(ActionEvent event) throws IOException {
        if (event.getSource() == IS_CART_button) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/Cart.fxml"));
            Parent root = loader.load(); // Removed setController(this)

            // Open the Cart window
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nomad Oasis");
            stage.show();

            // Call displayCartDetails with a valid traveler ID
            TravelerMainPageController controller = loader.getController(); // Get the controller instance
            controller.displayCartDetails(Integer.parseInt(Traveler.getUserid()));

            // Close the current window
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }

    
    @FXML
    private void IS_AddHandleClick() throws SQLException {
        int travelerID = Integer.parseInt(Traveler.getUserid()); 
        Connection conn = getConnection();
        Item item = new Item();
        String message = "";
        Traveler TTemp=Traveler;
    
        Cart C1=new Cart();
        // Shoes
        if (Integer.parseInt(IS_SHoes_quantity.getText()) > 0) {
            int itemID = 1; // ID for Shoes
           
            int quantity = Integer.parseInt(IS_SHoes_quantity.getText());
            
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Jacket
        if (Integer.parseInt(IS_jacket_quantity.getText()) > 0) {
            int itemID = 4; // ID for Jacket
            int quantity = Integer.parseInt(IS_jacket_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Torch
        if (Integer.parseInt(IS_torch_quantity.getText()) > 0) {
            int itemID = 8; // ID for Torch
            int quantity = Integer.parseInt(IS_torch_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Backpack
        if (Integer.parseInt(IS_bacgpack_quantity.getText()) > 0) {
            int itemID = 2; // ID for Backpack
            int quantity = Integer.parseInt(IS_bacgpack_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Gloves
        if (Integer.parseInt(IS_gloves_quantity.getText()) > 0) {
            int itemID = 5; // ID for Gloves
            int quantity = Integer.parseInt(IS_gloves_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Beanies
        if (Integer.parseInt(IS_beanies_quantity.getText()) > 0) {
            int itemID = 6; // ID for Beanies
            int quantity = Integer.parseInt(IS_beanies_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Camping gear
        if (Integer.parseInt(IS_camping_quantity.getText()) > 0) {
            int itemID = 3; // ID for Camping gear
            int quantity = Integer.parseInt(IS_camping_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Powerbank
        if (Integer.parseInt(is_powerbank_quantity.getText()) > 0) {
            int itemID = 9; // ID for Powerbank
            int quantity = Integer.parseInt(is_powerbank_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // First Aid
        if (Integer.parseInt(IS_FIrstAID_quantity.getText()) > 0) {
            int itemID = 7; // ID for First Aid
            int quantity = Integer.parseInt(IS_FIrstAID_quantity.getText());
            item.setItemid(itemID);
            item.setQuantity(quantity);
            message += C1.ItemtoCart(item, TTemp, conn) + "\n";
        }

        // Show the result
        if (message.isEmpty()) {
            showAlert("Cart", "No items were selected to add to the cart.");
        } else {
            showAlert("Cart", message);
        }
    }

    
    public void showAlert(String title, String message) 
    {
    	
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
   /////////////////////////////////////////////////////////
    //CArtBuyController

    @FXML
    private TextField Cart_Address_box;


    @FXML
    private TextField Cart_City_box;

    @FXML
    private TextField Cart_Contactinfo_box;

    @FXML
    private TextField Cart_State_box;

    @FXML
    private TextField Cart_Zip_box;

    @FXML
    private Text CART_Total_text;
    
    @FXML
    private TextArea Cart_orderSummary_area;
    
    
    @FXML
    private Button Cart_Back_button;

    @FXML
    private Button Cart_buybutton;
    
    public void CartBackbuttontoItemShop(ActionEvent event) throws IOException
    {
    	if (event.getSource() == Cart_Back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/itemShop.fxml"));

    		Parent root = loader.load();
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();
    	        

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    		
    	}
    }
    
 // Buy button logic
    public void CartBuyButtonPressed(ActionEvent event) {
        // Check if all fields are filled
        if (Cart_Address_box.getText().isEmpty() ||
            Cart_City_box.getText().isEmpty() ||
            Cart_Contactinfo_box.getText().isEmpty() ||
            Cart_State_box.getText().isEmpty() ||
            Cart_Zip_box.getText().isEmpty()) {

            // Show an alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill all the required fields.");
            alert.setContentText("Make sure all fields (Address, City, State, Zip, and Contact Information) are filled.");
            alert.showAndWait();
        } else {
            // Show confirmation dialog if all fields are filled
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Purchase");
            confirmationAlert.setHeaderText("Are you sure?");
            confirmationAlert.setContentText("Do you want to confirm your order?");

            // Wait for user response
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Order accepted
                	DBHandler dbHandler=new DBHandler(getConnection());
                	 dbHandler.clearCart(Integer.parseInt(Traveler.getUserid()));
                	
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Order Accepted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Your order has been successfully placed!");
                    successAlert.showAndWait();

                    // Navigate back to Item Shop
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/itemShop.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Nomad Oasis");
                        stage.show();
                        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    
    public void displayCartDetails(int travelerID) {
    	DBHandler DB=new DBHandler(getConnection());
        List<Item> cartItems = DB.getCartItems(travelerID); // Fetch cart items

        StringBuilder orderSummary = new StringBuilder();
        int total = 0;

        for (Item item : cartItems) {
            int itemTotal = item.getQuantity() * item.getPrice();

            // Append to order summary
            orderSummary.append(item.getName())
                        .append(" -\t Quantity: ").append(item.getQuantity())
                        .append(",\t Price: ").append(item.getPrice())
                        .append(",\t Total: ").append(itemTotal)
                        .append("\n");

            // Add to total cost
            total += itemTotal;
        }

        // Update the TextArea and Total Text in JavaFX
        Cart_orderSummary_area.setText(orderSummary.toString());
        CART_Total_text.setText("Total: " + total);
    }

    
    
    ////////////////////////////////////////////////////////////////////////////
    //ViewPackages
    
    @FXML
    private TextArea Packages_Desc_text;

    @FXML
    private TextArea Packages_Dest_text;

    @FXML
    private Text Packages_Duration_text;

    @FXML
    private Text Packages_Name_text;

    @FXML
    private Text Packages_Price_text;

    @FXML
    private ChoiceBox<String> Packages_available_box;
    
    @FXML
    private Button Package_Cancel_button;

    @FXML
    private Button Package_back_button;

    @FXML
    private Button Package_buyPack_button;
    
    public void populateChoiceBox() 
    {
    	DBHandler dbHandler=new DBHandler(connection);
        Packages_available_box.getItems().addAll(dbHandler.getAllPackageNames());
    }

    
    public void loadPackages() {
        if (Packages_available_box == null) {
            System.out.println("ChoiceBox not initialized");
            return;
        }
        DBHandler dbHandler=new DBHandler(connection);
        // Populate the ChoiceBox with data
        Packages_available_box.getItems().addAll(dbHandler.getAllPackageNames());

        // Add listener to handle selection
        Packages_available_box.setOnAction(event -> {
            String selectedPackage = Packages_available_box.getValue();
            if (selectedPackage != null) {
                displayPackageDetails(selectedPackage);
            }
        });
    }

    // Set up listener for ChoiceBox
    public void setupChoiceBoxListener() 
    {
        Packages_available_box.setOnAction(event -> {
            String selectedPackage = Packages_available_box.getValue();
            if (selectedPackage != null) {
                displayPackageDetails(selectedPackage);
            }
        });
    }

    // Display package details in UI
    public void displayPackageDetails(String packageName) {
        DBHandler dbHandler = new DBHandler(connection);
        Package pkg = dbHandler.getPackageDetails(packageName);

        if (pkg != null) {
            Packages_Name_text.setText(pkg.getName());

            // Display destinations line by line
            String[] destinations = pkg.getDestination().split(",\\s*");
            Packages_Dest_text.clear(); // Clear the TextArea before appending
            for (String destination : destinations) {
                Packages_Dest_text.appendText(destination + "\n");
            }

            // Display duration, price, and description
            Packages_Duration_text.setText(String.valueOf(pkg.getDuration())+" days");
            Packages_Desc_text.setWrapText(true); // Ensure text wraps
            Packages_Desc_text.setText(pkg.getDescription());
            Packages_Price_text.setText(String.valueOf(pkg.getPrice())+" Rs.");
        } else {
            System.out.println("Package not found: " + packageName);
        }
    }
    
    
    public void GoBackToTravelermainFromPackages(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Package_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
    	
    		Parent root = loader.load();
    		
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    public void GoBackToBuyPageFromPackages(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Package_buyPack_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/BuyPackage.fxml"));
    	
    		Parent root = loader.load();
    	
    		TravelerMainPageController controller = loader.getController();
            controller.PackageBuy_Name_button.setText(Packages_Name_text.getText());
            controller.PackageBuy_price_button.setText(Packages_Price_text.getText());
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    public void GoBackToCancelPageFromPackages(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == Package_Cancel_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/CancelPackage.fxml"));
    	
    		Parent root = loader.load();
    		 // Get the controller from the loader
            TravelerMainPageController controller = loader.getController();
            controller.populateCancelPackageTextArea(Integer.parseInt(Traveler.getUserid()));
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    //	Package Buy
    
    @FXML
    private Button PackageBuy_Buy_button;

    @FXML
    private Text PackageBuy_Name_button;

    @FXML
    private Text PackageBuy_Total_text;

    @FXML
    private Button PackageBuy_add_button;

    @FXML
    private Button PackageBuy_back_button;

    @FXML
    private Button PackageBuy_minus_button;

    @FXML
    private Text PackageBuy_price_button;

    @FXML
    private Text PackageBuy_quantity_button;
    
    
    public void GoToViewPackageFromBuy(ActionEvent event) throws IOException
    {
    	
    	if (event.getSource() == PackageBuy_back_button)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewPackages.fxml"));
    	
    		Parent root = loader.load();
    		
    		 TravelerMainPageController controller = loader.getController();

             // Populate the ChoiceBox (move this logic from initialize to here)
             controller.loadPackages();
    		
    		 Scene scene = new Scene(root);
    		 
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	}
    	
       
    }
    
    
    
    @FXML
    private void handleQuantityChange(ActionEvent event) {
        try {
            // Get and print the price text
            String priceText = PackageBuy_price_button.getText().replace("Rs.", "").trim();
  

            // Get and print the quantity and total text
            String quantityText = PackageBuy_quantity_button.getText().trim();
            String totalText = PackageBuy_Total_text.getText().replace("Rs.", "").trim();
    // Debugging: Print the raw total text

            // Validate the price (should be a valid number)

            double price = Double.parseDouble(priceText);

            // Validate the quantity (should be a valid integer)
            int quantity = 0;
  
                quantity = Integer.parseInt(quantityText);
         

            // Validate the total (should be a valid number)
            double total = 0;
       
                total = Double.parseDouble(totalText);
       

                if (event.getSource() == PackageBuy_add_button) {
                    if (quantity < 9) { // Prevent quantity from going beyond 9
                        quantity++;
                        total += price;
                    }
                } else if (event.getSource() == PackageBuy_minus_button) {
                    if (quantity > 0) {
                        quantity--;
                        total -= price;
                    }
                }

            // Update the UI with the new values
            PackageBuy_quantity_button.setText(String.valueOf(quantity));
            PackageBuy_Total_text.setText("Rs. " + String.format("%.2f", total));
        } catch (NumberFormatException e) {
            System.err.println("Invalid input for price or quantity: " + e.getMessage());
            // You can show an error dialog or alert to the user
        }
    }

    @FXML
    private void handleBuyPAckageButtonClick(ActionEvent event) {
        try {
            // Get the quantity
            String quantityText = PackageBuy_quantity_button.getText().trim();
            int quantity = Integer.parseInt(quantityText);

            // Check if the quantity is 0
            if (quantity == 0) {
                // Show an alert that nothing was bought
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Items Selected");
                alert.setHeaderText(null);
                alert.setContentText("You have not selected any items to buy.");
                alert.showAndWait();
            } else {
                // Show a confirmation alert for the purchase
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Purchase");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to buy this package?");

                // Wait for the user to respond to the confirmation
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Proceed with the buying process
                        // For now, just show a success message (you can replace this with actual buying logic)
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Purchase Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Thank you for your purchase!");
                        successAlert.showAndWait();
                        
                        DBHandler dbhandler=new DBHandler(getConnection());
                        dbhandler.addBookingPackage(Integer.parseInt(Traveler.getUserid()), PackageBuy_Name_button.getText(), quantity);
                        // Reset the quantity and total after successful purchase
                        PackageBuy_quantity_button.setText("0");
                        PackageBuy_Total_text.setText("Rs. 0.00");
                    }
                });
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input for quantity: " + e.getMessage());
            // Optionally, show an alert for invalid input (if necessary)
        }
    }

////////////////////////////////////////////////////////

    
		    
		public void editProfile(ActionEvent event) throws IOException, ClassNotFoundException
		{
		
		if (event.getSource() == Edit_Profile)
		{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerEditProfile.fxml"));
		Parent root = loader.load();
		
		
		TravelerEditProfileController controller1 = loader.getController();
		controller1.loadTravelerData();
		
		
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerEditProfile.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
		}
		}
		
		public void contactUs(ActionEvent event) throws IOException
		{
		
		if (event.getSource() == Contact_Us)
		{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerContactUs.fxml"));
		Parent root = loader.load();
		
		
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerContactUs.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		
		// Close the current window
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
		}
		
		
		
		}
		
		public void openLogs(ActionEvent event) throws IOException {
		if (event.getSource() == viewLog) 
		{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerViewHistory.fxml"));
		
		Parent root = loader.load();
		
	
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerContactUs.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Logs - Nomad Oasis");
		stage.show();
		
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
		}
		}
		@FXML
		public void bookHotelPage(ActionEvent event) throws IOException, ClassNotFoundException
		{
		
		if (event.getSource() == bookinghotel)
		{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerBookHotel.fxml"));
		Parent root = loader.load();
		
		
		
		
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerBookHotel.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();
		
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
		}
		}
///////////////////////
		
		//////////////////////////
		

	    @FXML
	    private Button CancelPackage_Back_Button;

	    @FXML
	    private Button CancelPackage_Cancel_Button;

	    @FXML
	    private Text CancelPackage_Returnprice_text;

	    @FXML
	    private ComboBox<String> CancelPackage_dropdown;

	    @FXML
	    private TextArea CancelPackage_packages_text;
	    
	    public void GoBackToPackagePageFromCancel(ActionEvent event) throws IOException
	    {
	    	
	    	if (event.getSource() == CancelPackage_Back_Button)
	    	{
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewPackages.fxml"));
	    	
	    		 Parent root = loader.load();
	    		 TravelerMainPageController controller = loader.getController();

	             // Populate the ChoiceBox (move this logic from initialize to here)
	             controller.loadPackages();
	    		 Scene scene = new Scene(root);
	    		 
	    	     Stage stage = new Stage();
	    	     stage.setScene(scene);
	    	     stage.setTitle("Nomad Oasis");
	    	     stage.show();

	    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	    	}
	    	
	       
	    }
		
	    public void populateCancelPackageTextArea(int travelerID) {
	        DBHandler dbhandler = new DBHandler(connection);
	        ArrayList<Package> bookedPackages = dbhandler.getBookedPackages(travelerID);

	        if (bookedPackages.isEmpty()) {
	            CancelPackage_packages_text.setText("No packages booked.");
	            CancelPackage_dropdown.getItems().clear();
	            return;
	        }

	        StringBuilder packageDetails = new StringBuilder();
	        CancelPackage_dropdown.getItems().clear();

	        for (Package pkg : bookedPackages) {
	            packageDetails.append("ID: ").append(pkg.getId()).append("\t")
	                          .append("Name: ").append(pkg.getName()).append("\n");
	                          

	            // Add package ID to the ComboBox
	            CancelPackage_dropdown.getItems().add(String.valueOf(pkg.getId()));
	        }

	        CancelPackage_packages_text.setText(packageDetails.toString());

	        // Add listener for ComboBox selection
	        CancelPackage_dropdown.setOnAction(this::handlePackageSelection);
	    }


	    
	    @FXML
	    public void handlePackageSelection(ActionEvent event) {
	        String selectedPackageID = CancelPackage_dropdown.getValue();

	        if (selectedPackageID == null || selectedPackageID.isEmpty()) {
	            CancelPackage_Returnprice_text.setText("No package selected.");
	            return;
	        }

	        int packageID = Integer.parseInt(selectedPackageID);

	        // Fetch price and quantity for the selected package
	        DBHandler dbhandler = new DBHandler(connection);
	        int price = dbhandler.getPackagePrice(packageID);
	        int quantity = dbhandler.getBookingQuantity(Integer.parseInt(Traveler.getUserid()), packageID); // Add travelerID as needed

	        if (price == -1 || quantity == -1) {
	            CancelPackage_Returnprice_text.setText("Error fetching package details.");
	            return;
	        }

	        // Calculate refund: total price minus 25%
	        int totalCost = price * quantity;
	        double refundAmount = totalCost * 0.75; // Deduct 25%

	        CancelPackage_Returnprice_text.setText("Refund Amount: " + refundAmount+" RS.");
	    }


	    @FXML
	    public void handleCancelPackage(ActionEvent event) throws IOException {
	        // Get selected package ID from the ComboBox
	        String selectedPackageID = CancelPackage_dropdown.getValue();

	        if (selectedPackageID == null || selectedPackageID.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText("No Package Selected");
	            alert.setContentText("Please select a package to cancel.");
	            alert.showAndWait();
	            return;
	        }

	        // Confirmation dialog
	        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	        confirmationAlert.setTitle("Confirmation");
	        confirmationAlert.setHeaderText("Are you sure?");
	        confirmationAlert.setContentText("Do you really want to cancel this package?");
	        
	        Optional<ButtonType> result = confirmationAlert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            int packageID = Integer.parseInt(selectedPackageID);
	            DBHandler dbhandler = new DBHandler(connection);

	            // Remove the booking from the database
	            boolean success = dbhandler.cancelBooking(Integer.parseInt(Traveler.getUserid()), packageID);
	            if (success) {
	                // Success alert
	                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                successAlert.setTitle("Success");
	                successAlert.setHeaderText("Cancellation Successful");
	                successAlert.setContentText("The package has been successfully canceled.");
	                successAlert.showAndWait();

	                // Redirect to ViewPackages page
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/ViewPackages.fxml"));
	                Parent root = loader.load();
		    		 TravelerMainPageController controller = loader.getController();

		             // Populate the ChoiceBox (move this logic from initialize to here)
		             controller.loadPackages();
	                Scene scene = new Scene(root);

	                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                stage.setScene(scene);
	                stage.setTitle("Nomad Oasis");
	                stage.show();
	            } else {
	                // Failure alert
	                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
	                failureAlert.setTitle("Error");
	                failureAlert.setHeaderText("Cancellation Failed");
	                failureAlert.setContentText("An error occurred while canceling the package. Please try again.");
	                failureAlert.showAndWait();
	            }
	        }
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
	        SharedState state = SharedState.getInstance();
	        this.connection = state.getConnection();
	        this.Traveler =  (backend.Traveler) state.getUser();
		}


   
    
}
