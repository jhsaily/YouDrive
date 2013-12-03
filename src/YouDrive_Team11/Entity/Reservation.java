package YouDrive_Team11.Entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * A reservation placed on a vehicle of a certain type, at a specific location,
 * for a certain duration, by a certain user.
 * @author David Stapleton
 *
 */
public class Reservation {

	/**
	 * The unique identifier of the this reservation in the YouDrive system
	 */
	private int id;
	
	/**
	 * The date/time for pickup of the car
	 */
	private Timestamp pickupTime;
	
	/**
	 * The duration of the rental. A double value in either hours or days
	 * (specified with the isHourly variable)
	 */
	private double rentalDuration;
	
	/**
	 * Whether this rental is hourly. If it's not hourly, it's daily.
	 */
	private boolean isHourly;
	
	/**
	 * The timestamp of when the vehicle was returned
	 */
	private Timestamp timeReturned;
	
	/**
	 * The date/time when the vehicle is due back
	 */
	private Timestamp timeDue;
	
	/**
	 * The vehicle for which this reservation is made
	 */
	private Vehicle vehicle;
	
	/**
	 * The pickup location where the vehicle will be picked up
	 */
	private RentalLocation pickupLocation;
	
	/**
	 * The customer making this reservation
	 */
	private Customer reservingCustomer;
	
	private boolean isActive;
	
	/**
	 * Creates a reservation object from the given parameters
	 * @param id
	 * @param pickupTime
	 * @param rentalDuration
	 * @param isHourly
	 * @param timeReturned
	 * @param timeDue
	 * @param vehicle
	 * @param pickupLocation
	 * @param reservingCustomer
	 * @param isActive
	 */
	public Reservation(int id, Timestamp pickupTime, double rentalDuration,
			boolean isHourly, Timestamp timeReturned, Timestamp timeDue,
			Vehicle vehicle, RentalLocation pickupLocation,
			Customer reservingCustomer, boolean isActive){
		this.id = id;
		this.pickupTime = pickupTime;
		this.rentalDuration = rentalDuration;
		this.isHourly = isHourly;
		this.timeReturned = timeReturned;
		this.timeDue = timeDue;
		this.vehicle = vehicle;
		this.pickupLocation = pickupLocation;
		this.reservingCustomer = reservingCustomer;
		this.isActive = isActive;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the pickupTime
	 */
	public Timestamp getPickupTime() {
		return pickupTime;
	}

	/**
	 * @return the rentalDuration
	 */
	public double getRentalDuration() {
		return rentalDuration;
	}

	/**
	 * @return the isHourly
	 */
	public boolean isHourly() {
		return isHourly;
	}

	/**
	 * @return the timeReturned
	 */
	public Timestamp getTimeReturned() {
		return timeReturned;
	}

	/**
	 * @return the timeDue
	 */
	public Timestamp getTimeDue() {
		return timeDue;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @return the pickupLocation
	 */
	public RentalLocation getPickupLocation() {
		return pickupLocation;
	}

	/**
	 * @return the reservingCustomer
	 */
	public Customer getReservingCustomer() {
		return reservingCustomer;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
}
