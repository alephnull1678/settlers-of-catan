
package catansim;

/**
 * Generic catalog of counted items.
 * Defines all operations any catalog must support.
 */
public interface Catalog<T>{
	
	public int getCount(T unit);
    /**
     * Adds a quantity of a unit.
     */
    boolean add(T unit, int amount);

    /**
     * Removes a quantity of a unit.
     * Returns false if insufficient quantity.
     */
    boolean remove(T unit, int amount);

    /**
     * Returns a snapshot (copy) of this catalog.
     * The snapshot should not affect the original.
     */
    Catalog<T> snapshot();
    
    
}
