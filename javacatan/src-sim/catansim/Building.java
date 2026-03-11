package catansim;

/**
 * Represents a generic building in the game.
 */
public class Building implements Piece {

    private final PlayerID owner;
    private final PieceTypes type;
    private final int resourceAmount;

    public Building(PlayerID owner, PieceTypes type) {
        if (owner == null) {
            throw new IllegalArgumentException("owner cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        this.owner = owner;
        this.type = type;
        this.resourceAmount = type.getResourceAmount();
    }

    @Override
    public PlayerID getOwnerPlayerID() {
        return owner;
    }

    @Override
    public PieceTypes getType() {
        return type;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }
}