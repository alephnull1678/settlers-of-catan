// --------------------------------------------------------
// Manual implementation
// --------------------------------------------------------

package catansim;

import java.util.EnumMap;


/************************************************************/
/**
 * 
 */
public class PieceHandler {
	
	//Max for each piece type
	public static final int MAX_ROADS = 15;
    public static final int MAX_SETTLEMENTS = 5;
    public static final int MAX_CITIES = 4;
    
    //We are going to use a stack pointer in an array so we don't have to actually
    //dereference the pieces when we give them away.
    //We will also be using an EnumMap to avoid switch statements
    private final EnumMap<BuildingTypes, Piece[]> buckets =
            new EnumMap<>(BuildingTypes.class);
    
    private final EnumMap<BuildingTypes, Integer> avail =
            new EnumMap<>(BuildingTypes.class);
    
    public PieceHandler() {
    	for (BuildingTypes type : BuildingTypes.values()) {

            int max = type.maxCount();

            Piece[] arr = new Piece[max];

            for (int i = 0; i < max; i++) {
                arr[i] = type.createPiece();
            }

            buckets.put(type, arr);
            avail.put(type, max);
        }
    }
    
    
    
    //"POP" one piece of type
    public Piece usePiece(BuildingTypes type) {
        if (type == null) throw new IllegalArgumentException("type cannot be null");

        int a = avail.get(type);
        if (a == 0) return null;

        Piece[] arr = buckets.get(type);
        Piece out = arr[a - 1];
        avail.put(type, a - 1);
        return out;
    }
    
    //"PUSH" piece back
    public void refundPiece(BuildingTypes type) {
    	if (type == null) throw new IllegalArgumentException("type cannot be null");
        Piece[] arr = buckets.get(type);
        int a = avail.get(type);

        if (a >= arr.length) return; // already full

        avail.put(type, a + 1);
    }
    
    
    
    //Get count of a resource
    public int getAvailable(BuildingTypes type) {
        if (type == null) throw new IllegalArgumentException("type cannot be null");
        return avail.get(type);
    }
    

}
