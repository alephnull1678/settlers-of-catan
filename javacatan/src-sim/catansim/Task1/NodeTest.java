package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

// Behaviors to be tested:
// 		1: Connect to tiles
//		2: Hold buildings
//		3: Nodes attach to nodes
public class NodeTest {
	private HardWiredBoard board;
    private PlayerID playerA = PlayerID.BLUE;
    private PlayerID playerB = PlayerID.ORANGE;
	
	// 1: Test that node does not allow more than 3 tiles
	@Test(expected = IllegalStateException.class)
	public void testFailOnForthTile() {
	    Node node = new Node(1);

	    // Adding tiles to one node
	    node.addTile(new Tile(Resource.WOOD, 4, 1));
	    node.addTile(new Tile(Resource.BRICK, 5, 2));
	    node.addTile(new Tile(Resource.SHEEP, 6, 3));
	    node.addTile(new Tile(Resource.WHEAT, 8, 4)); //Fail here
	}
	
	// 2: Test that the node can store a building
	@Test
	public void testNodeStoresBuilding() {
		Node node = new Node(10);
		Building settlement = new Building(PlayerID.BLUE, PieceTypes.SETTLEMENT);
		
		node.placeBuilding(settlement); //Places building on node
		
		assertEquals(settlement, node.getBuilding()); //Check that node has building 
	}
	
	// 3: Test that a node stores it connection to another node
	@Test
	public void testNodeStoresNeibhoursNode() {
		// Creating Nodes
		Node a = new Node(1);
		Node b = new Node(2);
		
		// Connect Nodes (a --> b)
		a.connectNode(b);
		
		// Get list of neighbor/connected nodes
		Node[] neighbours = a.getNeighbours();
		
		// Checks if the neighbor node is there
		boolean found = false;
		for (Node n : neighbours) {
			if(n == b) {
				found = true;
			}
			
		}
		assertTrue(found);
	}
	
	// 4: Test that HardWiredBoard Works
	@Test
	public void testHardWiredBoard() {
		Board board = new HardWiredBoard();
		assertTrue(board != null);
	}
	
	
	@Test
    public void testNodesExist() {
    	board = new HardWiredBoard();
    	
        assertNotNull(board.getNodes());
        assertEquals(54, board.getNodes().length);
    }

    @Test
    public void testRobberInitialTile() {
    	board = new HardWiredBoard();
    	
        assertNotNull(board.getRobberTile());
    }

    @Test
    public void testPlaceBuildingAndCollect() {
    	board = new HardWiredBoard();
    	
        Node node = board.getNodes()[0];
        Building settlement = new Building(playerA, PieceTypes.SETTLEMENT);
        board.placePiece(settlement, playerA, node);

        // Node should have the building
        assertEquals(settlement, node.getBuilding());

        // Collect resources on dice roll 10 (TILE 0 WOOD/10)
        Catalog<Resource> collected = board.collect(10, playerA);
        assertEquals(1, collected.getCount(Resource.WOOD));
    }

    @Test
    public void testCanPlaceSettlementRules() {
    	board = new HardWiredBoard();
    	
        Node node = board.getNodes()[0];

        // First settlement should be placeable
        assertTrue(board.canPlace(PieceTypes.SETTLEMENT, playerA, node));

        board.placePiece(new Building(playerA, PieceTypes.SETTLEMENT), playerA, node);

        // Cannot place settlement on same node
        assertFalse(board.canPlace(PieceTypes.SETTLEMENT, playerA, node));

        // Cannot place settlement adjacent to existing settlement
        Node neighbour = node.getNeighbours()[0];
        assertFalse(board.canPlace(PieceTypes.SETTLEMENT, playerA, neighbour));
    }

    @Test
    public void testCanPlaceCityRules() {
    	board = new HardWiredBoard();
    	
        Node node = board.getNodes()[0];
        // Must have settlement first
        assertFalse(board.canPlace(PieceTypes.CITY, playerA, node));

        // Place settlement first
        board.placePiece(new Building(playerA, PieceTypes.SETTLEMENT), playerA, node);
        assertTrue(board.canPlace(PieceTypes.CITY, playerA, node));

        // Place city
        board.placePiece(new Building(playerA, PieceTypes.CITY), playerA, node);
        assertFalse(board.canPlace(PieceTypes.CITY, playerA, node));
    }


    @Test
    public void testLongestRoad() {
    	board = new HardWiredBoard();
    	
        Node node0 = board.getNodes()[0];
        Node node1 = node0.getNeighbours()[0];
        Road road = new Road(playerA);
        board.placePiece(road, playerA, node0, node1);

        int length = board.getLongestRoadLength(playerA);
        assertEquals(1, length);

        PlayerID holder = board.checkLongestRoad();
        // Should not qualify yet (less than 5)
        assertNull(holder);
    }


    @Test
    public void testIllegalArguments() {
    	board = new HardWiredBoard();
    	
        assertThrows(IllegalArgumentException.class, () -> board.getLongestRoadLength(null));
    }
}
