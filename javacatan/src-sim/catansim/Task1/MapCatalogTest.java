package catansim.Task1;

import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.Map;

import org.junit.Test;

import catansim.MapCatalog;
import catansim.Catalog;

public class MapCatalogTest {

    @Test
    public void test_add_increasesCount() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 3);

        assertEquals(3, catalog.getCount("Wood"));
    }

    @Test
    public void test_add_multipleTimes_accumulates() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Wood", 2);
        catalog.add("Wood", 3);

        assertEquals(5, catalog.getCount("Wood"));
    }

    @Test
    public void test_remove_success() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Brick", 4);

        boolean removed = catalog.remove("Brick", 2);

        assertTrue(removed);
        assertEquals(2, catalog.getCount("Brick"));
    }

    @Test
    public void test_remove_failure_whenNotEnough() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Ore", 1);

        boolean removed = catalog.remove("Ore", 3);

        assertEquals(1, catalog.getCount("Ore"));
    }

    @Test
    public void test_remove_exactAmount_removesEntry() {

        MapCatalog<String> catalog = new MapCatalog<>();

        catalog.add("Sheep", 3);

        catalog.remove("Sheep", 3);

        assertEquals(0, catalog.getCount("Sheep"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_snapshopt_isReadOnly() {

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

        assertEquals(5, snapshot.getCount("Wheat"));
    }
}