// --------------------------------------------------------
// Manually Implemented
// --------------------------------------------------------

package catansim;

/************************************************************/
/**
 * 
 */
public class Tile {
	/**
	 * 
	 */
	private Resource resource;
	/**
	 * 
	 */
	private int diceNum;
	/**
	 * 
	 */
	private int tileID;
	
	
	public Tile(Resource resource, int diceNum, int tileID) {
		this.resource = resource;
		this.diceNum = diceNum;
		this.tileID = tileID;
	}
	
	public int getDiceNum() {
		return diceNum;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public int getTileID() {
		return tileID;
	}
}
