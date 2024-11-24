package consultant;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import backend.DBHandler;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConsultantRespondQueryController {

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
	private Button goback;

	@FXML
	private ComboBox<String> querycombobox;

	@FXML
	private Text queryinfo;

	@FXML
	private TextField replytextbox;

	@FXML
	private Button submitbutton;

	@FXML
	void goBackmain(ActionEvent event) throws ClassNotFoundException, IOException
	{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/consultant/ConsulatantMainPage.fxml"));
		Parent root = loader.load();

		ConsultantMainPageController controller = loader.getController();
		controller.setConsultantID(ConsultantID); 
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/consultant/ConsultantMainPage.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();


		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
	
	public void loadQueries() throws ClassNotFoundException 
	{	
		
			dbHandler=new DBHandler(connection);
		
		
	
        List<String> queryIDs = dbHandler.getAssignedQueries(ConsultantID);

        ObservableList<String> queryList = FXCollections.observableArrayList(queryIDs);
        querycombobox.setItems(queryList);
    }

    @FXML
    private void onQuerySelected(ActionEvent event) 
    {
        String selectedQueryID = querycombobox.getValue();
        if (selectedQueryID != null)
        {
            String queryInfoText = dbHandler.getQueryInfo(selectedQueryID);
            queryinfo.setText(queryInfoText);
        }
    }

    @FXML
    void submitResponse(ActionEvent event) throws ClassNotFoundException 
    {
        String selectedQueryID = querycombobox.getValue();
        String response = replytextbox.getText();

        if (selectedQueryID == null || response.isEmpty())
        {
            showAlert("Error", "Please select a query and enter your response.", Alert.AlertType.ERROR);
            return;
        }

        boolean isUpdated = dbHandler.submitResponse(selectedQueryID, response);
        if (isUpdated) 
        {
            showAlert("Success", "Response submitted successfully.", Alert.AlertType.INFORMATION);
            queryinfo.setText("");
            replytextbox.clear();
            loadQueries(); 
        }
        else 
        {
            showAlert("Error", "Failed to submit the response.", Alert.AlertType.ERROR);
        }
    }
	
	private void showAlert(String title, String message, Alert.AlertType alertType)
	{
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
