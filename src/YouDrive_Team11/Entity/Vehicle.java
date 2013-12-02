package YouDrive_Team11.Entity;

import java.sql.Date;
import java.util.LinkedList;

/**
 * Represents a vehicle in the YouDrive fleet
 * @author David Stapleton
 *
 */
public class Vehicle {

	/**
	 * The unique identifier of this vehicle in the YouDrive system
	 */
	private int id;
	
	/**
	 * A list of comments for this vehicle
	 */
	private LinkedList<Comment> comments;
	
	/**
	 * The make of this vehicle
	 */
	private String make;
	
	/**
	 * The model of this vehicle
	 */
	private String model;
	
	/**
	 * The year of this vehicle
	 */
	private int year;
	
	/**
	 * The tag for this vehicle
	 */
	private String tag;
	
	/**
	 * The mileage of this vehicle
	 */
	private int mileage;
	
	/**
	 * The date of this vehicle's last service
	 */
	private Date serviceDate;
	
	/**
	 * The condition of the vehicle
	 */
	private String condition;
	
	/**
	 * The type of vehicle
	 */
	private VehicleType vehicleType;
	
	/**
	 * Whether this vehicle is available for rental
	 */
	boolean isAvailable;
	
	/**
	 * Creates a vehicle object from the specified parameters
	 * @param id
	 * @param comments
	 * @param make
	 * @param model
	 * @param year
	 * @param tag
	 * @param mileage
	 * @param serviceDate
	 * @param condition
	 * @param vehicleType
	 * @param isAvailable
	 */
	public Vehicle(int id, LinkedList<Comment> comments, String make,
			String model, int year, String tag, int mileage,
			Date serviceDate, String condition, 
			VehicleType vehicleType, boolean isAvailable){
		this.id = id;
		this.comments = comments;
		this.make = make;
		this.model = model;
		this.year = year;
		this.tag = tag;
		this.mileage = mileage;
		this.serviceDate = serviceDate;
		this.condition = condition;
		this.vehicleType = vehicleType;
		this.isAvailable = isAvailable;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the comments
	 */
	public LinkedList<Comment> getComments() {
		return comments;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @return the mileage
	 */
	public int getMileage() {
		return mileage;
	}

	/**
	 * @return the serviceDate
	 */
	public Date getServiceDate() {
		return serviceDate;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @return the vehicleType
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}
}
