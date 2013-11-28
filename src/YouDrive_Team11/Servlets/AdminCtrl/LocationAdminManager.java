package YouDrive_Team11.Servlets.AdminCtrl;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import YouDrive_Team11.Persistence.*;
import YouDrive_Team11.Entity.*;

/**
 * Admin uses this class to manage the current rental locations
 * @author Tanya
 *
 */
public class LocationAdminManager extends HttpServlet{
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
	public LocationAdminManager(){
		super();
	}
	
	/**
	 * Handles get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Handles post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Adds a rental location to the database
	 * @param name			Name of the location
	 * @param capacity		Number of cars location holds
	 * @param addrLine1		Address of location
	 * @param addrLine2		Address of location
	 * @param city			City of location
	 * @param state			State of location
	 * @param zip			Zip code of location
	 * @param country		Country of location
	 * @return 				Returns a RentalLocation object
	 */
	public RentalLocation addRentalLocation(String name, int capacity, String addrLine1, String addrLine2, String city, String state, int zip, String country){
		return dao.createRentalLocation(name, capacity, addrLine1, addrLine2, city, state, zip, country);
	}
	
	/**
	 * Allows the admin to edit a specific location
	 * @param locationId		Unique identifier for location
	 * @param name				Name of location
	 * @param capacity			Number of cars it should hold
	 */
	public void editRentalLocation(int locationId, String name, int capacity){
		dao.updateRentalLocation(locationId, name, capacity);
	}
	
	/**
	 * Removes a location from the database
	 * @param locationId		Unique identifier for location
	 */
	public void removeRentalLocation(int locationId){
		dao.deleteRentalLocation(locationId);
	}
	
	/**
	 * Gets all the locations in the database
	 * @return		Returns a linked list of all the locations
	 */
	public LinkedList getAllLocations(){
		return dao.getAllRentalLocations();
	}
}
