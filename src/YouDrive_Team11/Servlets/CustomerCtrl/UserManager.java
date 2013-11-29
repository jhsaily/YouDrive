package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	//Declare temporary customer and admin objects
	Customer customer;
	Administrator admin;
	
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
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		//Get Servlet Context and dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		//If a session exists, then do everything, if not direct to log in : this prevents a user from going to a page without logging in.
		if(req.getSession().getAttribute("userType")!=null){

			//Get the session
			session=req.getSession();

			//Check if user is admin or customer and get appropriate session data
			if(session.getAttribute("userType").equals("customer")){
				//Get the current session attributes
				customer=(Customer)req.getSession().getAttribute("currentUser");
			}
			else{
				//Get the current session attributes
				admin=(Administrator)req.getSession().getAttribute("currentUser");
			}

			//Double Check if session exists, if not go to login screen MIGHT BE REDUNDANT DELETE IF SO
			if(customer==null && admin==null){
				System.out.println("No session");
				dispatcher=ctx.getRequestDispatcher("/index.jsp");
				dispatcher.forward(req, res);
			}//end if

			//If session exists (one of the objects are filled), do either Customer or Admin logic
			else{

				//CUSTOMER LOGIC
				if(customer!=null){
					System.out.println(customer.getUsername() + " is currently logged in.");

					//If the user clicks update profile
					if(req.getParameter("clicked").equals("update")){
						
						//Send in already existent user information to jsp vars
						req.setAttribute("firstName", customer.getFirstName());
						req.setAttribute("lastName", customer.getLastName());
						req.setAttribute("emailAddress", customer.getEmailAddress());
						req.setAttribute("addrLine1", customer.getMailingAddress().getStreetAddrLine1());
						req.setAttribute("addrLine2", customer.getMailingAddress().getStreetAddrLine2());
						req.setAttribute("city", customer.getMailingAddress().getCity());
						req.setAttribute("zip", customer.getMailingAddress().getZipCode());
						req.setAttribute("state", customer.getMailingAddress().getState());
						req.setAttribute("country", customer.getMailingAddress().getCountry());
						//req.setAttribute("licenseNumber", customer.getDriversLicense().getLicenseNumber());
						//req.setAttribute("licenseState", customer.getDriversLicense().getLicenseState());
						
						//Forward to dashboard
						dispatcher=ctx.getRequestDispatcher("/editprofile.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user clicks edit payment information, send already existent info to form
					if(req.getParameter("clicked").equals("payment")){
						
						//Send in already existent user information to jsp vars
						
						//req.setAttribute("cardExpirationMonth", customer.getPaymentInfo().getCardExpirationMonth());
						//req.setAttribute("cardExpirationYear", customer.getPaymentInfo().getCardExpirationYear());
						req.setAttribute("addrLine1", customer.getMailingAddress().getStreetAddrLine1());
						req.setAttribute("addrLine2", customer.getMailingAddress().getStreetAddrLine2());
						req.setAttribute("city", customer.getMailingAddress().getCity());
						req.setAttribute("zip", customer.getMailingAddress().getZipCode());
						req.setAttribute("state", customer.getMailingAddress().getState());
						req.setAttribute("country", customer.getMailingAddress().getCountry());
						
						//Forward to dashboard
						dispatcher=ctx.getRequestDispatcher("/editpayment.jsp");
						dispatcher.forward(req, res);
					}
				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){

				}//END ADMIN LOGIC
			}//end else
		}//end if

		//If the session doesn't exist, go back to log in screen.
		else{

			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}//end else
	}
	
	/**
	 * Manages all post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		//Get Servlet Context and dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		/**
		 * IF THE USER CANNOT LOG IN
		 */
		//If the user clicks register, create a new user.
		if(req.getParameter("register")!=null){
			
			//Make sure they entered numbers in the number places
			
			try{
			createUser(req.getParameter("username"), req.getParameter("password"), req.getParameter("email"), req.getParameter("firstname"),
					req.getParameter("lastname"), returnDate("06","30", "2014"), req.getParameter("addressline1"), req.getParameter("addressline2"), 
					"Chicago", req.getParameter("state"), Integer.valueOf(req.getParameter("zip")), req.getParameter("country"), req.getParameter("licensenum"), req.getParameter("licensestate"));
			}
			catch(Exception e){
				System.out.println("Invalid input");
				//TODO: TAKE BACK TO REGISTRATION SCREEN
			}
			
			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		
		//If the user clicks Forgot Password, send an email with a new one to them
		else if(req.getParameter("forgotPassword")!=null){
			resetPassword(req.getParameter("email"));
			
			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		
		/**
		 * IF THE USER CAN LOG IN
		 */
		//If a session exists, then do everything, if not direct to log in : this prevents a user from going to a page without logging in.
		else if(req.getSession().getAttribute("userType")!=null){

			//Get the session
			session=req.getSession();

			//Check if user is admin or customer and get appropriate session data
			if(session.getAttribute("userType").equals("customer")){
				//Get the current session attributes
				customer=(Customer)req.getSession().getAttribute("currentUser");
			}
			else{
				//Get the current session attributes
				admin=(Administrator)req.getSession().getAttribute("currentUser");
			}

			//Double Check if session exists, if not go to login screen MIGHT BE REDUNDANT DELETE IF SO
			if(customer==null && admin==null){
				System.out.println("No session");
				dispatcher=ctx.getRequestDispatcher("/index.jsp");
				dispatcher.forward(req, res);
			}//end if

			//If session exists (one of the objects are filled), do either Customer or Admin logic
			else{

				//CUSTOMER LOGIC
				if(customer!=null){
					System.out.println(customer.getUsername() + " is currently logged in.");

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
					
					//When the user submits his/her updated payment information, make the changes in the db
					if(req.getParameter("updatepayment")!=null){
						/*UNCOMMENT ME
						updatePaymentInfo(customer.getUsername(), customer.getParameter("currentpassword"), customer.getParameter("cardnumber"), customer.getParameter("addressline1"), customer.getParameter("addressline2"), customer.getParameter("zip"));
						*/
						System.out.println("Payment Information Updated!");
						
						//Forward to dashboard
						dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
						dispatcher.forward(req, res);
					}
					
				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){

				}//END ADMIN LOGIC
			}//end else
		}//end if

		//If the session doesn't exist, go back to log in screen.
		else{

			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}//end else
		
		
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
		generateRandomPassword();
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
		String randomPassword="";
		String allPossibleChars="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0; i<8; i++){
			int r=(int)(Math.random()*37);
			randomPassword=randomPassword + allPossibleChars.charAt(r);
		}
		System.out.println("Random Password: " + randomPassword);
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
	
	public Date returnDate(String mm, String dd, String yyyy) throws ParseException{
		
		java.util.Date utilDate=new SimpleDateFormat("MM/dd/yyyy").parse(mm+"/"+dd+"/"+yyyy);
		java.sql.Date date=new java.sql.Date(utilDate.getTime());
		
		return date;
	}
}
