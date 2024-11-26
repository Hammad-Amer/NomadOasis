package consultant;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;

import backend.DBHandler;
import backend.SharedState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import traveler.TravelerQueryResponseController;
import user.LoginSignupController;

public class ConsultantMainPageController {
	private DBHandler dbHandler;
	private Connection connection;
	private String ConsultantID;

	 @FXML
	 public void initialize() {
	        // Retrieve the shared data
	        SharedState state = SharedState.getInstance();
	        this.connection = state.getConnection();
	       

	
	    }
	 
	public void setDBHandler(DBHandler dbHandler)
	{
	    this.dbHandler = dbHandler;
	}
	public String getConsultantID()
	{
		return ConsultantID;
	}
	public void setConsultantID(String travelerID)
	{
		ConsultantID = travelerID;
	}


    @FXML
    private Button query;

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
    
    
    @FXML
    public 	void querysection(ActionEvent event) throws IOException, ClassNotFoundException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/consultant/ConsultantRespondQuery.fxml"));
		Parent root = loader.load();

		ConsultantRespondQueryController controller = loader.getController();
		controller.setConsultantID(ConsultantID);
		controller.loadQueries();
		controller.setDBHandler(dbHandler);

		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/consultant/ConsultantMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

    }


}