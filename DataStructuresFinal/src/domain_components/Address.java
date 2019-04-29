package domain_components;


/**This is a component class designed to store address data 
 * that is stored in a domain data structure.  It is used by the
 * simulation to represent the data of a single address.
 * This is similar to a "Node" in a stack/queue or other ADT
 * @author Erik E Johnson
 *
 */
public class Address 
{
	private String streetName;
	private String fullAddressString;
	private int streetNumber;
	private int numberOfLetters;
	private boolean isDelievered;
	
	// Constructors
	/**
	 * @param name
	 * @param num
	 * @param l
	 */
	public Address(String name, int num, int l) {
		setStreetName(name);
		setStreetNumber(num);
		setNumberOfLetters(l);
		isDelievered = false;
		fullAddressString = streetNumber + " " + streetName;
	}
	
	// Accessors
	/**
	 * @return
	 */
	public String getStreetName() {
		return streetName;
	}
	
	/**
	 * @return
	 */
	public int getStreetNumber() {
		return streetNumber;
	}
	
	/**
	 * @return
	 */
	public int getNumberOfLetters() {
		return numberOfLetters;
	}
	
	/**
	 * @return
	 */
	public boolean isDelievered() {
		return isDelievered;
	}
	
	// Mutators
	/**
	 * @param n
	 */
	public void setStreetName(String n) {
		streetName = n;
	}
	
	/**
	 * @param n
	 */
	public void setStreetNumber(int n) {
		streetNumber = n;
	}
	
	/**
	 * @param n
	 */
	public void setNumberOfLetters(int n) {
		numberOfLetters = n;
	}
	
	public String getAddressString() {
		return fullAddressString;
	}
	
	/**
	 * 
	 */
	public void deliverMail() {
		//setNumberOfLetters(0);
		isDelievered = true;
	}
}
