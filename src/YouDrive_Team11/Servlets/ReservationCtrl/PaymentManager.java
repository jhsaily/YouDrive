package YouDrive_Team11.Servlets.ReservationCtrl;

import java.io.IOException;
import java.util.Currency;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.Administrator;
import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Entity.Vehicle;
import YouDrive_Team11.Persistence.YouDriveDAO;

/**
 * Manages the payments made by a user
 * @author Tanya
 *
 */
public class PaymentManager extends HttpServlet {
	
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
	public PaymentManager(){
		super();
		YouDriveDAO dao=new YouDriveDAO();
	}
	
	/**
	 * Manages get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
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
					
					//If a payment is successfully processed
					if(req.getParameter("confirm")!=null){
						
						//Forward to payment success
						dispatcher=ctx.getRequestDispatcher("/paymentsuccess.jsp");
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
	 * Processes a payment
	 * @param amount		Payment amount
	 * @param un			Username of payee
	 */
	public void processPayment(Currency amount, String un){
		
	}
}
