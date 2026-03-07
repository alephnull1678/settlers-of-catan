package catansim.Task1;

import org.junit.Test;
import static org.junit.Assert.*;

import catansim.*;

// Behaviors to be tested:
// 		1: Connect to tiles
//		2: Connect to other nodes
//		3: Hold buildings
public class NodeTest {
	
	// Test that node does not allow more than 3 tiles
	@Test(expected = IllegalStateException.class)
	public void testFailOnForthTile() {
	    Node node = new Node(1);

	    node.addTile(new Tile(Resource.WOOD, 4, 1));
	    node.addTile(new Tile(Resource.BRICK, 5, 2));
	    node.addTile(new Tile(Resource.SHEEP, 6, 3));
	    node.addTile(new Tile(Resource.WHEAT, 8, 4));
	}

}
