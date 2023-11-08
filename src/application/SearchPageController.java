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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchPageController {
	
	    private int patientID;
		public void setPatientID(int patientID) {
	        this.patientID = patientID;
	    }
		 public int getPatientID() {
			 return patientID;
		 }

    @FXML
    private TableView<Availability> AvailabilityTable;

    @FXML
    private TextField DateFeild;

    @FXML
    private TextField DoctorsName;

    @FXML
    private TextField EndTime;

    @FXML
    private TableColumn<String, Doctor> FirstName;

    @FXML
    private Button HomeButton;

    @FXML
    private TableColumn<String, Doctor> LastName;

    @FXML
    private TableColumn<String, Doctor> Location;

    @FXML
    private Button LogOutButton;

    @FXML
    private TableColumn<String, Doctor> PhoneNumber;

    @FXML
    private TableView<Doctor> SearchDoctorsTable;

    @FXML
    private TextField SearchFeild;

    @FXML
    private Button SearchTableButton;

    @FXML
    private TableColumn<String, Doctor> Specialty;

    @FXML
    private TextField StartTime;

    @FXML
    private Button myAppointmentsButton;

    @FXML
    private Button searchButton;
    

    @FXML
    private TableColumn<Availability, LocalDate> av_Date;

    @FXML
    private TableColumn<Availability, LocalTime> av_Etime;

    @FXML
    private TableColumn<Availability, LocalTime> av_Stime;
    
    @FXML
    private Button SubmitAppointment;
  


    private ObservableList<Doctor> doctorsList;
    private ObservableList<Doctor> originalDoctorsList = FXCollections.observableArrayList();
    ObservableList<Availability> availabilities;


    
    public void initialize() throws SQLException {
        
	 // Retrieve the data from the database and create a list of Doctor objects
	    ArrayList<Doctor> doctorList = new ArrayList<>();
	    LastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    FirstName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	    Location.setCellValueFactory(new PropertyValueFactory<>("adresse"));
	    PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
	    Specialty.setCellValueFactory(new PropertyValueFactory<>("specialite"));
	    
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
        doctorsList = FXCollections.observableArrayList(doctorList);
	    
	    // Set the list of Doctor objects as the items of the TableView
	    SearchDoctorsTable.setItems(doctorsList);
	    originalDoctorsList.setAll(SearchDoctorsTable.getItems());
	    
	    /////// HANDLE THE SELECTED DOCTOR ////////////////////
	    SearchDoctorsTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	  
	    	
			@Override
			public void handle(MouseEvent event) {
				
				// Clear the date and time fields : THIS IS ADDED SO THAT EACH TIME WE SELECT A NEW DOCTOR WE CLEAR THE FEILDS
			    DateFeild.clear();
			    StartTime.clear();
			    EndTime.clear();
			    
			 // Clear the previous availability entries
			    AvailabilityTable.getItems().clear();
			    
			 // Clear the doctor name field in the appointment details form
		        DoctorsName.clear();
		        
			    
				// INITIALISER LES CASES 
				
				Doctor selectedDoctor = (Doctor) SearchDoctorsTable.getSelectionModel().getSelectedItem();
		    	if (selectedDoctor != null) {
		    	//DISPLAY DOCTOR S NAME IN DOCTOR FEILD IN APPOINTMENT DETAILS FORM
		    	DoctorsName.setText(selectedDoctor.getPrenom() + " " + selectedDoctor.getNom());
		    	
		    	ArrayList<Availability> avList = new ArrayList<>();
				av_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
			    av_Stime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
			    av_Etime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
			    
		    	// Get the doctor's availability from the availability table
		    	
			  
		    	try {
		    		String sql1 = "SELECT * FROM Availability WHERE id_doctor=" + selectedDoctor.getId_doctor()+" AND is_available=true ;";
		    		System.out.println("ID DOCTOR: "+selectedDoctor.getId_doctor());
		    		PreparedStatement statement = Admin.Connexion().prepareStatement(sql1);
		    	    ResultSet rs = statement.executeQuery(sql1);
		    	    
		            while (rs.next()) {
		            	LocalDate date = rs.getDate("date").toLocalDate();
		            	LocalTime startTime = rs.getTime("start_time").toLocalTime();
		            	LocalTime endTime = rs.getTime("end_time").toLocalTime();
		            	boolean isavailable = rs.getBoolean("is_available");
		            	int id_av = rs.getInt("id_av");
	           
		            	
		            	
		            	if(isavailable)
		            	{
		            	Availability availability = new Availability(id_av, date, startTime, endTime,isavailable);
		                 avList.add(availability);
		            	}
		            		                
		            }
		            
		            availabilities = FXCollections.observableArrayList(avList);
		            // Display the doctor's availability in the availability table
			    	AvailabilityTable.setItems(availabilities);
			    	
			    	
		        } 
		    	catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }

			    	
		    	}
		    	else {
		    		
		    		DoctorsName.clear();
		            AvailabilityTable.getItems().clear();
		            
		            System.out.println("DOCTOR NUUULL");
		    		
		    	}
				
			}
        
	    	
	    } );
	    
	    /////// HANDLE THE SELECTED AVAILABILITYYY////////////////////
           AvailabilityTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	  
	    	
			@Override
			public void handle(MouseEvent event) {
				// INITIALISER LES CASES 
				DateFeild.clear();
			    StartTime.clear();
			    EndTime.clear();
				
				Availability selectedAV = (Availability) AvailabilityTable.getSelectionModel().getSelectedItem();
		    	if (selectedAV != null) {
		    	
		    	   DateFeild.setText(selectedAV.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		    	   StartTime.setText(selectedAV.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
		    	   EndTime.setText(selectedAV.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
		   
			    	
		    	}
		    	else {
		    		
		            System.out.println("selected availability is null");   
		    		
		    	}
		    	
		    	
				
			}
	        

	    	
	    } );
	}
 

    @FXML
    void HomeAction(ActionEvent event) throws IOException, SQLException {
		    Patient patient = new Patient();
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
		    Parent HomePage = loader.load();;
	    	Scene HomePageScene = new Scene(HomePage);
	    	Stage HomeStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	PatientHomeController controller = loader.getController();                  
	    	 patient.setId_patient(getPatientID());
			 controller.setPatientID(patient.getId_patient());
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
    void SearchAction(ActionEvent event) {
    	

    }

    @FXML
    void SearchTableAction(ActionEvent event) {
    
    	

   	 String searchQuery = SearchFeild.getText().toLowerCase();
     doctorsList = SearchDoctorsTable.getItems();
     doctorsList.setAll(originalDoctorsList);
     ObservableList<Doctor> filteredList = FXCollections.observableArrayList();
 
   	     for (Doctor doctor : doctorsList) {
   	       String nom = doctor.getNom();
   	       String prenom = doctor.getPrenom();
   	       String adresse = doctor.getAdresse();
   	       String specialite = doctor.getSpecialite();
	      
   	        if (nom != null && nom.toLowerCase().contains(searchQuery) ||
   	             prenom != null && prenom.toLowerCase().contains(searchQuery) ||
   	          adresse != null && adresse.toLowerCase().contains(searchQuery) ||
   	          specialite != null && specialite.toLowerCase().contains(searchQuery)) {
   	            
   	        	
   	            filteredList.add(doctor);
   	        }
   	    }
			SearchDoctorsTable.setItems(filteredList);
		    // Get the selected doctor from the table
		   // selectedDoctor = SearchDoctorsTable.getSelectionModel().getSelectedItem();
		   
		 // Set the doctor's name
		   // DoctorsName.setText(selectedDoctor.getPrenom() + " " + selectedDoctor.getNom());


 }
    
    
    
    @FXML
    void SubmitAppointmentAction(ActionEvent event) throws SQLException {
    	 
    	 Availability availability = AvailabilityTable.getSelectionModel().getSelectedItem();
    	 
    	 if (availability == null) {
    	        // no availability selected, show an error message
    	        Alert alert = new Alert(Alert.AlertType.ERROR);
    	        alert.setTitle("Error");
    	        alert.setHeaderText("No Availability Selected");
    	        alert.setContentText("Please select an availability from the table.");
    	        alert.showAndWait();
    	        return;
    	    }
    	 
    	 
        Patient patient = new Patient();
    	Doctor selectedDoctor = (Doctor) SearchDoctorsTable.getSelectionModel().getSelectedItem();
    	
    	 LocalDate date = LocalDate.parse(DateFeild.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
         LocalTime startTime = LocalTime.parse(StartTime.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
         LocalTime endTime = LocalTime.parse(EndTime.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
         
        Appointment appointment = new Appointment( date,startTime,endTime,selectedDoctor.getId_doctor(),patientID);
        
         if (DateFeild.getText().isEmpty() || StartTime.getText().isEmpty() || 
                 EndTime.getText().isEmpty() || DoctorsName.getText().isEmpty()) {
             // Display an error message
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Feilds Error");
             alert.setContentText("All Feilds Are Required !");
             alert.showAndWait();
             return;
         }
         else{
        	 //System.out.println(appointment.getId_patient());
             patient.AddAppointment(appointment);
             availability.setAvailable(false);
             availability.updateAvailabilitytofalse();
             
             // Doctor successfully added, go back to login page
             Alert alert = new Alert(AlertType.INFORMATION);
             
             alert.setContentText("	Appointment added !");
             alert.showAndWait();
             
            
             
          // Clear the appointment details fields
             DateFeild.clear();
             StartTime.clear();
             EndTime.clear();
             DoctorsName.clear();
  
          // remove the selected availability from the availabilities list
             availabilities.remove(availability);
             
             // update the availability table
             AvailabilityTable.setItems(availabilities);  
          }
         

    }
    
    
    
    
    
    
    
    
   /* @FXML
    void handleRowClick(MouseEvent event) {
     
    	//TableView<Doctor> SearchDoctorsTable =  (TableView<?>) event.getSource();
    	Doctor selectedDoctor = (Doctor) SearchDoctorsTable.getSelectionModel().getSelectedItem();
    	if (selectedDoctor != null) {
    	//DISPLAY DOCTOR S NAME IN DOCTOR FEILD IN APPOINTMENT DETAILS FORM
    	DoctorsName.setText(selectedDoctor.getPrenom() + " " + selectedDoctor.getNom());
    	
    	// Get the doctor's availability from the availability table
    	ObservableList<Availability> availabilities = FXCollections.observableArrayList();
    	try {
            Connection con  = Admin.Connexion();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Availability WHERE id_doctor=" + selectedDoctor.getId_doctor());
            while (rs.next()) {
            	LocalDate date = rs.getDate("date").toLocalDate();
            	LocalTime startTime = rs.getTime("start_time").toLocalTime();
            	LocalTime endTime = rs.getTime("end_time").toLocalTime();
            	Availability availability = new Availability(date, startTime, endTime);
                
                
                availabilities.add(availability);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Display the doctor's availability in the availability table
    	AvailabilityTable.setItems(availabilities);
    	
    	}
    	else {
    		DoctorsName.clear();
            AvailabilityTable.getItems().clear();
            System.out.println("DOCTOR NUUULL");
    		
    	}
	}
*/
    
   

}
