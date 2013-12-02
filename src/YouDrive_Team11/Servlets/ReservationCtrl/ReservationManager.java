package YouDrive_Team11.Servlets.ReservationCtrl;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	Administrator admin=null;

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
		System.out.println("GET!");

		//Get the servlet context & the dispatcher for later use
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

					//If the user clicks place a reservation, populate the reserve.jsp page with locations
					if(req.getParameter("clicked").equals("place")){
						req.setAttribute("locations", getAllLocations());
						
						//Forward to reserve page
						dispatcher=ctx.getRequestDispatcher("/reserve.jsp");
						dispatcher.forward(req, res);
					}
					
					//After user has chosen a location, set the location and return the list of available vehicles there
					if(req.getParameter("clicked").equals("chooseLocation")){
						if(req.getParameter("vehiclechosen").equals("false")){
							
							//Create temp location object to obtain info about for later
							RentalLocation location=findLocation(Integer.valueOf(req.getParameter("location")));
							
							//Set all location attributes on reserve vehicle page
							req.setAttribute("locationName", location.getName());
							req.setAttribute("locationAddrLine1", location.getLocationAddress().getStreetAddrLine1());
							req.setAttribute("locationAddrLine2", location.getLocationAddress().getStreetAddrLine2());
							req.setAttribute("locationCity", location.getLocationAddress().getCity());
							req.setAttribute("locationState", location.getLocationAddress().getState());
							req.setAttribute("locationZip", location.getLocationAddress().getZipCode());
							req.setAttribute("locationCountry", location.getLocationAddress().getCountry());

							//Display all available vehicles at this location
							req.setAttribute("vehicles", getAllVehiclesFrom(location.getId()));
							req.setAttribute("locationId", location.getId());
							
							//Forward to page where they choose their vehicle
							dispatcher=ctx.getRequestDispatcher("/reservevehicle.jsp");
							dispatcher.forward(req, res);
						
						}
					}
					
					//If user clicks choose vehicle after choosing a location
					if(req.getParameter("clicked").equals("chooseVehicle")){
						
						//EDIT ME WHEN RANDY IMPLEMENTS RESERVATION STUFF
						System.out.println("Placing reservation for you!");
						
						//If user has payment information on file
						try{
							
							req.setAttribute("paymentAmount", "None");
							req.setAttribute("paymentReason", "Reservation");
							req.setAttribute("firstName", customer.getFirstName());
							req.setAttribute("lastName", customer.getLastName());
							req.setAttribute("cardNumber", customer.getPaymentInfo().getCreditCardNumber());
							req.setAttribute("cardExpMonth", customer.getPaymentInfo().getCardExpirationMonth());
							req.setAttribute("cardExpYear", customer.getPaymentInfo().getCardExpirationYear());
							req.setAttribute("addrLine1", customer.getPaymentInfo().getBillingAddress().getStreetAddrLine1());
							req.setAttribute("addrLine2", customer.getPaymentInfo().getBillingAddress().getStreetAddrLine2());
							req.setAttribute("city", customer.getPaymentInfo().getBillingAddress().getCity());
							req.setAttribute("state", customer.getPaymentInfo().getBillingAddress().getState());
							req.setAttribute("zip", customer.getPaymentInfo().getBillingAddress().getZipCode());
							req.setAttribute("country", customer.getPaymentInfo().getBillingAddress().getCountry());
							
							//Create the reservation and store in the database
							placeReservation(returnDate("12", "27", "2013"), 23, false, returnDate("12", "29", "2013"), Integer.valueOf(req.getParameter("vehicle")), Integer.valueOf(req.getParameter("locationid")), customer.getId());
							
							//Forward to page where reservation details are confirmed and paid for
							dispatcher=ctx.getRequestDispatcher("/processpayment.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							System.out.println("Invalid input or no payment info on file");
							
							//Forward to payment fail
							dispatcher=ctx.getRequestDispatcher("/paymentfail.jsp");
							dispatcher.forward(req, res);
						}
					}
					
					//If the user clicks view history, return a list of the past reservations.
					if(req.getParameter("clicked").equals("history")){
						req.setAttribute("listOfReservations", getAllReservations(customer.getUsername()));

						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/history.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user clicks return a rental, take them to the return page populated with their reservations
					if(req.getParameter("clicked").equals("return")){
						req.setAttribute("listOfActiveReservations", getAllReservations(customer.getUsername()));

						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/return.jsp");
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
	 * Manages post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("POST!");

		//Get the servlet context and the dispatcher
		ServletContext ctx=this.getServletContext();
		RequestDispatcher dispatcher;

		//If a session exists, then : this prevents a user from going to a page without logging in.
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
			}
			//If session exists, do either Customer or Admin logic
			else{

				//CUSTOMER LOGIC
				if(customer!=null){
					System.out.println(customer.getUsername() + " is currently logged in.");

					//If user clicks submit on place reservation page, place a reservation
					if(req.getParameter("placeReservation")!=null){
						System.out.println("Placing Reservation!");
						
						//Create a new reservation
						//placeReservation(null, 0, 0, 0);
						
						//Update Reservation Page
						req.setAttribute("listOfReservations", getAllReservations(customer.getUsername()));

						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/history.jsp");
						dispatcher.forward(req, res);
					}

					//If the user clicks cancel reservation, remove reservation from linked list
					if(req.getParameter("cancel")!=null){
						System.out.println("Cancelling reservation " + req.getParameter("reservationnumber") + " for " + customer.getUsername());

						//Remove the reservation
						cancelReservation(Integer.valueOf(req.getParameter("reservationnumber")));

						//Update Reservation History Page
						LinkedList<Reservation> updated=new LinkedList<Reservation>(); //DELETE ME
						updated=getAllReservations(customer.getUsername()); //DELETE ME
						updated.remove(); //DELETE ME
						req.setAttribute("listOfReservations", updated); //UPDATE ME to pass in the correct linked list

						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/history.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user clicks return on one of his/her reservations, send them to the return form with a res#
					if(req.getParameter("gotoreturnform")!=null){
						req.setAttribute("reservationNum", req.getParameter("reservationnumber"));
						
						//Forward to Return form page
						dispatcher=ctx.getRequestDispatcher("/returnform.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user submits the return form, add that reservation to their history, remove from active reservations
					if(req.getParameter("submitReturn")!=null){
						System.out.println("Rental successfully returned!");
						
						//Update Reservation History
						req.setAttribute("listOfReservations", getAllReservations(customer.getUsername())); //UPDATE ME to pass in the correct linked list
						
						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/history.jsp");
						dispatcher.forward(req, res);
					}
					
				}//END CUSTOMER LOGIC
		
			}
		}

		//If the session doesn't exist, go back to log in screen.
		else{

			//Forward to login screen
			dispatcher=ctx.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, res);
		}
	}

	/**
	 * Creates a new reservation under a particular user
	 * @param pickUp			Time to pick up the vehicle
	 * @param duration			Length of the reservation
	 * @param vehicleId		Unique vehicle type identifier
	 * @param locationId		Unique location identifier
	 * @return					Returns a reservation object
	 */
	public Reservation placeReservation(Date pickUp, double duration, boolean isHourly, Date timeDue, int vehicleId, int locationId, int custId){
		return dao.createReservation(pickUp, duration, isHourly, timeDue, vehicleId, locationId, custId);
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
	 * Returns a vehicle rental and changes reservation from active to past
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
	 * @throws ParseException 
	 */
	public LinkedList<Reservation> getAllReservations(String pw){
		LinkedList<Reservation> list=new LinkedList<Reservation>();
		try{
				//DELETE ME dummy values
				Reservation res1=new Reservation(1, returnDate("09", "29", "2013"), 10.0, false, null, null, null, null, customer);
				Reservation res2=new Reservation(2, returnDate("12", "27", "2013"), 4.5, false, null, null, null, null, customer);
				list.add(res1);
				list.add(res2);	
		}
		catch(Exception e){
			System.out.println("Failure in getAllReservations method");
		}
		
		return list;
	}
	
	public Date returnDate(String mm, String dd, String yyyy) throws ParseException{
		
		java.util.Date utilDate=new SimpleDateFormat("MM/dd/yyyy").parse(mm+"/"+dd+"/"+yyyy);
		java.sql.Date date=new java.sql.Date(utilDate.getTime());
		
		return date;
	}
	
	/**
	 * Gets all the locations in the database
	 * @return		Returns a linked list of all the locations
	 */
	public LinkedList getAllLocations(){
		return dao.getAllRentalLocations();
	}


	/**
	 * Gets all of the vehicles in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<Vehicle> getAllVehicles(){
		return dao.getAllVehicles();
	}
	
	public RentalLocation findLocation(int id){
		return dao.readRentalLocation(id);
	}
	
	/**
	 * Gets all of the vehicles from a specific location in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<Vehicle> getAllVehiclesFrom(int id){
		return dao.getAllVehicles(id);
	}
}