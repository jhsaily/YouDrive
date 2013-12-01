package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.IOException;

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
 * Authenticates and logs in an existing user
 * @author Tanya Siclait
 */
public class AuthenticationManager extends HttpServlet {
	
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Temporary customer and admin
	Customer customer;
	Administrator admin;
	
	/**
	 * Constructor
	 */
	public AuthenticationManager(){
		super();
		
		//Create DAO
		dao=new YouDriveDAO();
	}
	
	/**
	 * Handles get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

		System.out.println("GET!");
		
		//Set session
		session=req.getSession();
				
		//Get the servlet context & the dispatcher for later use
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		//If the user clicks logout, end the session.
		if(req.getParameter("clicked").equals("logout")){
			
			//If the user logging out is a customer
			if(session.getAttribute("userType").equals("customer")){
				System.out.println("User: " + ((Customer)session.getAttribute("currentUser")).getUsername() +  " is logging out.");	
			}
			
			//If the user logging out is an admin
			else{
				System.out.println("User: " + ((Administrator)session.getAttribute("currentUser")).getUsername() +  " is logging out.");	
			}
			
			logout();

			//Forward to homepage
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
	}
	
	/**
	 * Handles post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("POST!");
		
		//Get the servlet context & the dispatcher for later use
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		
		//Set session
		session=req.getSession();
		
		//Collect user information and try to log in
		String un, pw;
		un=req.getParameter("username");
		pw=req.getParameter("password");
		System.out.println("The user, " + un + ", is trying to log in.");
		
		//If login is successful, direct user to dashboard
		//TESTif(un.equals("admin")||un.equals("cust")){
		if(authenticateUser(un, pw)!=null){
			System.out.println("Successful Authentication!");
			
			//If the user is an administrator, take them to the admin dashboard
			//TESTif(un.equals("admin")){
				if(authenticateUser(un, pw).isAdmin()){ 
				
				//Bind objects to the user's session, e.g. Customer, Reservation objects
				//TESTadmin=new Administrator(5, "Adamistrator", "pw"); //DELETE ME LATER
					
				//Obtain user object from db and cast to admin
				admin=(Administrator)authenticateUser(un, pw);
				session.setAttribute("currentUser", admin);
				
				//If the user is an admin, set the user type to admin
				session.setAttribute("userType", "admin");
				
				dispatcher=ctx.getRequestDispatcher("/admindashboard.jsp");
				dispatcher.forward(req, res);
			}
			else{
				
				//Bind objects to the user's session, e.g. Customer, Reservation objects
				//TESTsession.setAttribute("currentUser", getCustomer(un, pw));
				
				//Obtain user object from db and cast to admin
				customer=(Customer)authenticateUser(un, pw);
				session.setAttribute("currentUser", customer);
				
				//If the user is a customer, set the user type to customer
				session.setAttribute("userType", "customer");
				
				dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
				dispatcher.forward(req, res);
			}
		}
		else{
			System.out.println("Incorrect user credentials");
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		
		
	
		
		
	}

	/**
	 * Verifies the user's username and password
	 * @param un		The username
	 * @param pw		The password
	 * @return			True if the user can be verified, false otherwise
	 */
	public User authenticateUser(String un, String pw){
		
		User user= dao.authenticateUser(un, pw);
		return user;
	}
	
	/**
	 * Tells us if a password is correct or not
	 * @param un		The username
	 * @param pw		The password
	 * @return			True if the password corresponds to the user
	 */
	public boolean doesPasswordMatch(String un, String pw){
		
		return true;
	}
	
	/**
	 * Retrieves a customer object with all relevant user data
	 * @param un
	 * @param pw
	 */
	public Customer login(String un, String pw){
		//Dummy customer created DELETE ME
		Address add=new Address(0, "123 Example Street", "Apt. 101", "Kennesaw", "GA", 21202, "Zimbabwe");
		PaymentInfo paymentInfo=new PaymentInfo(0, "123456789", 11, 2013, add);
		Customer one=new Customer(0, un, pw, "silly@fake.com", "Dummy", "User", null, null, add, paymentInfo);
		return one;
		
		/**
		 * Need a way to find customer by username not id
		 */
		//return dao.readCustomer(123);
	}
	
	//DUMMY METHOD DELETE ME
	public Customer getCustomer(String un, String pw){
		if(pw.equals("amy")){
			Address add=new Address(0, "456 Highway", "Apt. 101", "Dalton", "GA", 00502, "UK");
			PaymentInfo paymentInfo=new PaymentInfo(0, "4928394", 01, 2013, add);
			Customer two=new Customer(0, un, pw, "amy@fake.com", "Amy", "Smith", null, null, add, paymentInfo);
			return two;
		}
		else if(pw.equals("dummy")){
			Address add=new Address(0, "123 Example Street", "Apt. 101", "Kennesaw", "GA", 21202, "Zimbabwe");
			PaymentInfo paymentInfo=new PaymentInfo(0, "123456789", 11, 2013, add);
			Customer one=new Customer(0, un, pw, "silly@fake.com", "Dummy", "User", null, null, add, paymentInfo);
			return one;
		}
		else if(pw.equals("bob")){
			Address add=new Address(0, "789 Willow Park", "Suite 7", "Jokersville", "GA", 30283, "France");
			PaymentInfo paymentInfo=new PaymentInfo(0, "123456789", 11, 2013, add);
			Customer three=new Customer(0, un, pw, "BigBoy@fake.com", "Bob", "Wills", null, null, add, paymentInfo);
			return three;
		}
		
		return null;
	}
	
	/**
	 * Logs the user out
	 */
	public void logout(){
		session.invalidate();
	}
}
