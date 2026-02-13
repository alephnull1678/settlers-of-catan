package catansim;

public class Node {

    // Maximum values for Catan board geometry
    private static final int MAX_TILES = 3;
    private static final int MAX_NEIGHBORS = 3;
    private static final int MAX_ROADS = 3;

    private final int nodeID;

    private Tile[] tiles = new Tile[MAX_TILES];
    private int tileCount = 0;

    private Node[] neighbors = new Node[MAX_NEIGHBORS];
    private int neighborCount = 0;

    private Road[] roads = new Road[MAX_ROADS];
    private int roadCount = 0;

    private Building building = null;

    // --------------------------------------------------
    // Constructor
    // --------------------------------------------------

    public Node(int nodeID) {
        this.nodeID = nodeID;
    }

    // --------------------------------------------------
    // Tile Handling
    // --------------------------------------------------

    public void addTile(Tile tile) {
        if (tileCount >= MAX_TILES) {
            throw new IllegalStateException(
                "Node " + nodeID + " already has 3 tiles."
            );
        }
        tiles[tileCount++] = tile;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    // --------------------------------------------------
    // Neighbor Handling
    // --------------------------------------------------

    public void connectNode(Node node) {
        if (neighborCount >= MAX_NEIGHBORS) {
            throw new IllegalStateException(
                "Node " + nodeID + " already has 3 neighbors."
            );
        }
        neighbors[neighborCount++] = node;
    }

    public Node[] getNeighbours() {
        return neighbors;
    }

    // --------------------------------------------------
    // Road Handling
    // --------------------------------------------------

    public void addRoad(Road road) {
        if (roadCount >= MAX_ROADS) {
            throw new IllegalStateException(
                "Node " + nodeID + " already has 3 roads."
            );
        }
        roads[roadCount++] = road;
    }

    public Road[] getConnectedRoads() {
        return roads;
    }

    // --------------------------------------------------
    // Building Handling
    // --------------------------------------------------

    public void placeBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    // --------------------------------------------------
    // Utility
    // --------------------------------------------------

    public int getNodeID() {
        return nodeID;
    }
}
