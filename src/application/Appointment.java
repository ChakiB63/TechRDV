package application;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalTime;



public class Appointment {
    private int id_rdv;
    private int id_patient ;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int id_doctor;
    private Patient patient;

    public Appointment(int id_rdv, LocalDate date, LocalTime startTime, LocalTime endTime, int id_doctor,int id_patient) {
        this.id_rdv = id_rdv;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setId_doctor(id_doctor);
        this.setId_patient(id_patient);
    }
    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, int id_doctor,int id_patient) {
   
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setId_doctor(id_doctor);
        this.setId_patient(id_patient);
    }
    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, int id_doctor) {
        
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setId_doctor(id_doctor);
    
    }
public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime) {
        
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        
    
    }
    public Appointment() { };

    public Appointment(int id_rdv, LocalTime startTime, LocalTime  endTime) {
		// TODO Auto-generated constructor stub
    	this.id_rdv=id_rdv;
    	this.startTime = startTime;
        this.endTime = endTime;
	}
	public int getId_rdv() {
        return id_rdv;
    }

    public void setId_rdv(int id_rdv) {
        this.id_rdv = id_rdv;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
	public int getId_doctor() {
		return id_doctor;
	}
	public void setId_doctor(int id_doctor) {
		this.id_doctor = id_doctor;
	}
	public int getId_patient() {
		return id_patient;
	}
	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}
	
	void deleteAppointmentFromDatabase(int id_rdv) throws SQLException {
		
	    String sql = "DELETE FROM Appointment WHERE id_rdv = ?";
	    PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
	    statement.setInt(1, id_rdv);
	    statement.executeUpdate();
	}


  /* public boolean isAvailable() {
        List<Availability> availableTimes = doctor.getAvailability(date);
        if (availableTimes == null) {
            return false; // doctor is not available on this date
        }
        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(30)) {
            if (!availableTimes.contains(time)) {
                return false; // time slot is not available
            }
        }
        return true; // doctor is available at the specified time
    }*/
}

