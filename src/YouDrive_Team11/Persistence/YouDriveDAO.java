package YouDrive_Team11.Persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import YouDrive_Team11.Entity.Address;
import YouDrive_Team11.Entity.Administrator;
import YouDrive_Team11.Entity.Comment;
import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Entity.DriversLicense;
import YouDrive_Team11.Entity.PaymentInfo;
import YouDrive_Team11.Entity.RentalLocation;
import YouDrive_Team11.Entity.Reservation;
import YouDrive_Team11.Entity.User;
import YouDrive_Team11.Entity.Vehicle;
import YouDrive_Team11.Entity.VehicleType;

/**
 * The persistence class for the YouDrive system.
 * @author David Stapleton
 *
 */
public class YouDriveDAO {
	
	private PreparedStatement insertCustomerStatement;
	private PreparedStatement readCustomerStatement;
	private PreparedStatement getCustomerByUsernameStatement;
	private PreparedStatement updateCustomerStatement;
	private PreparedStatement revokeMembershipStatement;
	private PreparedStatement deleteCustomerStatement;
	private PreparedStatement getAllCustomersStatement;
	
	private PreparedStatement changeUsernameStatement;
	private PreparedStatement changePasswordStatement;
	private PreparedStatement changeEmailAddressStatement;
	
	private PreparedStatement authenticateUserStatement;
	
	private PreparedStatement readAddressStatement;
	private PreparedStatement updateAddressStatement;
	private PreparedStatement insertAddressStatement;
	
	private PreparedStatement readPaymentInfoStatement;
	private PreparedStatement insertPaymentInfoStatement;
	private PreparedStatement updatePaymentInfoStatement;
	
	private PreparedStatement insertDLStatement;
	private PreparedStatement updateDLStatement;
	private PreparedStatement readDLStatement;
	
	private PreparedStatement insertAdminStatement;
	private PreparedStatement readAdminStatement;
	private PreparedStatement updateAdminStatement;
	private PreparedStatement deleteAdminStatement;
	private PreparedStatement getAllAdminsStatement;
	
	private PreparedStatement insertVehicleTypeStatement;
	private PreparedStatement readVehicleTypeStatement;
	private PreparedStatement updateVehicleTypeStatement;
	private PreparedStatement deleteVehicleTypeStatement;
	private PreparedStatement getAllVehicleTypesStatement;
	
	private PreparedStatement insertCommentStatement;
	private PreparedStatement getCommentsStatement;
	
	private PreparedStatement insertVehicleStatement;
	private PreparedStatement readVehicleStatement;
	private PreparedStatement updateVehicleStatement;
	private PreparedStatement deleteVehicleStatement;
	private PreparedStatement getAllVehiclesStatement;
	private PreparedStatement getAllVehiclesAtLocationStatement;
	private PreparedStatement getAllVehiclesOfTypeAtLocationStatement;
	private PreparedStatement getAllAvailableVehiclesStatement;
	private PreparedStatement getAllAvailableVehiclesAtLocationStatement;
	private PreparedStatement getAllAvailableVehiclesOfTypeAtLocationStatement;
	private PreparedStatement getVehicleNotConflictingWithTimesStatement;
	
	private PreparedStatement insertRentalLocationStatement;
	private PreparedStatement readRentalLocationStatement;
	private PreparedStatement updateRentalLocationStatement;
	private PreparedStatement deleteRentalLocationStatement;
	private PreparedStatement getAllRentalLocationsStatement;
	
	private PreparedStatement getMembershipPriceStatement;
	private PreparedStatement setMembershipPriceStatement;
	
	private PreparedStatement insertReservationStatement;
	private PreparedStatement readReservationStatement;
	private PreparedStatement updateReservationStatement;
	private PreparedStatement closeReservationStatement;
	private PreparedStatement deleteReservationStatement;
	private PreparedStatement getAllReservationsStatement;
	private PreparedStatement getAllActiveReservationsStatement;
	
	private String availableVehiclesString = "SELECT vehicles.* " +
			"FROM vehicles " +
			"WHERE vehicles.location_id=? AND " +
			"vehicles.type_id=? AND " +
			"vehicles.id NOT IN (" +
			"SELECT vehicles.id FROM vehicles,reservations WHERE " +
			"reservations.timeReturned IS NULL AND " +
			"reservations.vehicle_id = vehicles.id AND " +
			"((pickupTime <= ? AND timeDue >= ?) OR " +
			"(pickupTime <= ? AND timeDue >= ?) OR " +
			"(pickupTime > ? AND timeDue < ?))" +
			")";
	private String isVehicleAvailableString = "SELECT vehicles.* " +
			"FROM vehicles " +
			"WHERE vehicles.id=? AND " +
			"vehicles.id NOT IN(" +
			"SELECT vehicles.id FROM vehicles,reservations WHERE " +
			"reservations.timeReturned IS NULL AND " +
			"reservations.vehicle_id = vehicles.id AND " +
			"((pickupTime <= ? AND timeDue >= ?) OR " +
			"(pickupTime <= ? AND timeDue >= ?) OR " +
			"(pickupTime > ? AND timeDue < ?))" +
			")";
	
	/**
	 * Creates an instance of the persistence class
	 */
	public YouDriveDAO(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Instantiated MySQL driver!");
			Connection conn = DriverManager.getConnection("jdbc:mysql://uml.cs.uga.edu/team11","team11","virtual");
			System.out.println("Connected to MySQL!");
			insertCustomerStatement = conn.prepareStatement("insert into users (username,password,isAdmin,emailAddress,firstName,lastName)" +
					" values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			readCustomerStatement = conn.prepareStatement("select * from users where id=? and isAdmin=0");
			getCustomerByUsernameStatement = conn.prepareStatement("select * from users where username=?");
			updateCustomerStatement = conn.prepareStatement("update users set firstName=?," +
					"lastName=?,membershipExpiration=? where id=? and isAdmin=0");
			revokeMembershipStatement = conn.prepareStatement("update users set membershipExpiration=NULL where id=? and isAdmin=0");
			deleteCustomerStatement = conn.prepareStatement("delete from users where id=? and isAdmin=0");
			getAllCustomersStatement = conn.prepareStatement("select * from users where isAdmin=0");
			
			changeUsernameStatement = conn.prepareStatement("update users set username=? where isAdmin=0 and id=?");
			changePasswordStatement = conn.prepareStatement("update users set password=? where isAdmin=0 and id=?");
			changeEmailAddressStatement = conn.prepareStatement("update users set emailAddress=? where isAdmin=0 and id=?");
			
			authenticateUserStatement = conn.prepareStatement("select * from users where username=? and password=?");
			
			readAddressStatement = conn.prepareStatement("select * from addresses where link_id=? and type=?");
			updateAddressStatement = conn.prepareStatement("update addresses set addrLine1=?,addrLine2=?,city=?,state=?,ZIP=?," +
					"country=? where id=?");
			insertAddressStatement = conn.prepareStatement("insert into addresses (addrLine1,addrLine2,city,state,ZIP,country,link_id,type)" +
					" values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			readPaymentInfoStatement = conn.prepareStatement("select * from payment_methods where user_id=?");
			updatePaymentInfoStatement = conn.prepareStatement("update payment_methods set cardNumber=?,monthExpiration=?," +
					"yearExpiration=?,securityCode=? where user_id=?");
			insertPaymentInfoStatement = conn.prepareStatement("insert into payment_methods (cardNumber,monthExpiration," +
					"yearExpiration,securityCode,user_id) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			readDLStatement = conn.prepareStatement("select * from dl where customer_id=?");
			updateDLStatement = conn.prepareStatement("update dl set dl_number=?,dl_state=? where customer_id=?");
			insertDLStatement = conn.prepareStatement("insert into dl (dl_number,dl_state,customer_id)" +
					" values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			insertAdminStatement = conn.prepareStatement("insert into users (username,password,isAdmin) values" +
					" (?,?,1)", Statement.RETURN_GENERATED_KEYS);
			readAdminStatement = conn.prepareStatement("select * from users where id=? and isAdmin=1");
			updateAdminStatement = conn.prepareStatement("update users set username=?,password=?" +
					" where id=? and isAdmin=1");
			deleteAdminStatement = conn.prepareStatement("delete from users where id=? and isAdmin=1");
			getAllAdminsStatement = conn.prepareStatement("select * from users where isAdmin=1");
			
			insertVehicleTypeStatement = conn.prepareStatement("insert into vehicle_types (description, hourlyRate," +
					"dailyRate) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			readVehicleTypeStatement = conn.prepareStatement("select * from vehicle_types where id=?");
			updateVehicleTypeStatement = conn.prepareStatement("update vehicle_types set description=?,hourlyRate=?," +
					"dailyRate=? where id=?");
			deleteVehicleTypeStatement = conn.prepareStatement("delete from vehicle_types where id=?");
			getAllVehicleTypesStatement = conn.prepareStatement("select * from vehicle_types");
			
			insertCommentStatement = conn.prepareStatement("insert into comments (content, vehicle_id) " +
					"values (?,?)", Statement.RETURN_GENERATED_KEYS);
			getCommentsStatement = conn.prepareStatement("select * from comments where vehicle_id=?");
			
			insertVehicleStatement = conn.prepareStatement("insert into vehicles (make,model,year,tag,mileage," +
					"serviceDate,vehicleCondition,type_id,location_id) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			readVehicleStatement = conn.prepareStatement("select * from vehicles where id=?");
			updateVehicleStatement = conn.prepareStatement("update vehicles set make=?,model=?,year=?,tag=?," +
					"mileage=?,serviceDate=?,vehicleCondition=?,type_id=?,location_id=? where id=?");
			deleteVehicleStatement = conn.prepareStatement("delete from vehicles where id=?");
			getAllVehiclesStatement = conn.prepareStatement("select * from vehicles");
			getAllVehiclesAtLocationStatement = conn.prepareStatement("select * from vehicles where location_id=?");
			getAllVehiclesOfTypeAtLocationStatement = conn.prepareStatement("select * from vehicles" +
					" where location_id=? and type_id=?");
			getAllAvailableVehiclesStatement = conn.prepareStatement("select * from vehicles");
			getAllAvailableVehiclesAtLocationStatement = conn.prepareStatement("select * from vehicles where location_id=?");
			//getAllAvailableVehiclesOfTypeAtLocationStatement = conn.prepareStatement("select * from vehicles" +
				//	" where location_id=? and type_id=? and isAvailable=1 and ");
			getAllAvailableVehiclesOfTypeAtLocationStatement = conn.prepareStatement(availableVehiclesString);
			getVehicleNotConflictingWithTimesStatement = conn.prepareStatement(isVehicleAvailableString);
			
			insertRentalLocationStatement = conn.prepareStatement("insert into locations (name,capacity) values " +
					"(?,?)", Statement.RETURN_GENERATED_KEYS);
			readRentalLocationStatement = conn.prepareStatement("select * from locations where id=?");
			updateRentalLocationStatement = conn.prepareStatement("update locations set name=?,capacity=?" +
					" where id=?");
			deleteRentalLocationStatement = conn.prepareStatement("delete from locations where id=?");
			getAllRentalLocationsStatement = conn.prepareStatement("select * from locations");
			
			getMembershipPriceStatement = conn.prepareStatement("select * from context where id=1");
			setMembershipPriceStatement = conn.prepareStatement("update context set membershipPrice=? where id=1");
			
			insertReservationStatement = conn.prepareStatement("insert into reservations (pickupTime,rentalDuration," +
					"isHourly,timeDue,vehicle_id,location_id,customer_id) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			readReservationStatement = conn.prepareStatement("select * from reservations where id=?");
			closeReservationStatement = conn.prepareStatement("update reservations set timeReturned=NOW() where id=?");
			getAllReservationsStatement = conn.prepareStatement("select * from reservations where customer_id=?");
			getAllActiveReservationsStatement = conn.prepareStatement("select * from reservations where customer_id=? and (timeReturned IS NULL or timeReturned='')");
			deleteReservationStatement = conn.prepareStatement("delete from reservations where id=?");
			
			//addCustomerStatement = conn.prepareStatement("insert into Customer (custName,custAddr,imageURL,creditLimit) values (?,?,?,?)");
			//updateUnpaidBalanceStatement = conn.prepareStatement("update Customer set unpaidBalance = ? where id=?");
		
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Adds a customer to the database and returns an instance of a Customer object
	 * @param username				The username of the customer
	 * @param password				The password of the customer
	 * @param emailAddress			The email address of the customer
	 * @param firstName				The customer's first name
	 * @param lastName				The customer's last name
	 * @param membershipExpiration	The customer's membership expiration date
	 * @param addrLine1				The first line of the customer's address
	 * @param addrLine2				The second line of the customer's address
	 * @param city					The city of the customer's address
	 * @param state					The state of the customer's address
	 * @param ZIP					The ZIP code of the customer's address
	 * @param country				The country of the customer's address
	 * @param DLNumber				The driver's license number of the customer
	 * @param DLState				The driver's license state of the customer
	 * @return	A customer object, encapsulating all the customer's data
	 */
	public Customer createCustomer(String username, String password, String emailAddress,
			String firstName, String lastName,
			String addrLine1, String addrLine2, String city, String state,
			int ZIP, String country, String DLNumber, String DLState){
		Customer customer = null;
		try{
			insertCustomerStatement.setString(1, username);
			insertCustomerStatement.setString(2, password);
			insertCustomerStatement.setBoolean(3, false);
			insertCustomerStatement.setString(4, emailAddress);
			insertCustomerStatement.setString(5, firstName);
			insertCustomerStatement.setString(6, lastName);
			insertCustomerStatement.executeUpdate();
			ResultSet key = insertCustomerStatement.getGeneratedKeys();
			key.next();
			int id = key.getInt(1);
			Address address = updateAddressForCustomer(id, addrLine1,addrLine2,city,state,ZIP,country);
			DriversLicense license = updateDLForCustomer(id, DLNumber, DLState);
			PaymentInfo info = getPaymentInfoForCustomer(id);
			customer = new Customer(id, username, password, emailAddress, firstName, lastName, null, license,address, info);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
	/**
	 * Retrieves a customer from the YouDrive system.
	 * @param custID	The unique id of the customer
	 * @return			A Customer represented by the unique id.
	 */
	public Customer readCustomer(int custID){
		Customer customer = null;
		try{
			readCustomerStatement.setInt(1, custID);
			ResultSet rs = readCustomerStatement.executeQuery();
			if(rs.next()){
				DriversLicense license = getDLForCustomer(custID);
				Address address = getAddressForCustomer(custID);
				PaymentInfo info = getPaymentInfoForCustomer(custID);
				customer = new Customer(custID, rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"), license, address, info);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
	/**
	 * Retrieves a customer from the YouDrive system from a username.
	 * @param username	The username of the user.
	 * @return			A Customer represented by the username.
	 */
	public Customer readCustomer(String username){
		Customer customer = null;
		try{
			getCustomerByUsernameStatement.setString(1, username);
			ResultSet rs = getCustomerByUsernameStatement.executeQuery();
			if(rs.next()){
				int custID = rs.getInt("id");
				DriversLicense license = getDLForCustomer(custID);
				Address address = getAddressForCustomer(custID);
				PaymentInfo info = getPaymentInfoForCustomer(custID);
				customer = new Customer(custID, rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"), license, address, info);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
	/**
	 * Updates the info for a customer identified by the supplied unique identifier.
	 * @param custID				The unique identifier of the customer to update
	 * @param firstName				The new first name
	 * @param lastName				The new last name
	 * @param membershipExpiration	The new membership expiration date
	 */
	public void updateCustomer(int custID, String firstName,
			String lastName, Date membershipExpiration){
		try{
			updateCustomerStatement.setString(1, firstName);
			updateCustomerStatement.setString(2, lastName);
			updateCustomerStatement.setDate(3, membershipExpiration);
			updateCustomerStatement.setInt(4, custID);
			updateCustomerStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Revokes the membership of the specified user
	 * @param customerID	The unique identifier of the user whose membership is being revoked.
	 */
	public void revokeMembership(int customerID){
		try{
			revokeMembershipStatement.setInt(1, customerID);
			revokeMembershipStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes the customer identified by the specified unique identifier
	 * from the YouDrive system
	 * @param custID	The unique identifier of the customer being deleted
	 */
	public void deleteCustomer(int custID){
		// First, delete the DL for this customer from the DL table.
		// Then, delete the mailing address for this customer from the Address table
		// What about reservations?
		try{
			deleteCustomerStatement.setInt(1, custID);
			deleteCustomerStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of all customers in the YouDrive system.
	 * @return	A list of YouDrive customers.
	 */
	public LinkedList<Customer> getAllCustomers(){
		LinkedList<Customer> list = new LinkedList<Customer>();
		try{
			ResultSet rs = getAllCustomersStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				DriversLicense license = getDLForCustomer(id);
				Address address = getAddressForCustomer(id);
				PaymentInfo info = getPaymentInfoForCustomer(id);
				Customer customer = new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"), license, address, info);
				list.add(customer);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Changes the username for the specfied user. Returns false if change is unsuccessful.
	 * @param userID		The unique identifier of the user who's username we're changing
	 * @param newUsername	The username we are changing to
	 * @return				True if successful, false if unsuccessful (username is taken)
	 */
	public boolean changeUsername(int userID, String newUsername){
		boolean success = false;
		try{
			changeUsernameStatement.setString(1, newUsername);
			changeUsernameStatement.setInt(2, userID);
			changeUsernameStatement.executeUpdate();
			success = true;
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return success;
	}
	
	/**
	 * Changes the password for the specified customer. Returns false if change is unsuccessful.
	 * @param userID		The unique identifier of the user who's password we're changing
	 * @param newPassword	The password we are changing to
	 * @return				True if successful, false if not.
	 */
	public boolean changePassword(int userID, String newPassword){
		boolean success = false;
		try{
			changePasswordStatement.setString(1, newPassword);
			changePasswordStatement.setInt(2, userID);
			changePasswordStatement.executeUpdate();
			success = true;
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return success;
	}
	
	/**
	 * Changes the email address for the specified user. Returns false if change is unsuccessful
	 * @param userID			The unique identifier of the user who's email address we're changing
	 * @param newEmailAddress	The email address we are changing to
	 * @return					True if successful, false if unsuccessful (email address is taken)
	 */
	public boolean changeEmailAddress(int userID, String newEmailAddress){
		boolean success = false;
		try{
			changeEmailAddressStatement.setString(1, newEmailAddress);
			changeEmailAddressStatement.setInt(2, userID);
			changeEmailAddressStatement.executeUpdate();
			success = true;
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return success;
	}
	
	/**
	 * Authenticates a user to the YouDrive system
	 * @param username	The username for the user
	 * @param password	The password for the user
	 * @return			A user object upon successful login. Null if unsuccessful.
	 */
	public User authenticateUser(String username, String password){
		User user = null;
		try{
			authenticateUserStatement.setString(1, username);
			authenticateUserStatement.setString(2, password);
			ResultSet rs = authenticateUserStatement.executeQuery();
			if(rs.next()){
				boolean isAdmin = rs.getBoolean("isAdmin");
				if(isAdmin){
					user = readAdministrator(rs.getInt("id"));
				}else{
					user = readCustomer(rs.getInt("id"));
				}
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return user;
	}
	
	/**
	 * If an address exists for a customer, it is updated with the new information.
	 * If there isn't an address for a customer, one is added to the database.
	 * @param id			The unique identifier of the customer to assign this address to
	 * @param addrLine1		The first line of the address
	 * @param addrLine2		The second line of the address
	 * @param city			The city of the address
	 * @param state			The state of the address
	 * @param ZIP			The zip code of the address
	 * @param country		The country of the address
	 * @return				An address object with the updated information.
	 */
	public Address updateAddressForCustomer(int id, String addrLine1, String addrLine2,
			String city, String state, int ZIP, String country){
		Address address = getAddressForCustomer(id);
		int modifiedID = 0;
		try{
			if(address != null){
				// This customer already has an address. Let's update it.
				updateAddressStatement.setString(1, addrLine1);
				updateAddressStatement.setString(2, addrLine2);
				updateAddressStatement.setString(3, city);
				updateAddressStatement.setString(4, state);
				updateAddressStatement.setInt(5, ZIP);
				updateAddressStatement.setString(6, country);
				updateAddressStatement.setInt(7, address.getId());
				updateAddressStatement.executeUpdate();
				modifiedID = address.getId();
			}else{
				// This customer does not have an address. Let's make one.
				insertAddressStatement.setString(1, addrLine1);
				insertAddressStatement.setString(2, addrLine2);
				insertAddressStatement.setString(3, city);
				insertAddressStatement.setString(4, state);
				insertAddressStatement.setInt(5, ZIP);
				insertAddressStatement.setString(6, country);
				insertAddressStatement.setInt(7, id);
				insertAddressStatement.setString(8, "customer");
				insertAddressStatement.executeUpdate();
				ResultSet key = insertAddressStatement.getGeneratedKeys();
				key.next();
				modifiedID = key.getInt(1);
			}
			address = new Address(modifiedID, addrLine1, addrLine2, city, state, ZIP, country);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * Returns the address associated with a customer
	 * @param custID	The unique identifier of the customer the address is associated with
	 * @return			The address for the customer
	 */
	private Address getAddressForCustomer(int custID){
		Address address = null;
		int id = 0;
		try{
			readAddressStatement.setInt(1, custID);
			readAddressStatement.setString(2, "customer");
			ResultSet rs = readAddressStatement.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
				address = new Address(id, rs.getString("addrLine1"), rs.getString("addrLine2"),
						rs.getString("city"), rs.getString("state"),
						rs.getInt("ZIP"), rs.getString("country"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * Adds payment info for a customer.
	 * @param customerID		The unique identifier of the customer for which we are adding payment information
	 * @param cardNumber		The card number
	 * @param expirationMonth	The card's month of expiry
	 * @param expirationYear	The card's year of expiry
	 * @param securityCode		The card's security code
	 * @param addrLine1			The first line of the card's billing address
	 * @param addrLine2			The second line of the card's billing address
	 * @param city				The city of the card's billing address
	 * @param state				The state of the card's billing address
	 * @param ZIP				The ZIP code of the card's billing address
	 * @param country			The country of the card's billing address
	 * @return					A PaymentInfo object encapsulating all this new data.
	 */
	public PaymentInfo addPaymentInfoForCustomer(int customerID, String cardNumber, int expirationMonth,
			int expirationYear, int securityCode, String addrLine1, String addrLine2, String city, String state,
			int ZIP, String country){
		PaymentInfo info = null;
		int paymentID = 0;
		try{
			insertPaymentInfoStatement.setString(1, cardNumber);
			insertPaymentInfoStatement.setInt(2, expirationMonth);
			insertPaymentInfoStatement.setInt(3, expirationYear);
			insertPaymentInfoStatement.setInt(4, securityCode);
			insertPaymentInfoStatement.setInt(5, customerID);
			insertPaymentInfoStatement.executeUpdate();
			ResultSet key = insertPaymentInfoStatement.getGeneratedKeys();
			key.next();
			paymentID = key.getInt(1);
			Address paymentAddress = updateAddressForPaymentInfo(paymentID, addrLine1,
					addrLine2, city, state, ZIP, country);
			info = new PaymentInfo(paymentID, cardNumber,
					expirationMonth, expirationYear, securityCode, paymentAddress);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return info;
	}
	
	/**
	 * Updates the payment information associated with a YouDrive customer
	 * @param customerID		The unique identifier of the customer for which we are updating payment info
	 * @param cardNumber		The new card number
	 * @param expirationMonth	The new card's month of expiration
	 * @param expirationYear	The new card's year of expiration
	 * @param securityCode		The new card's security code
	 */
	public void updatePaymentInfoForCustomer(int customerID, String cardNumber, int expirationMonth,
			int expirationYear, int securityCode){
		try{
			updatePaymentInfoStatement.setString(1, cardNumber);
			updatePaymentInfoStatement.setInt(2, expirationMonth);
			updatePaymentInfoStatement.setInt(3, expirationYear);
			updatePaymentInfoStatement.setInt(4, securityCode);
			updatePaymentInfoStatement.setInt(5, customerID);
			updatePaymentInfoStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns the payment info associated with a customer.
	 * This is a helper method. It is called from readCustomer, so a
	 * customer object already contains a PaymentInfo object.
	 * @param customerID	The unique identifier of the customer for which we want payment info
	 * @return	A PaymentInfo object with data for a given customer
	 */
	private PaymentInfo getPaymentInfoForCustomer(int customerID){
		PaymentInfo info = null;
		try{
			readPaymentInfoStatement.setInt(1, customerID);
			ResultSet rs = readPaymentInfoStatement.executeQuery();
			if(rs.next()){
				Address address = getAddressForPaymentInfo(rs.getInt("id"));
				info = new PaymentInfo(rs.getInt("id"), rs.getString("cardNumber"),
						rs.getInt("monthExpiration"), rs.getInt("yearExpiration"),
						rs.getInt("securityCode"), address);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return info;
	}
	
	/**
	 * If an address already exists for given payment info, it is updated.
	 * If not, a new address is created in the YouDrive system for the payment data.
	 * @param payment_id	The unique identifier of the payment information for which this address should be linked
	 * @param addrLine1		The first line of the card's billing address
	 * @param addrLine2		The second line of the card's billing address
	 * @param city			The city of the card's billing address
	 * @param state			The state of the card's billing address
	 * @param ZIP			The ZIP code of the card's billing address
	 * @param country		The country of the card's billing address
	 * @return				An address object containing the address for a given payment info
	 */
	public Address updateAddressForPaymentInfo(int payment_id, String addrLine1, String addrLine2,
			String city, String state, int ZIP, String country){
		Address address = getAddressForPaymentInfo(payment_id);
		int modifiedID = 0;
		try{
			if(address != null){
				// This customer already has an address. Let's update it.
				updateAddressStatement.setString(1, addrLine1);
				updateAddressStatement.setString(2, addrLine2);
				updateAddressStatement.setString(3, city);
				updateAddressStatement.setString(4, state);
				updateAddressStatement.setInt(5, ZIP);
				updateAddressStatement.setString(6, country);
				updateAddressStatement.setInt(7, address.getId());
				updateAddressStatement.executeUpdate();
				modifiedID = address.getId();
			}else{
				// This customer does not have an address. Let's make one.
				insertAddressStatement.setString(1, addrLine1);
				insertAddressStatement.setString(2, addrLine2);
				insertAddressStatement.setString(3, city);
				insertAddressStatement.setString(4, state);
				insertAddressStatement.setInt(5, ZIP);
				insertAddressStatement.setString(6, country);
				insertAddressStatement.setInt(7, payment_id);
				insertAddressStatement.setString(8, "payment");
				insertAddressStatement.executeUpdate();
				ResultSet key = insertAddressStatement.getGeneratedKeys();
				key.next();
				modifiedID = key.getInt(1);
			}
			address = new Address(modifiedID, addrLine1, addrLine2, city, state, ZIP, country);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * Helper method. Returns an address for a given payment info.
	 * PaymentInfo objects already have the address encapsulated within.
	 * Instead, use customer.getPaymentInfo().getBillingAddress().getCertainAttribute() or 
	 * ${customer.paymentInfo.billingAddress.certainAttribute}
	 * @param paymentID		The unique identifier for the payment info for which we want the billing address
	 * @return				An address object encapsulating the billing address for a given payment method.
	 */
	private Address getAddressForPaymentInfo(int paymentID){
		Address address = null;
		int id = 0;
		try{
			readAddressStatement.setInt(1, paymentID);
			readAddressStatement.setString(2, "payment");
			ResultSet rs = readAddressStatement.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
				address = new Address(id, rs.getString("addrLine1"), rs.getString("addrLine2"),
						rs.getString("city"), rs.getString("state"),
						rs.getInt("ZIP"), rs.getString("country"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * If a DL exists for a customer, it is updated.
	 * If a DL is not found in the system, a new one is created.
	 * @param custID		The unique identifier of the customer to link the DL to
	 * @param DLNumber		The driver's license number
	 * @param DLState		The driver's license state of issue
	 * @return				The updated driver's license
	 */
	public DriversLicense updateDLForCustomer(int custID, String DLNumber, String DLState){
		DriversLicense license = getDLForCustomer(custID);
		int modifiedID = 0;
		try{
			if(license != null){
				// This customer already has a DL. Let's update it.
				updateDLStatement.setString(1, DLNumber);
				updateDLStatement.setString(2, DLState);
				updateDLStatement.setInt(3, license.getId());
				modifiedID = license.getId();
			}else{
				// This customer doesn't have a DL on file. Let's make one.
				insertDLStatement.setString(1, DLNumber);
				insertDLStatement.setString(2, DLState);
				insertDLStatement.setInt(3, custID);
				insertDLStatement.executeUpdate();
				ResultSet key = insertDLStatement.getGeneratedKeys();
				key.next();
				modifiedID = key.getInt(1);
			}
			license = new DriversLicense(modifiedID, DLNumber, DLState);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return license;
	}
	
	/**
	 * Returns the driver's license associated with a customer
	 * @param custID	The unique identifier of the customer
	 * @return			The driver's license for the customer
	 */
	private DriversLicense getDLForCustomer(int custID){
		DriversLicense license = null;
		int id = 0;
		try{
			readDLStatement.setInt(1, custID);
			ResultSet rs = readDLStatement.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
				license = new DriversLicense(id, rs.getString("dl_number"), rs.getString("dl_state"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return license;
	}
	
	/**
	 * Adds an administrator to the YouDrive database.
	 * Returns an Administrator object.
	 * @param username	The username of the admin
	 * @param password	The password of the admin
	 * @return			An Administrator object encapsulating the provided info
	 */
	public Administrator createAdministrator(String username, String password){
		Administrator admin = null;
		int modifiedID = 0;
		try{
			insertAdminStatement.setString(1, username);
			insertAdminStatement.setString(2, password);
			insertAdminStatement.executeUpdate();
			ResultSet key = insertAdminStatement.getGeneratedKeys();
			key.next();
			modifiedID = key.getInt(1);
			admin = new Administrator(modifiedID, username, password);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return admin;
	}
	
	/**
	 * Returns the administrator associated with a given unique ID
	 * @param adminID	The unique identifier of the administrator
	 * @return			The Administrator with the specified unique ID.
	 */
	public Administrator readAdministrator(int adminID){
		Administrator admin = null;
		try{
			readAdminStatement.setInt(1, adminID);
			ResultSet rs = readAdminStatement.executeQuery();
			if(rs.next()){
				admin = new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return admin;
	}
	
	/**
	 * Updates an administrator with the specified information
	 * @param adminID	The unique identifier of the admin to be updated
	 * @param username	The new username for the admin
	 * @param password	The new password for the admin
	 */
	public void updateAdministrator(int adminID, String username, String password){
		try{
			updateAdminStatement.setString(1, username);
			updateAdminStatement.setString(2, password);
			updateAdminStatement.setInt(3, adminID);
			updateAdminStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes an administrator from the YouDrive database
	 * @param adminID	The unique identifier of the administrator to be removed
	 */
	public void deleteAdministrator(int adminID){
		try{
			deleteAdminStatement.setInt(1, adminID);
			deleteAdminStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of administrators of the YouDrive system.
	 * @return A LinkedList of Administrators
	 */
	public LinkedList<Administrator> getAllAdministrators(){
		LinkedList<Administrator> list = new LinkedList<Administrator>();
		try{
			ResultSet rs = getAllAdminsStatement.executeQuery();
			while(rs.next()){
				Administrator admin = new Administrator(rs.getInt("id"), rs.getString("username"),
						rs.getString("password"));
				list.add(admin);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Adds a vehicle type to the YouDrive system
	 * @param description	The description of this vehicle type (i.e. "luxury car, truck, etc.")
	 * @param hourlyRate	The hourly cost of renting a vehicle of this type
	 * @param dailyRate		The daily cost of renting a vehicle of this type
	 * @return				A VehicleType object encapsulating this added information
	 */
	public VehicleType createVehicleType(String description, double hourlyRate, double dailyRate){
		VehicleType vt = null;
		int modifiedID = 0;
		try{
			insertVehicleTypeStatement.setString(1, description);
			insertVehicleTypeStatement.setDouble(2, hourlyRate);
			insertVehicleTypeStatement.setDouble(3, dailyRate);
			insertVehicleTypeStatement.executeUpdate();
			ResultSet key = insertVehicleTypeStatement.getGeneratedKeys();
			key.next();
			modifiedID = key.getInt(1);
			vt = new VehicleType(modifiedID, description, hourlyRate, dailyRate);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return vt;
	}
	
	/**
	 * Retrieves the vehicle type associated with the given unique id
	 * @param id	The unique identifier of the vehicle type to retrieve
	 * @return		A VehicleType object specified by the given unique ID.
	 */
	public VehicleType readVehicleType(int id){
		VehicleType vt = null;
		try{
			readVehicleTypeStatement.setInt(1, id);
			ResultSet rs = readVehicleTypeStatement.executeQuery();
			if(rs.next()){
				vt = new VehicleType(rs.getInt("id"), rs.getString("description"),
						rs.getDouble("hourlyRate"), rs.getDouble("dailyRate"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return vt;
	}
	
	/**
	 * Updates a vehicle type with new information
	 * @param id			The unique identifier of the vehicle type
	 * @param description	The new description of the vehicle type (i.e. "luxury car, truck, etc.")
	 * @param hourlyRate	The new hourly cost of renting a vehicle of this type
	 * @param dailyRate		The new daily cost of renting a vehicle of this type
	 */
	public void updateVehicleType(int id, String description, double hourlyRate, double dailyRate){
		try{
			updateVehicleTypeStatement.setString(1, description);
			updateVehicleTypeStatement.setDouble(2, hourlyRate);
			updateVehicleTypeStatement.setDouble(3, dailyRate);
			updateVehicleTypeStatement.setInt(4, id);
			updateVehicleTypeStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes the vehicle type denoted by the specified id from the YouDrive system
	 * @param id	The unique ID of the vehicle type to remove
	 */
	public void deleteVehicleTypeStatement(int id){
		try{
			deleteVehicleTypeStatement.setInt(1, id);
			deleteVehicleTypeStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of vehicle types
	 * @return A LinkedList of VehicleType objects
	 */
	public LinkedList<VehicleType> getAllVehicleTypes(){
		LinkedList<VehicleType> list = new LinkedList<VehicleType>();
		try{
			ResultSet rs = getAllVehicleTypesStatement.executeQuery();
			while(rs.next()){
				VehicleType vt = new VehicleType(rs.getInt("id"), rs.getString("description"),
						rs.getDouble("hourlyRate"), rs.getDouble("dailyRate"));
				list.add(vt);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Adds a comment to the YouDrive system for a given vehicle
	 * @param vehicleID		The unique identifier of the vehicle for which this comment was made
	 * @param content		The text of the comment
	 * @return				The comment object
	 */
	public Comment createComment(int vehicleID, String content){
		Comment comment = null;
		int id = 0;
		try{
			insertCommentStatement.setString(1, content);
			insertCommentStatement.setInt(2, vehicleID);
			insertCommentStatement.executeUpdate();
			ResultSet key = insertCommentStatement.getGeneratedKeys();
			key.next();
			id = key.getInt(1);
			comment = new Comment (id, content);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return comment;
	}
	
	/**
	 * Returns a list of comments for a given vehicle
	 * @param vehicleID		The unique identifier of the vehicle we are searching comments for
	 * @return				A list of comments for a given vehicle
	 */
	public LinkedList<Comment> getComments(int vehicleID){
		LinkedList<Comment> list = new LinkedList<Comment>();
		try{
			getCommentsStatement.setInt(1, vehicleID);
			ResultSet rs = getCommentsStatement.executeQuery();
			while(rs.next()){
				Comment comment = new Comment(rs.getInt("id"), rs.getString("content"));
				list.add(comment);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Adds a vehicle to the YouDrive system and returns a Vehicle object
	 * @param make			The make of the car (i.e. "Toyota")
	 * @param model			The model of the car (i.e. "Camry")
	 * @param year			The year of the car
	 * @param tag			The tag of the car
	 * @param mileage		The current mileage of the car
	 * @param serviceDate	The date of last service
	 * @param condition		The condition of the car ("good, excellent, etc")
	 * @param vehicle_type	The unique identifier of the associated vehicle type
	 * @param location		The unique identifier of the associated rental location
	 * @return				A Vehicle object encapsulating this data
	 */
	public Vehicle createVehicle(String make, String model,
			int year, String tag, int mileage, Date serviceDate,
			String condition, int vehicle_type, int location){
		Vehicle vehicle = null;
		int vehicleID = 0;
		try{
			insertVehicleStatement.setString(1, make);
			insertVehicleStatement.setString(2, model);
			insertVehicleStatement.setInt(3, year);
			insertVehicleStatement.setString(4, tag);
			insertVehicleStatement.setInt(5, mileage);
			insertVehicleStatement.setDate(6, serviceDate);
			insertVehicleStatement.setString(7, condition);
			insertVehicleStatement.setInt(8, vehicle_type);
			insertVehicleStatement.setInt(9, location);
			insertVehicleStatement.executeUpdate();
			ResultSet key = insertVehicleStatement.getGeneratedKeys();
			key.next();
			vehicleID = key.getInt(1);
			VehicleType vt = readVehicleType(vehicle_type);
			vehicle = new Vehicle(vehicleID, new LinkedList<Comment>(), make,
					model, year, tag, mileage, serviceDate, condition, vt);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return vehicle;
	}
	
	/**
	 * Retrieves a Vehicle from the YouDrive database
	 * @param vehicleID		The unique identifier of the vehicle to retrieve
	 * @return				A Vehicle object identified by the unique ID
	 */
	public Vehicle readVehicle(int vehicleID){
		Vehicle vehicle = null;
		try{
			readVehicleStatement.setInt(1, vehicleID);
			ResultSet rs = readVehicleStatement.executeQuery();
			if(rs.next()){
				LinkedList<Comment> comments = getComments(vehicleID);
				vehicle = new Vehicle(vehicleID, comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"),
						rs.getString("vehicleCondition"), readVehicleType(rs.getInt("type_id")));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return vehicle;
	}
	
	/**
	 * Updates the vehicle identified by the unique identifier with the new data
	 * @param vehicleID			The unique identifier of the vehicle to update
	 * @param make				The new make of the vehicle ("Toyota")
	 * @param model				The new model of the vehicle ("Camry")
	 * @param year				The new year
	 * @param tag				The new tag
	 * @param mileage			The new mileage
	 * @param serviceDate		The new last date of service
	 * @param condition			The new condition of the vehicle
	 * @param vehicle_type		The new id of the vehicle type
	 * @param location			The new location of the vehicle type
	 * @return					A Vehicle object with the updated information
	 */
	public Vehicle updateVehicle(int vehicleID, String make, String model,
			int year, String tag, int mileage, Date serviceDate,
			String condition, int vehicle_type, int location){
		Vehicle vehicle = null;
		try{
			updateVehicleStatement.setString(1, make);
			updateVehicleStatement.setString(2, model);
			updateVehicleStatement.setInt(3, year);
			updateVehicleStatement.setString(4, tag);
			updateVehicleStatement.setInt(5, mileage);
			updateVehicleStatement.setDate(6, serviceDate);
			updateVehicleStatement.setString(7, condition);
			updateVehicleStatement.setInt(8, vehicle_type);
			updateVehicleStatement.setInt(9, location);
			updateVehicleStatement.setInt(10, vehicleID);
			updateVehicleStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return vehicle;
	}
	
	/**
	 * Removes the vehicle specified by the unique identifier
	 * @param vehicleID		The unique identifier of the vehicle to be removed
	 */
	public void deleteVehicle(int vehicleID){
		try{
			deleteVehicleStatement.setInt(1, vehicleID);
			deleteVehicleStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves a list of all vehicles in the YouDrive system
	 * @return	A LinkedList of Vehicles in the YouDrive system
	 */
	public LinkedList<Vehicle> getAllVehicles(){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			ResultSet rs = getAllVehiclesStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/*
	 * Retrieves a list of all AVAILABLE vehicles in the YouDrive system
	 * @return	A LinkedList of Vehicles in the YouDrive system
	 */
	/*public LinkedList<Vehicle> getAllAvailableVehicles(){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			ResultSet rs = getAllAvailableVehiclesStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}*/
	
	/**
	 * Retrieves a list of all vehicles in the YouDrive system at the
	 * rental location specified by the provided unique identifier.
	 * @param locationID	The unique identifier of the rental location
	 * @return	A LinkedList of Vehicles at the specified rental location
	 */
	public LinkedList<Vehicle> getAllVehicles(int locationID){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			getAllVehiclesAtLocationStatement.setInt(1, locationID);
			ResultSet rs = getAllVehiclesAtLocationStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/*
	 * Retrieves a list of all AVAILABLE vehicles in the YouDrive system at the
	 * rental location specified by the provided unique identifier.
	 * @param locationID	The unique identifier of the rental location
	 * @return	A LinkedList of Vehicles at the specified rental location
	 */
	/*public LinkedList<Vehicle> getAllAvailableVehicles(int locationID){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			getAllAvailableVehiclesAtLocationStatement.setInt(1, locationID);
			ResultSet rs = getAllAvailableVehiclesAtLocationStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}*/
	
	/**
	 * Retrieves a list of vehicles at the specified location of a certain type
	 * @param locationID	The unique identifier of the rental location
	 * @param typeID		The unique identifier of the vehicle type
	 * @return				A LinkedList of Vehicles at the specified rental location of a certain type
	 */
	public LinkedList<Vehicle> getAllVehicles(int locationID, int typeID){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			getAllVehiclesOfTypeAtLocationStatement.setInt(1, locationID);
			getAllVehiclesOfTypeAtLocationStatement.setInt(2, typeID);
			ResultSet rs = getAllVehiclesOfTypeAtLocationStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/*
	 * Retrieves a list of AVAILABLE vehicles at the specified location of a certain type
	 * @param locationID	The unique identifier of the rental location
	 * @param typeID		The unique identifier of the vehicle type
	 * @return				A LinkedList of Vehicles at the specified rental location of a certain type
	 */
	/*public LinkedList<Vehicle> getAllAvailableVehicles(int locationID, int typeID){
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		try{
			getAllAvailableVehiclesOfTypeAtLocationStatement.setInt(1, locationID);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setInt(2, typeID);
			ResultSet rs = getAllAvailableVehiclesOfTypeAtLocationStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")), rs.getBoolean("isAvailable"));
				list.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}*/
	
	/**
	 * Retrieves a list of vehicles at the specified location of a certain type AVAILABLE for a
	 * given span specified by a start and end timestamp.
	 * @param locationID		The unique identifier of the rental location
	 * @param typeID			The unique identifier of the vehicle type
	 * @param reservationStart	The proposed start timestamp for the reservation
	 * @param reservationEnd	The proposed end timestamp for the reservation
	 * @return					A list of vehicles available for the given time span, of the given type, at the given location
	 */
	public LinkedList<Vehicle> getAllAvailableVehicles(int locationID, int typeID, Timestamp reservationStart, Timestamp reservationEnd){
		LinkedList<Vehicle> availableVehicles = new LinkedList<Vehicle>();
		try{
			getAllAvailableVehiclesOfTypeAtLocationStatement.setInt(1, locationID);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setInt(2, typeID);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(3, reservationStart);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(4, reservationStart);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(5, reservationEnd);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(6,  reservationEnd);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(7, reservationStart);
			getAllAvailableVehiclesOfTypeAtLocationStatement.setTimestamp(8, reservationEnd);
			ResultSet rs = getAllAvailableVehiclesOfTypeAtLocationStatement.executeQuery();
			while(rs.next()){
				LinkedList<Comment> comments = getComments(rs.getInt("id"));
				Vehicle vehicle = new Vehicle(rs.getInt("id"), comments, rs.getString("make"),
						rs.getString("model"), rs.getInt("year"), rs.getString("tag"),
						rs.getInt("mileage"), rs.getDate("serviceDate"), rs.getString("vehicleCondition"),
						readVehicleType(rs.getInt("type_id")));
				availableVehicles.add(vehicle);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return availableVehicles;
	}
	
	/**
	 * Tells whether a specified vehicle is available for the given span.
	 * @param vehicleID		The unique ID of the vehicle
	 * @param startDate		The start timestamp the vehicle should be available for
	 * @param endDate		The end timestamp the vehicle should be available for
	 * @return				True if the vehicle is available, false otherwise.
	 */
	public boolean isVehicleAvailable(int vehicleID, Date startDate, Date endDate){
		boolean value = false;
		try{
			getVehicleNotConflictingWithTimesStatement.setInt(1, vehicleID);
			getVehicleNotConflictingWithTimesStatement.setDate(2, startDate);
			getVehicleNotConflictingWithTimesStatement.setDate(3, startDate);
			getVehicleNotConflictingWithTimesStatement.setDate(4, endDate);
			getVehicleNotConflictingWithTimesStatement.setDate(5, endDate);
			getVehicleNotConflictingWithTimesStatement.setDate(6, startDate);
			getVehicleNotConflictingWithTimesStatement.setDate(7, endDate);
			ResultSet rs = getVehicleNotConflictingWithTimesStatement.executeQuery();
			value = rs.next();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return value;
	}
	
	/**
	 * Creates a rental location in the YouDrive system
	 * @param name			The name of the location (ex. Southside)
	 * @param capacity		The maximum number of vehicles this location can hold
	 * @param addrLine1		Line 1 of the address
	 * @param addrLine2		Line 2 of the address
	 * @param city			Rental location address city
	 * @param state			Rental location address state
	 * @param ZIP			Rental location zip code
	 * @param country		Rental location country
	 * @return				A RentalLocation object encapsulating this new data
	 */
	public RentalLocation createRentalLocation(String name, int capacity, String addrLine1,
			String addrLine2, String city, String state, int ZIP, String country){
		
		RentalLocation location = null;
		int locationID = 0;
		try{
			insertRentalLocationStatement.setString(1, name);
			insertRentalLocationStatement.setInt(2, capacity);
			insertRentalLocationStatement.executeUpdate();
			ResultSet key = insertRentalLocationStatement.getGeneratedKeys();
			key.next();
			locationID = key.getInt(1);
			Address address = updateAddressForRentalLocation(locationID,addrLine1,
					addrLine2,city,state,ZIP,country);
			LinkedList<Vehicle> vehicles = getAllVehicles(locationID);
			location = new RentalLocation(locationID, name, capacity, address, vehicles);
			
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return location;
	}
	
	/**
	 * Retrieves the rental location from the YouDrive system associated with
	 * the given unique identifier
	 * @param locationID	The unique identifier of the rental location for which we are searching
	 * @return				A RentalLocation object given by the unique ID.
	 */
	public RentalLocation readRentalLocation(int locationID){
		RentalLocation location = null;
		try{
			readRentalLocationStatement.setInt(1, locationID);
			ResultSet rs = readRentalLocationStatement.executeQuery();
			if(rs.next()){
				Address address = getAddressForRentalLocation(locationID);
				LinkedList<Vehicle> vehicles = getAllVehicles(locationID);
				location = new RentalLocation(rs.getInt("id"), rs.getString("name"),
						rs.getInt("capacity"), address, vehicles);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return location;
	}
	
	/**
	 * Updates the name and capacity of a rental location
	 * @param locationID	The unique identifier of the rental location
	 * @param name			The new name of the location
	 * @param capacity		The new capacity of the location
	 */
	public void updateRentalLocation(int locationID, String name, int capacity){
		try{
			updateRentalLocationStatement.setString(1, name);
			updateRentalLocationStatement.setInt(2, capacity);
			updateRentalLocationStatement.setInt(3, locationID);
			updateRentalLocationStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes a rental location from the YouDrive system
	 * @param locationID	The unique identifier of the rental location to be removed
	 */
	public void deleteRentalLocation(int locationID){
		try{
			deleteRentalLocationStatement.setInt(1, locationID);
			deleteRentalLocationStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of rental locations in the YouDrive system.
	 * Each RentalLocation object contains a list of Vehicle objects
	 * located at that location.
	 * @return	A LinkedList of Vehicle objects at a location
	 */
	public LinkedList<RentalLocation> getAllRentalLocations(){
		LinkedList<RentalLocation> list = new LinkedList<RentalLocation>();
		try{
			ResultSet rs = getAllRentalLocationsStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				Address address = getAddressForRentalLocation(id);
				LinkedList<Vehicle> vehicles = getAllVehicles(id);
				RentalLocation location = new RentalLocation(id, rs.getString("name"),
						rs.getInt("capacity"), address, vehicles);
				list.add(location);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Updates the address in the YouDrive system for a given location
	 * @param locationID	The unique identifier of the rental location to update
	 * @param addrLine1		The new address line 1
	 * @param addrLine2		The new address line 2
	 * @param city			The new address city
	 * @param state			The new address state
	 * @param ZIP			The new address zip code
	 * @param country		The new address country
	 */
	public Address updateAddressForRentalLocation(int locationID, String addrLine1, 
			String addrLine2, String city, String state, int ZIP, String country){
		Address address = getAddressForRentalLocation(locationID);
		int modifiedID = 0;
		try{
			if(address != null){
				// This customer already has an address. Let's update it.
				updateAddressStatement.setString(1, addrLine1);
				updateAddressStatement.setString(2, addrLine2);
				updateAddressStatement.setString(3, city);
				updateAddressStatement.setString(4, state);
				updateAddressStatement.setInt(5, ZIP);
				updateAddressStatement.setString(6, country);
				updateAddressStatement.setInt(7, address.getId());
				updateAddressStatement.executeUpdate();
				modifiedID = address.getId();
			}else{
				// This customer does not have an address. Let's make one.
				insertAddressStatement.setString(1, addrLine1);
				insertAddressStatement.setString(2, addrLine2);
				insertAddressStatement.setString(3, city);
				insertAddressStatement.setString(4, state);
				insertAddressStatement.setInt(5, ZIP);
				insertAddressStatement.setString(6, country);
				insertAddressStatement.setInt(7, locationID);
				insertAddressStatement.setString(8, "rental_location");
				insertAddressStatement.executeUpdate();
				ResultSet key = insertAddressStatement.getGeneratedKeys();
				key.next();
				modifiedID = key.getInt(1);
			}
			address = new Address(modifiedID, addrLine1, addrLine2, city, state, ZIP, country);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * Retrieves the address for a given rental location
	 * @param locationID	The unique identifier of the rental location
	 * @return				An Address object representing the address of the rental location
	 */
	private Address getAddressForRentalLocation(int locationID){
		Address address = null;
		int id = 0;
		try{
			readAddressStatement.setInt(1, locationID);
			readAddressStatement.setString(2, "rental_location");
			ResultSet rs = readAddressStatement.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
				address = new Address(id, rs.getString("addrLine1"), rs.getString("addrLine2"),
						rs.getString("city"), rs.getString("state"),
						rs.getInt("ZIP"), rs.getString("country"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
	
	/**
	 * Gets the current 6-month membership price
	 * @return	A double representing the current 6-month YouDrive membership price.
	 */
	public double getMembershipPrice(){
		double price = 0.0;
		try{
			ResultSet rs = getMembershipPriceStatement.executeQuery();
			if(rs.next()){
				price = rs.getDouble("membershipPrice");
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return price;
	}
	
	/**
	 * Sets the 6-month YouDrive membership price
	 * @param newPrice	The new price to set
	 */
	public void setMembershipPrice(double newPrice){
		try{
			setMembershipPriceStatement.setDouble(1, newPrice);
			setMembershipPriceStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Adds a reservation for a vehicle to the YouDrive system
	 * @param pickupTime		The date/time the vehicle can be picked up
	 * @param rentalDuration	The rental duration, in either hours or days depending on rental type
	 * @param isHourly			Whether the rental is hourly (true) or daily (false)
	 * @param timeDue			The date/time the vehicle is due to be returned
	 * @param vehicle_id		The unique identifier of the vehicle we are renting
	 * @param location_id		The unique identifier of the rental location to pickup from
	 * @param customer_id		The unique identifier of the customer placing the reservation
	 * @return	A Reservation object, encapsulating this reservation data.
	 */
	public Reservation createReservation(Date pickupTime, double rentalDuration,
			boolean isHourly, Date timeDue, int vehicle_id, int location_id, int customer_id){
		Reservation reservation = null;
		int id = 0;
		Vehicle vehicle = readVehicle(vehicle_id);
		if(vehicle != null && isVehicleAvailable(vehicle_id, pickupTime, timeDue)){
			try{
				insertReservationStatement.setDate(1, pickupTime);
				insertReservationStatement.setDouble(2, rentalDuration);
				insertReservationStatement.setBoolean(3, isHourly);
				insertReservationStatement.setDate(4, timeDue);
				insertReservationStatement.setInt(5, vehicle_id);
				insertReservationStatement.setInt(6, location_id);
				insertReservationStatement.setInt(7, customer_id);
				insertReservationStatement.executeUpdate();
				ResultSet key = insertReservationStatement.getGeneratedKeys();
				if(key.next()){
					id = key.getInt(1);
					RentalLocation location = readRentalLocation(location_id);
					Customer customer = readCustomer(customer_id);
					vehicle = readVehicle(vehicle_id);
					reservation = new Reservation(id, pickupTime, rentalDuration,
							isHourly,null,timeDue,vehicle,location,customer, true);
				}
			}catch(SQLException e){
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		
		return reservation;
	}
	
	/**
	 * Retrieves a reservation from the YouDrive system.
	 * @param reservationID		The unique identifier of the reservation
	 * @return					A Reservation object encapsulating the reservation data.
	 */
	public Reservation readReservation(int reservationID){
		Reservation reservation = null;
		try{
			readReservationStatement.setInt(1, reservationID);
			ResultSet rs = readReservationStatement.executeQuery();
			if(rs.next()){
				Vehicle vehicle = readVehicle(rs.getInt("vehicle_id"));
				RentalLocation location = readRentalLocation(rs.getInt("location_id"));
				Customer customer = readCustomer(rs.getInt("customer_id"));
				reservation = new Reservation(rs.getInt("id"), rs.getDate("pickupTime"), rs.getDouble("rentalDuration"),
						rs.getBoolean("isHourly"), rs.getDate("timeReturned"), rs.getDate("timeDue"),vehicle,
						location,customer,rs.getBoolean("isActive"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return reservation;
	}
	
	/**
	 * Closes a reservation by returning a vehicle. Marks the vehicle to be returned at the current timestamp
	 * and marks the vehicle as available.
	 * @param reservationID		The unique identifier of the reservation in the YouDrive system.
	 */
	public void closeReservation(int reservationID){
		try{
			Reservation reservation = readReservation(reservationID);
			if(reservation != null){
				closeReservationStatement.setInt(1, reservationID);
				closeReservationStatement.executeUpdate();
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes a reservation from the database
	 * @param reservationID	The unique identifier of the reservation to cancel
	 */
	public void cancelReservation(int reservationID){
		try{
			deleteReservationStatement.setInt(1, reservationID);
			deleteReservationStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns a list of reservations associated with a user, either including or excluding past reservations.
	 * @param customerID				The unique identifier of the customer we're looking up reservations for
	 * @param includePastReservations	Whether we should include past reservations (true) or not (false)
	 * @return							A LinkedList of Reservation objects
	 */
	public LinkedList<Reservation> getAllReservations(int customerID, boolean includePastReservations){
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		ResultSet rs;
		try{
			if(includePastReservations){
				getAllReservationsStatement.setInt(1, customerID);
				rs = getAllReservationsStatement.executeQuery();
			}else{
				getAllActiveReservationsStatement.setInt(1, customerID);
				rs = getAllActiveReservationsStatement.executeQuery();
			}
			while(rs.next()){
				Vehicle vehicle = readVehicle(rs.getInt("vehicle_id"));
				RentalLocation location = readRentalLocation(rs.getInt("location_id"));
				Customer customer = readCustomer(rs.getInt("customer_id"));
				Date timeReturned = rs.getDate("timeReturned");
				boolean isActive = (timeReturned == null);
				Reservation reservation = new Reservation(rs.getInt("id"), rs.getDate("pickupTime"),
						rs.getDouble("rentalDuration"), rs.getBoolean("isHourly"), rs.getDate("timeReturned"),
						rs.getDate("timeDue"), vehicle, location, customer, isActive);
				list.add(reservation);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
}
