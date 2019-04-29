package domain_components;

/**
 * This class represents a street
 * @author Erik E Johnson
 *
 */
public class Street {
	private String streetName;
	private String orientation;
	private int location;
	
	public Street(String n, String or, int loc) {
		setStreetName(n);
		setOrientation(or);
		setLocation(loc);
	}
	
	// getters and setters
	public String getStreetName(){
		return streetName;
	}
	
	public void setStreetName(String n) {
		streetName = n;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	public void setOrientation(String o) {
		orientation = o;
	}
	
	public int getLocation() {
		return location;
	}
	
	public void setLocation(int loc) {
		location = loc;
	}
}
