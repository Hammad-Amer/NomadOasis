package traveler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import backend.DBHandler;
import backend.SharedState;
import backend.Traveler;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TravelerEditProfileController {
	
    @FXML
    private Button goback_Main;
    
	@FXML
    private Button ProfileEdit;

    @FXML
    private TextField UpdateProfile_username;

    @FXML
    private TextField UpdateProfile_Email;

    @FXML
    private TextField UpdateProfile_Info;
    
    @FXML
    private Text type_text;
    
    private Traveler currentTraveler;
    private DBHandler dbHandler;
    private Connection connection;
    private String TravelerID;

    @FXML
	 public void initialize() {
	        // Retrieve the shared data
	        SharedState state = SharedState.getInstance();
	        this.connection = state.getConnection();
	        this.TravelerID = state.getTravelerID();

	
	    }
	 
	
	public String getTravelerID() {
		return TravelerID;
	}



	public void setTravelerID(String travelerID) {
		TravelerID = travelerID;
	}

	public void goBackTravelerMain(ActionEvent event) throws IOException
    {
    	
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
    		Parent root = loader.load();
    		
    		TravelerMainPageController controller = loader.getController();
    		controller.setTravelerID(TravelerID);
    		
    		
    		 Scene scene = new Scene(root);
    		 
    	     scene.getStylesheets().add(getClass().getResource("/traveler/TravelerMainPage.css").toExternalForm());
    	     Stage stage = new Stage();
    	     stage.setScene(scene);
    	     stage.setTitle("Nomad Oasis");
    	     stage.show();

    	     ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	
    }
	 
	public void loadTravelerData() throws ClassNotFoundException 
	{
        dbHandler = new DBHandler(connection);
       
        currentTraveler = dbHandler.fetchTravelerData(TravelerID);

        if (currentTraveler != null)
        {	
            UpdateProfile_username.setText(currentTraveler.getUsername());
            UpdateProfile_Email.setText(currentTraveler.getEmail());
            UpdateProfile_Info.setText("Add Profile Info Here");
            type_text.setText("Traveler"); 
        }
    }

    @FXML
    public void editedProfile(ActionEvent event) throws ClassNotFoundException
    {
    
           dbHandler = new DBHandler(connection);
   
          
        
        String updatedUsername = UpdateProfile_username.getText();
        String updatedEmail = UpdateProfile_Email.getText();
        String updatedInfo = UpdateProfile_Info.getText();

        boolean success = dbHandler.updateTravelerData(TravelerID, updatedUsername, updatedEmail);

        if (success) 
        {
            System.out.println("Profile updated successfully!");
        } 
        else 
        {
            System.err.println("Failed to update profile.");
           
        }
    }

}
