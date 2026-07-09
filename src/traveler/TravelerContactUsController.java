package traveler;
import backend.Traveler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import backend.DBHandler;
import backend.Query;
import backend.SharedState;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TravelerContactUsController implements Initializable{

	private DBHandler dbHandler;
	private Traveler Traveler;

	 @FXML
	 public void initialize() 
	 {
	        SharedState state = SharedState.getInstance();
	        dbHandler = DBHandler.getInstance();
	        this.Traveler = (backend.Traveler) state.getUser();

	
	    }
	 
	public void setDBHandler(DBHandler dbHandler)
	{
	    this.dbHandler = dbHandler;
	}

	@FXML
	private Button goback_Main;

	@FXML
	private Button QuerySubmit;

	@FXML
	private TextField ContactUs_username;

	@FXML
	private TextField ContactUs_Email;

	@FXML
	private TextField ContactUs_Query;

	@FXML
	private Button response;

	@FXML
	void goBackTravelerMain(ActionEvent event) throws IOException 
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

	@FXML
	void openResponsePage(ActionEvent event) throws IOException, ClassNotFoundException 
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerQueryResponse.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerContactUs.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

	}
	@FXML
	void submitQuery(ActionEvent event) throws ClassNotFoundException
	{
		String username = ContactUs_username.getText();
		String email = ContactUs_Email.getText();
		String queryContent = ContactUs_Query.getText();
		
		if (username.isEmpty() || email.isEmpty() || queryContent.isEmpty())
		{
			showAlert("All fields must be filled out.");
			return;
		}

		String submissionResponse = Traveler.addQueryToDB(queryContent);
		Query q1= new Query(Traveler.getUserid(),queryContent);
		
		if (submissionResponse.equals("Query submitted successfully."))
		{
			int consultantID = dbHandler.getConsultantID();
			if (consultantID != -1)
			{
				String assignmentResponse = q1.assignConsultantToQuery(Traveler.getUserid(), consultantID);
				
				
				showAlert(assignmentResponse);
				
				if(assignmentResponse.equals("Consultant assigned successfully."))
				{
					 ContactUs_username.clear();
					 ContactUs_Email.clear();
					 ContactUs_Query.clear();
				}
			}
			else 
			{
				showAlert("No consultants available.");
			}
		}
		else
		{
			showAlert(submissionResponse);
		}
    }


	private void showAlert(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Nomad Oasis");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
 {
        SharedState state = SharedState.getInstance();
        dbHandler = DBHandler.getInstance();
        this.Traveler = (backend.Traveler) state.getUser();
	}
}
