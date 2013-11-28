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
		if(un.equals("admin")||un.equals("cust")){
		//UNCOMMENT ME if(authenticateUser(un, pw)!=null){
			System.out.println("Successful Authentication!");
			
			//Set the session user
			//session.setAttribute("currentUser", login(un, pw));
			
			//If the user is an administrator, take them to the admin dashboard
			if(un.equals("admin")){
				//UNCOMMENT ME if(authenticateUser(un, pw).isAdmin()){ 
				
				//Bind objects to the user's session, e.g. Customer, Reservation objects
				admin=new Administrator(5, "Adamistrator", "pw"); //DELETE ME LATER
				session.setAttribute("currentUser", admin);
				
				//If the user is an admin, set the user type to admin
				session.setAttribute("userType", "admin");
				
				dispatcher=ctx.getRequestDispatcher("/admindashboard.jsp");
				dispatcher.forward(req, res);
			}
			else{
				
				//Bind objects to the user's session, e.g. Customer, Reservation objects
				session.setAttribute("currentUser", login(un, pw));
				
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
		Customer one=new Customer(0, un, pw, pw, pw, pw, null, null, null);
		return one;
		
		/**
		 * Need a way to find customer by username not id
		 */
		//return dao.readCustomer(123);
	}
	
	/**
	 * Logs the user out
	 */
	public void logout(){
		session.invalidate();
	}
}
