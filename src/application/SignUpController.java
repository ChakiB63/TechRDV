
package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private Button Submit;

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private PasswordField password;

    @FXML
    private TextField prenom;

    @FXML
    private TextField tel;

    @FXML
    private ComboBox<String> adresse;
    

    public void initialize() {
    	ObservableList<String> ComboBoxList = FXCollections.observableArrayList();
        
        ArrayList<String> moroccanRegions = new ArrayList<>();
        moroccanRegions.add("Tanger-Tétouan-Al Hoceima");
        moroccanRegions.add("L'Oriental");
        moroccanRegions.add("Fès-Meknès");
        moroccanRegions.add("Rabat-Salé-Kénitra");
        moroccanRegions.add("Béni Mellal-Khénifra");
        moroccanRegions.add("Casablanca-Settat");
        moroccanRegions.add("Marrakech-Safi");
        moroccanRegions.add("Drâa-Tafilalet");
        moroccanRegions.add("Souss-Massa");
        moroccanRegions.add("Guelmim-Oued Noun");
        moroccanRegions.add("Laâyoune-Sakia El Hamra");
        moroccanRegions.add("Dakhla-Oued Ed-Dahab");
        
        ComboBoxList.addAll(moroccanRegions);
        adresse.setItems(ComboBoxList);
    }

    @FXML
    void SubmitSignUp(ActionEvent event) throws IOException {
    	 String selectedAdress = adresse.getValue();
        Admin admin = new Admin();
         Patient patient = new Patient(nom.getText(),prenom.getText(), selectedAdress, tel.getText(),email.getText(), password.getText(),email.getText());
         if (nom.getText().isEmpty() || prenom.getText().isEmpty() || selectedAdress == null
                 || tel.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty()) {
             // Display an error message
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Feilds Error");
             alert.setContentText("All Feilds Are Required !");
             alert.showAndWait();
             return;
         }
         else{
          try {
        	// Check if email is valid
 		    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
 		            "[a-zA-Z0-9_+&*-]+)*@" +
 		            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
 		            "A-Z]{2,7}$";
 		    if (!email.getText().matches(emailRegex)) {
 		        Alert alert = new Alert(AlertType.ERROR, "Please enter a valid email address.");
 		        alert.showAndWait();
 		        return;
 		    }
 		// Check if phone number is valid Moroccan phone number
 		    String phoneRegex = "^(\\+212|0)([ \\-_/]*)(\\d[ \\-_/]*){9}$";
 		    if (!tel.getText().matches(phoneRegex)) {
 		        Alert alert = new Alert(Alert.AlertType.ERROR);
 		        alert.setTitle("Erreur");
 		        alert.setHeaderText("Veuillez saisir un numéro de téléphone valide");
 		        alert.showAndWait();
 		        return;
 		    }

 		    // Check if email already exists
 		    try {
 		        if (admin.isPatientEmailExist(email.getText())) {
 		            Alert alert = new Alert(AlertType.ERROR, "This email is already registered.");
 		            alert.showAndWait();
 		            return;
 		        }
 		    } catch (SQLException e) {
 		        e.printStackTrace();
 		        Alert alert = new Alert(AlertType.ERROR, "Error occurred while checking email.");
 		        alert.showAndWait();
 		        return;
 		    }
             admin.AddPatient(patient);
             // Doctor successfully added, go back to login page
             Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
             Scene scene = new Scene(root);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(scene);
             stage.show();
         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("Erreur Inscription!");
         }
         }

    }
    @FXML
    void BackToLogin(MouseEvent event) throws IOException {
    	
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    
    }
    
    

}
