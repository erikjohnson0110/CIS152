package domain;

import domain_components.Block;
import domain_components.Intersection;

public class Graph {
	private int MAX_VERTS;    // Maximum number of vertices - Set to 5
	private Intersection vertexList[];  // a list of all vertices in the graph
	private int adjacencyMatrix[][];    // adjacency matrix for the graph, displaying edges and weights.
	private int nVerts;  
	
	/** Sole constructor.  Parameter is number of vertices in the graph.
	 * @param verts - Number of vertices in the graph.
	 */
	public Graph(int verts) {
		MAX_VERTS = verts;
		vertexList = new Intersection[MAX_VERTS];              // initialize the vertexList
		adjacencyMatrix = new int[MAX_VERTS][MAX_VERTS];           // initialize the adjacencyMatrix
		nVerts = 0;                                        // initialize the number of vertices to zero
		for (int i = 0; i < MAX_VERTS; i++){               // set all values in the adjacencyMatrix to zero
			for (int j = 0; j < MAX_VERTS; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}
	}
	
	
	/**
	 * Adds a vertex to the Graph.  The vertex added will NOT have any edges assigned.
	 * Edges must be assigned using the addEdge() method.
	 * @param n - The name property of the vertex to be added.
	 */
	public void addIntersection(Intersection i) {
		if (nVerts <= MAX_VERTS)                   // if more vertices can be added
		{
			vertexList[nVerts] = i;                // create a new vertex and add it
			nVerts++;                              // increment the number of vertices counter 
		}
	}
	
	
	/**
	 * Adds an UNDIRECTED edge to the graph.  This will technically
	 * add TWO edges, one from start to end, and one from end to start.
	 * @param startVertName - The name property of the starting vertex
	 * @param endVertName - The name property of the ending vertex
	 * @param weight - The weight of the edge (miles between cities)
	 */
	public void addBlock(Block temp) {
		int startPos = findIntersectionPos(temp.getStart());              // find the index of the starting vertex
		int endPos = findIntersectionPos(temp.getEnd());                  // find the index of the ending vertex
		
		if (startPos >= 0 && endPos >= 0)                         // if both vertices exist
		{                                                         // add two edges to the matrix, one S-->E and one E-->S
			adjacencyMatrix[startPos][endPos] = temp.getLength();           // add the edge to the adjacency matrix using the weight
			adjacencyMatrix[endPos][startPos] = temp.getLength();           // add the edge to the adjacency matrix using the weight
		}
		else                                                      // if both do NOT exist, figure out which is missing
		{
			if (startPos < 0)                                     // if the starting vertex does not exist
			{
				System.out.println("Start vertex not found.");    // output to the console
			}
			
			if (endPos < 0)                                       // if the ending vertex does not exist
			{
				System.out.println("End vertex not found.");      // output to the console
			}
		}
	}
	
	
	
	/**
	 * Returns an integer value that represents the vertex's position
	 * in the vertexList.  If a vertex that matches the search string is 
	 * not found, -1 is returned.  
	 * @param n - Name property string to search for
	 * @return - Integer value of the index of that vertex.  -1 if not found.
	 */
	private int findIntersectionPos(Intersection n) 
	{
		int returnVal = -1;                            // set default return value to -1.  
		for (int i = 0; i < nVerts; i++)               // iterate through the vertex list
		{
			if (n.getBlockNumEW() == vertexList[i].getBlockNumEW())  // if the search string matches the current vertex's name property
			{
				if (n.getBlockNumNS() == vertexList[i].getBlockNumNS()) {
					returnVal = i;                                  // set returnValue to the index value
				}                     
			}
		}
		return returnVal;                              // return the returnValue.  If no vertex is found, this remains -1
	}
	
}
