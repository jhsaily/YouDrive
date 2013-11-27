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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
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
	
	
	public void updateEmailAddress(String un, String email){
		dao.changeEmailAddress(customer.getId(), email);
	}
	
	public void updateName(String un, String fName, String lName){
		dao.updateCustomer(customer.getId(), fName, lName, customer.getMembershipExpiration());

	}
	
	public void updateResidenceAddress(String un, String street1, String street2, int zip, String city, String state, String country){
		dao.updateAddressForCustomer(customer.getId(), street1, street2, city, state, zip, country);
	}
	
	public void updateDriversLicense(String un, String licenseNum, String licenseState){
		dao.updateDLForCustomer(customer.getId(), licenseNum, licenseState);
	}
	
	public void updatePassword(String un, String oldPw, String newPw){
		dao.changePassword(customer.getId(), newPw);
		
	}
	
	public void resetPassword(String un, String email){
		
	}
	
	public void updatePaymentInfo(String un, String pw, int ccNum, String street1, String street2, int zip){
		
	}

	public boolean doesUserExist(){
		return true;
	}
	
	public String generateRandomPassword(){
		String randomPassword="Random";
		return randomPassword;
	}
	
	public void sendConfirmEmail(String un){
		
	}
	
	public Customer findCustomer(String un){
		Customer customer=null;
		
		return customer;
	}
	
	public PaymentInfo getCreditInfo(){
		
		PaymentInfo info=null;
		
		return info;
	}
}
