package application;
	
import java.sql.SQLException;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("LoginPage.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("TECH-RV App");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Admin.Connexion();
		
		launch(args);
		
		Admin.Connexion().close();
		/*User u = new User();
		ArrayList<User> doctors = new ArrayList<User>();
		ArrayList<User> patients = new ArrayList<User>();
		doctors = u.RetreiveDoctorData();
		patients = u.RetreivePatientData();
		ArrayList<User> ALL = new ArrayList<User>();
		ALL.addAll(doctors);
		ALL.addAll(patients);
		for (int i = 0; i < ALL.size(); i++) {
		      User user = ALL.get(i);
		      System.out.println("User " + (i+1) + ": login=" + user.getLogin() + ", password=" + user.getPassword());
		    }
		//System.out.println(doctors);
		//System.out.println(patients);
		 * 
		 */
	}
}
