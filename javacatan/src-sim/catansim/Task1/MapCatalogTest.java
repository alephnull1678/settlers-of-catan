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
}