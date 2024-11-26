package traveler;
import backend.DBHandler;
import backend.SharedState;
import backend.Traveler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import traveler.TravelerMainPageController;
import traveler.TravelerViewHistoryController;

public class TravelerViewHistoryController implements Initializable
{
	private DBHandler dbHandler;
	private Traveler Traveler;

	@FXML
	private Button goback_Main;
	@FXML
	private TextArea textArea;
	@FXML
	public void goBackTravelerMain(ActionEvent event) throws IOException
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerMainPage.fxml"));
		Parent root = loader.load();

		TravelerMainPageController controller = loader.getController();


		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		// Close the current window
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        SharedState state = SharedState.getInstance();
        dbHandler =DBHandler.getInstance();
        this.Traveler = (backend.Traveler) state.getUser();
        loadTravelerHistory();
		
	}
	


	////////////////////////////////////////////////////////////////////////

	public void loadTravelerHistory() {
		try {
			List<String> logs = dbHandler.getTravelerLogs(Traveler.getUserid());
			StringBuilder history = new StringBuilder();

			for (String log : logs) {
				history.append(log).append("\n");
			}

			textArea.setText(history.toString());
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			textArea.setText("Failed to load history.");
		}
	}








	

	


}
