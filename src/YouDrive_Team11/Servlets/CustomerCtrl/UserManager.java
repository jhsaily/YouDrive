package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.*;
import YouDrive_Team11.Persistence.*;

/**
 * Class to allow management of all user info 
 * @author Tanya
 *
 */
public class UserManager extends HttpServlet {
	
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
	public UserManager(){
		super();
		
		//Create DAO
		dao=new YouDriveDAO();
	}
	
	/**
	 * Manages all get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
	}
	
	/**
	 * Manages all post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		//Get Servlet Context and dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		//If the user clicks register, create a new user.
		if(req.getParameter("register")!=null){
			
			createUser(req.getParameter("username"), req.getParameter("password"), req.getParameter("email"), req.getParameter("firstname"),
					req.getParameter("lastname"), new Date(12, 12, 12), req.getParameter("addressline1"), req.getParameter("addressline2"), 
					"Chicago", req.getParameter("state"), Integer.valueOf(req.getParameter("zip")), req.getParameter("country"), req.getParameter("licensenum"), req.getParameter("licensestate"));
			
			
			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		
		//If the user clicks Forgot Password, send an email with a new one to them
		if(req.getParameter("forgotPassword")!=null){
			resetPassword(req.getParameter("email"));
			
			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		//If the user clicks update profile
		if(req.getParameter("updateprofile")!=null){
			/*UNCOMMENT ME
			updatePassword(customer.getUsername(), req.getParameter("currentpassword"), req.getParameter("newpassword"));
			updateName(customer.getUsername(), req.getParameter("firstname"), req.getParameter("lastname"));
			updateEmailAddress(customer.getUsername(), req.getParameter("email"));
			updateResidenceAddress(customer.getUsername(), req.getParameter("addressline1"), req.getParameter("addressline2"), Integer.valueOf(req.getParameter("zip")), req.getParameter("city"), req.getParameter("state"), req.getParameter("country"));
			updateDriversLicense(customer.getUsername(), req.getParameter("licensenum"), req.getParameter("licensestate"));
			*/
			System.out.println("User Information Updated!");
			
			//Forward to dashboard
			dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(req, res);
		}
	}
	
	/**
	 * Passes information to the DB to create a new customer
	 * 
	 * @param username				The customer's username
	 * @param password				The customer's password
	 * @param emailAddress			The customer's email
	 * @param firstName				The customer's first name
	 * @param lastName				The customer last name
	 * @param membershipExpiration	The date that the customer's membership will expire
	 * @param addrLine1				The customer's address
	 * @param addrLine2				The customer's address
	 * @param city					The city where the customer lives
	 * @param state					The state where the customer lives
	 * @param ZIP					The customer's zip code
	 * @param country				The country where the customer lives
	 * @param DLNumber				The customer's driver's license number
	 * @param DLState				The customer's driver's license state
	 * @return						Returns a customer object
	 */
	public Customer createUser(String username, String password, String emailAddress, String firstName, String lastName, Date membershipExpiration, String addrLine1, String addrLine2, String city, String state, int ZIP, String country, String DLNumber, String DLState){
		Customer customer=dao.createCustomer(username, password, emailAddress, firstName, lastName, membershipExpiration, addrLine1, addrLine2, city, state, ZIP, country, DLNumber, DLState);
		this.customer=customer;
		return customer;
	}
	
	/**
	 * Updates email address
	 * @param un		Username
	 * @param email		User email address
	 */
	public void updateEmailAddress(String un, String email){
		dao.changeEmailAddress(customer.getId(), email);
	}
	
	/**
	 * Updates user's first and last name
	 * @param un			Username
	 * @param fName			First name
	 * @param lName			Last name
	 */
	public void updateName(String un, String fName, String lName){
		dao.updateCustomer(customer.getId(), fName, lName, customer.getMembershipExpiration());

	}
	
	/**
	 * Updates address of the user
	 * @param un			Username
	 * @param street1		Address of user
	 * @param street2		Address of user
	 * @param zip			Zip code of user
	 * @param city			City of user
	 * @param state			State where user lives
	 * @param country		Country where user lives
	 */
	public void updateResidenceAddress(String un, String street1, String street2, int zip, String city, String state, String country){
		dao.updateAddressForCustomer(customer.getId(), street1, street2, city, state, zip, country);
	}
	
	/**
	 * Update driver's license information
	 * @param un				Username
	 * @param licenseNum		License number
	 * @param licenseState		License state
	 */
	public void updateDriversLicense(String un, String licenseNum, String licenseState){
		dao.updateDLForCustomer(customer.getId(), licenseNum, licenseState);
	}
	
	/**
	 * Updates a user's password
	 * @param un		Username
	 * @param oldPw		Old password
	 * @param newPw		New password
	 */
	public void updatePassword(String un, String oldPw, String newPw){
		dao.changePassword(customer.getId(), newPw);
		
	}
	
	/**
	 * Resets the user's password by sending an email with a random one
	 * @param email		Email address
	 */
	public void resetPassword(String email){
		
	}
	
	/**
	 * Updates the user's payment information
	 * @param un			Username
	 * @param pw			Password
	 * @param ccNum			Credit card number
	 * @param street1		User's billing address
	 * @param street2		User's billing address
	 * @param zip			User's zip code
	 */
	public void updatePaymentInfo(String un, String pw, int ccNum, String street1, String street2, int zip){
		
	}

	/**
	 * Tells whether or not a user exists
	 * @return		Returns true if user exists, false otherwise
	 */
	public boolean doesUserExist(){
		return true;
	}
	
	/**
	 * Generates a random password
	 * @return		Returns a random password
	 */
	public String generateRandomPassword(){
		String randomPassword="Random";
		return randomPassword;
	}
	
	/**
	 * Sends a confirmation email to the user upon registration
	 * @param un		Username
	 */
	public void sendConfirmEmail(String un){
		
	}
	
	/**
	 * Returns payment information
	 * @return		Returns a PaymentInfo object
	 */
	public PaymentInfo getCreditInfo(){
		
		PaymentInfo info=null;
		
		return info;
	}
}
