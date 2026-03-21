package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Scanner;

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
    
    @Test
    public void gameTest() {
    	//The game requires a list of all players in the game, the board to be used, the validator used to validate actions, the dice to be used, and the maximum number of rounds.
		//For this simulation, we will use a hard-wired board that creates the same collection of tiles, resources and dice numbers every time.
		//We will also use basic Player Agents that will do a linear search through every possible valid move (provided by the Validator) and choose randomly.
		//Furthermore, we will be using a Human Player that can read player commands and execute them in-game.
		//We will be sending a MultiDice made up of two dice (the range of the rolls for this sim will be 1-12.)
		
        //Create board and validator
        Board board = new HardWiredBoard();
        Validator validator = new Validator();
        
        //Create dice
        MultiDice dice = new MultiDice();
        dice.addDice(new RegularDice());
        dice.addDice(new RegularDice());

        //Create scanner for human input
        Scanner scanner = new Scanner(System.in);

        //Create state machine
        StateMachine stateMachine = new StateMachine();

        //Create players
        /*Player[] players = new Player[] {
        	    new HumanPlayer(PlayerID.BLUE, scanner),
        	    new AgentPlayer(PlayerID.RED, new IntelligentDecisionStrategy()),
        	    new AgentPlayer(PlayerID.WHITE, new IntelligentDecisionStrategy()),
        	    new AgentPlayer(PlayerID.ORANGE, new IntelligentDecisionStrategy())
        	};*/
        
        Player[] players = new Player[] {
        	    new AgentPlayer(PlayerID.BLUE, new IntelligentDecisionStrategy()),
        	    new AgentPlayer(PlayerID.RED, new IntelligentDecisionStrategy()),
        	    new AgentPlayer(PlayerID.WHITE, new IntelligentDecisionStrategy()),
        	    new AgentPlayer(PlayerID.ORANGE, new IntelligentDecisionStrategy())};

        //Run the game. The game will end either when a Player reaches 10 victory points or the game reaches the max number of rounds as assigned by maxRounds.
        //Every round, the game will output to the terminal:
        //The dice roll
        //The round and the player's chosen actions
        //If applicable: an awarding of a player's Longest Road award
        //The number of victory points for each player
        
        //Note that due to this simulation not implementing trading, development cards, etc. there is a possibility that the players may choose to place their starting settlements in areas where
        //they do not receive the necessary resources to place any pieces down. In this case, rounds may often reach the maxRounds threshold without a winner. This is to be expected.
        
        int maxRounds = 8192;
        Game game = new Game(players, board, validator, dice, stateMachine, maxRounds);
        game.addVisualizer(new GamePythonVisualizer(board));
        game.run();

        scanner.close();
    }
}
