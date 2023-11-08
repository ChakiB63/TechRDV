package application;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
	
	private int id_admin;
	 
	public Admin() {};
	
	 public Admin(int id_admin) {
		super();
		this.id_admin = id_admin;
	}

	public int getId_admin() {
		return id_admin;
	}

	public void setId_admin(int id_admin) {
		this.id_admin = id_admin;
	}

	public static Connection Connexion() throws SQLException {
			
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test","root","root");
		 return con ;
		
	}
	 
	 public void AddPatient(Patient patient) throws SQLException {
			
			
			String sql ;
			Statement stm = Connexion().createStatement();
			sql = "INSERT INTO Patient (nom, prenom, adresse, tel, login, pass, email) " +
				      "VALUES (" + 
				      "\"" + patient.getNom() + "\", " +
				      "\"" + patient.getPrenom() + "\", " +
				      "\"" + patient.getAdresse() + "\", " +
				      "\"" + patient.getTel() + "\", " +
				      "\"" + patient.getLogin() + "\", " +
				      "\"" + patient.getPassword() + "\", " +
				      "\"" + patient.getEmail() + "\"" +
				      ");";

		    stm.executeUpdate(sql);
		 
		}
	 
	 public void AddDoctor(Doctor doctor) throws SQLException
	 {
		
		 String sql ;
			Statement stm = Connexion().createStatement();
			sql = "INSERT INTO Doctor (nom, prenom, adresse, tel, login, pass, email,specialite) " +
				      "VALUES (" + 
				      "\"" + doctor.getNom() + "\", " +
				      "\"" + doctor.getPrenom() + "\", " +
				      "\"" + doctor.getAdresse() + "\", " +
				      "\"" + doctor.getTel() + "\", " +
				      "\"" + doctor.getLogin() + "\", " +
				      "\"" + doctor.getPassword() + "\", " +
				      "\"" + doctor.getEmail() + "\"," +
				      "\"" + doctor.getSpecialite() + "\"" +
				      ");";

		    stm.executeUpdate(sql);
		 
	 }
	 public boolean isDoctorEmailExist(String email) throws SQLException {
		    String sql = "SELECT email FROM Doctor WHERE email = ?";
		    PreparedStatement statement = Connexion().prepareStatement(sql);
		    statement.setString(1, email);
		    ResultSet result = statement.executeQuery();
		    return result.next();
		}
	 public boolean isPatientEmailExist(String email) throws SQLException {
		    String sql = "SELECT email FROM Patient WHERE email = ?";
		    PreparedStatement statement = Connexion().prepareStatement(sql);
		    statement.setString(1, email);
		    ResultSet result = statement.executeQuery();
		    return result.next();
		}


}
