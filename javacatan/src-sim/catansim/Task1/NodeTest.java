package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

// Behaviors to be tested:
// 		1: Connect to tiles
//		2: Hold buildings
//		3: Nodes attach to nodes
public class NodeTest {
	
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
		Settlement settlement = new Settlement(PlayerID.BLUE);
		
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

}
