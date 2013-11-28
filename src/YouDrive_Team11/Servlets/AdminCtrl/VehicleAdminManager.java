package YouDrive_Team11.Servlets.AdminCtrl;

import java.sql.Date;
import java.util.Currency;
import java.util.LinkedList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.*;
import YouDrive_Team11.Persistence.*;

public class VehicleAdminManager extends HttpServlet {
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Declare temporary customer object
	Vehicle vehicle;
	
	/**
	 * Constructor
	 */
	public VehicleAdminManager(){
		super();
		dao=new YouDriveDAO();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void addVehicleType(String description, Currency hourlyRate, Currency dailyRate){
		
	}
	
	public void editVehicleType(int vehicleTypeId, String description, Currency hourlyRate, Currency dailyRate){
		
	}
	
	public void removeVehicleType(int vehicleTypeId){
		
	}
	
	public Vehicle addVehicle(String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		Vehicle vehicle=dao.createVehicle(make, model, year, tag, mileage, serviceDate, condition, type, location);
		return vehicle;
	}
	
	public void editVehicle(int vehicleId, String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		dao.updateVehicle(vehicleId, make, model, year, tag, mileage, serviceDate, condition, type, location);
	}
	
	public void removeVehicle(int vehicleId){
		dao.deleteVehicle(vehicleId);
	}
	
	public LinkedList<Vehicle> getAllVehicles(){
		return dao.getAllVehicles();
	}
}
