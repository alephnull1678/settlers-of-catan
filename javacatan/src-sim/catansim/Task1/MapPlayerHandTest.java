package catansim.Task1;

import static org.junit.Assert.*;
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
}
