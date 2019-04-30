package domain_components;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**This is a component class designed to store "block" data
 * in a domain package data structure.  It serves the function
 * of an "Edge" in a weighted graph, and emulates a single block
 * in a neighborhood.  Its edges are intersections.
 * @author Erik E Johnson
 *
 */
public class Block {
	// graph ADT members
	private Intersection start;
	private Intersection end;
	private int blockLength; // number of addresses divided by 2??  Total addresses?  Prob total addresses. Serves as weight of graph
	
	private int blockStartHouseNum;
	private int blockEndHouseNum;
	
	// cosmetic data for GUI
	private Street street;
	private Street top;
	private Street bottom;
	private int lastNumDelivered;
	public boolean isDone;
	public int mailManLoc;
	
	// mail delivery data
	private List<Address> allAddresses;
	private Stack<Address> goDown;
	private Queue<Address> goUp;
	
	// Constructor
	/*
	public Block(Intersection s, Intersection e, int l, Street str) {
		start = s;
		end = e;
		blockLength = l;
		street = str;
		allAddresses = new LinkedList<Address>();
		goDown = new Stack<Address>();
		goUp = new LinkedList<Address>();
	}
	*/
	public Block(Intersection s, Intersection e)
	{
		if (s.getNorthSouth().getStreetName().equals(e.getNorthSouth().getStreetName())) 
		{
			street = s.getNorthSouth();
			top = e.getEastWest();
			bottom = s.getEastWest();
			
			if (s.getBlockNumNS() > e.getBlockNumNS()) {
				blockStartHouseNum = e.getBlockNumNS();
				blockEndHouseNum = s.getBlockNumNS();
				start = e;
				end = s;
			}
			else 
			{
				blockStartHouseNum = s.getBlockNumNS();
				blockEndHouseNum = e.getBlockNumNS();
				start = s;
				end = e;
			}
		}
		else if (s.getEastWest().getStreetName().equals(e.getEastWest().getStreetName())) 
		{
			street = s.getEastWest();
			top = e.getNorthSouth();
			bottom = s.getNorthSouth();
			
			if (s.getBlockNumEW() > e.getBlockNumEW()) 
			{
				blockStartHouseNum = e.getBlockNumEW();
				blockEndHouseNum = s.getBlockNumEW();
				start = e;
				end = s;
			}
			else 
			{
				blockStartHouseNum = s.getBlockNumEW();
				blockEndHouseNum = e.getBlockNumEW();
				start = s;
				end = e;
			}
		}
		else 
		{
			// error
			System.out.println("Block Creation Error: Cannot detect valid intersections.");
		}
		
		blockLength = blockEndHouseNum - blockStartHouseNum;
		allAddresses = new LinkedList<Address>();
		goDown = new Stack<Address>();
		goUp = new LinkedList<Address>();
		lastNumDelivered = 0;
		populate();
		isDone = false;
		mailManLoc = 2;
		
	}
	
	// Accessors
	public Intersection getStart() {
		return start;
	}
	
	public Intersection getEnd() {
		return end;
	}
	
	public int getLength() {
		return blockLength;
	}
	
	public Street getStreet() {
		return street;
	}
	
	public String getBlockName() {
		return street.getStreetName();
	}
	
	public String getTopRoadName() {
		return top.getStreetName();
	}
	
	public String getBtmRoadName() {
		return bottom.getStreetName();
	}
	
	public List<Address> getAddresses(){
		return allAddresses;
	}
	
	public int getLastNumDelivered() {
		return lastNumDelivered;
	}
	
	// Mutators
	public void addAddress(String name, int num, int let) {
		allAddresses.add(new Address(name, num, let));
	}
	
	// Helpers
	private void populate() {
		String streetName = street.getStreetName();
		int houseNum = blockStartHouseNum;
		int numLetters = 0;
		
		//System.out.println("Creating mail for " + blockStartHouseNum + " block of " + streetName);
		//System.out.println(" ");
		
		for (int i = 0; i < blockLength - 1; i++) 
		{
			houseNum++;
			//System.out.println("POPULATING MAIL FOR ADDRESS: " + houseNum + " " + streetName);
			numLetters = (int)(Math.random() * 5);
			addAddress(streetName, houseNum, numLetters);
		}
		//System.out.println(" ");
		//System.out.println("SORTING MAIL FOR THE BLOCK");
		//System.out.println("***********************************************");
		for (int j = 0; j < allAddresses.size(); j++) 
		{
			Address temp = allAddresses.get(j);
			
			if ((temp.getStreetNumber() % 2) == 0) 
			{
				goUp.add(temp);
			}
			else 
			{
				goDown.add(temp);
			}
		}
	}
	
	// Delete this method????
	public void deliver() 
	{
		//System.out.println("");
		//System.out.println("***************************************");
		//System.out.println("Delivering mail for " + blockStartHouseNum + " block of " + street.getStreetName());
		//System.out.println("");
		
		while (!goUp.isEmpty()) 
		{
			Address temp = goUp.poll();
			System.out.printf("Delivered %d letters to %s.\n", temp.getNumberOfLetters(), temp.getAddressString());
			temp.deliverMail();
		}
		System.out.println(" ");
		
		while (!goDown.isEmpty()) 
		{
			Address temp = goDown.pop();
			System.out.printf("Delivered %d letters to %s.\n", temp.getNumberOfLetters(), temp.getAddressString());
			temp.deliverMail();
		}
		System.out.println(" ");
		System.out.println("FINISHED DELIVERING MAIL FOR THIS BLOCK.");
	}
	
	public boolean deliverNext() {
		boolean returnVal = false;
		if (!goUp.isEmpty()) {
			Address temp = goUp.poll();
			lastNumDelivered = temp.getNumberOfLetters();
			temp.deliverMail();
			mailManLoc+=4;
			if (goUp.isEmpty()) {
				mailManLoc-=2;
			}
			
			returnVal = true;
		}
		else if (!goDown.isEmpty())
		{
			Address temp = goDown.pop();
			lastNumDelivered = temp.getNumberOfLetters();
			temp.deliverMail();
			mailManLoc-=4;
			returnVal = true;
		}
		else 
		{
			lastNumDelivered = 0;
			isDone = true;
			returnVal = false;
		}
		return returnVal;
	}
}
