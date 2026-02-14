// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

/************************************************************/
/**
 * 
 */
public class Action {
	
	
	private Node[] nodes;
	private PieceTypes pieceType;
	
	/**
     * @param nodes  the target node for this action
     * @param piece the building to place
     */
	public Action(Node node, PieceTypes pieceType) {
        
		this.nodes = new Node[] { node };
        this.pieceType = pieceType;
    }
	
	/**
     * @param nodes  the target node for this action
     * @param piece the building to place
     */
	public Action(Node node1, Node node2, PieceTypes pieceType) {
        
		this.nodes = new Node[] { node1, node2 };
        this.pieceType = pieceType;
    }
	
	/**
	 * 
	 * @return 
	 */
	public Node[] getNodes() {
		return nodes.clone();
	}

	/**
	 * 
	 * @return 
	 */
	public PieceTypes getPieceType() {
		return pieceType;
	}
}
