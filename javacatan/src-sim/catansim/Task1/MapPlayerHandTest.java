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
}
