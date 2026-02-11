
package catansim;

import java.util.EnumMap;
import java.util.Map;

public interface Catalog<T> {
    int getCount(T unit);
}

class MapCatalog implements Catalog<Resource> {

    private final Map<Resource, Integer> counts = new EnumMap<>(Resource.class);

    @Override
    public int getCount(Resource unit) {
        return counts.getOrDefault(unit, 0);
    }

    // This is a helper to update counts internally so we should add this to the UML
    public boolean add(Resource unit, int amount) {
        if (amount <= 0) return false;
        counts.put(unit, getCount(unit) + amount);
        return true;
    }

    public boolean remove(Resource unit, int amount) {
        if (amount <= 0) return false;
        int cur = getCount(unit);
        if (cur < amount) return false;
        int next = cur - amount;
        if (next == 0) counts.remove(unit);
        else counts.put(unit, next);
        return true;
    }
}

