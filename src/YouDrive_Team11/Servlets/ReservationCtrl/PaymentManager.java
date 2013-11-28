package YouDrive_Team11.Servlets.ReservationCtrl;

import java.util.Currency;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages the payments made by a user
 * @author Tanya
 *
 */
public class PaymentManager extends HttpServlet {
	/**
	 * Constructor
	 */
	public PaymentManager(){
		super();
	}
	
	/**
	 * Manages get requests and responses
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Manages post requests and responses
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	/**
	 * Processes a payment
	 * @param amount		Payment amount
	 * @param un			Username of payee
	 */
	public void processPayment(Currency amount, String un){
		
	}
}
