package YouDrive_Team11.Servlets.ReservationCtrl;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	//Declare temporary customer and admin object
	Customer customer=null;
	Administrator admin=null;

	/**
	 * Constructor
	 */
	public MembershipManager(){
		super();
		dao=new YouDriveDAO();
	}

	/**
	 * Responds to get requests and responses
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		//Get Servlet Context and dispatcher
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

					//If the user clicks extend membership
					if(req.getParameter("clicked").equals("extend")){

						//Fill in membership price attribute before loading page
						req.setAttribute("membershipMonthlyPrice", getCurrentMembershipPrice());

						//Fill a date object with this customer's membership info
						Date d=dao.readCustomer(customer.getId()).getMembershipExpiration();

						//If the date object is empty, return false to jsp, else return true
						if(d==null){
							System.out.println("This member DOES NOT have a membership");
							req.setAttribute("hasBeenMember", "false");
						}
						else{
							System.out.println("This member already has a membership");
							req.setAttribute("hasBeenMember", "true");
						}

						//Forward to extend membership page
						dispatcher=ctx.getRequestDispatcher("/extendmembership.jsp");
						dispatcher.forward(req, res);

					}

					//If the user clicks cancel membership
					if(req.getParameter("clicked").equals("cancel")){

						//Update user membership
						cancelMembership(customer.getUsername());

						//Reset session attr
						customer=dao.readCustomer(customer.getId());
						session.setAttribute("currentUser", customer);

						//Forward to login screen
						dispatcher=ctx.getRequestDispatcher("/dashboard.jsp");
						dispatcher.forward(req, res);
					}



				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){
					if(req.getParameter("clicked").equals("managemembership")){

						//Need to be able to get the current membership price to set it in the page
						req.setAttribute("memPrice", getCurrentMembershipPrice());

						//Forward to Manage membership page
						dispatcher=ctx.getRequestDispatcher("/managemembership.jsp");
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
	 * Responds to post requests and responses
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		//Get Servlet Context and dispatcher
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

					//If the user agrees to extend membership
					if(req.getParameter("extend")!=null){
						//Make sure a value is entered before trying anything, if not go back to this page.
						try{
							//Update user membership
							System.out.println("Extending your membership by: " + req.getParameter("months") + " months.");

							//Check if user doesn't have a membership
							if(customer.getMembershipExpiration()==null){

								//If user doesn't have membership and tries to extend for less than 6 months, show error.
								if(Integer.valueOf(req.getParameter("months"))<6){
									req.setAttribute("error", "You must sign up for at least a 6 month membership.");

									//Forward to manage membership
									req.setAttribute("memPrice", getCurrentMembershipPrice());
									dispatcher=ctx.getRequestDispatcher("/extendmembership.jsp");
									dispatcher.forward(req, res);
								}
								else{
									//If more than 6 months is entered, calculate date 6 months from today
									try{
										java.util.Date d=new java.util.Date();
										DateFormat mm=new SimpleDateFormat("MM");
										DateFormat dd=new SimpleDateFormat("dd");
										DateFormat yy=new SimpleDateFormat("yyyy");
										Date currentDate=returnDate(String.valueOf(mm.format(d)), String.valueOf(dd.format(d)), String.valueOf(yy.format(d)));
										Date newDate=getNewDate(currentDate.toString(), Integer.valueOf(req.getParameter("months")));
										extendMembership(customer.getUsername(), newDate);

										try{
											int pmtamt=(int)(Integer.valueOf(req.getParameter("months")) * getCurrentMembershipPrice());

											req.setAttribute("paymentAmount", pmtamt);
											req.setAttribute("paymentReason", "Membership Extension");
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

											//Forward to payment page
											dispatcher=ctx.getRequestDispatcher("/processpayment.jsp");
											dispatcher.forward(req, res);
										}
										catch(Exception e){
											System.out.println("Invalid input or no payment info on file");
											
											//Remove membership
											cancelMembership(customer.getUsername());

											//Reset session attr
											customer=dao.readCustomer(customer.getId());
											session.setAttribute("currentUser", customer);
											
											//Forward to payment fail
											dispatcher=ctx.getRequestDispatcher("/paymentfail.jsp");
											dispatcher.forward(req, res);
										}


									}
									catch(Exception e){
										System.out.println("Couldn't extend membership.");

										//Forward to manage membership
										req.setAttribute("memPrice", getCurrentMembershipPrice());
										dispatcher=ctx.getRequestDispatcher("/extendmembership.jsp");
										dispatcher.forward(req, res);
									}

								}

							}
							else{
								try{
									String currentMemExpirationDate=customer.getMembershipExpiration().toString();
									//String currentMemExpirationDate="2013-11-09";//DELETE ME LATER
									System.out.println("Current Exp Date: " + currentMemExpirationDate);
									Date newDate=getNewDate(currentMemExpirationDate, Integer.valueOf(req.getParameter("months")));
									System.out.println("New date is: " + newDate);
									extendMembership(customer.getUsername(), newDate);
								}
								catch(Exception e){
									System.out.println("Could not get new date");

									//Forward to manage membership
									req.setAttribute("memPrice", getCurrentMembershipPrice());
									dispatcher=ctx.getRequestDispatcher("/extendemembership.jsp");
									dispatcher.forward(req, res);
								}

								//If user has payment information on file
								try{
									int pmtamt=(int)(Integer.valueOf(req.getParameter("months")) * getCurrentMembershipPrice());

									req.setAttribute("paymentAmount", pmtamt);
									req.setAttribute("paymentReason", "Membership Extension");
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

									//Forward to payment page
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
						}
						catch(Exception e){
							//Must enter a number!
							System.out.println("Didn't enter anything!");

							//Forward to payment fail
							dispatcher=ctx.getRequestDispatcher("/extendmembership.jsp");
							dispatcher.forward(req, res);
						}
					}

				}//END CUSTOMER LOGIC

				//ADMIN LOGIC
				else if(admin!=null){

					//If user submits new membership price, change it in the db
					if(req.getParameter("editPrice")!=null){
						changePrice(Integer.valueOf(req.getParameter("price")));

						//Reset the attributes on the page
						req.setAttribute("memPrice", getCurrentMembershipPrice());

						//Forward to change price screen
						req.setAttribute("output", "Price changed.");
						dispatcher=ctx.getRequestDispatcher("/managemembership.jsp");
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
	 * Changes the membership price
	 * @param price		Price of membership
	 */
	public void changePrice(int price){
		dao.setMembershipPrice(price);
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
		Customer c=dao.readCustomer(un);
		dao.revokeMembership(c.getId());
	}

	public Date returnDate(String mm, String dd, String yyyy) throws ParseException{

		java.util.Date utilDate=new SimpleDateFormat("MM/dd/yyyy").parse(mm+"/"+dd+"/"+yyyy);
		java.sql.Date date=new java.sql.Date(utilDate.getTime());

		return date;
	}

	public Date getNewDate(String oldDate, int months) throws ParseException{

		//Get the year, months, and day from the sql date yyyy-mm-dd
		String y=oldDate.substring(0,4);
		System.out.println("Year: " + y);
		String m=oldDate.substring(5,7);
		String d=oldDate.substring(8,10);

		//Get the integer value of months and year (in case months surpass year)
		Integer newm=Integer.valueOf(m) + months;
		Integer newy=Integer.valueOf(y);

		//If the new number of months is more than 12, change to a month number and increase the year
		while(newm>12){
			newm=newm-12;
			newy=newy+1;
		}

		//Find the sql date of the new numbers
		return returnDate(newm.toString(), d, newy.toString());

	}

	public double getCurrentMembershipPrice(){
		return dao.getMembershipPrice();
	}
}

