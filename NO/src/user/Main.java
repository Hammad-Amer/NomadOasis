package user;
import backend.DBHandler;	
import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	private static Connection connection;
	private Stage primaryStage;
	private StackPane mainLayout;
	 private static DBHandler dbHandler;
	 
	@Override
	public void start(Stage primaryStage) {
		
		dbHandler = DBHandler.getInstance();
        try {
        	  connection = dbHandler.connect();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
        
		try {
			
			this.primaryStage=primaryStage;
			this.primaryStage.setTitle("Nomad Oasis");
			
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setScene(scene);
			//primaryStage.show();
			
			ShowLoginSignUp();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void ShowLoginSignUp() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/user/LoginSignup.fxml"));
		mainLayout = loader.load();
		
		 LoginSignupController controller = loader.getController();
	   // LoginSignupController controller = new LoginSignupController();
	  //  loader.setController(controller);
		
		
		Scene scene= new Scene(mainLayout);
		scene.getStylesheets().add(getClass().getResource("LoginSignup.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	 @Override
	    public void stop() {
	        // Disconnect database on application exit
	        if (dbHandler != null) {
	            dbHandler.disconnect();
	        }
	    }
	
}
