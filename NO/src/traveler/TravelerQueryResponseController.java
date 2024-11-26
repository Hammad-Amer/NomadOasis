package traveler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import backend.DBHandler;
import backend.Query;
import backend.SharedState;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TravelerQueryResponseController implements Initializable
{
	private DBHandler dbHandler;
	private Connection connection;
	private String TravelerID;

	public void setDBHandler(DBHandler dbHandler)
	{
	    this.dbHandler = dbHandler;
	}
	
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
	private ComboBox<Integer> DropDownResponse;

    @FXML
    private Text QueryInfo;

    @FXML
    private Text ResponseText;

	@FXML
	public void contactUs(ActionEvent event) throws IOException
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/traveler/TravelerContactUs.fxml"));
		Parent root = loader.load();


		TravelerContactUsController controller1 = loader.getController();
		controller1.setTravelerID(TravelerID);


		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/traveler/TravelerContactUs.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();

		// Close the current window
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();

	}
	
	


	private void populateComboBox() {
        if (TravelerID != null && !TravelerID.isEmpty())
        {
            ObservableList<Integer> queryIDs = FXCollections.observableArrayList(dbHandler.getQueryIDs(TravelerID));
            DropDownResponse.setItems(queryIDs);

            DropDownResponse.setOnAction(event -> displayQueryDetails());
        }
    }

    private void displayQueryDetails() 
    {
        Integer selectedQueryID = DropDownResponse.getSelectionModel().getSelectedItem();

        if (selectedQueryID != null)
        {
            Query query = dbHandler.getQueryDetails(selectedQueryID);

            if (query != null) 
            {
                QueryInfo.setText(query.viewQueryContent());
                String responseText = query.getResponse();
                ResponseText.setText(responseText != null ? responseText : "NO RESPONSE YET");
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	       // Populate the combo box when the controller is initialized
        SharedState state = SharedState.getInstance();
        this.connection = state.getConnection();
        dbHandler =new DBHandler(connection);
        this.TravelerID = state.getTravelerID();
        populateComboBox();
	}
	

}