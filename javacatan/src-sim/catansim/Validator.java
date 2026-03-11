package catansim;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public List<Action> getValidActions(
            StaticBoard board,
            PlayerID playerID,
            Catalog<Resource> resourcesOwned,
            Catalog<PieceTypes> piecesOwned,
            GameStates state
    ) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (playerID == null) {
            throw new IllegalArgumentException("playerID cannot be null");
        }
        if (piecesOwned == null) {
            throw new IllegalArgumentException("piecesOwned cannot be null");
        }
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }

        List<Action> actions = new ArrayList<>();

        //Send in a different set of actions depending on the state
        switch (state) {

            case WAITING_FOR_ROLL:
                actions.add(new Action(ActionTypes.ROLL));
                return actions;

            case NEW_TURN:
                actions.add(new Action(ActionTypes.GO));
                return actions;

            case PLAYER_ACTIONS:
                actions.add(new Action(ActionTypes.LIST));
                actions.add(new Action(ActionTypes.GO));

                for (PieceTypes type : PieceTypes.values()) {

                    if (resourcesOwned != null && !canAfford(type, resourcesOwned)) {
                        continue;
                    }

                    if (piecesOwned.getCount(type) <= 0) {
                        continue;
                    }

                    if (type == PieceTypes.ROAD) {
                        addValidRoadActions(board, playerID, actions);
                    } else {
                        addValidBuildingActions(board, playerID, type, actions);
                    }
                }

                return actions;

            default:
                return actions;
        }
    }

    private boolean canAfford(PieceTypes type, Catalog<Resource> resourcesOwned) {
        Catalog<Resource> cost = type.getCost();

        for (Resource r : Resource.values()) {
            int need = cost.getCount(r);
            if (need > 0 && resourcesOwned.getCount(r) < need) {
                return false;
            }
        }
        return true;
    }

    private void addValidBuildingActions(
            StaticBoard board,
            PlayerID playerID,
            PieceTypes type,
            List<Action> out
    ) {
        for (Node n : board.getNodes()) {
            if (board.canPlace(type, playerID, n)) {
                out.add(new BuildAction(n, type));
            }
        }
    }

    private void addValidRoadActions(
            StaticBoard board,
            PlayerID playerID,
            List<Action> out
    ) {
        for (Node a : board.getNodes()) {
            for (Node b : a.getNeighbours()) {
                if (a.getNodeID() >= b.getNodeID()) {
                    continue;
                }

                if (board.canPlace(PieceTypes.ROAD, playerID, a, b)) {
                    out.add(new BuildAction(a, b, PieceTypes.ROAD));
                }
            }
        }
    }

    public List<Action> getAllActionsFromNode(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }

        List<Action> actions = new ArrayList<>();

        actions.add(new BuildAction(node, PieceTypes.SETTLEMENT));
        actions.add(new BuildAction(node, PieceTypes.CITY));

        for (Node neighbour : node.getNeighbours()) {
            if (neighbour == null) {
                continue;
            }

            actions.add(new BuildAction(node, neighbour, PieceTypes.ROAD));
        }

        return actions;
    }
}