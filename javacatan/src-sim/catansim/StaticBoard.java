// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/************************************************************/
/**
 * 
 */
public interface StaticBoard {
	
	
	public Tile getRobberTile();
	
	/**
	 * 
	 * @return 
	 */
	public Node[] getNodes();
	
	/**
	 * 
	 */
	public class board {
	};

	/**
	 * 
	 * @return 
	 */
	public PlayerID checkLongestRoad();
	
	public int getLongestRoadLength();
	
	public boolean canConnectRoads(Action action, PlayerID playerID);
	
	/**
	 * 
	 * @param road 
	 * @param node 
	 * @return 
	 */
	public boolean canPlace(PieceTypes placedbuildingType, PlayerID playerID, Node nodeStart, Node nodeEnd);
	
	/**
	 * 
	 * @param road 
	 * @param node 
	 * @return 
	 */
	public boolean canPlace(PieceTypes placedbuildingType, PlayerID playerID, Node nodeToPlaceOn);
}
