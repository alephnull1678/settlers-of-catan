package catansim;

import java.util.EnumMap;
import java.util.Map;

public class MapCatalog implements Catalog<Resource> {

    private final Map<Resource, Integer> counts = new EnumMap<>(Resource.class);

    @Override
    public int getCount(Resource unit) {
        return counts.getOrDefault(unit, 0);
    }

    public boolean add(Resource unit, int amount) {
        if (amount <= 0) return false;
        counts.put(unit, getCount(unit) + amount);
        return true;
    }

    public boolean remove(Resource unit, int amount) {
        if (amount <= 0) return false;
        int cur = getCount(unit);
        if (cur < amount) return false;
        counts.put(unit, cur - amount);
        return true;
    }
}
