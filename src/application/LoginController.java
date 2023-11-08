package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
	
	private final String PREF_EMAIL = "login_email";
    private Preferences prefs = Preferences.userNodeForPackage(LoginController.class);

	
	private int tentatives =0;

    @FXML
    private Button GoToSignUpButton;

    @FXML
    private Button LoginButtton;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;
    
    
    public void initialize() {
        String savedEmail = prefs.get(PREF_EMAIL, "luc.dubois@gmail.com");
        login.setText(savedEmail);
    }

    @FXML
    void GoToSignUpPage(ActionEvent event) throws IOException {
    	
    	
    	Parent ChooseType = (Parent)FXMLLoader.load(getClass().getResource("ChooseType.fxml"));
    	Scene ChooseTypeScene = new Scene(ChooseType);
    	Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	appStage.setScene(ChooseTypeScene);
    	appStage.setTitle("Choose Type");
    	appStage.show();
    	
  

    }

    @FXML
    void LoginAction(ActionEvent event) throws SQLException, IOException {
        
        boolean userFound = false;
        
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Admin.Connexion();
        
        ArrayList<Doctor> doctors = doctor.RetreiveDoctorData();
        ArrayList<Patient> patients = patient.RetreivePatientData();
    
        
     // Check if email is valid
        
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
	            "[a-zA-Z0-9_+&*-]+)*@" +
	            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	            "A-Z]{2,7}$";
	    if (!login.getText().matches(emailRegex)) {
	        Alert alert = new Alert(AlertType.ERROR, "Please enter a valid email address.");
	        alert.showAndWait();
	        
	    }
	    
	    else {
          //BOUCLER DANS DOCTORS
         for (Doctor D : doctors) {
            if (login.getText().equals(D.getLogin()) && password.getText().equals(D.getPassword())) {
            	
                userFound = true;
                // Load the doctor home window and close the login window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
                Parent root = loader.load();

                DoctorHomeController controller1 = loader.getController();
                controller1.setIDdoc(D.getId_doctor());
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
                break;
            }
        }
        
        //BOUCLER DANS PATIENTS 
         for (Patient P : patients) {
            if (login.getText().equals(P.getLogin()) && password.getText().equals(P.getPassword())) {
            	
                userFound = true;
                // Load the patient home window and close the login window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
                Parent root = loader.load();
                
                //WELCOME MESSAGE
                PatientHomeController controller = loader.getController();
                controller.getWelcomeText().setText("Hello " + P.getPrenom() + "!");
                controller.setPatientID(P.getId_patient());   
                
                
               
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
                
                break;
            }
         }

         if (!userFound) {
            tentatives++;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Etat de connexion");
            alert.setContentText("Identifiant ou mot de passe incorrect");
            alert.showAndWait();

            if (tentatives == 3) {
                LoginButtton.getScene().getWindow().hide();
            }
        }
	    }
        
        
    }

}
