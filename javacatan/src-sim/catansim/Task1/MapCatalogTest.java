package catansim.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import catansim.MapCatalog;
import catansim.Catalog;

/**
 * Unit tests for MapCatalog
 * These tests verify add, remove, and snapshot behaviour.
 */
public class MapCatalogTest {

    // Tests that adding resources increases the stored count
    @Test
    public void test_add_increasesCount() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 3);

        assertEquals("Count should increase after add", 3, catalog.getCount("Wood"));
    }

    // Tests that multiple adds accumulate the total correctly
    @Test
    public void test_add_multipleTimes_accumulates() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 2);
        catalog.add("Wood", 3);

        assertEquals("Counts should accumulate", 5, catalog.getCount("Wood"));
    }

    // Tests successful removal when enough resources exist
    @Test
    public void test_remove_success() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Brick", 4);

        boolean removed = catalog.remove("Brick", 2);

        assertTrue("Remove should succeed", removed);
        assertEquals("Count should decrease", 2, catalog.getCount("Brick"));
    }

    // Tests removal failure when trying to remove more resources than available
    @Test
    public void test_remove_failure_whenNotEnough() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Ore", 1);

        boolean removed = catalog.remove("Ore", 3);
        
        assertFalse("Remove should fail if insufficient resources", removed);
        assertEquals("Count should remain unchanged", 1, catalog.getCount("Ore"));
    }

    // Boundary test: removing the exact amount should leave the count at zero
    @Test
    public void test_remove_exactAmount_removesEntry() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Sheep", 3);
        
        boolean removed = catalog.remove("Sheep", 3);

        assertTrue("Remove should succeed when removing exact amount", removed);
        assertEquals("Removing exact amount should result in zero", 0, catalog.getCount("Sheep"));
    }

    // tests that snapshot returns a read only catalog
    @Test(expected = UnsupportedOperationException.class)
    public void test_snapshot_isReadOnly() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 2);

        Catalog<String> snapshot = catalog.snapshot();

        snapshot.add("Wood", 1);
    }

    // Tests that snapshot keeps the resource count from the original catalog
    @Test
    public void test_snapshot_preservesCounts() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wheat", 5);

        Catalog<String> snapshot = catalog.snapshot();

        assertEquals("Snapshot should keep resource counts", 5, snapshot.getCount("Wheat"));
    }

    // Test that adding a null resource fails and does not modify the catalog
    @Test
    public void test_add_nullUnit_returnsFalse() {

        MapCatalog<String> catalog = new MapCatalog<>();

        boolean added = catalog.add(null, 3);

        assertFalse("Adding null resource should fail", added);
        assertEquals("Catalog should remain unchanged", 0, catalog.getCount(null));
    }
}