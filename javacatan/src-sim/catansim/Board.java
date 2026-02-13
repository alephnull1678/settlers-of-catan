// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/************************************************************/
/**
 * 
 */
public interface Board extends StaticBoard {
	
	/**
	 * @param road The road piece being placed.
	 * @param playerID The player who owns the road.
	 * @param nodeOne One endpoint node of the road.
	 * @param nodeTwo The other endpoint node of the road.
	 */
	public void placePiece(Road road, PlayerID playerID, Node nodeOne, Node nodeTwo);
	
	/**
	 *
	 * @param building The building piece being placed.
	 * @param playerID The player who owns the building.
	 * @param node The node where the building is placed.
	 */
	public void placePiece(Building building, PlayerID playerID, Node node);

	/**
	 * @param diceNum 
	 * @param playerID 
	 * @return 
	 */
	public Catalog<Resource> collect(int diceNum, PlayerID playerID);
	
	/**
	 * 
	 * @param playerID 
	 * @param node 
	 * @return 
	 */
	public Catalog<Resource> collectFirst(PlayerID playerID, Node node);

}
