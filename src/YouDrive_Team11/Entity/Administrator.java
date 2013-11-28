package YouDrive_Team11.Entity;

/**
 * Represents an Administrator of the YouDrive system
 * @author David Stapleton
 *
 */
public class Administrator extends User {

	/**
	 * Creates a new Administrator with the specified username and password
	 * @param id		The unique identifier of this administrator in the YouDrive system.
	 * @param username
	 * @param password
	 */
	public Administrator(int id, String username, String password){
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdmin = true;
	}
}
