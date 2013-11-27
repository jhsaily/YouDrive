package YouDrive_Team11.Servlets.AdminCtrl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Persistence.*;
import YouDrive_Team11.Entity.*;

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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public Customer manageProfile(String un){
		return dao.readCustomer(123);
		
	}
	
	public void removeUser(String un){
		dao.deleteCustomer(123);
	}
}
