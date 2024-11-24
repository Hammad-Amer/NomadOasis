package traveler;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import traveler.TravelerMainPageController;
import traveler.TravelerViewHistoryController;

public class TravelerViewHistoryController 
{
	private String TravelerID;

	public String getTravelerID() 
	{
		return TravelerID;
	}
	public void setTravelerID(String travelerID) 
	{
		TravelerID = travelerID;
	}
	@FXML
	private Button goback_Main;

	@FXML
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

		// Close the current window
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

	}

}
