package catansim.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import catansim.MapCatalog;
import catansim.Catalog;

public class MapCatalogTest {

    @Test
    public void test_add_increasesCount() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 3);

        assertEquals("Count should increase after add", 3, catalog.getCount("Wood"));
    }

    @Test
    public void test_add_multipleTimes_accumulates() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 2);
        catalog.add("Wood", 3);

        assertEquals("Counts should accumulate", 5, catalog.getCount("Wood"));
    }

    @Test
    public void test_remove_success() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Brick", 4);

        boolean removed = catalog.remove("Brick", 2);

        assertTrue("Remove should succeed", removed);
        assertEquals("Count should decrease", 2, catalog.getCount("Brick"));
    }

    @Test
    public void test_remove_failure_whenNotEnough() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Ore", 1);

        boolean removed = catalog.remove("Ore", 3);
        
        assertFalse("Remove should fail if insufficient resources", removed);
        assertEquals("Count should remain unchanged", 1, catalog.getCount("Ore"));
    }

    @Test
    public void test_remove_exactAmount_removesEntry() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Sheep", 3);

        catalog.remove("Sheep", 3);
        
        boolean removed = catalog.remove("Sheep", 3);

        assertTrue("Remove should succeed when removing exact amount", removed);
        assertEquals("Removing exact amount should result in zero", 0, catalog.getCount("Sheep"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_snapshot_isReadOnly() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 2);

        Catalog<String> snapshot = catalog.snapshot();

        snapshot.add("Wood", 1);
    }

    @Test
    public void test_snapshot_preservesCounts() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wheat", 5);

        Catalog<String> snapshot = catalog.snapshot();

        assertEquals("Snapshot should keep resource counts", 5, snapshot.getCount("Wheat"));
    }
}