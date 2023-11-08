package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseTypeController {

    @FXML
    private Button TypeDoctor;

    @FXML
    private Button TypePatient;

    @FXML
    void TypeDoctorChosen(ActionEvent event) throws IOException {
    	Parent signInDpage = (Parent)FXMLLoader.load(getClass().getResource("SignUpDoctor.fxml"));
    	Scene signInDScene = new Scene(signInDpage);
    	Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	appStage.setScene(signInDScene);
    	appStage.setTitle("Inscription");
    	appStage.show();

    }

    @FXML
    void TypePatientChosen(ActionEvent event) throws IOException {
    	
    	Parent signInpage = (Parent)FXMLLoader.load(getClass().getResource("SignUp.fxml"));
    	Scene signInScene = new Scene(signInpage);
    	Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	appStage.setScene(signInScene);
    	appStage.setTitle("Inscription");
    	appStage.show();

    }

}
