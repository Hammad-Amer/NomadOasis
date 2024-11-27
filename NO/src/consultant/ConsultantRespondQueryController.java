package consultant;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import backend.DBHandler;
import backend.SharedState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import backend.Consultant;
public class ConsultantRespondQueryController implements Initializable {

	private DBHandler dbHandler;
	private Consultant Consultant;

	@FXML
	public void initialize()
	{
		SharedState state = SharedState.getInstance();
		this.Consultant= (backend.Consultant) state.getUser();
		dbHandler = DBHandler.getInstance();
	}
	public void setDBHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
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

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nomad Oasis");
		stage.show();


		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
	public void loadQueries() throws ClassNotFoundException 
	{	
		List<String> queryIDs = Consultant.getQueries();

		ObservableList<String> queryList = FXCollections.observableArrayList(queryIDs);
		querycombobox.setItems(queryList);
	}
	@FXML
	private void onQuerySelected(ActionEvent event) 
	{
		String selectedQueryID = querycombobox.getValue();
		if (selectedQueryID != null)
		{
			String queryInfoText = Consultant.getQInfo(selectedQueryID);
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
		boolean isUpdated = Consultant.submitQueryResponse(selectedQueryID, response);
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		SharedState state = SharedState.getInstance();
		this.Consultant= (backend.Consultant) state.getUser();
		dbHandler = DBHandler.getInstance();
	}
}
