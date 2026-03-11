package catansim;

/************************************************************/
/**
 * Represents a player's hand of resources.
 * Uses composition by aggregating a Catalog<Resource>.
 */
public class PlayerHand {

    private final Catalog<Resource> catalog;

    public PlayerHand() {
        this.catalog = new HashMapCatalog<Resource>();
    }

    public boolean addCard(Resource resource, int count) {
        if (resource == null || count <= 0) {
            return false;
        }
        return catalog.add(resource, count);
    }

    public boolean removeCard(Resource resource, int count) {
        if (resource == null || count <= 0) {
            return false;
        }
        return catalog.remove(resource, count);
    }

    public boolean addHand(Catalog<Resource> catalogIn) {
        if (catalogIn == null) {
            return false;
        }

        for (Resource r : Resource.values()) {
            int amt = catalogIn.getCount(r);
            if (amt > 0) {
                catalog.add(r, amt);
            }
        }
        return true;
    }

    public boolean removeHand(Catalog<Resource> catalogOut) {
        if (catalogOut == null) {
            return false;
        }

        // First pass: check feasibility
        for (Resource r : Resource.values()) {
            int amt = catalogOut.getCount(r);
            if (amt > 0 && catalog.getCount(r) < amt) {
                return false;
            }
        }

        // Second pass: actually remove
        for (Resource r : Resource.values()) {
            int amt = catalogOut.getCount(r);
            if (amt > 0) {
                catalog.remove(r, amt);
            }
        }
        return true;
    }

    public int getCount(Resource unit) {
        return catalog.getCount(unit);
    }

    public boolean add(Resource unit, int amount) {
        if (unit == null || amount <= 0) {
            return false;
        }
        catalog.add(unit, amount);
        return true;
    }

    public boolean remove(Resource unit, int count) {
        return catalog.remove(unit, count);
    }

    public Catalog<Resource> snapshot() {
        return catalog.snapshot();
    }
}