package YouDrive_Team11.Persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import YouDrive_Team11.Entity.Address;
import YouDrive_Team11.Entity.Administrator;
import YouDrive_Team11.Entity.Comment;
import YouDrive_Team11.Entity.Customer;
import YouDrive_Team11.Entity.DriversLicense;
import YouDrive_Team11.Entity.VehicleType;

/**
 * The persistence class for the YouDrive system.
 * @author David Stapleton
 *
 */
public class YouDriveDAO {
	
	private PreparedStatement insertCustomerStatement;
	private PreparedStatement readCustomerStatement;
	private PreparedStatement updateCustomerStatement;
	private PreparedStatement deleteCustomerStatement;
	private PreparedStatement getAllCustomersStatement;
	
	private PreparedStatement readAddressStatement;
	private PreparedStatement updateAddressStatement;
	private PreparedStatement insertAddressStatement;
	
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
	private PreparedStatement getallVehiclesOfTypeAtLocationStatement;
	
	/**
	 * Creates an instance of the persistence class
	 */
	public YouDriveDAO(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Instantiated MySQL driver!");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/team11","team11","virtual");
			System.out.println("Connected to MySQL!");
			insertCustomerStatement = conn.prepareStatement("insert into users (username,password,isAdmin,emailAddress,firstName,lastName,membershipExpiration)" +
					"values (?,?,0,?,?,?,?");
			readCustomerStatement = conn.prepareStatement("select * from users where id=? and isAdmin=0");
			updateCustomerStatement = conn.prepareStatement("update users set username=?,password=?,emailAddress=?,firstName=?," +
					"lastName=?,membershipExpiration=? where id=? and isAdmin=0");
			deleteCustomerStatement = conn.prepareStatement("delete from users where id=? and isAdmin=0");
			getAllCustomersStatement = conn.prepareStatement("select * from users where isAdmin=0");
			
			readAddressStatement = conn.prepareStatement("select * from addresses where link_id=? and type=?");
			updateAddressStatement = conn.prepareStatement("update addresses set addrLine1=?,addrLine2=?,city=?,state=?,ZIP=?," +
					"country=? where id=?");
			insertAddressStatement = conn.prepareStatement("insert into addresses (addrLine1,addrLine2,city,state,ZIP,country,link_id,type)" +
					" values (?,?,?,?,?,?,?,?)");
			
			readDLStatement = conn.prepareStatement("select * from dl where customer_id=?");
			updateDLStatement = conn.prepareStatement("update dl set dl_number=?,dl_state=? where customer_id=?");
			insertDLStatement = conn.prepareStatement("insert into dl (dl_number,dl_state,customer_id)" +
					" values (?,?,?)");
			
			insertAdminStatement = conn.prepareStatement("insert into users (username,password,isAdmin) values" +
					" (?,?,1)");
			readAdminStatement = conn.prepareStatement("select * from users where id=? and isAdmin=1");
			updateAdminStatement = conn.prepareStatement("update users set username=?,password=?" +
					" where id=? and isAdmin=1");
			deleteAdminStatement = conn.prepareStatement("delete from users where id=? and isAdmin=1");
			getAllAdminsStatement = conn.prepareStatement("select * from users where isAdmin=1");
			
			insertVehicleTypeStatement = conn.prepareStatement("insert into vehicle_types (description, hourlyRate," +
					"dailyRate) values (?,?,?)");
			readVehicleTypeStatement = conn.prepareStatement("select * from vehicle_types where id=?");
			updateVehicleTypeStatement = conn.prepareStatement("update vehicle_types set description=?,hourlyRate=?," +
					"dailyRate=? where id=?");
			deleteVehicleTypeStatement = conn.prepareStatement("delete from vehicle_types where id=?");
			getAllVehicleTypesStatement = conn.prepareStatement("select * from vehicle_types");
			
			insertCommentStatement = conn.prepareStatement("insert into comments (content, vehicle_id) " +
					"values (?,?)");
			getCommentsStatement = conn.prepareStatement("select * from comments where vehicle_id=?");
			
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
			String firstName, String lastName, Date membershipExpiration,
			String addrLine1, String addrLine2, String city, String state,
			int ZIP, String country, String DLNumber, String DLState){
		Customer customer = null;
		try{
			insertCustomerStatement.setString(0, username);
			insertCustomerStatement.setString(1, password);
			insertCustomerStatement.setString(2, emailAddress);
			insertCustomerStatement.setString(3, firstName);
			insertCustomerStatement.setString(4, lastName);
			insertCustomerStatement.setDate(5, membershipExpiration);
			insertCustomerStatement.executeUpdate();
			ResultSet key = insertCustomerStatement.getGeneratedKeys();
			key.next();
			int id = key.getInt(1);
			Address address = updateAddressForCustomer(id, addrLine1,addrLine2,city,state,ZIP,country);
			DriversLicense license = updateDLForCustomer(id, DLNumber, DLState);
			customer = new Customer(id, username, password, emailAddress, firstName, lastName, membershipExpiration, license,address);//DL & Mailing address!
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
				customer = new Customer(custID, rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"), license, address);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
	/**
	 * Updates the info for a customer identified by the supplied unique identifier.
	 * @param custID				The unique identifier of the customer to update
	 * @param username				The new username
	 * @param password				The new password
	 * @param emailAddress			The new email address
	 * @param firstName				The new first name
	 * @param lastName				The new last name
	 * @param membershipExpiration	The new membership expiration date
	 */
	public void updateCustomer(int custID, String username, String password, String emailAddress,
			String firstName, String lastName, Date membershipExpiration){
		try{
			updateCustomerStatement.setString(1, username);
			updateCustomerStatement.setString(2, password);
			updateCustomerStatement.setString(3, emailAddress);
			updateCustomerStatement.setString(4, firstName);
			updateCustomerStatement.setString(5, lastName);
			updateCustomerStatement.setDate(6, membershipExpiration);
			updateCustomerStatement.setInt(7, custID);
			updateCustomerStatement.executeUpdate();
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
				DriversLicense license = getDLForCustomer(rs.getInt("id"));
				Address address = getAddressForCustomer(rs.getInt("id"));
				Customer customer = new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"), license, address);
				list.add(customer);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
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
	private Address updateAddressForCustomer(int id, String addrLine1, String addrLine2,
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
	 * If a DL exists for a customer, it is updated.
	 * If a DL is not found in the system, a new one is created.
	 * @param custID		The unique identifier of the customer to link the DL to
	 * @param DLNumber		The driver's license number
	 * @param DLState		The driver's license state of issue
	 * @return				The updated driver's license
	 */
	private DriversLicense updateDLForCustomer(int custID, String DLNumber, String DLState){
		DriversLicense license = getDLForCustomer(custID);
		int modifiedID = 0;
		try{
			if(license != null){
				// This customer already has a DL. Let's update it.
				updateDLStatement.setString(1, DLNumber);
				updateDLStatement.setString(2, DLState);
				updateDLStatement.setInt(3, license.getId());
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
}
