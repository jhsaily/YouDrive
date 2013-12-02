package YouDrive_Team11.Servlets.AdminCtrl;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import YouDrive_Team11.Persistence.*;
import YouDrive_Team11.Entity.*;

/**
 * Admin uses this class to manage the current rental locations
 * @author Tanya
 *
 */
public class LocationAdminManager extends HttpServlet{
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Declare temporary objects
	Customer customer=null;
	Administrator admin=null;
	Vehicle vehicle;
	
	/**
	 * Constructor
	 */
	public LocationAdminManager(){
		super();
		
		//Create DAO
		dao=new YouDriveDAO();
	}
	
	/**
	 * Handles get requests and responses
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
	

				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){
					//If the user clicks manage locations, populate the page with all locations
					if(req.getParameter("clicked").equals("manage")){
						req.setAttribute("locations", getAllLocations());
						
						//Forward to manage locations Page
						dispatcher=ctx.getRequestDispatcher("/managelocation.jsp");
						dispatcher.forward(req, res);
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
	 * Handles post requests and responses
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("POST!");

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
	

				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){
					
					//If user clicks add a location under vehicle management
					if(req.getParameter("addLocation")!=null){
						try{
							addRentalLocation(req.getParameter("locationname"), Integer.valueOf(req.getParameter("capacity")), req.getParameter("addressline1"), req.getParameter("addressline2"), req.getParameter("city"), req.getParameter("state"), Integer.valueOf(req.getParameter("zip")), req.getParameter("country"));
							
							//Forward back to add location page
							req.setAttribute("output", "Location, " + req.getParameter("locationname")+", Added!");
							dispatcher=ctx.getRequestDispatcher("/addlocation.jsp");
							dispatcher.forward(req, res);
						}
						catch (Exception e){
							req.setAttribute("output", "Invalid Input");
							System.out.println("Invalid Input");
							dispatcher=ctx.getRequestDispatcher("/addlocation.jsp");
							dispatcher.forward(req, res);
						}
					}
					
					//If a user clicks submit after selecting a location to manage, send them to the vehicles at that location
					if(req.getParameter("manageLocation")!=null){
						
						//EDIT Return vehicles from location selected
						//UNCOMMENT ME req.setAttribute("vehicles", getAllVehicles(location id));
						req.setAttribute("vehicles", getAllVehicles());
						
						//Forward to vehicle management page
						dispatcher=ctx.getRequestDispatcher("/managevehicle.jsp");
						dispatcher.forward(req, res);
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
	 * Adds a rental location to the database
	 * @param name			Name of the location
	 * @param capacity		Number of cars location holds
	 * @param addrLine1		Address of location
	 * @param addrLine2		Address of location
	 * @param city			City of location
	 * @param state			State of location
	 * @param zip			Zip code of location
	 * @param country		Country of location
	 * @return 				Returns a RentalLocation object
	 */
	public RentalLocation addRentalLocation(String name, int capacity, String addrLine1, String addrLine2, String city, String state, int zip, String country){
		RentalLocation location= dao.createRentalLocation(name, capacity, addrLine1, addrLine2, city, state, zip, country);

		return location;
	}
	
	/**
	 * Allows the admin to edit a specific location
	 * @param locationId		Unique identifier for location
	 * @param name				Name of location
	 * @param capacity			Number of cars it should hold
	 */
	public void editRentalLocation(int locationId, String name, int capacity){
		dao.updateRentalLocation(locationId, name, capacity);
	}
	
	/**
	 * Removes a location from the database
	 * @param locationId		Unique identifier for location
	 */
	public void removeRentalLocation(int locationId){
		dao.deleteRentalLocation(locationId);
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
	//EDIT Eventually must return vehicles AT A PARTICULAR LOCATION
	public LinkedList<Vehicle> getAllVehicles(){
		return dao.getAllVehicles();
	}
}
