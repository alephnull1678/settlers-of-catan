
package catansim;

public interface PlayerHand {
	
    boolean addCard(Resource resource, Integer count);
    
    boolean removeCard(Resource resource, Integer count);
    
    boolean removeHand(Catalog<Resource> catalog);
    
    boolean addHand(Catalog<Resource> catalog);
}
