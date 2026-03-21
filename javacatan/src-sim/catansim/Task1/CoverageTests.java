package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

public class CoverageTests {
	
	private HardWiredBoard board;
    private PlayerID playerA = PlayerID.BLUE;
    private PlayerID playerB = PlayerID.ORANGE;

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
	
	
    @Test
    public void testRestore() {
    	board = new HardWiredBoard();
    	
        board.restore(board.createMemento());
    }
}
