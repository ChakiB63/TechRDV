package application;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;


public class Doctor extends User {
	 
	private int id_doctor ;
	private String Specialite ;
	
	private ArrayList<Availability> availability;


	public int getId_doctor() throws SQLException {
	    String sql = "SELECT id_doctor FROM Doctor WHERE nom=? AND prenom=?";
	   
	    PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
	    statement.setString(1, this.getNom());
	    statement.setString(2, this.getPrenom());
	    
	    
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
	        id_doctor = resultSet.getInt("id_doctor");
	    }
	    Admin.Connexion().close();
	    return id_doctor;
	}


	public void setId_doctor(int id_doctor) {
		this.id_doctor = id_doctor;
	}

	public String getSpecialite() {
		return Specialite;
	}

	public void setSpecialite(String specialite) {
		Specialite = specialite;
	}
	
	public Doctor(int id_doctor, String nom, String prenom, String adresse, String tel, String login, String password, String email,
            String specialite) {
    super(nom, prenom, adresse, tel, login, password, email);
    Specialite = specialite;
    this.id_doctor = id_doctor;
    setAvailability(new ArrayList<Availability>());
}


	public Doctor(String nom, String prenom, String adresse, String tel, String login, String password, String email,
			String specialite) {
		super(nom, prenom, adresse, tel, login, password, email);
		Specialite = specialite;
		
		setAvailability(new ArrayList<Availability>()); 
		
	}
	public Doctor(String nom, String prenom,String tel, String email) {
		super(nom, prenom, tel,  email);
		
		setAvailability(new ArrayList<Availability>()); 
		
	}
	
	public Doctor(String nom, String prenom ,String specialite, String tel, String adresse) {
		super(nom, prenom, adresse, tel);
		Specialite = specialite;
		setAvailability(new ArrayList<Availability>()); 
		
		
	}
	public Doctor(String login, String password) {
		super(login, password);
		
		
	}
	
	public Doctor() {
		
		setAvailability(new ArrayList<Availability>()); 
		
	}


	public ArrayList<Availability> getAvailability() {
		return availability;
	}


	public void setAvailability(ArrayList<Availability> availability) {
		this.availability = availability;
	}
	

}
