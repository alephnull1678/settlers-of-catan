// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

import java.util.ArrayList;
import java.util.List;

public class Validator {

	public List<Action> getValidActions(
	        StaticBoard board,
	        PlayerID playerID,
	        Catalog<Resource> resourcesOwned,
	        Catalog<PieceTypes> piecesOwned
	) {
	    if (board == null) throw new IllegalArgumentException("board cannot be null");
	    if (playerID == null) throw new IllegalArgumentException("playerID cannot be null");
	    if (piecesOwned == null) throw new IllegalArgumentException("piecesOwned cannot be null");

	    List<Action> actions = new ArrayList<>();

	    for (PieceTypes type : PieceTypes.values()) {

	        // 1) resources? (only check if provided)
	        if (resourcesOwned != null && !canAfford(type, resourcesOwned)) continue;

	        // 2) pieces?
	        if (piecesOwned != null && piecesOwned.getCount(type) <= 0) continue;

	        // 3) board allows it?
	        if (type == PieceTypes.ROAD) {
	            addValidRoadActions(board, playerID, type, actions);
	        } else {
	            addValidBuildingActions(board, playerID, type, actions);
	        }
	    }

	    return actions;
	}


    private boolean canAfford(PieceTypes type, Catalog<Resource> resourcesOwned) {
        Catalog<Resource> cost = type.getCost();

        for (Resource r : Resource.values()) {
            int need = cost.getCount(r);
            if (need <= 0) continue;

            if (resourcesOwned.getCount(r) < need) return false;
        }
        return true;
    }

    private void addValidBuildingActions(
            StaticBoard board,
            PlayerID playerID,
            PieceTypes type,
            List<Action> out
    ) {
        //Type is SETTLEMENT or CITY
        for (Node n : board.getNodes()) {
            if (board.canPlace(type, playerID, n)) {
                out.add(new Action(n, type));
            }
        }
    }

    private void addValidRoadActions(
            StaticBoard board,
            PlayerID playerID,
            PieceTypes type,
            List<Action> out
    ) {
        for (Node a : board.getNodes()) {
            for (Node b : a.getNeighbours()) {

                // Avoid duplicates if adjacency is symmetric
                // Replace getNodeID() with whatever ID your Node has
                if (a.getNodeID() >= b.getNodeID()) continue;

                if (board.canPlace(type, playerID, a, b)) {
                    out.add(new Action(a, b, PieceTypes.ROAD));
                }
            }
        }
    }
    
    
    
    public List<Action> getAllActionsFromNode(Node node) {
        if (node == null) throw new IllegalArgumentException("node cannot be null");

        List<Action> actions = new ArrayList<>();

        // ------------------
        // Building actions
        // ------------------
        actions.add(new Action(node, PieceTypes.SETTLEMENT));
        actions.add(new Action(node, PieceTypes.CITY));

        // ------------------
        // Road actions
        // ------------------
        for (Node neighbour : node.getNeighbours()) {
            if (neighbour == null) continue;

            actions.add(new Action(
                    node, neighbour,
                    PieceTypes.ROAD
            ));
        }

        return actions;
    }
}
