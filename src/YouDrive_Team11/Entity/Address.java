package YouDrive_Team11.Entity;

/**
 * Represents an address in the YouDrive system
 * @author David Stapleton
 *
 */
public class Address {
	
	/**
	 * The unique identifier of this address in the YouDrive system.
	 */
	private int id;

	/**
	 * The first line of the address
	 */
	private String streetAddrLine1;
	
	/**
	 * The second line of the address
	 */
	private String streetAddrLine2;
	
	/**
	 * The ZIP code of the address
	 */
	private int zipCode;
	
	/**
	 * The state for the address
	 */
	private String state;
	
	/**
	 * The city for the address
	 */
	private String city;
	
	/**
	 * The country for the address
	 */
	private String country;
	
	/**
	 * Creates a new Address object
	 * @param id				The unique identifier of this Address
	 * @param streetAddrLine1	The first line of the address
	 * @param streetAddrLine2	The second line of the address
	 * @param city				The city of the address
	 * @param state				The state of the address
	 * @param zipCode			The zip code of the address
	 * @param country			The country of the address
	 */
	public Address(int id, String streetAddrLine1, String streetAddrLine2,
			String city, String state, int zipCode, String country){
		this.id = id;
		this.streetAddrLine1 = streetAddrLine1;
		this.streetAddrLine2 = streetAddrLine2;
		this.zipCode = zipCode;
		this.state = state;
		this.city = city;
		this.country = country;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the streetAddrLine1
	 */
	public String getStreetAddrLine1() {
		return streetAddrLine1;
	}

	/**
	 * @return the streetAddrLine2
	 */
	public String getStreetAddrLine2() {
		return streetAddrLine2;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
}
