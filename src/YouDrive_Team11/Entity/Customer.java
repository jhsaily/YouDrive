package YouDrive_Team11.Entity;

import java.sql.Date;

/**
 * Represents a customer of the YouDrive system
 * @author David Stapleton
 *
 */
public class Customer extends User {

	/**
	 * The email address of the customer
	 */
	private String emailAddress;
	
	/**
	 * The first name of the customer
	 */
	private String firstName;
	
	/**
	 * The last name of the customer
	 */
	private String lastName;
	
	/**
	 * The expiration date of the customer's membership.
	 * This is a SQL date!
	 */
	private Date membershipExpiration;
	
	/**
	 * The driver's license for this customer.
	 */
	private DriversLicense driversLicense;
	
	/**
	 * The mailing address for this customer
	 */
	private Address mailingAddress;
	
	/**
	 * Creates a customer object from the specified inputs.
	 * @param id					The unique ID of the customer in the YouDrive system.
	 * @param username				The username for the customer.
	 * @param password				The password for the customer.
	 * @param emailAddress			The email address for the customer.
	 * @param firstName				The first name of the customer.
	 * @param lastName				The last name of the customer.
	 * @param membershipExpiration	The expiration date of the customer's membership. SQL date.
	 * @param dl					The driver's license for this customer.
	 * @param mailingAddress		The mailing address for this user
	 */
	public Customer(int id, String username, String password,
			String emailAddress, String firstName,
			String lastName, Date membershipExpiration,
			DriversLicense dl, Address mailingAddress){
		this.id = id;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.membershipExpiration = membershipExpiration;
		this.driversLicense = dl;
		this.mailingAddress = mailingAddress;
		this.isAdmin = false;
	}
	
	/**
	 * Sends the password (reset?) email to the user. IMPLEMENT
	 */
	public void sendPasswordEmail(){
		
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the membershipExpiration
	 */
	public Date getMembershipExpiration() {
		return membershipExpiration;
	}

	/**
	 * @return the driversLicense
	 */
	public DriversLicense getDriversLicense() {
		return driversLicense;
	}

	/**
	 * @return the mailingAddress
	 */
	public Address getMailingAddress() {
		return mailingAddress;
	}
}
