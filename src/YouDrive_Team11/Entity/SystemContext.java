package YouDrive_Team11.Entity;

/**
 * Represents certain YouDrive system data
 * @author David Stapleton
 *
 */
public class SystemContext {

	/**
	 * The six-month membership fee for YouDrive
	 */
	private double membershipPrice;
	
	/**
	 * Creates a system context with the given data
	 * @param membershipPrice	The six-month membership price
	 */
	public SystemContext(double membershipPrice){
		this.membershipPrice = membershipPrice;
	}

	/**
	 * @return the membershipPrice
	 */
	public double getMembershipPrice() {
		return membershipPrice;
	}
}
