// --------------------------------------------------------
// Manual implementation
// --------------------------------------------------------

package catansim;

/************************************************************/
/**
 * 
 */
public class Node {
	/**
	 * 
	 */
	private Tile[] tiles;
	/**
	 * 
	 */
	private int nodeID;
	/**
	 * 
	 */
	private Node[] neighbors;
	/**
	 * 
	 */
	private Road[] roads = null;
	/**
	 * 
	 */
	private Building building = null;
	
	private int tileCount = 0;
	
	public Node(int nodeID, Tile[] tiles) {
		this.tiles = tiles;
		this.neighbors = new Node[3];
		this.nodeID = nodeID;
	}

	/**
	 * 
	 * @param diceNum 
	 * @return 
	 */
	public Tile[] getTiles() {
		return tiles;
	}
	
	public void addTile(Tile tile) {
        if (tileCount < 3) {
            tiles[tileCount] = tile;
            tileCount++;
        } else {
            throw new IllegalStateException("Node already has 3 tiles.");
        }
    }
	
	public Node[] getNeighbours() {
		return neighbors;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public Road[] getConnectedRoads() {
		return roads;
	}

	public void placeBuilding(Building building) {
		this.building = building;
	}
	
	public void addRoad(Road road) {
		roads[roads.length] = road; // add the road to the end of the list
	}
	
	public void connectNode(Node node) {
		neighbors[neighbors.length] = node;
	}
}
