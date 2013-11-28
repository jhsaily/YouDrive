package YouDrive_Team11.Servlets.AdminCtrl;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import YouDrive_Team11.Persistence.*;
import YouDrive_Team11.Entity.*;

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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public RentalLocation addRentalLocation(String name, int capacity, String addrLine1, String addrLine2, String city, String state, int zip, String country){
		return dao.createRentalLocation(name, capacity, addrLine1, addrLine2, city, state, zip, country);
	}
	
	public void editRentalLocation(int locationId, String name, int capacity){
		dao.updateRentalLocation(locationId, name, capacity);
	}
	
	public void removeRentalLocation(int locationId){
		dao.deleteRentalLocation(locationId);
	}
	
	public LinkedList getAllLocations(){
		return dao.getAllRentalLocations();
	}
}
