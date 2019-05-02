package domain_components;


/**This is a component class designed to store address data 
 * that is stored in a block.  It is used by the
 * simulation to represent the data of a single address.
 * This is similar to a "Node" in a stack/queue or other ADT
 * @author Erik E Johnson
 *
 */
public class Address 
{
	private String streetName;        // the street name that this address is on
	private String fullAddressString; // the full address string, for easy cosmetic access
	private int streetNumber;         // the house number of this address
	private int numberOfLetters;      // the number of letters to be delivered
	private boolean isDelievered;     // status of if the letters have been delivered
	
	// Constructors
	/** Sole constructor - Requires a street name, house number, and
	 * number of letters
	 * @param name - the street name
	 * @param num - the house number
	 * @param l - the number of letters to be delivered
	 */
	public Address(String name, int num, int l) {
		try {
			setStreetName(name);    // set the street name
			setStreetNumber(num);   // set the house number
			setNumberOfLetters(l);  // set the number of letters
			isDelievered = false;   // set is delivered to false as default
			fullAddressString = streetNumber + " " + streetName;  // create a full address string for easy access by GUI
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	// Accessors
	/** Returns street name
	 * @return - the street name
	 */
	public String getStreetName() {
		return streetName;
	}
	
	/** returns the house number
	 * @return - the house number
	 */
	public int getStreetNumber() {
		return streetNumber;
	}
	
	/** Returns the number of letters to be delivered
	 * @return - the number of letters
	 */
	public int getNumberOfLetters() {
		return numberOfLetters;
	}
	
	/** Returns whether this address has been delivered or not
	 * @return - boolean value for if delivered
	 */
	public boolean isDelievered() {
		return isDelievered;
	}
	
	// Mutators
	/** Sets the street name for this address
	 * @param n - the street name (eg. "22nd Street")
	 */
	public void setStreetName(String n) {
		streetName = n;
	}
	
	/** Sets the Street Number
	 * @param n - the street number to set
	 * @throws Exception - Invalid House Number
	 */
	public void setStreetNumber(int n) throws Exception {
		if (n > 0) {
			streetNumber = n;
		}
		else {
			throw new Exception("Invalid House Number");
		}
	}
	
	/** Sets the number of letters
	 * @param n - the number of letters
	 * @throws Exception - Invalid Number of Letters
	 */
	public void setNumberOfLetters(int n) throws Exception {
		if (n >= 0) {
			numberOfLetters = n;
		}
		else {
			throw new Exception("Invalid Number of Letters");
		}
	}
	
	/** Returns a full address string for ease of cosmetic uses.
	 *  this uses properties of the class
	 *  Example: "309 24th Street"
	 * @return - the FULL address string
	 */
	public String getAddressString() {
		return fullAddressString;
	}
	
	/** sets the delivered status for this address to true
	 */
	public void deliverMail() {
		isDelievered = true;
	}
}
