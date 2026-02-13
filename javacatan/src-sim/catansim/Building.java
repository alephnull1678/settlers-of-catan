// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/**
 * Represents a generic building in the game.
 */
public abstract class Building implements Piece {
	
	private PlayerID owner;
	private int resourceAmount;
	
	/**
	 * Constructor for building.
	 * @param owner The player who owns the building.
	 */
	public Building(PlayerID owner) {
		this.owner = owner;
	}
	
	/**
	 * Returns the player owns this building
	 */
	@Override
	public PlayerID getOwnerPlayerID() {
		return owner;
	}
	
	/**
	 * Returns how many resources this building produces.
	 */
	public int getResourceAmount() {
		return resourceAmount;
	}
	
	/**
	 * Sets how many resources this building produces. 
	 */
	public void setResourceAmount(int resourceAmount) {
		this.resourceAmount = resourceAmount;
	}
}