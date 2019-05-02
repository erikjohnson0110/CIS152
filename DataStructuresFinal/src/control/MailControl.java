package control;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import domain_components.Address;
import domain_components.Block;
import domain_components.Intersection;
import domain_components.Street;

/**This is the control class for my program's data.  It encapsulates
 * the program data from the GUI layer, and keeps all of the logic separate
 * from the GUI.  This component generates a neighborhood, stores the neighborhood
 * data, and has a method to retrieve data for the GUI to use.
 * This class contains the main data structures used by the simulation.
 * A neighborhood can be generated with only Street data. The rest is derived
 * from the data contained in the streets, and all blocks/intersections are 
 * generated using an algorithm I developed
 * @author Erik E Johnson
 *
 */
public class MailControl {
	// Data structures to store data used by the simulation
	private List<Street> streetsNorthSouth;    // a list that stores all north-south streets
	private List<Street> streetsEastWest;      // a list that stores all east-west streets
	private List<Intersection> intersections;  // a list that stores all intersections in the neighborhood
	private Queue<Block> blocks;               // a queue of blocks that will be delivered
	private List<Address> addresses;           // a list of addresses
	
	private Block current;                     // the current block being sent to the GUI for processing visually
	
	private Intersection[][] intersectionArray;// a 2-d array to store intersections, used in generating the neighborhood
	private int arrRows;                       // a variable to store number of rows in the 2-d array
	private int arrCols;                       // a variable to store number of cols in the 2-d array
	
	// constructor
	/** Default constructor - Initializes and instantiates all
	 * lists, and sets current block to null
	 */
	public MailControl(){                                 // Constructor logic
		streetsNorthSouth = new LinkedList<Street>();     // create new list of Streets
		streetsEastWest = new LinkedList<Street>();       // create new list of Streets
		
		intersections = new LinkedList<Intersection>();   // create new list of intersections
		
		blocks = new LinkedList<Block>();                 // create new list of blocks
		
		addresses = new LinkedList<Address>();            // create new list of addresses
		current = null;                                   // set current block to null
	}
	
	// Accessors and Mutators
	/** Returns the current block for delivery
	 * @return -  The current block for delivery
	 */
	public Block getCurrentBlock() {
		return current;
	}
	
	/** Returns the NEXT block in the queue
	 * @return - the next block in the queue
	 */
	public Block getNextBlock() {
		return blocks.poll();
	}
	
	// Helper methods
	/** Generates a new neighborhood.  Initializes
	 *  the object, and populates it with new data.
	 */
	public void generateNeighborhood() {
		try {                         // Encapsulated functionality in private methods, see below.  This is for ease of building out new generation function later
			initialize();             // initializes the object's lists and data.  prevents duplicate items in lists
			loadStreetsEastWest();    // Load street data for east/west streets - hard coded for now, could later be expanded to pull from file or DB perhaps
			loadStreetsNorthSouth();  // Load street data for north/south streets - hard coded for now.
			initializeArrays();       // initializes arrays
			
			createIntersections();    // creates intersections
			createBlocks();           // creates blocks
		}
		catch (Exception ex) {        // catch any exceptions thrown
			System.out.println(ex.getMessage()); // display message in console
		}
	}
	
	/** Method for delivering the next address in the current block
	 * that is being delivered.  Returns a boolean value of true if there
	 * is mail to be delivered.  Returns false if not.
	 * @return
	 */
	public boolean deliverNext() {
		boolean returnVal = false;
		if (current != null) {                 // if current is not null
			returnVal = current.deliverNext(); // call current block's deliverNext method
		}
		return returnVal;
	}
	
	
	// Private methods to encapsulate functionality.
	// This will allow me to add functionality later.
	/** This method clears this object's lists, and sets
	 * the current block to null.  
	 */
	private void initialize() {
		streetsEastWest.clear();
		streetsNorthSouth.clear();
		addresses.clear();
		blocks.clear();
		current = null;
	}
	
	/** This method adds several hard-coded streets to the
	 * east-west streets list.  In the future, this could be modified to accept user 
	 * input, accept data from a file, or from a database.
	 */
	private void loadStreetsEastWest() {
		streetsEastWest.add(new Street("Douglas Avenue", "EW", 310));
		streetsEastWest.add(new Street("Hickman Road", "EW", 300));
		streetsEastWest.add(new Street("University Avenue", "EW", 280));
	}
	
	/** This method adds several hard-coded streets to the
	 * north-south streets list.  In the future, this could be modified to accept user input,
	 * accept data from a file, or from a database.
	 * 
	 */
	private void loadStreetsNorthSouth() {
		streetsNorthSouth.add(new Street("62nd Street", "NS", 620));
		streetsNorthSouth.add(new Street("63rd Street", "NS", 630));
	}
	
	/** This method initializes the intersection array
	 * based on the size of the east/west street list
	 * and the north/south street list.
	 */
	private void initializeArrays() {
		arrRows = streetsEastWest.size();
		arrCols = streetsNorthSouth.size();
		intersectionArray = new Intersection[arrRows][arrCols];
	}
	
	/** This method creates intersections based on the street data
	 * in the two street lists (north/south and east/west).  It
	 * adds the intersections to the intersections array, which is
	 * used to create blocks.
	 */
	private void createIntersections() {
		// go through each east west street and make intersections
		for (int i = 0; i < streetsEastWest.size(); i++) 
		{
			// go through each north south street
			for (int j = 0; j < streetsNorthSouth.size(); j++) 
			{
				Intersection tempInt = new Intersection(streetsNorthSouth.get(j), streetsEastWest.get(i));  // create a new intersection using the NS and EW street
				intersections.add(tempInt);                                                                 // add to the intersection list
				intersectionArray[i][j] = tempInt;                                                          // add to intersection array for block processing
			}
		}
	}
	
	/** This method creates blocks from the intersections populated in 
	 * the intersection array.
	 */
	private void createBlocks() {
		for (int k = 0; k < arrRows; k++) {            // iterate through each row of the intersection array
			for (int l = 0; l < arrCols; l++) {        // iterate through each column of the intersection array for each index in the rows
				if ((l + 1) < arrCols) {               // if the column to the left is not out of bounds, create block between current and left intersection
					blocks.add(new Block(intersectionArray[k][l], intersectionArray[k][(l + 1)]));
				}
				if ((k + 1) < arrRows) {               // if the row below is not out of bounds, create a block between current intersection, and lower intersection
					blocks.add(new Block(intersectionArray[k][l], intersectionArray[(k + 1)][(l)]));
				}
			}
		}
		                                                  // now that all the blocks are generated, we want to get all of the addresses
		for (int m = 0; m < blocks.size(); m++) {
			Block temp = blocks.poll();                   // dequeue a block
			List<Address> tempList = temp.getAddresses(); // save the current block's list of addresses
			for (int n = 0; n < tempList.size(); n++) {   // iterate through each of the block's addresses
				addresses.add(tempList.get(n));           // add them to the Controller's list of addresses
			}
			blocks.add(temp);                             // enqueue the block back at the end of the line.
		}                                                 // when all blocks are processed, they will be in the same order as they started.
	}
}
