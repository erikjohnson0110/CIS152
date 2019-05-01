package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JComponent;

import domain_components.Address;
import domain_components.Block;

/** This class is used to render a block object within a JPanel.
 * All of the information needed to render the block is stored in the
 * block class.
 * @author Erik E Johnson
 *
 */
@SuppressWarnings("serial")
public class MailDraw extends JComponent {
	private boolean blockLoaded = false;                         // flag for if a block has been loaded into this object - initially false
	                                                             // constants for drawing the border and window
	private final int N_POINTS_SQUARE = 4;                       // Constant for the number of points in a square/rectangle (4)
	private final int[] xPointsBorder = {0, 0, 500, 500};        // constant array of x-coordinates used in drawing the component's border
	private final int[] yPointsBorder = {0, 500, 500, 0};        // constant array of y-coordinates used in drawing the component's border
	
	                                                             // constants for drawing top road
	private final int[] xPointsTopRoad = {0 , 0, 500, 500};      // constant array of x-coordinates used in drawing the top road
	private final int[] yPointsTopRoad = {40, 60, 60, 40};       // constant array of y-coordinates used in drawing the top road
	private String topRoadName;                                  // a string to store the top road's name for display
	private final int[] xPointsTopNameBox = {200, 200, 300, 300};// a constant array of x-coordinates use in drawing the road name background box
	private final int[] yPointsTopNameBox = {45, 55, 55, 45};    // a constant array of y-coordinates use in drawing the road name background box
	
	                                                             // constants for drawing bottom road
	private final int[] xPointsBtmRoad = {0 , 0, 500, 500};      // constant array of x-coordinates used in drawing the bottom road
	private final int[] yPointsBtmRoad = {440, 460, 460, 440};   // constant array of y-coordinates used in drawing the bottom road
	private String btmRoadName;                                  // a string to store the bottom road's name for display
	private final int[] xPointsBtmNameBox = {200, 200, 300, 300};// a constant array of x-coordinates use in drawing the road name background box
	private final int[] yPointsBtmNameBox = {445, 455, 455, 445};// a constant array of y-coordinates use in drawing the road name background box
	
	                                                                // constants for drawing block road
	private final int[] xPointsBlock = {240, 240, 260, 260};        // constant array of x-coordinates used in drawing the block road
	private final int[] yPointsBlock = {60, 440, 440, 60};          // constant array of y-coordinates used in drawing the block road
	private String blockName;                                       // a string to store the block road's name for display
	private final int[] xPointsBlockNameBox = {200, 200, 300, 300}; // a constant array of x-coordinates use in drawing the road name background box
	private final int[] yPointsBlockNameBox = {240, 260, 260, 240}; // a constant array of y-coordinates use in drawing the road name background box
	
	                                                                // variables for drawing individual addresses
	private int addressMinY = 60;                                   // variable to store the min y-value of each address, initially at 60
	private int addressMaxY = 440;                                  // variable to store the max y-value of each address, initially at 440
	                                                                // all addresses draw between these two values 60 and 440
	                                                                // left side addresses
	private int leftMinX = 120;                                     // min x-value for addresses on the left side of the screen
	private int leftMaxX = 240;                                     // max x-value for addresses on the left side of the screen
	
	                                                                // right side addresses
	private int rightMinX = 260;                                    // min x-value for addresses on the right side of the screen
	private int rightMaxX = 380;                                    // max x-value for addresses on the right side of the screen
	
	                                                                
	List<int[]> coordsToDraw = new LinkedList<int[]>();             // stores a list of int[]s which are the coordinates of all addresses to draw
	
	List<Address> allAddresses = new LinkedList<Address>();         // stores a list of all addresses on this block
	Queue<Address> evens = new LinkedList<Address>();               // stores the even numbered addresses
	Queue<Address> odds = new LinkedList<Address>();                // stores the odd numbered addresses
	
	private int mailManLoc;                                         // an int that stores which address the mail carrier is currently at
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * This method contains instructions for painting the graphic
	 */
	public void paintComponent(Graphics g) {                             // paint component starts with drawing the border and main roads
		g.setColor(Color.BLACK);                                         // set color to black
		g.drawPolygon(xPointsBorder, yPointsBorder, N_POINTS_SQUARE);    // use values to draw border
		g.fillPolygon(xPointsTopRoad, yPointsTopRoad, N_POINTS_SQUARE);  // use values to draw top road
		g.fillPolygon(xPointsBtmRoad, yPointsBtmRoad, N_POINTS_SQUARE);  // use values to draw bottom road
		g.fillPolygon(xPointsBlock, yPointsBlock, N_POINTS_SQUARE);      // use values to draw the middle "Block" road
		
		
		if (blockLoaded)                                                 // if a block has been loaded, draw it
		{	                                                             // first, draw address squares
			if (coordsToDraw.size() > 0) {                               // if there are coordinates to draw
				int adrInd = 0;                                          // start at index 0
				for (int a = 0; a <= (coordsToDraw.size()-2); a+=2) {    // iterate through each coordinate, incrementing by 2 each time
					                                                     // this is because 2 int[]s are needed (1 for x-coords, 1 for y-coords)
					Address current = null;                              // set a variable for current address, and initialize as null
					if (adrInd < allAddresses.size()) {                  // if the current array index is less than the size of the array
						current = allAddresses.get(adrInd);              // set current to the address at the current index
					}
					else {                                               // else don't get any more addresses, we're done
					}
					
					int[] xP = coordsToDraw.get(a);                      // save the current x-coordinates to a new array
					int[] yP = coordsToDraw.get((a+1));                  // save the current y-coordinates to a new array
					
					String aString = "Vacant Lot";                       // set address display string to "Vacant Lot" by default
					String lString = "";                                 // set number of letters string to blank.
					if (current != null) {                               // if current address is not null
						aString = current.getAddressString();            // set the address string to current address name
						lString = current.getNumberOfLetters() + " letters"; // set number of letters string to this addresses number of letters
						if (current.getNumberOfLetters() != 0)           // if number of letters is NOT zero, paint green or red (leave grey if 0 letters)
						{
							if (current.isDelievered()) {                // if address is delivered, make it green
								g.setColor(Color.GREEN);
								g.fillPolygon(xP, yP, N_POINTS_SQUARE);
							}
							else                                         // if address is NOT delivered, make it red
							{
								g.setColor(Color.RED);
								g.fillPolygon(xP, yP, N_POINTS_SQUARE);
							}
						}
					}
					
					g.setColor(Color.BLACK);                             // address is now colored. set color to black
					g.drawPolygon(xP, yP, N_POINTS_SQUARE);              // add black border around address  
					
					if (mailManLoc == a) {                               // if mail carrier location is current address, draw mail carrier, if not skip
						g.setColor(Color.BLUE);
						g.fillRect(xP[0], yP[0], 15, 15);
					}
					
					g.setColor(Color.BLACK);                             // set color to black
					g.drawString(aString, (xP[0] + 10), (yP[0] + 20));   // draw the address name string (ex "303 42nd Street")
					g.drawString(lString, (xP[0] + 10), (yP[0] + 35));   // draw the number of letters string (ex "5 letters")
					adrInd++;                                            // address is drawn, so increment index to next address
				}
			}
			                                                            // next draw street names and display boxes on top of everything, so they dont get burried
			g.setColor(Color.WHITE);                                    // change color to white for background box
			g.fillPolygon(xPointsTopNameBox, yPointsTopNameBox, N_POINTS_SQUARE);  // draw top road name box
			g.fillPolygon(xPointsBtmNameBox, yPointsBtmNameBox, N_POINTS_SQUARE);  // draw bottom road name box
			g.fillPolygon(xPointsBlockNameBox, yPointsBlockNameBox, N_POINTS_SQUARE); // draw block road name box
			                                                            // draw name text
			g.setColor(Color.BLACK);                                    // change color to black
			g.setFont(Font.getFont("Arial"));                           // set font to Arial
			g.drawString(topRoadName, 210, 56);                         // draw top road name
			g.drawString(btmRoadName, 210, 456);                        // draw btm road name
			g.drawString(blockName, 210, 256);                          // draw block name
		}
	}                                                                   // block is done!
	
	/** This method loads a block into the graphic drawing
	 * object.  It accepts a block object, stores it, and 
	 * gets it ready to be drawn.
	 * @param b -  the block object to be drawn
	 */
	public void setBlockGraphic(Block b) {
		blockLoaded = true;                 // set block loaded flag to true
		topRoadName = b.getTopRoadName();   // get top road name from block object
		btmRoadName = b.getBtmRoadName();   // get bottom road name from block object
		blockName = b.getBlockName();       // get the BLOCK name text from the block object
		mailManLoc = b.mailManLoc;          // set the mailman location
		
		int blockSize = b.getLength();                                // an int to store the current block's length.
		int lotsPerSide = (blockSize / 2);                            // calculate lots per side
		int lotHeight = ((addressMaxY - addressMinY) / lotsPerSide);  // figure out lot height based on number of lots per side
		
		int lotMinYBoundAccumulator = addressMinY;                         // accumulators that store y-axis values for lots as we draw them
		int lotMaxYBoundAccumulator = lotMinYBoundAccumulator + lotHeight; // max y value for the current lot
		
		int[] leftLotXPoints = {leftMinX, leftMinX, leftMaxX, leftMaxX};      // int array of x-axis coords for left side lots.  these don't change 
		int[] rightLotXPoints = {rightMinX, rightMinX, rightMaxX, rightMaxX}; // int array of x-axis coords for right side lots.  these don't change
		
		allAddresses = b.getAddresses();                                      // get all addresses for this block
		
		evens.clear();                                                       // clear list of even addresses to ensure any previous blocks are cleared out
		odds.clear();                                                        // clear list of odd addresses to ensure previous blocks are cleared out
		for (int i = 0; i < allAddresses.size(); i++)                        // iterate through each address
		{
			if ((allAddresses.get(i).getStreetNumber() % 2) == 0)            // if address# divided by 2 has a remainder of 0, it is even
			{
				evens.add(allAddresses.get(i));                              // add to even side
			}
			else if ((allAddresses.get(i).getStreetNumber() % 2) == 1)       // if it has a remainder of 1, then it is odd
			{
				odds.add(allAddresses.get(i));                               // add it to odd side

			}
		}
		
		List<int[]> tempCoords = new LinkedList<int[]>();                   // create a temporary linked list to store these coordinates
		for (int j = 0; j < lotsPerSide; j++) {                             // create both left and right lots at the same time
			int[] tempY = {lotMinYBoundAccumulator, lotMaxYBoundAccumulator, lotMaxYBoundAccumulator, lotMinYBoundAccumulator};  // set y-axis coords from accumulators
			tempCoords.add(leftLotXPoints);                                 // add x-axis constants
			tempCoords.add(tempY);                                          // add y-axis coordinates for current address
			
			tempCoords.add(rightLotXPoints);                                // do the same for the right side of the road
			tempCoords.add(tempY);
			
			lotMinYBoundAccumulator += lotHeight;                           // increment min y-axis accumulator by lot height
			lotMaxYBoundAccumulator += lotHeight;                           // increment max y-axis accumulator by lot height
		}
		coordsToDraw = tempCoords;                                          // save list to Coords To Draw list so they can be drawn
	} 
	
}
