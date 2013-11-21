package YouDrive_Team11.Persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import YouDrive_Team11.Entity.Address;
import YouDrive_Team11.Entity.Customer;

public class YouDriveDAO {
	private PreparedStatement insertCustomerStatement;
	private PreparedStatement readCustomerStatement;
	private PreparedStatement updateCustomerStatement;
	private PreparedStatement deleteCustomerStatement;
	private PreparedStatement getAllCustomersStatement;
	
	private PreparedStatement readAddressStatement;
	private PreparedStatement updateAddressStatement;
	private PreparedStatement insertAddressStatement;
	
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
					"country=? where link_id=? and type=?");
			insertAddressStatement = conn.prepareStatement("insert into addresses (addrLine1,addrLine2,city,state,ZIP,country,link_id,type)" +
					" values (?,?,?,?,?,?,?,?)");
			
			//addCustomerStatement = conn.prepareStatement("insert into Customer (custName,custAddr,imageURL,creditLimit) values (?,?,?,?)");
			//updateUnpaidBalanceStatement = conn.prepareStatement("update Customer set unpaidBalance = ? where id=?");
		
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public Customer createCustomer(String username, String password, String emailAddress,
			String firstName, String lastName, Date membershipExpiration,
			String addrLine1, String addrLine2, String city, String state,
			int ZIP, String country){
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
			customer = new Customer(id, username, password, emailAddress, firstName, lastName, membershipExpiration, ,address);//DL & Mailing address!
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
	public Customer readCustomer(int custID){
		Customer customer = null;
		try{
			readCustomerStatement.setInt(0, custID);
			ResultSet rs = readCustomerStatement.executeQuery();
			if(rs.next()){
				customer = new Customer(custID, rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"));
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return customer;
	}
	
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
	
	public LinkedList<Customer> getAllCustomers(){
		LinkedList<Customer> list = new LinkedList<Customer>();
		try{
			ResultSet rs = getAllCustomersStatement.executeQuery();
			while(rs.next()){
				Customer customer = new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("emailAddress"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("membershipExpiration"));
				list.add(customer);
			}
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	private Address updateAddressForCustomer(int id, String addrLine1, String addrLine2,
			String city, String state, int ZIP, String country){
		Address address = null;
		int modifiedID = 0;
		try{
			readAddressStatement.setInt(1, id);
			readAddressStatement.setString(2, "customer");
			ResultSet rs = readAddressStatement.executeQuery();
			if(rs.next()){
				// This customer already has an address. Let's update it.
				modifiedID = rs.getInt("id");
				updateAddressStatement.setString(1, addrLine1);
				updateAddressStatement.setString(2, addrLine2);
				updateAddressStatement.setString(3, city);
				updateAddressStatement.setString(4, state);
				updateAddressStatement.setInt(5, ZIP);
				updateAddressStatement.setString(6, country);
				updateAddressStatement.setInt(7, id);
				updateAddressStatement.setString(8, "customer");
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
			address = new Address(modifiedID, addrLine1, addrLine2, ZIP, state, city, country);
		}catch(SQLException e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return address;
	}
}
