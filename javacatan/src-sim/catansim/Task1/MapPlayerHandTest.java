package catansim.Task1;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import catansim.MapPlayerHand;
import catansim.MapCatalog;
import catansim.Resource;

/**
 * Unit tests for MapPlayerHand.
 * These tests verify adding cards, removing cards,
 * and hand removal behaviour
 */
public class MapPlayerHandTest {
    
    // Tests that adding cards increases the resource count
    @Test
    public void test_addCard_increasesCount() {

        MapPlayerHand hand = new MapPlayerHand();

        hand.addCard(Resource.WOOD, 2);

        assertEquals("Player should have 2 wood after adding", 2, hand.getCount(Resource.WOOD));
    }

    // Tests that addCard rejects invalid input (null resource)
    @Test
    public void test_addCard_invalidInput_returnsFalse() {

        MapPlayerHand hand = new MapPlayerHand();

        boolean added = hand.addCard(null, 2);

        assertFalse("Adding null resources should fail", added);
    }

    // Tests successful removal of cards when the player has enough resources
    @Test
    public void test_removeCard_success() {

        MapPlayerHand hand = new MapPlayerHand();

        hand.addCard(Resource.BRICK, 3);

        boolean removed = hand.removeCard(Resource.BRICK, 2);

        assertTrue("Remove should succeed when enough resources exist", removed);
        assertEquals("Resource count should decrease after removal", 1, hand.getCount(Resource.BRICK));
    }

    // Tests that removal fails when attempting to remove more resources then available
    @Test
    public void test_removeCard_failure_whenNotEnough() {

        MapPlayerHand hand = new MapPlayerHand();

        hand.addCard(Resource.ORE, 1);

        boolean removed = hand.removeCard(Resource.ORE, 3);

        assertFalse("Remove should fail when insufficient resources", removed);
        assertEquals("Resource count should remain unchanged", 1, hand.getCount(Resource.ORE));
    }

    // Tests that removeHand fails when the player lacks required resources
    // and that the player's hand remains unchanged 
    @Test
    public void test_removeHand_atomicFailure() {

        MapPlayerHand hand = new MapPlayerHand();

        hand.addCard(Resource.WOOD, 2);
        hand.addCard(Resource.BRICK, 1);

        MapCatalog<Resource> cost = new MapCatalog<>();
        cost.add(Resource.WOOD, 2);
        cost.add(Resource.BRICK, 3);

        boolean removed = hand.removeHand(cost);

        assertFalse("removeHand should fail if any resource is insufficient", removed);

        assertEquals("Wood should remain unchanged", 2, hand.getCount(Resource.WOOD));
        assertEquals("Brick should remain unchanged", 1, hand.getCount(Resource.BRICK));
    }

    // Tests that addHand correctly adds multiple resources from another catalog
    @Test
    public void test_addHand_multipleResources() {

        MapPlayerHand hand = new MapPlayerHand();

        MapCatalog<Resource> incoming = new MapCatalog<>();
        incoming.add(Resource.WOOD, 2);
        incoming.add(Resource.BRICK, 1);

        boolean added = hand.addHand(incoming);

        assertTrue("addHand should succeed when given a valid catalog", added);
        assertEquals("Wood should be added to the hand", 2, hand.getCount(Resource.WOOD));
        assertEquals("Brick should be added to the hand", 1, hand.getCount(Resource.BRICK));
    }
}