// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/**
 * Represents a city building.
 */
public class City extends Building {
	
	/**
	 * Constructor for city.
	 * @param owner The player who owns the city.
	 */
	public City(PlayerID owner) {
		super(owner);
		setResourceAmount(2);
	}
	
	/**
	 * Returns the type of this piece.
	 */
	@Override
	public PieceTypes getType() {
		return PieceTypes.CITY;
	}
}