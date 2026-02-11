package catansim;

import java.util.ArrayList;
import java.util.List;

public class Validator {

	public List<Action> getValidActions(
	        StaticBoard board,
	        PlayerID playerID,
	        Catalog<Resource> resourcesOwned,
	        Catalog<BuildingTypes> piecesOwned
	) {
	    if (board == null) throw new IllegalArgumentException("board cannot be null");
	    if (playerID == null) throw new IllegalArgumentException("playerID cannot be null");
	    if (piecesOwned == null) throw new IllegalArgumentException("piecesOwned cannot be null");

	    List<Action> actions = new ArrayList<>();

	    for (BuildingTypes type : BuildingTypes.values()) {

	        // 1) resources? (only check if provided)
	        if (resourcesOwned != null && !canAfford(type, resourcesOwned)) continue;

	        // 2) pieces?
	        if (piecesOwned.getCount(type) <= 0) continue;

	        // 3) board allows it?
	        if (type == BuildingTypes.ROAD) {
	            addValidRoadActions(board, playerID, type, actions);
	        } else {
	            addValidBuildingActions(board, playerID, type, actions);
	        }
	    }

	    return actions;
	}


    private boolean canAfford(BuildingTypes type, Catalog<Resource> resourcesOwned) {
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
            BuildingTypes type,
            List<Action> out
    ) {
        //Type is SETTLEMENT or CITY
        for (Node n : board.getAllNodes()) {
            if (board.canPlace(type, playerID, n)) {
                out.add(new Action(n, type));
            }
        }
    }

    private void addValidRoadActions(
            StaticBoard board,
            PlayerID playerID,
            BuildingTypes type,
            List<Action> out
    ) {
        for (Node a : board.getAllNodes()) {
            for (Node b : board.getAdjacentNodes(a)) {

                // Avoid duplicates if adjacency is symmetric
                // Replace getNodeID() with whatever ID your Node has
                if (a.getNodeID() >= b.getNodeID()) continue;

                if (board.canPlace(type, playerID, a, b)) {
                    out.add(new Action(a, b, BuildingTypes.ROAD));
                }
            }
        }
    }
}
