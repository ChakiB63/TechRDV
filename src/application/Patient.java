package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient extends User {
	private int id_patient ;
	

	public int getId_patient() throws SQLException {
		String sql = "SELECT id_patient FROM Patient WHERE nom=? AND prenom=?";
		   
	    PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
	    statement.setString(1, this.getNom());
	    statement.setString(2, this.getPrenom());
	    
	    
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
	        id_patient= resultSet.getInt("id_patient");
	    }
	    Admin.Connexion().close();
	    return id_patient;
	}

	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}

	public Patient(String nom, String prenom, String adresse, String tel, String login, String password, String email) {
		super(nom, prenom, adresse, tel, login, password, email);
		
	}
	
	public Patient() {
		
		
	}
	
	public Patient (String login, String password) {
		super(login, password);
		
	}
	
	public void AddAppointment(Appointment appointment) throws SQLException {
		 String sql ;
			Statement stm = Admin.Connexion().createStatement();
			sql = "INSERT INTO Appointment (date, startTime,endTime,id_doctor,id_patient) " +
				      "VALUES (" + 
				      "\"" + appointment.getDate() + "\", " +
				      "\"" + appointment.getStartTime() + "\", " +
				      "\"" + appointment.getEndTime() + "\", " +
				      "\"" + appointment.getId_doctor() + "\", " +
				      "\"" + appointment.getId_patient() + "\" " +				   
				      ");";

		    stm.executeUpdate(sql);
	}
	
	

}
