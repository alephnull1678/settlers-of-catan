package catansim;

import java.util.EnumMap;

/************************************************************/
/**
 * Manages the pool of pieces available to a player.
 */
public class PieceHandler {

    private final EnumMap<PieceTypes, Piece[]> buckets =
            new EnumMap<>(PieceTypes.class);

    private final EnumMap<PieceTypes, Integer> avail =
            new EnumMap<>(PieceTypes.class);

    public PieceHandler(PlayerID owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner cannot be null");
        }

        for (PieceTypes type : PieceTypes.values()) {
            int max = type.maxCount();
            Piece[] arr = new Piece[max];

            for (int i = 0; i < max; i++) {
                arr[i] = type.createPiece(owner);
            }

            buckets.put(type, arr);
            avail.put(type, max);
        }
    }

    public Piece usePiece(PieceTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        int a = avail.get(type);
        if (a == 0) {
            return null;
        }

        Piece[] arr = buckets.get(type);
        Piece out = arr[a - 1];
        avail.put(type, a - 1);
        return out;
    }

    public void refundPiece(PieceTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        Piece[] arr = buckets.get(type);
        int a = avail.get(type);

        if (a >= arr.length) {
            return;
        }

        avail.put(type, a + 1);
    }

    public int getAvailable(PieceTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        return avail.get(type);
    }
}