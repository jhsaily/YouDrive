package YouDrive_Team11.Servlets.CustomerCtrl;

import java.sql.Date;

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
	
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public Customer createUser(String username, String password, String emailAddress, String firstName, String lastName, Date membershipExpiration, String addrLine1, String addrLine2, String city, String state, int ZIP, String country, String DLNumber, String DLState){
		Customer customer=dao.createCustomer(username, password, emailAddress, firstName, lastName, membershipExpiration, addrLine1, addrLine2, city, state, ZIP, country, DLNumber, DLState);
		this.customer=customer;
		return customer;
	}
	
	public void updateEmailAddress(String un, String pw, String email){
		dao.updateCustomer(customer.getId(), un, pw, email, customer.getFirstName(), customer.getLastName(), customer.getMembershipExpiration());
	}
	
	public void updateName(String un, String pw, String fName, String lName){
		dao.updateCustomer(customer.getId(), un, pw, customer.getEmailAddress(), fName, lName, customer.getMembershipExpiration());

	}
	
	public void updateResidenceAddress(String un, String pw, String street1, String street2, int zip, String city, String state, String country){

	}
	
	public void updateDriversLicense(String un, String pw, String licenseNum, String licenseState){

	}
	
	public void updatePassword(String un, String oldPw, String newPw){
		
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
