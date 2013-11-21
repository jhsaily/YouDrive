package YouDrive_Team11.Entity;

import java.util.LinkedList;

/**
 * A rental location of YouDrive. Holds vehicles.
 * @author David Stapleton
 *
 */
public class RentalLocation {

	/**
	 * The unique identifier of this rental location in the YouDrive system
	 */
	private int id;
	
	/**
	 * The name of this rental location
	 */
	private String name;
	
	/**
	 * The maximum number of vehicles this location can hold
	 */
	private int capacity;
	
	/**
	 * The address of this rental location
	 */
	private Address locationAddress;
	
	/**
	 * A list of vehicles at this rental location
	 */
	private LinkedList<Vehicle> vehicles;
	
	/**
	 * Creates a rental location object from the specified parameters
	 * @param id
	 * @param name
	 * @param capacity
	 * @param locationAddress
	 * @param vehicles
	 */
	public RentalLocation(int id, String name, int capacity,
			Address locationAddress, LinkedList<Vehicle> vehicles){
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.locationAddress = locationAddress;
		this.vehicles = vehicles;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the locationAddress
	 */
	public Address getLocationAddress() {
		return locationAddress;
	}

	/**
	 * @return the vehicles
	 */
	public LinkedList<Vehicle> getVehicles() {
		return vehicles;
	}
}
