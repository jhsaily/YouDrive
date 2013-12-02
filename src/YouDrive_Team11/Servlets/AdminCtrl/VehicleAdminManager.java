package YouDrive_Team11.Servlets.AdminCtrl;

import java.io.IOException;
import java.sql.Date;
import java.util.Currency;
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
 * Class that allows the administrator to manage all the different vehicles
 * @author Tanya
 *
 */
public class VehicleAdminManager extends HttpServlet {
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
	public VehicleAdminManager(){
		super();
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
	

				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){
					
					
					//If a user clicks manage vehicle type in nav populate page with types
					if(req.getParameter("clicked").equals("managetype")){
						req.setAttribute("VehicleTypes", getAllVehicleTypes());
						
						//Forward to manage types page
						dispatcher=ctx.getRequestDispatcher("/managevehicletype.jsp");
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
	 * Manages post requests and responses
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
					
					
					//If a user clicks add vehicle type in nav and then hits submit, add to db
					if(req.getParameter("addVehicleType")!=null){
						//In case user enters string into currency slots
						try{
							addVehicleType(req.getParameter("description"), Integer.valueOf(req.getParameter("hourlyRate")), Integer.valueOf(req.getParameter("dailyRate")));
							
							//Forward to vehicle management page
							req.setAttribute("output", "Vehicle type, " + req.getParameter("description") + ", Added.");
							dispatcher=ctx.getRequestDispatcher("/addvehicletype.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							//Forward to vehicle management page
							req.setAttribute("output", "Invalid input");
							dispatcher=ctx.getRequestDispatcher("/addvehicletype.jsp");
							dispatcher.forward(req, res);
						}
					}
					
					//If a user clicks submit after selecting the vehicle type they'd like to edit, forward to the edit page
					if(req.getParameter("manageVehicleType")!=null){
						
						//Create a temporary object for the vehicle type					
						VehicleType vt=getVehicleType(Integer.valueOf(req.getParameter("vehicletype")));
						
						//Set the vehicle type on the edit page to the chosen one with all its attributes
						req.setAttribute("typeDescription", vt.getDescription());
						req.setAttribute("dailyRate", vt.getDailyRate());
						req.setAttribute("hourlyRate", vt.getHourlyRate());
						
						//Forward to vehicle type edit page
						dispatcher=ctx.getRequestDispatcher("/editvehicletype.jsp");
						dispatcher.forward(req, res);
					}
					

					//If a user clicks submit after editing vehicle information, update the information in the db
					if(req.getParameter("updatevehicle")!=null){
						//EDIT ME Idk what the id attr is from the jsp page. It will probably be set beforehand
						editVehicle(Integer.valueOf(req.getParameter("vehicle")), req.getParameter("vehiclemake"), req.getParameter("vehiclemodel"), Integer.valueOf(req.getParameter("vehicleyear")), req.getParameter("vehicletag"), Integer.valueOf(req.getParameter("vehiclemileage")), new Date(00000), req.getParameter("vehiclecondition"), Integer.valueOf(req.getParameter("vehicletype")), 0);
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
	 * Adds a vehicle type to a vehicle in the database
	 * @param description		Description of the vehicle type
	 * @param hourlyRate		The hourly rate of the rental
	 * @param dailyRate			The daily rate of the rental
	 */
	public void addVehicleType(String description, int hourlyRate, int dailyRate){
		dao.createVehicleType(description, (double)hourlyRate, (double)dailyRate);
	}
	
	public VehicleType getVehicleType(int id){
		return dao.readVehicleType(id);
	}
	
	/**
	 * Updates vehicle type information
	 * @param vehicleTypeId		Unique identifier for vehicle type
	 * @param description		Description of vehicle type
	 * @param hourlyRate		The hourly rate of the rental
	 * @param dailyRate			The daily rate of the rental
	 */
	public void editVehicleType(int vehicleTypeId, String description, Currency hourlyRate, Currency dailyRate){
		
	}
	
	/**
	 * Removes a vehicle type from a vehicle in the database
	 * @param vehicleTypeId		Unique identifier for the vehicle type
	 */
	public void removeVehicleType(int vehicleTypeId){
		
	}
	
	/**
	 * Adds a vehicle to the database
	 * @param make				The vehicle make
	 * @param model				The vehicle model
	 * @param year				The year of the vehicle
	 * @param tag				The tag of the vehicle
	 * @param mileage			The number of mileage on the vehicle
	 * @param serviceDate		The sql date of service
	 * @param condition			The condition of the vehicle
	 * @param type				The vehicle type (integer)
	 * @param location			The location (integer)
	 * @return					Returns a vehicle object
	 */
	public Vehicle addVehicle(String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		Vehicle vehicle=dao.createVehicle(make, model, year, tag, mileage, serviceDate, condition, type, location);
		return vehicle;
	}
	
	/**
	 * Updates information about a vehicle
	 * @param make				The vehicle make
	 * @param model				The vehicle model
	 * @param year				The year of the vehicle
	 * @param tag				The tag of the vehicle
	 * @param mileage			The number of mileage on the vehicle
	 * @param serviceDate		The sql date of service
	 * @param condition			The condition of the vehicle
	 * @param type				The vehicle type (integer)
	 * @param location			The location (integer)
	 */
	public void editVehicle(int vehicleId, String make, String model, int year, String tag, int mileage, Date serviceDate, String condition, int type, int location){
		dao.updateVehicle(vehicleId, make, model, year, tag, mileage, serviceDate, condition, type, location);
	}
	
	/**
	 * Removes a vehicle from the database
	 * @param vehicleId		Unique identifier for a vehicle
	 */
	public void removeVehicle(int vehicleId){
		dao.deleteVehicle(vehicleId);
	}
	
	/**
	 * Gets all of the vehicles in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<Vehicle> getAllVehicles(){
		return dao.getAllVehicles();
	}
	
	/**
	 * Gets all of the vehicles in the database
	 * @return		Returns a linked list of all the vehicles
	 */
	public LinkedList<VehicleType> getAllVehicleTypes(){
		return dao.getAllVehicleTypes();
	}
}
