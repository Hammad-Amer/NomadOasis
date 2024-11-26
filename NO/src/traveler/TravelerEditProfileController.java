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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TravelerEditProfileController implements Initializable{
	
    @FXML
    private Button goback_Main;
    
	@FXML
    private Button ProfileEdit;

    @FXML
    private TextField UpdateProfile_username;

    @FXML
    private TextField UpdateProfile_Email;
    
    @FXML
    private Text type_text;
    
    private Traveler currentTraveler;
    private DBHandler dbHandler;
    private Connection connection;
    private Traveler Traveler;

    @FXML
	 public void initialize() {
	        // Retrieve the shared data
	        SharedState state = SharedState.getInstance();
	        this.Traveler = (backend.Traveler) state.getUser();
	        dbHandler = DBHandler.getInstance();
	
	    }
	 

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
	 
	public void loadTravelerData() throws ClassNotFoundException 
	{

        currentTraveler = dbHandler.fetchTravelerData(Traveler.getUserid());

        if (currentTraveler != null)
        {	
            UpdateProfile_username.setText(currentTraveler.getUsername());
            UpdateProfile_Email.setText(currentTraveler.getEmail());
            type_text.setText("Traveler"); 
        }
    }

    @FXML
    public void editedProfile(ActionEvent event) throws ClassNotFoundException
    {
      
        
        String updatedUsername = UpdateProfile_username.getText();
        String updatedEmail = UpdateProfile_Email.getText();

        boolean success = dbHandler.updateTravelerData(Traveler.getUserid(), updatedUsername, updatedEmail);

        if (success) 
        {
        	showAlert("Profile updated successfully!.");
        } 
        else 
        {
        	showAlert("Failed to update profile.");
           
        }
    }
    
    private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Nomad Oasis");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        SharedState state = SharedState.getInstance();
        this.Traveler = (backend.Traveler) state.getUser();
        dbHandler = DBHandler.getInstance();
	}

}
