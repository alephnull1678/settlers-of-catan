// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/**
 * Represents any piece placed on the board.
 */
public interface Piece {
	/**
	 * Returns the player who owns this piece.
	 */
	PlayerID getOwnerPlayerID();
	
	/**
	 * Returns the type of this piece. 
	 */
	PieceTypes getType();
}