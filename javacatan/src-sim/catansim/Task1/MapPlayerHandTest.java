package catansim.Task1;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import catansim.MapPlayerHand;
import catansim.MapCatalog;
import catansim.Resource;

/**
 * Unit tests for MapPlayerHand.
 * These tests verify adding cards, removing cards
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

    // Test successful removal of cards when the player has enough resources
    @Test
    public void test_removeCard_success() {

        MapPlayerHand hand = new MapPlayerHand();

        hand.addCard(Resource.BRICK, 3);

        boolean removed = hand.removeCard(Resource.BRICK, 2);

        assertTrue("Remove should succeed when enough resources exist", removed);
        assertEquals("Resource count should decrease after removal`", 1, hand.getCount(Resource.BRICK));
    }
}
