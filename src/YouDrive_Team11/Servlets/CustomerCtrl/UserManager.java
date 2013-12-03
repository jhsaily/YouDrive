package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

					//Make sure you don't get an error, if so display null page
					try{
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
							req.setAttribute("licenseNumber", customer.getDriversLicense().getLicenseNumber());
							req.setAttribute("licenseState", customer.getDriversLicense().getLicenseState());

							//Forward to edit profile
							dispatcher=ctx.getRequestDispatcher("/editprofile.jsp");
							dispatcher.forward(req, res);

						}
					}
					catch(Exception e){
						System.out.println("Something went wrong!");

						//Forward to edit profile!
						dispatcher=ctx.getRequestDispatcher("/editprofile.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user clicks edit payment information, send already existent info to form
					if(req.getParameter("clicked").equals("payment")){

						try{
							//Send in already existent user information to jsp vars

							req.setAttribute("cardExpirationMonth", customer.getPaymentInfo().getCardExpirationMonth());
							req.setAttribute("cardExpirationYear", customer.getPaymentInfo().getCardExpirationYear());
							req.setAttribute("addrLine1", customer.getPaymentInfo().getBillingAddress().getStreetAddrLine1());
							req.setAttribute("addrLine2", customer.getPaymentInfo().getBillingAddress().getStreetAddrLine2());
							req.setAttribute("city", customer.getPaymentInfo().getBillingAddress().getCity());
							req.setAttribute("zip", customer.getPaymentInfo().getBillingAddress().getZipCode());
							req.setAttribute("state", customer.getPaymentInfo().getBillingAddress().getState());
							req.setAttribute("country", customer.getPaymentInfo().getBillingAddress().getCountry());

							//Forward to payment page
							dispatcher=ctx.getRequestDispatcher("/editpayment.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							System.out.println("Could not pull up payment information. Sending empty payment page.");

							//Forward to payment page
							dispatcher=ctx.getRequestDispatcher("/editpayment.jsp");
							dispatcher.forward(req, res);
						}
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
						req.getParameter("lastname"), req.getParameter("addressline1"), req.getParameter("addressline2"), 
						req.getParameter("city"), req.getParameter("state"), Integer.valueOf(req.getParameter("zip")), req.getParameter("country"), req.getParameter("licensenum"), req.getParameter("licensestate"));
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

			//Need to call method that retrieves a customer object and then retrieves email to send new password to using that object
			//Fill customer object with retrieved customer
			customer=findCustomer(req.getParameter("username"));

			//If customer is found in db, reset their password
			if(customer!=null){

				//Retrieve email and send new password
				resetPassword(customer.getEmailAddress());

				//DELETE ME resetPassword(req.getParameter("username"));

			}//end if

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

						//Try to prevent invalid entries, especially in number fields
						try{
							//If the customer enters a new password, change this
							if(!(req.getParameter("newpassword").equals(""))){
								updatePassword(customer.getUsername(), req.getParameter("newpassword"));
							}
							updateName(customer.getUsername(), req.getParameter("firstname"), req.getParameter("lastname"));
							updateEmailAddress(customer.getUsername(), req.getParameter("email"));
							updateResidenceAddress(customer.getUsername(), req.getParameter("addressline1"), req.getParameter("addressline2"), Integer.valueOf(req.getParameter("zip")), req.getParameter("city"), req.getParameter("state"), req.getParameter("country"));
							updateDriversLicense(customer.getUsername(), req.getParameter("licensenum"), req.getParameter("licensestate"));

							//Update temp customer object and re-bind session object
							customer=findCustomerById(customer.getId());
							session.setAttribute("currentUser", customer);

							System.out.println("User Information Updated!");

							//Forward to dashboard
							dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							System.out.println("Invalid information was entered");

							//Forward to dashboard if info was invalid
							dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
							dispatcher.forward(req, res);
						}

					}

					//When the user submits his/her updated payment information, make the changes in the db
					if(req.getParameter("updatepayment")!=null){

						//Prevent invalid information from being entered
						try{
							//Update payment information in the database
							updatePaymentInfo(customer.getUsername(), req.getParameter("cardnumber"), Integer.valueOf(req.getParameter("cardexpmonth")), Integer.valueOf(req.getParameter("cardexpyear")), Integer.valueOf(req.getParameter("cardverification")), req.getParameter("addressline1"), req.getParameter("addressline2"), req.getParameter("city"), req.getParameter("state"), Integer.valueOf(req.getParameter("zip")), req.getParameter("country"));


							//Update the session attribute so that the information is fed back to the jsp page when it's clicked on
							System.out.println("Payment Information Updated!");
							customer=findCustomerById(customer.getId());
							session.setAttribute("currentUser", customer);


							//Forward to dashboard
							dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
							dispatcher.forward(req, res);

						}
						catch(Exception e){
							System.out.println("Invalid payment information");

							//Forward to dashboard if payment info was invalid
							dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
							dispatcher.forward(req, res);
						}
					}

				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){

					//If the user hits submit after editing a user's information, update it in the db
					if(req.getParameter("updateProfile")!=null){

						//Remove membership if it is checked
						if(req.getParameter("revokemembership")!=null){
							System.out.println("revoking membership");
							Customer c=findCustomer(req.getParameter("username"));
							dao.revokeMembership(c.getId());
						}
						//Check if remove profile is checked
						if(req.getParameter("removeprofile")==null){
							updateName(req.getParameter("username"), req.getParameter("firstname"), req.getParameter("lastname"));
							updateEmailAddress(req.getParameter("username"), req.getParameter("email"));
							updateResidenceAddress(req.getParameter("username"), req.getParameter("addressline1"), req.getParameter("addressline2"), Integer.valueOf(req.getParameter("zip")), req.getParameter("city"), req.getParameter("state"), req.getParameter("country"));
							updateDriversLicense(req.getParameter("username"), req.getParameter("licensenum"), req.getParameter("licensestate"));

							//Forward to manage user screen
							dispatcher=ctx.getRequestDispatcher("/manageusers.jsp");
							dispatcher.forward(req, res);
						}
						else{
							//Remove the user
							removeUser(req.getParameter("username"));

							//Forward to UserAdminManager screen
							dispatcher=ctx.getRequestDispatcher("/manageusers.jsp"); 
							dispatcher.forward(req, res);
						}
					}

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
	public Customer createUser(String username, String password, String emailAddress, String firstName, String lastName, String addrLine1, String addrLine2, String city, String state, int ZIP, String country, String DLNumber, String DLState){
		return dao.createCustomer(username, password, emailAddress, firstName, lastName, addrLine1, addrLine2, city, state, ZIP, country, DLNumber, DLState);

	}

	/**
	 * Updates email address
	 * @param un		Username
	 * @param email		User email address
	 */
	public void updateEmailAddress(String un, String email){
		Customer c=findCustomer(un);
		dao.changeEmailAddress(c.getId(), email);
	}

	/**
	 * Updates user's first and last name
	 * @param un			Username
	 * @param fName			First name
	 * @param lName			Last name
	 */
	public void updateName(String un, String fName, String lName){
		Customer c=findCustomer(un);
		System.out.println(un);
		dao.updateCustomer(c.getId(), fName, lName, c.getMembershipExpiration());

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
		Customer c=findCustomer(un);
		dao.updateAddressForCustomer(c.getId(), street1, street2, city, state, zip, country);
	}

	/**
	 * Update driver's license information
	 * @param un				Username
	 * @param licenseNum		License number
	 * @param licenseState		License state
	 */
	public void updateDriversLicense(String un, String licenseNum, String licenseState){
		Customer c=findCustomer(un);
		dao.updateDLForCustomer(c.getId(), licenseNum, licenseState);
	}

	/**
	 * Updates a user's password
	 * @param un		Username
	 * @param oldPw		Old password
	 * @param newPw		New password
	 */
	public void updatePassword(String un, String newPw){
		Customer c=findCustomer(un);
		dao.changePassword(c.getId(), newPw);

	}

	/**
	 * Resets the user's password by sending an email with a random one
	 * @param email		Email address
	 * @throws UnsupportedEncodingException 
	 */
	public void resetPassword(String email) throws UnsupportedEncodingException{
		//Generate and store a new password
		String newP=generateRandomPassword();

		//Update customer information in db
		updatePassword(customer.getUsername(), newP);

		//Send email with new login info
		SendEmail mail=new SendEmail();
		mail.send(email, "Forgotten Password", "Greetings YouDrive Member! \n\nYour new password is: " + newP + "\nPlease type this into the login screen exactly as you see it. \n\nThank You \nTeam 11");


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
	public void updatePaymentInfo(String un, String ccNum, int ccExpMonth, int ccExpYear, int ccSecurity, String street1, String street2, String city, String state, int zip, String country){
		Customer c=findCustomer(un);
		dao.updatePaymentInfoForCustomer(c.getId(), ccNum, ccExpMonth, ccExpYear, ccSecurity);
		dao.updateAddressForPaymentInfo(c.getPaymentInfo().getId(), street1, street2, city, state, zip, country);
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
			int r=(int)(Math.random()*36);
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

	public Customer findCustomer(String un){
		Customer cust=dao.readCustomer(un); 

		return cust;
	}

	public Customer findCustomerById(int id){
		return dao.readCustomer(id);
	}

	/**
	 * Removes a user from the database
	 * @param un		The customer's username
	 */
	public void removeUser(String un){
		Customer cust=dao.readCustomer(un);
		dao.deleteCustomer(cust.getId());
	}
}
