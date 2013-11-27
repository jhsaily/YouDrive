package YouDrive_Team11.Servlets.ReservationCtrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Persistence.YouDriveDAO;

public class ReservationManager extends HttpServlet {
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Temporary Customer object
	Customer customer=null;
	
	
	/**
	 * Constructor
	 */
	public ReservationManager(){
		super();
		
		//Create DAO
		dao=new YouDriveDAO();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("POST!");
		
		//Get the servlet context and the dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
		
		//Get the current session attributes
		customer=(Customer)req.getSession().getAttribute("currentCustomer");
		
		
		
		
		if(customer!=null){
			System.out.println("The current user is: " + customer.getUsername());
			
		}
		else{
			System.out.println("Please log in to make a reservation");
		}
		
		
		dispatcher.forward(req, res);
	}
}
