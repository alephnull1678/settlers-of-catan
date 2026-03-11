package catansim;

import java.util.Arrays;

public class Node {

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

    public Node(int nodeID) {
        this.nodeID = nodeID;
    }

    public void addTile(Tile tile) {
        if (tileCount >= MAX_TILES) {
            throw new IllegalStateException("Node " + nodeID + " already has 3 tiles.");
        }
        tiles[tileCount++] = tile;
    }

    public Tile[] getTiles() {
        return Arrays.copyOf(tiles, tileCount);
    }

    public void connectNode(Node node) {
        if (neighborCount >= MAX_NEIGHBORS) {
            throw new IllegalStateException("Node " + nodeID + " already has 3 neighbors.");
        }
        neighbors[neighborCount++] = node;
    }

    public Node[] getNeighbours() {
        return Arrays.copyOf(neighbors, neighborCount);
    }

    public void addRoad(Road road) {
        if (roadCount >= MAX_ROADS) {
            throw new IllegalStateException("Node " + nodeID + " already has 3 roads.");
        }
        roads[roadCount++] = road;
    }

    public Road[] getConnectedRoads() {
        return Arrays.copyOf(roads, roadCount);
    }

    public void placeBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public int getNodeID() {
        return nodeID;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Node other = (Node) obj;
        return nodeID == other.nodeID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(nodeID);
    }
}