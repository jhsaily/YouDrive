package YouDrive_Team11.Servlets.AdminCtrl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Persistence.*;
import YouDrive_Team11.Entity.*;

/**
 * Class that manages administrator accessibility
 * @author Tanya
 *
 */
public class UserAdminManager extends HttpServlet{
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Declare temporary customer object
	Customer customer;
	
	/**
	 * Constructor
	 */
	public UserAdminManager(){
		super();
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
	 * Allows the admin to edit a customer's profile
	 * @param un		Customer's username
	 * @return			Returns a Customer object
	 */
	public Customer manageProfile(String un){
		return dao.readCustomer(123);
		
	}
	
	/**
	 * Removes a user from the database
	 * @param un		The customer's username
	 */
	public void removeUser(String un){
		dao.deleteCustomer(123);
	}
}
