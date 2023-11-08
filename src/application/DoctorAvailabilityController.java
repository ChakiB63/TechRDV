package application;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DoctorAvailabilityController {

    private int IDdoc;

    public int getIDdoc() {
        return IDdoc;
    }

    public void setIDdoc(int iDdoc) {
        IDdoc = iDdoc;
    }

    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button app_btn;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button add_btn;

    @FXML
    private Button search_btn;

    @FXML
    private DatePicker date_select;

    @FXML
    private TableView<Availability> table;

    @FXML
    private TableColumn<Availability, Integer> identifiant;

    @FXML
    private TableColumn<Availability, LocalTime> start_time;

    @FXML
    private TableColumn<Availability, LocalTime> end_time;

    private ObservableList<Availability> data;

    @FXML
    void LogOutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    void goto_app(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

        DoctorHomeController controller1 = loader.getController();
        controller1.setIDdoc(getIDdoc());
    }

    Date Jour;

    @FXML
    void find_avai(ActionEvent event) throws SQLException {
        Date d = Date.valueOf(date_select.getValue());
        Date jour = new Date(d.getTime());
        
        Jour = jour;
        
        //Jour = LocalDate.parse(DateFeild.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        

        String sql1 = "SELECT * FROM availability WHERE id_doctor=? AND date=?";
        System.out.println("IDdoc :"+IDdoc);
        PreparedStatement prep = Admin.Connexion().prepareStatement(sql1);
        prep.setInt(1, IDdoc);
        prep.setDate(2, jour);
        ResultSet res = prep.executeQuery();

        ArrayList<Availability> avs = new ArrayList<Availability>();
        while (res.next()) {
            avs.add(new Availability(res.getInt("id_av"), res.getTime("start_time").toLocalTime(),
                    res.getTime("end_time").toLocalTime()));
        }

        System.out.println(avs.size());
        data = FXCollections.observableArrayList(avs);

        table.getItems().clear();

        identifiant.setCellValueFactory(new PropertyValueFactory<>("id_availability"));
        start_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        end_time.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        table.setItems(data);
        
        table.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	  
	    	
			@Override
			public void handle(MouseEvent event) {
				Availability as = table.getSelectionModel().getSelectedItem();
		        id_av = as.getId_availability();
		        String sql4 = "Select * from availability where id_av =" + id_av;
		        PreparedStatement prep4;
				try {
					prep4 = Admin.Connexion().prepareStatement(sql4);
				
		        ResultSet res4 = prep4.executeQuery();

		        while (res4.next()) {
		            from.setText(res4.getTime("start_time").toString());
		            to.setText(res4.getTime("end_time").toString());
		            System.out.println(res4.getTime("start_time").toString()+"---"+res4.getTime("start_time").toString());
		        }
		        Admin.Connexion().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		       
				}
				
			}
			);

        Admin.Connexion().close();

    }

	    private int id_av;


	    
	    @FXML
	    void print_avai(ActionEvent event) throws SQLException {
	   
	    }
	    
	    
	    @FXML
	    void add_avai(ActionEvent event) throws SQLException {
	        java.util.Date d = java.util.Date.from(date_select.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	        Date jour = new Date(d.getTime());
	        
	        LocalTime startTime = LocalTime.parse(from.getText());
	        LocalTime endTime = LocalTime.parse(to.getText());

	        String sql3 = "Insert into availability(date ,start_time, end_time,id_doctor) values(?,?,?,?)";
	        PreparedStatement prep3 = Admin.Connexion().prepareStatement(sql3);
	        prep3.setDate(1, jour);
	        prep3.setTime(2, Time.valueOf(startTime));
	        prep3.setTime(3, Time.valueOf(endTime));
	        prep3.setInt(4, IDdoc);
	        prep3.executeUpdate();
	        Alert al = new Alert(AlertType.CONFIRMATION, "Updated successfully !!!", ButtonType.OK);
	        al.showAndWait();
	        from.setText("");
	        to.setText("");
	        this.find_avai(event);
	        
	        Admin.Connexion().close();
	    }
	    
	    @FXML
	    void update_avai(ActionEvent event) throws SQLException {

	        String sql3 = "Update availability set start_time=?, end_time=? where id_av=?";
	        PreparedStatement prep3 = Admin.Connexion().prepareStatement(sql3);
	        prep3.setTime(1, Time.valueOf(LocalTime.parse(from.getText())));
	        prep3.setTime(2, Time.valueOf(LocalTime.parse(to.getText())));
	        prep3.setInt(3, id_av);
	        prep3.executeUpdate();
	        Alert al = new Alert(AlertType.CONFIRMATION, "Updated successfully !!!", ButtonType.OK);
	        al.showAndWait();
	        from.setText("");
	        to.setText("");
	        this.find_avai(event);

	        Admin.Connexion().close();
	    }
	    @FXML
	    void delete_avai(ActionEvent event) throws SQLException {
	    	
	    	String sql3="Delete from availability where id_av= "+id_av;
	    	PreparedStatement prep3=Admin.Connexion().prepareStatement(sql3);
			prep3.executeUpdate();
			Alert al=new Alert(AlertType.CONFIRMATION, "Deleted successfully !!!", javafx.scene.control.ButtonType.OK );
			al.showAndWait();
			from.setText("");
	        to.setText("");
	        this.find_avai(event);
			
			Admin.Connexion().close();

	    }

}
