package application;


import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

import java.util.ArrayList;
//
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DoctorHomeController  {
		
	private int IDdoc ;
	public int getIDdoc() {
		return IDdoc;
	}

	public void setIDdoc(int iDdoc) {
		IDdoc = iDdoc;
	}
		    
		    
		    int id_patient;// to know the patient treated

		    
		    @FXML
		    private TextField mobile;
		    
		    @FXML
		    private TextField email;

		    @FXML
		    private TextField first_name;
		    
		    @FXML
		    private TextField last_name;
		    
		    @FXML
		    private TextArea medical_bg;

		    

		    
		    
		    
		    @FXML
		    private DatePicker date_select;
		    
		    @FXML
		    private TableView<Appointment> table;
		    
		    @FXML
		    private TableColumn<Appointment, Integer> identifiant;
		     
		    @FXML
		    private TableColumn<Appointment, String> start_time;
		    
		    @FXML
		    private TableColumn<Appointment, String> end_time;

		    

		
		    ObservableList<Appointment> data = FXCollections.observableArrayList();
		    ObservableList<Appointment> data2 ;
		    

		    
		    @FXML
		    private Button search_btn;

		    @FXML
		    private Button modify_btn;
		    
		    @FXML
		    private Button avai_btn;
		    
		    @FXML
		    private Button LogOutButton;
		    /*
		     * 
		     * DoctorAvailabilityController controller1 = loader.getController();
                controller1.setIDdoc(D.getId_doctor());
		
		*/
		    
		   

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
		    void goto_avai(ActionEvent event) throws IOException {
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorAvailability.fxml"));
		   	    Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.setScene(scene);
		        stage.show();
		        ((Node)(event.getSource())).getScene().getWindow().hide();
		        
		        DoctorAvailabilityController controller1 = loader.getController();
	            controller1.setIDdoc(getIDdoc());
		    }
		    
		    
		    @FXML
		    void find_app(ActionEvent event) throws SQLException {
		    	
		    
			    java.util.Date d=java.util.Date.from(date_select.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			    Date jour=new Date(d.getTime());
			    System.out.println(jour.toLocalDate());
			    System.out.println(jour);
			    
		    	String sql1="Select * from appointment where id_doctor=? and date=?";
		    	PreparedStatement prep=Admin.Connexion().prepareStatement(sql1);
		    	System.out.println(IDdoc);
				prep.setInt(1,IDdoc);
				prep.setDate(2,jour);
				ResultSet res=prep.executeQuery();
				
				ArrayList<Appointment> rdv=new ArrayList<Appointment>();
				while(res.next()) {
					rdv.add(new Appointment(res.getInt("id_rdv"),res.getTime("startTime").toLocalTime(), res.getTime("endTime").toLocalTime()));
				}
				
				System.out.println(rdv.size());
				data2= FXCollections.observableArrayList(rdv);
				
				table.getItems().clear();
				
				identifiant.setCellValueFactory(new PropertyValueFactory<>("id_rdv"));
				start_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
				end_time.setCellValueFactory(new PropertyValueFactory<>("endTime"));
				
				table.setItems(data2);

				Admin.Connexion().close();
		    }
	
		    @FXML
		    void find_pat(MouseEvent event) throws SQLException {
		    	

		    	Appointment a= (Appointment) table.getSelectionModel().getSelectedItem();
		    	
		    	String sql4 ="Select * from appointment where id_rdv ="+a.getId_rdv();
		    	PreparedStatement prep4=Admin.Connexion().prepareStatement(sql4);
		    	
		    	//id_patient=a.getId_patient();
		    	
				ResultSet res4=prep4.executeQuery();
				
				while(res4.next()) {
					id_patient=res4.getInt("id_patient");
				}
				
		    	String sql2 ="Select * from patient where id_patient=?";
		    	PreparedStatement prep2=Admin.Connexion().prepareStatement(sql2);
		    	
				prep2.setInt(1,id_patient);
				ResultSet res2=prep2.executeQuery();
				
				while(res2.next()) {
					mobile.setText(res2.getString("tel"));
					email.setText(res2.getString("email"));
					first_name.setText(res2.getString("nom"));
					last_name.setText(res2.getString("prenom"));
					medical_bg.setText(res2.getString("medical_bg"));
				}

				Admin.Connexion().close();
				
		    }

		    @FXML
		    void modify_pat(ActionEvent event) throws SQLException {

		    	String sql3="Update patient set medical_bg=? where id_patient=?";
		    	PreparedStatement prep3=Admin.Connexion().prepareStatement(sql3);
				prep3.setString(1,medical_bg.getText());
				prep3.setInt(2,id_patient);
				prep3.executeUpdate();
				Alert al=new Alert(AlertType.CONFIRMATION, "Updated successfully !!!", javafx.scene.control.ButtonType.OK );
				al.showAndWait();
				
				Admin.Connexion().close();
				
		    }

		

	}

	

