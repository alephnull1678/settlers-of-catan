// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

import java.util.HashMap;
import java.util.Map;

public class MapCatalog<T> implements Catalog<T> {

    private final Map<T, Integer> counts;

    /**
     * Default constructor — starts empty
     */
    public MapCatalog() {
        this.counts = new HashMap<>();
    }

    @Override
    public int getCount(T unit) {
        if (unit == null) return 0;
        return counts.getOrDefault(unit, 0);
    }

    /**
     * Add units to the catalog
     */
    @Override
    public boolean add(T unit, int amount) {
        if (unit == null || amount <= 0) return false;

        counts.put(unit, getCount(unit) + amount);
        return true;
    }

    /**
     * Remove units from the catalog
     */
    @Override
    public boolean remove(T unit, int amount) {
        if (unit == null || amount <= 0) return false;

        int current = getCount(unit);
        if (current < amount) return false;

        int newValue = current - amount;

        if (newValue == 0) counts.remove(unit);
        else counts.put(unit, newValue);

        return true;
    }

    /**
     * Returns a READ-ONLY snapshot of this catalog.
     * The returned Catalog cannot modify the original.
     */
    @Override
    public Catalog<T> snapshot() {
        Map<T, Integer> copy = new HashMap<>(counts);

        return new Catalog<T>() {
            @Override
            public int getCount(T unit) {
                return copy.getOrDefault(unit, 0);
            }

            @Override
            public boolean add(T unit, int amount) {
                throw new UnsupportedOperationException("Snapshot is read-only");
            }

            @Override
            public boolean remove(T unit, int amount) {
                throw new UnsupportedOperationException("Snapshot is read-only");
            }

            @Override
            public Catalog<T> snapshot() {
                return this; // snapshot of snapshot
            }
        };
    }

}
