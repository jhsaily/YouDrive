package YouDrive_Team11.Servlets.ReservationCtrl;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;

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
 * Class to manage the rental reservations a user makes
 * @author Tanya
 *
 */
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
	
	/**
	 * Manages get requests and responses
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("Get!");
		
		//Set session
		session=req.getSession();
		customer=(Customer)session.getAttribute("currentUser");
		
		//Get the servlet context & the dispatcher for later use
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;
		
		//If the user clicks view history, return a list of the past reservations.
		if(req.getParameter("clicked").equals("history")){
			req.setAttribute("listOfReservations", getAllReservations(customer.getUsername()));
			
			//Forward to homepage
			dispatcher=ctx.getRequestDispatcher("/history.jsp");
			dispatcher.forward(req, res);
		}
	}
	
	/**
	 * Manages post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("POST!");
		
		//Get the servlet context and the dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
		
		//Get the current session attributes
		customer=(Customer)req.getSession().getAttribute("currentUser");
		
		//If user clicks submit on place reservation page, place a reservation
		if(req.getParameter("placeReservation")!=null){
			System.out.println("Placing Reservation!");
			
			//Update Reservation Page
			req.setAttribute("listOfReservations", getAllReservations(customer.getUsername()));
			
			//Forward to Reservation History page
			dispatcher=ctx.getRequestDispatcher("/history.jsp");
		}
		
		//If the user clicks cancel reservation, remove reservation from linked list
		if(req.getParameter("cancel")!=null){
			System.out.println("Cancelling reservation " + req.getParameter("reservationnumber") + " for " + customer.getUsername());
			
			//Remove the reservation
			cancelReservation(Integer.valueOf(req.getParameter("reservationnumber")));
			
			//Update Reservation Page
			req.setAttribute("listOfReservations", getAllReservations(customer.getUsername()));
			
			//Forward to Reservation History page
			dispatcher=ctx.getRequestDispatcher("/history.jsp");
		}
		
		if(customer!=null){
			System.out.println("The current user is: " + customer.getUsername());
			
		}
		else{
			System.out.println("Please log in to make a reservation");
			
			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			
		}
		
		
		dispatcher.forward(req, res);
	}
	
	/**
	 * Creates a new reservation under a particular user
	 * @param pickUp			Time to pick up the vehicle
	 * @param duration			Length of the reservation
	 * @param vehicleTypeId		Unique vehicle type identifier
	 * @param locationId		Unique location identifier
	 * @return					Returns a reservation object
	 */
	public Reservation placeReservation(Date pickUp, double duration, int vehicleTypeId, int locationId){
		Reservation reservation=null;
		return reservation;
	}
	
	/**
	 * Cancels a reservation under a particular user
	 * @param reservationId
	 */
	public void cancelReservation(int reservationId){
		System.out.println("Reservation with number: " + reservationId + " has been removed");
	}

	/**
	 * Updates information about a reservation
	 * @param reservationId		Unique reservation identifier
	 * @param pickUp			Time to pick up vehicle
	 * @param duration			Length of rental
	 */
	public void updateReservation(int reservationId, Date pickUp, double duration){
		
	}
	
	/**
	 * Returns a vehicle rental and terminates registration
	 * @param reservationId		Unique reservation identifier
	 * @param timeReturned		Time vehicle was returned
	 */
	public void returnRental(int reservationId, Date timeReturned){
		
	}
	
	/**
	 * Locates information about a reservation in the database
	 * @param reservationId		Unique reservation identifier
	 * @return					Returns a Reservation object
	 */
	public Reservation findReservation(int reservationId){
		Reservation reservation=null;
		return reservation;
	}
	
	/**
	 * Returns all the reservations under a particular user
	 * @param un		Username
	 * @return			Returns a linked list of the reservations
	 */
	public LinkedList<Reservation> getAllReservations(String un){
		LinkedList<Reservation> list=new LinkedList<Reservation>();
		Reservation res1=new Reservation(5, new Date(12,3, 5), 5.7, false, null, null, null, null, customer);
		Reservation res2=new Reservation(54, new Date(12,3, 5), 4.5, false, null, null, null, null, customer);
		list.add(res1);
		list.add(res2);
		return list;
	}
	
	public void loadSession(HttpServletRequest req){
		
	}
}
