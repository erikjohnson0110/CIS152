package domain_components;

/**
 * This class represents a street
 * @author Erik E Johnson
 *
 */
public class Street {
	private String streetName;            // The name of the street
	private String orientation;           // The street's orientation
	private int location;                 // The streets location relative to a perpendicular street (eg. a NS road 33rd Street is the 3300 block of all EW roads)
	
	
	/** Sole constructor - creates a new street object and
	 * requires all properties to be set upon instantiation
	 * @param n - the name of the street
	 * @param or - the orientation ("NS" or "EW")
	 * @param loc - the location of the street relative to a perpendicular street
	 */
	public Street(String n, String or, int loc) {
		try {
			setStreetName(n);    // set the name
			setOrientation(or);  // set the orientation
			setLocation(loc);    // set the location
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	// getters and setters
	/** Returns a string that contains the streets name
	 * @return - a string of the streets name
	 */
	public String getStreetName(){
		return streetName;
	}
	
	/** Sets the street's name
	 * @param n - the name to set
	 */
	public void setStreetName(String n) {
		streetName = n;
	}
	
	/** Returns a string with the streets orientation
	 * @return - the streets orientation
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/** Sets the street's orientation
	 * @param o - The orientation (Valid options: "NS" or "EW")
	 * @throws Exception - Invalid Orientation
	 */
	public void setOrientation(String o) throws Exception {
		if (o.equalsIgnoreCase("NS") || o.equalsIgnoreCase("EW")) {
			orientation = o;
		}
		else {
			throw new Exception("Invalid Orientation");
		}
	}
	
	/** returns the location of the street relative to 
	 * perpendicular roads
	 * @return - the location of the street
	 */
	public int getLocation() {
		return location;
	}
	
	/** Sets the location of the street relative to
	 * perpendicular roads
	 * @param loc
	 * @throws Exception - Negative Location Not Allowed
	 */
	public void setLocation(int loc) throws Exception {
		if (loc > 0) {
			location = loc;
		}
		else {
			throw new Exception("Negative Location Not Allowed");
		}
	}
}
