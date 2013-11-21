package YouDrive_Team11.Entity;

/**
 * Represents a customer's driver's license
 * @author David Stapleton
 *
 */
public class DriversLicense {
	
	/**
	 * The unique identifier of this license in the YouDrive system
	 */
	private int id;
	
	/**
	 * The license number
	 */
	private String licenseNumber;
	
	/**
	 * The state to which this license belongs
	 */
	private String licenseState;
	
	/**
	 * Creates a license object based on the supplied data
	 * @param id				The unique identifer of the license in the system
	 * @param licenseNumber		The license number
	 * @param licenseState		The license state
	 */
	public DriversLicense(int id, String licenseNumber, String licenseState){
		this.id = id;
		this.licenseNumber = licenseNumber;
		this.licenseState = licenseState;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the licenseNumber
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * @return the licenseState
	 */
	public String getLicenseState() {
		return licenseState;
	}

}
