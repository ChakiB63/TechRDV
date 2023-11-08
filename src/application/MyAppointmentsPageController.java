
package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MyAppointmentsPageController {
	
	private int patientID;
	public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
	 public int getPatientID() {
		 return patientID;
	 }
    
	private int id_doc; 
	@FXML
	private TextField Email;
	
     @FXML
	 private TextField Fullname;
     
     @FXML
     private TextField Phonenumber;

	  

    @FXML
    private TableView<Appointment> AppointmentsTable;

    @FXML
    private Button BookAppointment;

    @FXML
    private TableColumn<Appointment, LocalDate> Date;

    @FXML
    private Button DeleteSelected;

    @FXML
    private Button HomeButton;

    @FXML
    private TableColumn<Appointment,LocalDate > EndTime;

    @FXML
    private Button LogOutButton;

    @FXML
    private TableColumn<Appointment, LocalDate> StartTime;

    @FXML
    private Button myAppointmentsButton;

    @FXML
    private Button searchButton;
    
    private ObservableList<Appointment> AppointmentsList;

    public void Generate() throws SQLException, IOException {
		 
    	ArrayList<Appointment> apList = new ArrayList<>();
		Date.setCellValueFactory(new PropertyValueFactory<>("date"));
	    StartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
	    EndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
	    
	    String sql = "SELECT * FROM Appointment WHERE id_patient="+patientID;
		System.out.println(patientID);
		
		PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
	    ResultSet rs = statement.executeQuery(sql);
	    
        while (rs.next()) {
        	LocalDate date = rs.getDate("date").toLocalDate();
        	LocalTime startTime = rs.getTime("startTime").toLocalTime();
        	LocalTime endTime = rs.getTime("startTime").toLocalTime();
        	int IDdoc =  rs.getInt("id_doctor");
        	setId_doc(IDdoc) ;
        	Appointment appointment = new Appointment( date, startTime, endTime,IDdoc);
            apList.add(appointment);
        }
        
   
        AppointmentsList = FXCollections.observableArrayList(apList);
	    
	    // Set the list of Doctor objects as the items of the TableView
	    AppointmentsTable.setItems(AppointmentsList);
	    
	    /////// HANDLE THE SELECTED APPOINTMENT////////////////////
        AppointmentsTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	  
	    	
			@Override
			public void handle(MouseEvent event) {
				// INITIALISER LES CASES 
		    	Phonenumber.clear();
			    Email.clear();
			    Fullname.clear();
			    System.out.println(patientID);
			    Appointment selectedAP = (Appointment) AppointmentsTable.getSelectionModel().getSelectedItem();
			    
			    if (selectedAP != null) {
			    String sql1 = "SELECT * FROM doctor WHERE id_doctor =" + selectedAP.getId_doctor();
				
				PreparedStatement statement;
				try {
					statement = Admin.Connexion().prepareStatement(sql1);
			        ResultSet rs = statement.executeQuery(sql1);
			    
		        while (rs.next()) {
		        
		        	String FirstName = rs.getString("prenom");
		        	String LastName = rs.getString("nom");
		        	String Phone = rs.getString("tel");
		        	String EmailAd = rs.getString("email");
		        	
		        	Doctor doc = new Doctor(FirstName,LastName,EmailAd,Phone);
		        	Fullname.setText(doc.getPrenom()+" "+doc.getNom());
		        	Phonenumber.setText(doc.getTel());
		        	Email.setText(doc.getEmail());
		        }
		            
		        
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		    	
				
			}}
	        

	    	
	    } );
	    
    }

    @FXML
    void BookAppointmentAction(ActionEvent event) throws IOException, SQLException {
    	
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

    @FXML
    void DeleteSelectedAction(ActionEvent event) {
    	System.out.println(patientID);
    	 Appointment selectedAppointment = AppointmentsTable.getSelectionModel().getSelectedItem();
    	    if (selectedAppointment != null) {
    	        try {
    	            // Delete the appointment from the database
    	            selectedAppointment.deleteAppointmentFromDatabase(selectedAppointment.getId_rdv());

    	            // Remove the appointment from the AppointmentsList
    	            AppointmentsList.remove(selectedAppointment);
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    }

    @FXML
    void HomeAction(ActionEvent event) throws IOException {
    	
    	Parent HomePage = (Parent)FXMLLoader.load(getClass().getResource("PatientHome.fxml"));
    	Scene HomePageScene = new Scene(HomePage);
    	Stage HomeStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	HomeStage.setScene(HomePageScene);
    	HomeStage.show();
    	

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
    void MyAppointmentsAction(ActionEvent event) {

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
	public int getId_doc() {
		return id_doc;
	}
	public void setId_doc(int id_doc) {
		this.id_doc = id_doc;
	}

}
