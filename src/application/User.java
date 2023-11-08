package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
	
    private String Nom ;
    private String Prenom ;
    private String Adresse ;
    private String Tel ;
	private String Login ;
	private String Password ;
	private String Email ;
	
	//GETTERS SETTERS 
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getLogin() {
		return Login;
	}
	public void setLogin(String login) {
		Login = login;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public User(String nom, String prenom, String adresse, String tel, String login, String password, String email) {
		
		Nom = nom;
		Prenom = prenom;
		Adresse = adresse;
		Tel = tel;
		Login = login;
		Password = password;
		Email = email;
	}
	
    public User(String nom, String prenom, String adresse, String tel) {
		
		Nom = nom;
		Prenom = prenom;
		Adresse = adresse;
		Tel = tel;
			}
	
	
	
	public User() {};	
	
	public User(String login, String password) {
		Login = login;
		Password = password;
	}
	
public ArrayList<Doctor> RetreiveDoctorData() throws SQLException {
		
		ArrayList<Doctor> ListeUsers = new ArrayList<Doctor>();
		String sql ;
		Statement stm;
    	stm = Admin.Connexion().createStatement();
    	sql = "SELECT * FROM Doctor ";
		ResultSet res = stm.executeQuery(sql);
        
      
        
		while (res.next()) {
			String nom = res.getString("nom");
	        String prenom = res.getString("prenom");
	        String adresse = res.getString("adresse");
	        String tel = res.getString("tel");
	        String login = res.getString("login");
	        String password = res.getString("pass");
	        String email = res.getString("email");
	        String specialite = res.getString("specialite");

	        Doctor doctor = new Doctor(nom, prenom, adresse, tel, login, password, email, specialite);
	        ListeUsers.add(doctor);
	        
	       
		}
		return ListeUsers ;
		
	}

public ArrayList<Patient> RetreivePatientData() throws SQLException {
	
	ArrayList<Patient> ListeUsers = new ArrayList<Patient>();
	String sql ;
	Statement stm;
	stm = Admin.Connexion().createStatement();
	sql = "SELECT * FROM Patient ";
	ResultSet res = stm.executeQuery(sql);
    
	while (res.next()) {
		String nom = res.getString("nom");
        String prenom = res.getString("Prenom");
        String adresse = res.getString("adresse");
        String tel = res.getString("tel");
        String login = res.getString("login");
        String password = res.getString("pass");
        String email = res.getString("email");
        Patient patient = new Patient(nom, prenom, adresse, tel, login, password, email);
        ListeUsers.add(patient);
        
       
	}
	return ListeUsers ;
	
}	    

}

