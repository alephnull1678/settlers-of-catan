// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/**
 * Represents a settlement building.
 */
public class Settlement extends Building {
	
	/**
	 * Constructor for settlment
	 * @param owner The player who owns the settlement
	 */
	public Settlement(PlayerID owner) {
		super(node, owner);
		setResourceAmount(1);
	}
	
	/**
	 * Returns the type of this piece.
	 */
	@Override
	public BuildingTypes getType() {
		return BuildingTypes.SETTLEMENT;
	}
}