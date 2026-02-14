// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

public class MapPlayerHand implements PlayerHand {

    // Composition: a player hand "has a" catalog of resources
    private final Catalog<Resource> catalog;

    public MapPlayerHand() {
        this.catalog = new MapCatalog<Resource>();
    }

  
    @Override
    public boolean addCard(Resource resource, int count) {
        if (resource == null || count <= 0) return false;
        return catalog.add(resource, count);
    }

 
    //Returns false if the player does not have enough.
    @Override
    public boolean removeCard(Resource resource, int count) {
        if (resource == null || count <= 0) return false;
        return catalog.remove(resource, count);
    }


    @Override
    public boolean addHand(Catalog<Resource> catalogIn) {
        if (catalogIn == null) return false;

        for (Resource r : Resource.values()) {
            int amt = catalogIn.getCount(r);
            if (amt > 0) {
                catalog.add(r, amt);
            }
        }
        return true;
    }


    //Returns false if any resource is insufficient.
    @Override
    public boolean removeHand(Catalog<Resource> catalogOut) {
        if (catalogOut == null) return false;

        // first pass: check feasibility
        for (Resource r : Resource.values()) {
            int amt = catalogOut.getCount(r);
            if (amt > 0 && catalog.getCount(r) < amt) {
                return false;
            }
        }

        // second pass: actually remove
        for (Resource r : Resource.values()) {
            int amt = catalogOut.getCount(r);
            if (amt > 0) {
                catalog.remove(r, amt);
            }
        }
        return true;
    }

  
    
    
    //Override methods to satisfy Catalog implementation
    @Override
    public int getCount(Resource unit) {
        return catalog.getCount(unit);
    }

    @Override
    public boolean add(Resource unit, int amount) {
    	if (unit == null || amount <= 0) return false;
        catalog.add(unit, amount);
        return true;
    }

    @Override
    public boolean remove(Resource unit, int count) {
        return catalog.remove(unit, count);
    }

    @Override
    public Catalog<Resource> snapshot() {
        return catalog.snapshot();
    }
}
