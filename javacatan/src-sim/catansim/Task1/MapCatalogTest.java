package catansim.Task1;

import static org.junit.Assert.*;

import java.beans.Transient;

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
}