package YouDrive_Team11.Entity;

/**
 * Represents a type of vehicle in the YouDrive system
 * @author David Stapleton
 *
 */
public class VehicleType {

	/**
	 * The unique identifier of this vehicle type in the YouDrive system
	 */
	private int id;
	
	/**
	 * The description of the vehicle type. i.e. "luxury car, truck, etc."
	 */
	private String description;
	
	/**
	 * The rate for renting this type of vehicle hourly
	 */
	private double hourlyRate;
	
	/**
	 * The rate for renting this type of vehicle daily
	 */
	private double dailyRate;
	
	/**
	 * Creates a VehicleType object
	 * @param id			The unique identifier of the vehicle type
	 * @param description	The description of the vehicle type (i.e. "luxury car, truck, etc.")
	 * @param hourlyRate	The hourly cost of renting a vehicle of this type
	 * @param dailyRate		The daily cost of renting a vehicle of this type
	 */
	public VehicleType(int id, String description,
			double hourlyRate, double dailyRate){
		this.id = id;
		this.description = description;
		this.hourlyRate = hourlyRate;
		this.dailyRate = dailyRate;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the hourlyRate
	 */
	public double getHourlyRate() {
		return hourlyRate;
	}

	/**
	 * @return the dailyRate
	 */
	public double getDailyRate() {
		return dailyRate;
	}
}
