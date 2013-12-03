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
						req.setAttribute("vehicleTypes", getAllVehicles());
						
						//Forward to reserve page
						dispatcher=ctx.getRequestDispatcher("/reserve.jsp");
						dispatcher.forward(req, res);
					}
					
					
					
					
					//If the user clicks view history, return a list of the past reservations.
					if(req.getParameter("clicked").equals("history")){
						req.setAttribute("listOfActiveReservations", getAllActiveReservations(customer.getId()));

						//Forward to Reservation History page
						dispatcher=ctx.getRequestDispatcher("/history.jsp");
						dispatcher.forward(req, res);
					}
					
					//If the user clicks return a rental, take them to the return page populated with their reservations
					if(req.getParameter("clicked").equals("return")){
						req.setAttribute("listOfActiveReservations", getAllActiveReservations(customer.getId()));

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

					//If user clicks submit on place reservation page, collect information and forward to vehicles page
					if(req.getParameter("placeReservation")!=null){
						try{
							//Set all the attributes on the vehicle reservation page before forwarding
							req.setAttribute("month", req.getParameter("pickupmonth"));
							req.setAttribute("day", req.getParameter("pickupday"));
							req.setAttribute("year", req.getParameter("pickupyear"));
							req.setAttribute("hour", req.getParameter("rentallengthhours"));
							req.setAttribute("meridiem", req.getParameter("pickupmeridiem"));
							req.setAttribute("rentalType", req.getParameter("rentaltype"));
							req.setAttribute("rentalLength", req.getParameter("rentallengthhours"));
							req.setAttribute("vehicleType", req.getParameter("vehicletype"));
							req.setAttribute("location", req.getParameter("location"));
							
							//Create temporary location object
							RentalLocation rl=findLocation(Integer.valueOf(req.getParameter("location")));
							VehicleType vt=findVehicleType(Integer.valueOf(req.getParameter("vehicletype")));
							
							//Set attributes on vehicle page to ones from object
							req.setAttribute("description", vt.getDescription());
							req.setAttribute("dailyRate", vt.getDailyRate());
							req.setAttribute("hourlyRate", vt.getHourlyRate());
							req.setAttribute("locationname", rl.getName());
							req.setAttribute("streetAddrLine1", rl.getLocationAddress().getStreetAddrLine1());
							req.setAttribute("streetAddrLine2", rl.getLocationAddress().getStreetAddrLine2());
							req.setAttribute("city", rl.getLocationAddress().getCity());
							req.setAttribute("state", rl.getLocationAddress().getState());
							req.setAttribute("zipCode", rl.getLocationAddress().getZipCode());
							req.setAttribute("country", rl.getLocationAddress().getCountry());
						
							//Send in the available vehicles from that location
							//req.setAttribute("availableVehicles", getAllVehiclesFrom(Integer.valueOf(req.getParameter("location")), Integer.valueOf(req.getParameter("vehicletype")), returnDate(req.getParameter("pickupmonth"), req.getParameter("pickupday"), req.getParameter("pickupyear")), returnDate(req.getParameter("pickupmonth"), req.getParameter("pickupday"), req.getParameter("pickupyear"))));
							req.setAttribute("availableVehicles", dao.getAllVehicles());
							
							//Forward to reserve vehicle page
							dispatcher=ctx.getRequestDispatcher("/reservevehicle.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							System.out.println("Could not place reservation");
							
							//Reset attributes
							req.setAttribute("locations", getAllLocations());
							req.setAttribute("vehicleTypes", getAllVehicles());
							
							//Forward to reserve vehicle page
							dispatcher=ctx.getRequestDispatcher("/reserve.jsp");
							dispatcher.forward(req, res);
						}
							
							
					}
					
					//If customer submits on reserve vehicle page
					if(req.getParameter("placeReservationFinal")!=null){
						
						///Create the reservation
						try{
							
							boolean hly;
							if(req.getParameter("rentaltype").equals("hourly")){
								System.out.println("hourly is true");
								hly=true;
							}
							else{
								hly=false;
							}
							Reservation r=placeReservation(returnDate(req.getParameter("pickupmonth"), req.getParameter("pickupday"), req.getParameter("pickupyear")), Double.valueOf(req.getParameter("rentallength")), hly, returnDate(req.getParameter("pickupmonth"), req.getParameter("pickupday"), req.getParameter("pickupyear")), Integer.valueOf(req.getParameter("vehicle")), Integer.valueOf(req.getParameter("location")), customer.getId());
							
							System.out.println(r.getPickupTime().getHours());
							
							if(r==null){
								System.out.println("r is null");
							}
							
							//Update Reservation Page
							req.setAttribute("listOfActiveReservations", getAllActiveReservations(customer.getId()));
		
							//Forward to Reservation History page
							dispatcher=ctx.getRequestDispatcher("/history.jsp");
							dispatcher.forward(req, res);
							
						}
						catch(Exception e){
							System.out.println("Could not place reservation!");
							
							//Reset attributes
							req.setAttribute("locations", getAllLocations());
							req.setAttribute("vehicleTypes", getAllVehicles());
							
							//Forward to reserve page
							dispatcher=ctx.getRequestDispatcher("/reserve.jsp");
							dispatcher.forward(req, res);
						}
						
						
				}

					//If the user clicks cancel reservation, remove reservation from linked list
					if(req.getParameter("cancel")!=null){
						System.out.println("Cancelling reservation " + req.getParameter("reservationnumber") + " for " + customer.getUsername());

						//Remove the reservation
						cancelReservation(Integer.valueOf(req.getParameter("reservationnumber")));

						//Update Reservation History Page
						req.setAttribute("listOfActiveReservations", getAllActiveReservations(customer.getId())); //UPDATE ME to pass in the correct linked list

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
						req.setAttribute("listOfActiveReservations", getAllActiveReservations(customer.getId())); //UPDATE ME to pass in the correct linked list
						
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
		//Remove reservation
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
	public LinkedList<Reservation> getAllActiveReservations(int id){
		return dao.getAllReservations(id, false);
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
	public LinkedList<VehicleType> getAllVehicles(){
		return dao.getAllVehicleTypes();
	}
	
	public boolean doTimesConflict(Date startTime1, Date endTime1, Date startTime2, Date endTime2) {
		long start1 = startTime1.getTime();
		long end1 = endTime1.getTime();
		
		long start2 = startTime2.getTime();
		long end2 = endTime2.getTime();
		
		if (start1 <= end2 && end1 >= start2)
		{
			return true;
		}
		
		
		return false; //There is no conflict
	}
	
	public RentalLocation findLocation(int id){
		return dao.readRentalLocation(id);
	}
	
	public VehicleType findVehicleType(int id){
		return dao.readVehicleType(id);
	}
	
	/**
	 * Gets all of the vehicles from a specific location in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<Vehicle> getAllVehiclesFrom(int id, int type, Date start, Date end){
		return dao.getAllAvailableVehicles(id, type, start, end);
	}
	
	public boolean doesReservationConflict(Date start, Date end, int locationId, int VehicleTypeId){
		//Take in start time and check if it is before the start time of each reservation
		//currently placed on all vehicles under a specific vehicle type. If true, 
		return false;
	}
}