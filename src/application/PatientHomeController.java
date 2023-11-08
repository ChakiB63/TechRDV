package application;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientHomeController {
	
	 private int patientID;
	 public int getPatientID() {
		 return patientID;
	 }
	public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
	
	 @FXML
     private Text welcomeText;
	 @FXML
	 private Button myAppointmentsButton;

	 @FXML
	 private Button searchButton;
	 
	 
	 @FXML
	 private TableView<Doctor> FeaturedDoctorsTable;
	 
	    @FXML
	    private TableColumn<Doctor, String> FirstName;

	    @FXML
	    private TableColumn<Doctor, String> LastName;

	    @FXML
	    private TableColumn<Doctor, String> Location;

	    @FXML
	    private TableColumn<Doctor, String> PhoneNumber;

	    @FXML
	    private TableColumn<Doctor, String> Specialty;
	 
	    
	 
	 
	 public void initialize() throws SQLException {
		    // Bind the table columns to the Doctor class fields
		    LastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    FirstName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		    Location.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		    PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    Specialty.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            
		 // Retrieve the data from the database and create a list of Doctor objects
		    ArrayList<Doctor> doctorList = new ArrayList<>();
		    
		    String sql = "SELECT * FROM Doctor ";
		   
		    PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
		    ResultSet resultSet = statement.executeQuery(sql);
		    
		    while (resultSet.next()) {
		    	String lastName = resultSet.getString("nom");
		        String firstName = resultSet.getString("prenom");
		        String location = resultSet.getString("adresse");
		        String phonenumber = resultSet.getString("tel");
		        String specialty = resultSet.getString("specialite");
		        Doctor doctor = new Doctor(lastName, firstName, specialty, phonenumber, location);
		        doctorList.add(doctor);
		    }

		    // Set the list of Doctor objects as the items of the TableView
		    FeaturedDoctorsTable.setItems(FXCollections.observableArrayList(doctorList));
		    
		}
	 
	 
	    
	 @FXML
	 void MyAppointmentsAction(ActionEvent event) throws IOException, SQLException {
		 
		 
		 Patient patient = new Patient();
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAppointmentsPage.fxml"));
    	 Parent APage = loader.load();
    	 
    	 MyAppointmentsPageController controller = loader.getController();                  
    	 patient.setId_patient(getPatientID());
		 controller.setPatientID(patient.getId_patient());
		 
		 System.out.println(patientID);
		 System.out.println(patient.getId_patient());
		 System.out.println(controller.getPatientID());
		 
	    	Scene PageScene = new Scene(APage);
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();    	
	    	stage.setScene(PageScene);
	    	stage.show();

	    }

	 @FXML
	 void SearchAction(ActionEvent event) throws IOException, SQLException {
		 
		 Patient patient = new Patient();
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPage.fxml"));        
		 Parent SearchPage = loader.load();
		 
		 SearchPageController controller = loader.getController();                  
		 patient.setId_patient(getPatientID());
		 controller.setPatientID(patient.getId_patient());
		 
		 System.out.println(patientID);
		 System.out.println(patient.getId_patient());
		 System.out.println(controller.getPatientID());
		 
	    	Scene SearchPageScene = new Scene(SearchPage);
	    	Stage SearchStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	
	    	SearchStage.setScene(SearchPageScene);
	    	SearchStage.show();

	    }
	    
	 public Text getWelcomeText() {
	        return welcomeText;
	    }
	 
     @FXML
     void LogOutAction(ActionEvent event) throws IOException {
    	 
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    	 Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.show();
         ((Node)(event.getSource())).getScene().getWindow().hide();
         


	    }
     @FXML
     void HomeAction(ActionEvent event) throws IOException {
		 

    }
          


}
