package control;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import domain_components.Address;
import domain_components.Block;
import domain_components.Intersection;
import domain_components.Street;

public class MailControl {
	private List<Street> streetsNorthSouth;
	private List<Street> streetsEastWest;
	private List<Intersection> intersections;
	private Queue<Block> blocks;
	private List<Address> addresses;
	
	private Block current;
	
	private Intersection[][] intersectionArray;
	private int arrRows;
	private int arrCols;
	
	// constructor
	public MailControl(){
		streetsNorthSouth = new LinkedList<Street>();
		streetsEastWest = new LinkedList<Street>();
		
		intersections = new LinkedList<Intersection>();
		
		blocks = new LinkedList<Block>();
		
		addresses = new LinkedList<Address>();
		current = null;
	}
	
	// Accessors and Mutators
	public Block getCurrentBlock() {
		return current;
	}
	
	public Block getNextBlock() {
		return blocks.poll();
	}
	
	// Helper methods
	public void generateNeighborhood() {
		try {
			initialize();
			loadStreetsEastWest();
			loadStreetsNorthSouth();
			initializeArrays();
			
			createIntersections();
			createBlocks();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public boolean deliverNext() {
		boolean returnVal = false;
		if (current != null) {
			returnVal = current.deliverNext();
		}
		return returnVal;
	}
	
	
	// Private functions to encapsulate functionality.
	// This will allow me to add functionality later.
	private void initialize() {
		streetsEastWest.clear();
		streetsNorthSouth.clear();
		addresses.clear();
		blocks.clear();
		current = null;
	}
	
	private void loadStreetsEastWest() {
		streetsEastWest.add(new Street("Douglas Avenue", "EW", 310));
		streetsEastWest.add(new Street("Hickman Road", "EW", 300));
		streetsEastWest.add(new Street("University Avenue", "EW", 280));
	}
	
	private void loadStreetsNorthSouth() {
		streetsNorthSouth.add(new Street("62nd Street", "NS", 620));
		streetsNorthSouth.add(new Street("63rd Street", "NS", 630));
	}
	
	private void initializeArrays() {
		arrRows = streetsEastWest.size();
		arrCols = streetsNorthSouth.size();
		intersectionArray = new Intersection[arrRows][arrCols];
	}
	
	private void createIntersections() {
		// go through each east west street and make intersections
		for (int i = 0; i < streetsEastWest.size(); i++) 
		{
			// go through each north south street
			for (int j = 0; j < streetsNorthSouth.size(); j++) 
			{
				Intersection tempInt = new Intersection(streetsNorthSouth.get(j), streetsEastWest.get(i));
				intersections.add(tempInt);
				intersectionArray[i][j] = tempInt;
			}
		}
	}
	
	private void createBlocks() {
		// go through intersections and create blocks
		// outer loop checks every intersection
		for (int k = 0; k < arrRows; k++) {
			for (int l = 0; l < arrCols; l++) {
				if ((l + 1) < arrCols) {
					//System.out.println("Created Block");
					//System.out.println(intersectionArray[k][l].print());
					//System.out.println(intersectionArray[k][(l + 1)].print());
					blocks.add(new Block(intersectionArray[k][l], intersectionArray[k][(l + 1)]));
				}
				if ((k + 1) < arrRows) {
					//System.out.println("Created Block");
					//System.out.println(intersectionArray[k][l].print());
					//System.out.println(intersectionArray[(k + 1)][(l)].print());
					blocks.add(new Block(intersectionArray[k][l], intersectionArray[(k + 1)][(l)]));
				}
			}
		}

		for (int m = 0; m < blocks.size(); m++) {
			Block temp = blocks.poll();
			List<Address> tempList = temp.getAddresses();
			for (int n = 0; n < tempList.size(); n++) {
				addresses.add(tempList.get(n));
			}
			blocks.add(temp);
		}
	}
}
