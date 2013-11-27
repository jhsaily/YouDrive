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
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher=ctx.getRequestDispatcher("/success.jsp");
		
		//Get the current session attributes
		String un=(String)req.getSession().getAttribute("currentUserName");
		Character one=(Character)req.getSession().getAttribute("firstChar");
		
		System.out.println("POST!");
		String currentRes = req.getParameter("res");
		if(un!=null){
			currentRes="The current user is: " + un + " and the first char is: " + one;
			
		}
		else{
			currentRes="Please log in to make a reservation";
		}
		req.setAttribute("res", currentRes);
		
		dispatcher.forward(req, res);
	}
}
