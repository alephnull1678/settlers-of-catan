
package catansim;

public interface PlayerHand {
	
    boolean addCard(Resource resource, int count);
    
    boolean removeCard(Resource resource, int count);
    
    boolean removeHand(Catalog<Resource> catalog);
    
    boolean addHand(Catalog<Resource> catalog);
}
