package application;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {
	private int id_availability ;
	private  LocalDate Date;
	private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
	public LocalDate getDate() {
		return Date;
	}
	public void setDate(LocalDate date) {
		Date = date;
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
	public Availability(LocalDate date, LocalTime startTime, LocalTime endTime) {
		super();
		Date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		setAvailable(true) ;
	}
	public Availability(LocalDate date, LocalTime startTime, LocalTime endTime,boolean isAvailable) {
		super();
		Date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		setAvailable(true) ;
	}
	public Availability(int id_availability ,LocalDate date, LocalTime startTime, LocalTime endTime,boolean isAvailable) {
		super();
		this.id_availability=id_availability;
		Date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		setAvailable(true) ;
	}
    public Availability() {setAvailable(true);}
    
	public Availability(int id_availability, LocalTime startTime, LocalTime endTime) {
		// TODO Auto-generated constructor stub
		this.id_availability=id_availability;
		
		this.startTime = startTime;
		this.endTime = endTime;
		setAvailable(true) ;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
   public void updateAvailabilitytofalse() throws SQLException {
	    
	    // Update the is_available column to false
	    String sql = "UPDATE Availability SET is_available = false WHERE id_av="+getId_availability() ;
	    PreparedStatement statement = Admin.Connexion().prepareStatement(sql);
	   
	    statement.executeUpdate();
	  
	}
	public int getId_availability() {
		return id_availability;
	}
	public void setId_availability(int id_availability) {
		this.id_availability = id_availability;
	}
	
}
