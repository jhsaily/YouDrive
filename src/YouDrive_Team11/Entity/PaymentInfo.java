package YouDrive_Team11.Entity;

/**
 * Represents payment info for a YouDrive customer
 * @author David Stapleton
 *
 */
public class PaymentInfo {

	/**
	 * The unique identifier of this payment info in the YouDrive system
	 */
	private int id;
	
	/**
	 * The credit card number
	 */
	private String creditCardNumber;
	
	/**
	 * The month in which the card expires
	 */
	private int cardExpirationMonth;
	
	/**
	 * The year in which the card expires
	 */
	private int cardExpirationYear;
	
	/**
	 * The billing address associated with this payment info.
	 */
	private Address billingAddress;
	
	/**
	 * Creates a PaymentInfo object from the specified parameters
	 * @param id
	 * @param creditCardNumber
	 * @param cardExpirationMonth
	 * @param cardExpirationYear
	 * @param billingAddress
	 */
	public PaymentInfo(int id, String creditCardNumber,
			int cardExpirationMonth, int cardExpirationYear,
			Address billingAddress){
		this.id = id;
		this.creditCardNumber = creditCardNumber;
		this.cardExpirationMonth = cardExpirationMonth;
		this.cardExpirationYear = cardExpirationYear;
		this.billingAddress = billingAddress;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @return the cardExpirationMonth
	 */
	public int getCardExpirationMonth() {
		return cardExpirationMonth;
	}

	/**
	 * @return the cardExpirationYear
	 */
	public int getCardExpirationYear() {
		return cardExpirationYear;
	}

	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}
}
