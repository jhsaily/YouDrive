package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.Customer;
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
	
	/**
	 * Constructor
	 */
	public AuthenticationManager(){
		super();
		
		//Create DAO
		dao=new YouDriveDAO();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

		System.out.println("Get!");
		
		//Get the servlet context & the dispatcher for later use
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		//Ends the session and returns the user to the homepage
		logout();
		dispatcher=ctx.getRequestDispatcher("/index.jsp");
		dispatcher.forward(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("Post!");
		
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
		if(authenticateUser(un, pw)){
			System.out.println("Successful Authentication!");
			
			//Bind objects to the user's session, e.g. Customer, Reservation objects
			session.setAttribute("currentCustomer", login(un, pw));
			
			
			dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(req, res);
		}
		else{
			System.out.println("Incorrect user credentials");
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
		
		
		
		

		
		
		
		
	}
	
	public boolean authenticateUser(String un, String pw){
		
		//return dao.authenticateUser(un, pw);
		
		//Since Randy hasn't made the tables yet, we're going to return true every time. DELETE ME
		
		return true;
	}
	
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
		Customer one=new Customer(0, pw, pw, pw, pw, pw, null, null, null);
		return one;
		
		/**
		 * Need a way to find customer by username not id
		 */
		//return dao.readCustomer(123);
	}
	
	public void logout(){
		session.invalidate();
	}
}