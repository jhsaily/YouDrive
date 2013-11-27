package YouDrive_Team11.Servlets.ReservationCtrl;

import java.sql.Date;
import java.util.Currency;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Persistence.YouDriveDAO;


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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		//Passing in our customer object as long as we are still in session
		customer= (Customer)req.getSession().getAttribute("currentCustomer");
		
	}
	
	public void changePrice(Currency price){
		
	}
	
	public void extendMembership(String un, Date newDate){
		dao.updateCustomer(customer.getId(), customer.getFirstName(), customer.getLastName(), newDate);
	}
	
	public void cancelMembership(String un){
		
	}
}
