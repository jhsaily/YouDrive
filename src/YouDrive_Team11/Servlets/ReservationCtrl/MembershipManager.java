package YouDrive_Team11.Servlets.ReservationCtrl;

import java.sql.Date;
import java.util.Currency;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Persistence.YouDriveDAO;

/**
 * Class that manages the membership details for YouDrive
 * @author Tanya
 *
 */
public class MembershipManager extends HttpServlet{
	/**
	 * Variables
	 */
	
	//Declare session object
	HttpSession session;
	
	//Declare DAO
	YouDriveDAO dao;
	
	//Declare temporary customer object
	Customer customer=null;
		
	/**
	 * Constructor
	 */
	public MembershipManager(){
		super();
		dao=new YouDriveDAO();
	}
	
	/**
	 * Responds to get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Responds to post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		//Passing in our customer object as long as we are still in session
		customer= (Customer)req.getSession().getAttribute("currentCustomer");
		
	}
	
	/**
	 * Changes the membership price
	 * @param price		Price of membership
	 */
	public void changePrice(Currency price){
		
	}
	
	/**
	 * Extends the membership of a user
	 * @param un		Username
	 * @param newDate	The new expiration date
	 */
	public void extendMembership(String un, Date newDate){
		dao.updateCustomer(customer.getId(), customer.getFirstName(), customer.getLastName(), newDate);
	}
	
	/**
	 * Cancels a user's membership
	 * @param un		Username
	 */
	public void cancelMembership(String un){
		
	}
}
