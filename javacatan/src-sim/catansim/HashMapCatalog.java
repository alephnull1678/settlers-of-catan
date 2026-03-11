package catansim;

import java.util.HashMap;
import java.util.Map;

public class HashMapCatalog<T> implements Catalog<T> {

    private final Map<T, Integer> counts;
    private final boolean readOnly;

    /**
     * Default constructor — starts empty
     */
    public HashMapCatalog() {
        this.counts = new HashMap<>();
        this.readOnly = false;
    }

    /**
     * Private constructor used for snapshots
     */
    private HashMapCatalog(Map<T, Integer> counts, boolean readOnly) {
        this.counts = new HashMap<>(counts);
        this.readOnly = readOnly;
    }

    @Override
    public int getCount(T unit) {
        if (unit == null) {
            return 0;
        }
        return counts.getOrDefault(unit, 0);
    }

    /**
     * Add units to the catalog
     */
    @Override
    public boolean add(T unit, int amount) {
        if (readOnly) {
            throw new UnsupportedOperationException("Snapshot is read-only");
        }

        if (unit == null || amount <= 0) {
            return false;
        }

        counts.put(unit, getCount(unit) + amount);
        return true;
    }

    /**
     * Remove units from the catalog
     */
    @Override
    public boolean remove(T unit, int amount) {
        if (readOnly) {
            throw new UnsupportedOperationException("Snapshot is read-only");
        }

        if (unit == null || amount <= 0) {
            return false;
        }

        int current = getCount(unit);
        if (current < amount) {
            return false;
        }

        int newValue = current - amount;

        if (newValue == 0) {
            counts.remove(unit);
        } else {
            counts.put(unit, newValue);
        }

        return true;
    }

    /**
     * Returns a read-only snapshot of this catalog.
     */
    @Override
    public Catalog<T> snapshot() {
        return new HashMapCatalog<>(counts, true);
    }

}