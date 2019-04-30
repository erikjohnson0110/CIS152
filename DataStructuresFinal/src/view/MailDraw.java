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

@SuppressWarnings("serial")
public class MailDraw extends JComponent {
	private boolean blockLoaded = false;
	
	// constants for drawing the border and window
	private final int N_POINTS_SQUARE = 4;
	private final int[] xPointsBorder = {0, 0, 500, 500};
	private final int[] yPointsBorder = {0, 500, 500, 0};
	
	// constants for drawing top road
	private final int[] xPointsTopRoad = {0 , 0, 500, 500};
	private final int[] yPointsTopRoad = {40, 60, 60, 40};
	private String topRoadName;
	private final int[] xPointsTopNameBox = {200, 200, 300, 300};
	private final int[] yPointsTopNameBox = {45, 55, 55, 45};
	
	// constants for drawing bottom road
	private final int[] xPointsBtmRoad = {0 , 0, 500, 500};
	private final int[] yPointsBtmRoad = {440, 460, 460, 440};
	private String btmRoadName;
	private final int[] xPointsBtmNameBox = {200, 200, 300, 300};
	private final int[] yPointsBtmNameBox = {445, 455, 455, 445};
	
	// constants for drawing block road
	private final int[] xPointsBlock = {240, 240, 260, 260};
	private final int[] yPointsBlock = {60, 440, 440, 60};
	private String blockName;
	private final int[] xPointsBlockNameBox = {200, 200, 300, 300};
	private final int[] yPointsBlockNameBox = {240, 260, 260, 240};
	
	// variables for drawing individual addresses
	private int addressMinY = 60;
	private int addressMaxY = 440;
	
	// left side addresses
	private int leftMinX = 120;
	private int leftMaxX = 240;
	
	// right side addresses
	private int rightMinX = 260;
	private int rightMaxX = 380;
	
	// final address draw
	List<int[]> coordsToDraw = new LinkedList<int[]>();
	//int[] evenCoordIndices;
	//int[] oddCoordIndices;
	
	List<Address> allAddresses = new LinkedList<Address>();
	Queue<Address> evens = new LinkedList<Address>();
	Queue<Address> odds = new LinkedList<Address>();
	
	// test address draw
	//private final int[] testAddressesX = {leftMinX, leftMinX, leftMaxX, leftMaxX};
	//private final int[] testAddressesY = {addressMinY, addressMaxY, addressMaxY, addressMinY};
	Queue<Address> testEvens = new LinkedList<Address>();
	Queue<Address> testOdds = new LinkedList<Address>();
	
	private int mailManLoc;
	
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawPolygon(xPointsBorder, yPointsBorder, N_POINTS_SQUARE);
		g.fillPolygon(xPointsTopRoad, yPointsTopRoad, N_POINTS_SQUARE);
		g.fillPolygon(xPointsBtmRoad, yPointsBtmRoad, N_POINTS_SQUARE);
		g.fillPolygon(xPointsBlock, yPointsBlock, N_POINTS_SQUARE);
		
		// test
		//System.out.println("DRAW ADDRESSES");
		//System.out.println("Coords To Draw: " + coordsToDraw.size());
		if (blockLoaded) 
		{	
			// draw address squares
			if (coordsToDraw.size() > 0) {
				int adrInd = 0;
				for (int a = 0; a <= (coordsToDraw.size()-2); a+=2) {
					
					Address current = null;
					if (adrInd < allAddresses.size()) {
						current = allAddresses.get(adrInd);
					}
					else {
					}
					
					int[] xP = coordsToDraw.get(a);
					int[] yP = coordsToDraw.get((a+1));
					
					//System.out.println("xP: " + xP[0] + " " + xP[1] + " " + xP[2] + " " + xP[3]);
					//System.out.println("yP: " + yP[0] + " " + yP[1] + " " + yP[2] + " " + yP[3]);
					//System.out.println(" ");
					
					String aString = "Vacant Lot";
					String lString = "";
					if (current != null) {
						aString = current.getAddressString();
						lString = current.getNumberOfLetters() + " letters";
						if (current.getNumberOfLetters() != 0) 
						{
							if (current.isDelievered()) {
								g.setColor(Color.GREEN);
								g.fillPolygon(xP, yP, N_POINTS_SQUARE);
							}
							else 
							{
								g.setColor(Color.RED);
								g.fillPolygon(xP, yP, N_POINTS_SQUARE);
							}
						}
					}
					g.setColor(Color.BLACK);
					g.drawPolygon(xP, yP, N_POINTS_SQUARE);
					
					if (mailManLoc == a) {
						// draw mailman
						g.setColor(Color.BLUE);
						g.fillRect(xP[0], yP[0], 15, 15);
					}
					
					adrInd++;
					
					// test print start index of coords to print
					g.setColor(Color.BLACK);
					//String aString = "index " + a;
					g.drawString(aString, (xP[0] + 10), (yP[0] + 20));
					g.drawString(lString, (xP[0] + 10), (yP[0] + 35));
				}
			}
			// draw street names
			// draw squares for names
			g.setColor(Color.WHITE);
			g.fillPolygon(xPointsTopNameBox, yPointsTopNameBox, N_POINTS_SQUARE);
			g.fillPolygon(xPointsBtmNameBox, yPointsBtmNameBox, N_POINTS_SQUARE);
			g.fillPolygon(xPointsBlockNameBox, yPointsBlockNameBox, N_POINTS_SQUARE);
				
			// print names
			g.setColor(Color.BLACK);
			g.setFont(Font.getFont("Arial"));
			g.drawString(topRoadName, 210, 56);
			g.drawString(btmRoadName, 210, 456);
			g.drawString(blockName, 210, 256);
		}
	}
	
	public void setBlockGraphic(Block b) {
		blockLoaded = true;
		topRoadName = b.getTopRoadName();
		btmRoadName = b.getBtmRoadName();
		blockName = b.getBlockName();
		mailManLoc = b.mailManLoc;
		
		int blockSize = b.getLength();
		int lotsPerSide = (blockSize / 2);
		int lotHeight = ((addressMaxY - addressMinY) / lotsPerSide);
		
		int lotMinYBoundAccumulator = addressMinY; // initial value set to minY
		int lotMaxYBoundAccumulator = lotMinYBoundAccumulator + lotHeight;
		
		int[] leftLotXPoints = {leftMinX, leftMinX, leftMaxX, leftMaxX};
		//int[] leftLotYPoints = {lotMinYBoundAccumulator, lotMaxYBoundAccumulator, lotMaxYBoundAccumulator, lotMinYBoundAccumulator};
		
		int[] rightLotXPoints = {rightMinX, rightMinX, rightMaxX, rightMaxX};
		//int[] rightLotYPoints = {lotMinYBoundAccumulator, lotMaxYBoundAccumulator, lotMaxYBoundAccumulator, lotMinYBoundAccumulator};
		
		allAddresses = b.getAddresses();
		//evenCoordIndices = new int[lotsPerSide];
		//oddCoordIndices = new int[lotsPerSide];
		
		evens.clear();
		odds.clear();
		for (int i = 0; i < allAddresses.size(); i++) 
		{
			if ((allAddresses.get(i).getStreetNumber() % 2) == 0) 
			{
				evens.add(allAddresses.get(i));
			}
			else if ((allAddresses.get(i).getStreetNumber() % 2) == 1) 
			{
				odds.add(allAddresses.get(i));

			}
		}
		
		List<int[]> tempCoords = new LinkedList<int[]>();
		for (int j = 0; j < lotsPerSide; j++) {
			int[] tempY = {lotMinYBoundAccumulator, lotMaxYBoundAccumulator, lotMaxYBoundAccumulator, lotMinYBoundAccumulator};
			tempCoords.add(leftLotXPoints);
			tempCoords.add(tempY);
			
			tempCoords.add(rightLotXPoints);
			tempCoords.add(tempY);
			
			lotMinYBoundAccumulator += lotHeight;
			lotMaxYBoundAccumulator += lotHeight;
		}
		coordsToDraw = tempCoords;
	}
	
}
