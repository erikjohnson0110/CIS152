package domain_components;

/** 
 * @author Erik E Johnson
 *
 */
public class Intersection {
	private static int keyVal = 0;
	private int myKey;
	private Street NorthSouth;
	private Street EastWest;
	
	private int BlockNumNorthSouth;
	private int BlockNumEastWest;
	
	public boolean visited;
	
	// constructors
	public Intersection() {
		NorthSouth = null;
		EastWest = null;
		
		BlockNumNorthSouth = 0;
		BlockNumEastWest = 0;
		
		visited = false;
	}

	public Intersection(Street ns, Street ew) {
		NorthSouth = ns;
		EastWest = ew;
		BlockNumNorthSouth = ew.getLocation();
		BlockNumEastWest = ns.getLocation();
		visited = false;
		myKey = keyVal;
		keyVal++;
	}

	// accessors
	public int getKey() {
		return myKey;
	}
	
	public Street getNorthSouth() {
		return NorthSouth;
	}
	
	public Street getEastWest() {
		return EastWest;
	}
	
	public int getBlockNumNS() {
		return BlockNumNorthSouth;
	}
	
	public int getBlockNumEW() {
		return BlockNumEastWest;
	}
	
	// mutators
	public void setNorthSouth(Street ns) {
		NorthSouth = ns;
	}
	
	public void setEastWest(Street ew) {
		EastWest = ew;
	}
	
	public String print() {
		return "[Intersection - " + NorthSouth.getStreetName() + " & " + EastWest.getStreetName() + "]";
	}
}
