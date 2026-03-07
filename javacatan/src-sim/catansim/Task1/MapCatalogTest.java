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

        assertEquals("Count after getting wood", 3, catalog.getCount("Wood"));
    }
}