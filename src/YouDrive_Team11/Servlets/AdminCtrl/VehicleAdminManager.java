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

/**
 * Class that allows the administrator to manage all the different vehicles
 * @author Tanya
 *
 */
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
	
	/**
	 * Manages get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Manages post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Adds a vehicle type to a vehicle in the database
	 * @param description		Description of the vehicle type
	 * @param hourlyRate		The hourly rate of the rental
	 * @param dailyRate			The daily rate of the rental
	 */
	public void addVehicleType(String description, Currency hourlyRate, Currency dailyRate){
		
	}
	
	/**
	 * Updates vehicle type information
	 * @param vehicleTypeId		Unique identifier for vehicle type
	 * @param description		Description of vehicle type
	 * @param hourlyRate		The hourly rate of the rental
	 * @param dailyRate			The daily rate of the rental
	 */
	public void editVehicleType(int vehicleTypeId, String description, Currency hourlyRate, Currency dailyRate){
		
	}
	
	/**
	 * Removes a vehicle type from a vehicle in the database
	 * @param vehicleTypeId		Unique identifier for the vehicle type
	 */
	public void removeVehicleType(int vehicleTypeId){
		
	}
	
	/**
	 * Adds a vehicle to the database
	 * @param make				The vehicle make
	 * @param model				The vehicle model
	 * @param year				The year of the vehicle
	 * @param tag				The tag of the vehicle
	 * @param mileage			The number of mileage on the vehicle
	 * @param serviceDate		The sql date of service
	 * @param condition			The condition of the vehicle
	 * @param type				The vehicle type (integer)
	 * @param location			The location (integer)
	 * @return					Returns a vehicle object
	 */
	public Vehicle addVehicle(String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		Vehicle vehicle=dao.createVehicle(make, model, year, tag, mileage, serviceDate, condition, type, location);
		return vehicle;
	}
	
	/**
	 * Updates information about a vehicle
	 * @param make				The vehicle make
	 * @param model				The vehicle model
	 * @param year				The year of the vehicle
	 * @param tag				The tag of the vehicle
	 * @param mileage			The number of mileage on the vehicle
	 * @param serviceDate		The sql date of service
	 * @param condition			The condition of the vehicle
	 * @param type				The vehicle type (integer)
	 * @param location			The location (integer)
	 */
	public void editVehicle(int vehicleId, String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		dao.updateVehicle(vehicleId, make, model, year, tag, mileage, serviceDate, condition, type, location);
	}
	
	/**
	 * Removes a vehicle from the database
	 * @param vehicleId		Unique identifier for a vehicle
	 */
	public void removeVehicle(int vehicleId){
		dao.deleteVehicle(vehicleId);
	}
	
	/**
	 * Gets all of the vehicles in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<Vehicle> getAllVehicles(){
		return dao.getAllVehicles();
	}
}
