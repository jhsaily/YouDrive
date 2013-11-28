package YouDrive_Team11.Entity;

/**
 * The abstract superclass of customers and admins
 * @author David Stapleton
 *
 */
public abstract class User {

	/**
	 * The unique identifier for this user in the YouDrive system.
	 */
	protected int id;
	
	/**
	 * The username for the user to log in.
	 */
	protected String username;
	
	/**
	 * The password for the user to log in.
	 */
	protected String password;
	
	/**
	 * Whether the user is an admin or not
	 */
	protected boolean isAdmin;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Returns whether or not a user is an admin
	 * @return True if the user is an admin. False if not.
	 */
	public boolean isAdmin(){
		return isAdmin;
	}
	
}
