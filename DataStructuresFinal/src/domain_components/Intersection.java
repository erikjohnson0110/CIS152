package domain_components;

/** This class represents an intersection of two streets.
 *  It has limited use in this implementation, other than for 
 *  cosmetic information in the GUI and creation of blocks, but 
 *  could be expanded upon later to be used as a "Node" in a
 *  Tree or Weighted Graph ADT.
 * @author Erik E Johnson
 *
 */
public class Intersection {
	private Street NorthSouth;      // the NorthSouth oriented street 
	private Street EastWest;        // the EastWest oriented street
	
	private int BlockNumNorthSouth; // the block number of the north south street - used by blocks to calculate sizes
	private int BlockNumEastWest;   // the block number of the east west street
	
	public boolean visited;         // was visited flag to be used in a Graph ADT for an MST algorithm
	
	// constructors
	/** Default constructor - creates a blank intersection
	 *  with null values for streets, zeros for numbers,
	 *  and a visited flag of false.
	 */
	public Intersection() {
		NorthSouth = null;
		EastWest = null;
		
		BlockNumNorthSouth = 0;
		BlockNumEastWest = 0;
		
		visited = false;
	}

	/** Non-Default constructor that accepts two street objects
	 * as parameters.  It derives information from these streets.
	 * @param ns - the North/South oriented street
	 * @param ew - the East/West oriented street
	 */
	public Intersection(Street ns, Street ew) {
		NorthSouth = ns;                       // set the north south road to ns
		EastWest = ew;                         // set the east west road to ew
		BlockNumNorthSouth = ew.getLocation(); // set the block number for the NS road using ew road location (remember this is the ew road loc relative to a NS road)
		BlockNumEastWest = ns.getLocation();   // set the block number for the EW road using the ns road location
		visited = false;
	}

	// accessors
	/** Returns this intersections NS street
	 * @return - the north south street
	 */
	public Street getNorthSouth() {
		return NorthSouth;
	}
	
	/** Returns this intersection's EW street
	 * @return - the east west street
	 */
	public Street getEastWest() {
		return EastWest;
	}
	
	/** Returns the NS Block number
	 * @return - the NS Block number
	 */
	public int getBlockNumNS() {
		return BlockNumNorthSouth;
	}
	
	/** Returns the EW Block number
	 * @return - the EW block number
	 */
	public int getBlockNumEW() {
		return BlockNumEastWest;
	}
	
	/** Returns a string with this intersection's street names.
	 * used in debugging.
	 * @return
	 */
	public String print() {
		return "[Intersection - " + NorthSouth.getStreetName() + " & " + EastWest.getStreetName() + "]";
	}
}
