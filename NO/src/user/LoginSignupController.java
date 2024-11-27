package user;

import traveler.TravelerMainPageController;
import backend.Admin;
import backend.Consultant;
import backend.DBHandler;
import backend.Traveler;
import backend.User;
import backend.UserFactory;
import consultant.ConsultantMainPageController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import admin.AdminMainPageController;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import backend.SharedState;
public class LoginSignupController implements Initializable {


	@FXML
	private TextField Rgiester_CNIC;
	@FXML
	private DatePicker Register_DOB;
	@FXML
	private TextField Rgiester_email1;

	@FXML
	private ChoiceBox<String> Register_Gender;

	@FXML
	private Button Login_button;

	@FXML
	private AnchorPane Login_form;

	@FXML
	private PasswordField Login_password;

	@FXML
	private TextField Login_username;

	@FXML
	private AnchorPane Register_Form;

	@FXML
	private PasswordField Register_password;

	@FXML
	private Button Register_signup_button;

	@FXML
	private TextField Rgiester_username;


	@FXML
	private Label Slider_create_account;

	@FXML
	private Button Slider_CreateNew_button;

	@FXML
	private AnchorPane Slider_Form;

	@FXML
	private ImageView Slider_LOGO;

	@FXML
	private Button Slider_Login_button;


	public void SuccessfulLogin(ActionEvent event) throws IOException, ClassNotFoundException {
	    String username = Login_username.getText();
	    String password = Login_password.getText();

	    if (event.getSource() == Login_button) {
	        DBHandler dbHandler = DBHandler.getInstance();

	        String travelerID = dbHandler.validateTravelerLogin(username, password);

	        if (travelerID != null) {
	            String userType = dbHandler.getUserType(username);

	          
	            User user = UserFactory.createUser(userType);
	            user.setUserid(travelerID);

	            SharedState sharedState = SharedState.getInstance();
	            sharedState.setUser(user);

	    
	            String fxmlPath = "";
	            String cssPath = "";
	            
	            if (user instanceof Traveler) 
	            {
	                fxmlPath = "/traveler/TravelerMainPage.fxml";
	                cssPath = "/traveler/TravelerMainPage.css";
	            } 
	            else if (user instanceof Admin) 
	            {
	                fxmlPath = "/admin/AdminMainPage.fxml";
	                cssPath = "/admin/AdminMainPage.css";
	            } 
	            else if (user instanceof Consultant) 
	            {
	                fxmlPath = "/consultant/ConsulatantMainPage.fxml";
	                cssPath = "/consultant/ConsultantMainPage.css";
	            }

	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	            Parent root = loader.load();

	            Scene scene = new Scene(root);
	            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            stage.setTitle("Nomad Oasis");
	            stage.show();

	          
	            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Login Failed");
	            alert.setHeaderText("Invalid Username or Password");
	            alert.setContentText("Please try again.");
	            alert.showAndWait();
	        }
	    }
	}

	public void switchForm(ActionEvent event)
	{

		TranslateTransition slider = new TranslateTransition();

		if (event.getSource() == Slider_CreateNew_button) {
			slider.setNode(Slider_Form);
			slider.setToX(400);
			slider.setDuration(Duration.seconds(.5));

			slider.setOnFinished((ActionEvent e) -> {
				Slider_Login_button.setVisible(true);
				Slider_CreateNew_button.setVisible(false);


				Login_form.setVisible(false);
				Register_Form.setVisible(true);
				Slider_create_account.setText("Already have an account");

			});

			slider.play();
		} 
		else if (event.getSource() == Slider_Login_button) 
		{
			slider.setNode(Slider_Form);
			slider.setToX(0);
			slider.setDuration(Duration.seconds(.5));

			slider.setOnFinished((ActionEvent e) -> {
				Slider_Login_button.setVisible(false);
				Slider_CreateNew_button.setVisible(true);
				Slider_create_account.setText("Create Account");

				Login_form.setVisible(true);
				Register_Form.setVisible(false);
			});

			slider.play();

		}
	}


	public void handleSignUp(ActionEvent event)
	{
		String email = Rgiester_email1.getText();
		String username = Rgiester_username.getText();
		String password = Register_password.getText();
		String cnic = Rgiester_CNIC.getText();
		String gender = Register_Gender.getValue(); 
		LocalDate dob = Register_DOB.getValue(); 
		
		
		
		if (email.isEmpty() || username.isEmpty() || password.isEmpty() || cnic.isEmpty() || gender == null || dob == null)
		{
			showAlert("Form Incomplete", "Please fill in all fields before submitting.");
			return;
		}
		if (!isAgeValid(dob)) 
		{
			showAlert("Age Restriction", "You must be at least 18 years old to register.");
			return;
		}
		if (!validateCNIC(cnic)) 
		{
		    showAlert("Invalid CNIC", "Please enter a valid 13-digit CNIC without dashes.");
		    return;
		}
		Traveler T1 = new Traveler();
		if(!T1.validateUsername1(username))
		{
			showAlert("Username Taken", "This Username is already taken, Try another one");
			return;
		}
		T1 = new Traveler(email, username, password, cnic, gender, dob.toString());

		boolean isRegistered = T1.addtravelertoDB();

		if (isRegistered) 
		{
			showAlertS("Registration Success", "Account Created Successfully", "You can now log in using your credentials.");
		} 
		else 
		{
			showAlert("Registration Failed", "Error Creating Account", "Please check your details and try again.");
		}
	}

	private void showAlert(String title, String headerText, String contentText) 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	private void showAlertS(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	// Overloaded method for simpler use
	private void showAlert(String title, String contentText) {
		showAlert(title, title, contentText);
	}

	private boolean isAgeValid(LocalDate dob) {
		if (dob == null) return false; // Return false if DOB is not selected
		LocalDate currentDate = LocalDate.now();
		int age = Period.between(dob, currentDate).getYears();
		return age >= 18;
	}

	public void initialize(URL location, ResourceBundle resources) {
		// Populate the Gender ChoiceBox with "Male" and "Female"
		Register_Gender.getItems().addAll("Male", "Female");


	}

	private boolean validateCNIC(String input) {
	    if (input == null || input.length() != 13) {
	        return false;
	    }
	    try {
	        new java.math.BigInteger(input); // Ensures all characters are numeric
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}






}
