// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/**
 * Represents a road placed on the board.
 */
public class Road implements Piece {
	
	private PlayerID owner;
	
	/**
	 * Constructor for road.
	 * @param owner The player who owns the road.
	 */
	public Road(PlayerID owner) {
		this.owner = owner;
	}
	
	/**
	 * Returns the player who owns this road.
	 */
	@Override
	public PlayerID getOwnerPlayerID() {
		return owner;
	}
	
	/**
	 * Returns the type of this piece. 
	 */
	@Override
	public BuildingTypes getType() {
		return BuildingTypes.ROAD;
	}
}
