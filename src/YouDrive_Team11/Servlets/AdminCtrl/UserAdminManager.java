package YouDrive_Team11.Servlets.AdminCtrl;

import java.io.IOException;

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
 * Class that manages administrator accessibility
 * @author Tanya
 *
 */
public class UserAdminManager extends HttpServlet{
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
	public UserAdminManager(){
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
					
					//If user clicks search for customer under user management
					if(req.getParameter("searchUser")!=null){
						
						//Make sure nothing funny was entered by putting this in a try catch
						try{
							//Get the object associated with this username
							Customer c=manageProfile(req.getParameter("username"));
							req.setAttribute("userName", c.getUsername());
							req.setAttribute("firstName", c.getFirstName());
							req.setAttribute("lastName", c.getLastName());
							req.setAttribute("emailAddress", c.getEmailAddress());
							req.setAttribute("addrLine1", c.getMailingAddress().getStreetAddrLine1());
							req.setAttribute("addrLine2", c.getMailingAddress().getStreetAddrLine2());
							req.setAttribute("city", c.getMailingAddress().getCity());
							req.setAttribute("state", c.getMailingAddress().getState());
							req.setAttribute("zip", c.getMailingAddress().getZipCode());
							req.setAttribute("country", c.getMailingAddress().getCountry());
							req.setAttribute("licenseNumber", c.getDriversLicense().getLicenseNumber());
							req.setAttribute("licenseState", c.getDriversLicense().getLicenseState());
						

							//Forward to edit user screen
							dispatcher=ctx.getRequestDispatcher("/edituser.jsp");
							dispatcher.forward(req, res);
						}
						catch(Exception e){
							System.out.println("Invalid entry for edit user");
							
							//Forward to manage users page
							dispatcher=ctx.getRequestDispatcher("/manageusers.jsp");
							dispatcher.forward(req, res);
						}
						
						
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
	 * Allows the admin to edit a customer's profile
	 * @param un		Customer's username
	 * @return			Returns a Customer object
	 */
	public Customer manageProfile(String un){
		return dao.readCustomer(un);
		
	}
	
	/**
	 * Removes a user from the database
	 * @param un		The customer's username
	 */
	public void removeUser(String un){
		Customer cust=dao.readCustomer(un);
		dao.deleteCustomer(cust.getId());
	}
}
