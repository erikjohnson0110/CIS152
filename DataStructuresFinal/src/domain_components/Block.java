package domain_components;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**This is a component class designed to store "block" data
 * in the MailControl and GUI classes.  It could serve the function
 * of an "Edge" in a weighted graph, and emulates a single block
 * in a neighborhood.  It is bounded by two intersections.
 * @author Erik E Johnson
 *
 */
/**
 * @author Erik E Johnson
 *
 */
public class Block {
	private Intersection start; // the starting intersection
	private Intersection end;   // the ending intersection
	private int blockLength;    // number of addresses on the block (both sides)
	
	private int blockStartHouseNum;  // the starting house number for the block
	private int blockEndHouseNum;    // the ending house number for the block
	
	// cosmetic data for GUI
	private Street street;       // the street this block is on
	private Street top;          // the bounding street of the top intersection 
	private Street bottom;       // the bounding street of the bottom intersection
	private int lastNumDelivered;// an int for the last house delivered
	public boolean isDone;       // flag for if all houses have been delivered
	public int mailManLoc;       // location of the mail carrier in the form of an index value of the allAddresses list
	
	// mail delivery data
	private List<Address> allAddresses;  // list to store all addresses
	private Stack<Address> goDown;       // stack to store addresses to access in descending order (simulates going down the block)
	private Queue<Address> goUp;         // list to store addresses to access in ascending order   (simulates going up the block)
	
	// Constructor
	/** Sole constructor - creates a block bounded by two
	 * intersections.  The intersections are the parameters
	 * the constructor determines which is the start and end, so the order
	 * does not truly matter.
	 * @param s - the starting block
	 * @param e - the ending block
	 */
	public Block(Intersection s, Intersection e)
	{
		if (s.getNorthSouth().getStreetName().equals(e.getNorthSouth().getStreetName()))   // if the North/South road on both intersections match
		{                                                                                  // then this block is on that road
			street = s.getNorthSouth();                                                    // set street to the North/South road
			top = e.getEastWest();                                                         // set the top road
			bottom = s.getEastWest();                                                      // set the bottom road
			
			if (s.getBlockNumNS() > e.getBlockNumNS()) {                                   // if the start param number is greater than the end param num
				blockStartHouseNum = e.getBlockNumNS();                                    // flip the blocks
				blockEndHouseNum = s.getBlockNumNS();
				start = e;
				end = s;
			}
			else                                                                           // otherwise set s to start, and e to end
			{
				blockStartHouseNum = s.getBlockNumNS();
				blockEndHouseNum = e.getBlockNumNS();
				start = s;
				end = e;
			}
		}
		else if (s.getEastWest().getStreetName().equals(e.getEastWest().getStreetName())) // else if, the East/West road names match
		{																				  // then this block is on that road
			street = s.getEastWest();                                                     // similar to previous, set street to the East/West road
			top = e.getNorthSouth();                                                      // set the top road
			bottom = s.getNorthSouth();                                                   // set the bottom road
			
			if (s.getBlockNumEW() > e.getBlockNumEW())                                    // check them to make sure they're in the right order
			{
				blockStartHouseNum = e.getBlockNumEW();                                   // if not flip them
				blockEndHouseNum = s.getBlockNumEW();
				start = e;
				end = s;
			}
			else 
			{
				blockStartHouseNum = s.getBlockNumEW();                                   // if so, keep them in the same order.
				blockEndHouseNum = e.getBlockNumEW();
				start = s;
				end = e;
			}
		}
		else 
		{
			// error - this shouldn't happen, but is in here just in case!
			System.out.println("Block Creation Error: Cannot detect valid intersections.");
			start = s;
			end = e;
		}
		
		blockLength = blockEndHouseNum - blockStartHouseNum;   // calculate block length from the derived house number data
		allAddresses = new LinkedList<Address>();              // create a new linked list to store the block's addresses
		goDown = new Stack<Address>();                         // create a new stack to hold the downward addresses
		goUp = new LinkedList<Address>();                      // create a new queue to hold the upward addresses
		lastNumDelivered = 0;                                  // initialize number delivered
		populate();                                            // populate helper method - creates all addresses, and fills them with mail (0-4 letters via rng)
		isDone = false;                                        // initialize is done flag to false
		mailManLoc = 2;                                        // set the mail carrier's start location value
		
	}
	
	// Accessors
	
	/** Returns the number of houses on the block.
	 * @return - the length of the block
	 */
	public int getLength() {
		return blockLength;
	}

	/** Returns the street name of the block 
	 * @return - the street name of the block
	 */
	public String getBlockName() {
		return street.getStreetName();
	}
	
	/** Returns the name of the top road
	 * @return - the name of the top road
	 */
	public String getTopRoadName() {
		return top.getStreetName();
	}
	
	/** Returns the name of the bottom road name
	 * @return - the bottom road name
	 */
	public String getBtmRoadName() {
		return bottom.getStreetName();
	}
	
	/** Returns a list of all addresses on the block.
	 * @return - all addresses on the block
	 */
	public List<Address> getAddresses(){
		return allAddresses;
	}
	
	/** Returns the number of the last address delivered
	 * @return - the last address number that was delivered
	 */
	public int getLastNumDelivered() {
		return lastNumDelivered;
	}
	
	// Mutators
	/** Adds an address to the list.
	 * @param name - the street name
	 * @param num - the house number
	 * @param let - the number of letters
	 */
	public void addAddress(String name, int num, int let) {
		allAddresses.add(new Address(name, num, let));
	}
	
	
	// Helpers
	/** This method initializes and populates simulation data
	 * for this block.
	 */
	private void populate() {
		String streetName = street.getStreetName();  // create a string that stores the street name
		int houseNum = blockStartHouseNum;           // create an int that stores the starting house number
		int numLetters = 0;                          // create and intitialize an int for number of letters to deliver

		for (int i = 0; i < blockLength - 1; i++)   // for each house on the block
		{
			houseNum++;                             // increment house number counter
			numLetters = (int)(Math.random() * 5);  // generate a number of letters from 0 to 4
			addAddress(streetName, houseNum, numLetters); // add this address to the list
		}

		
		                                                // all addresses now exist in the list in ascending order.  Now we sort them.
		for (int j = 0; j < allAddresses.size(); j++)   // for each address
		{
			Address temp = allAddresses.get(j);         // get the current address
			
			if ((temp.getStreetNumber() % 2) == 0)      // if it is even
			{
				goUp.add(temp);                         // put it in the ascending pile
			}
			else 
			{
				goDown.add(temp);                       // if it is odd, put it in the descending pile
			}                                           // this splitting of the mail into a stack and a queue is how the simulation
		}                                               // decides to go up one side of the road, and then down the other, like a person would.
	}
	
	/** Attempts to deliver the next address in the delivery queue.
	 * Returns true, if successful.  Returns false if there are no
	 * letters left.
	 * @return
	 */
	public boolean deliverNext() {
		boolean returnVal = false;                      // default return value of false
		if (!goUp.isEmpty()) {                          // if the ascending pile is not empty
			Address temp = goUp.poll();                 // dequeue the address
			lastNumDelivered = temp.getNumberOfLetters();// update last number delivered to the number of letters
			temp.deliverMail();                          // deliver this addresses mail
			mailManLoc+=4;                               // increment counter by 4.  This has to do with how the GUI tracks the mail carrier position.
			if (goUp.isEmpty()) {                        // if  the list is empty after the current one was dequeued, then subtract two.  just trust me here!
				mailManLoc-=2;                           // it has to do with how Swing stores x and y coordinates in separate arrays, so the x/y vals take 2
			}                                            // array indices. 
			
			returnVal = true;                            // this was successful, return true
		}
		else if (!goDown.isEmpty())                      // if ascending pile is empty, check the descending pile
		{
			Address temp = goDown.pop();                 // if it has mail, pop it off the stack
			lastNumDelivered = temp.getNumberOfLetters();// update the last number delivered
			temp.deliverMail();                          // deliver it
			mailManLoc-=4;                               // decrement the mail carrier location by 4
			returnVal = true;                            // this was a success, return true
		}
		else                                             // if ascending is empty, and descending is empty
		{
			lastNumDelivered = 0;                        // set num delivered to zero
			isDone = true;                               // change the is done flag to true
			returnVal = false;                           // return value false
		}
		return returnVal;
	}
}
