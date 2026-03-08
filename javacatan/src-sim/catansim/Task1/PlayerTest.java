package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

//Behaviors to be tested:
//		1: Player Holds Resources
//		2: Choose Action
//		3: Constructor Rejects Null Player 
//		4: Failed Piece Purchase 

public class PlayerTest {

	// 1: Test that player stores resources when given
	@Test
	public void testPlayerHoldsResources() {
		// Create Player
		Player player = new Player(PlayerID.BLUE);
		
		// Add resources into a catalog
		MapCatalog<Resource> gained = new MapCatalog<>();
		gained.add(Resource.WOOD, 2);
		gained.add(Resource.ORE, 1);
		
		//Deal resources to player
		player.dealResources(gained);
		
		// Get the players resources
		Catalog<Resource> resources = player.getResourceCatalog();
		
		// Check that the player has the correct resources
		assertEquals(2, resources.getCount(Resource.WOOD));
		assertEquals(1, resources.getCount(Resource.ORE));
	}
	
	// 2: Test that players hand updates when dealt resources 
	@Test
	public void testChooseAction() {
		Player player = new Player(PlayerID.BLUE);
		
		// Create a list of no possible actions
		assertNull(player.chooseAction(new Action[0]));
		// ChooseAction should return null if it correctly identifies there are no possible actions
		assertNull(player.chooseAction(null));
	}
	
	// 3: Test to make sure constructor can handle a null player
	@Test(expected = IllegalArgumentException.class)
	public void testHandleNullPlayer() {
		new Player(null);
	}
	
	// 4: Test to see that a piece purchase fails when the player has insufficient resources
	@Test
	public void testPieceConsumptionWithoutResources() {
		// Default player has no resources
		Player player = new Player(PlayerID.BLUE);
		
		// Attempt to purchase road
		Piece piece = player.consumePiece(PieceTypes.ROAD);
		
		// Return null if purchase was unsuccessful
		assertNull(piece);
	}
}
