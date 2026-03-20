package catansim;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class IntelligentDecisionStrategy implements DecisionStrategy {

    private final Random rng = new Random();

    @Override
    public Action decideAction(Player player, List<Action> actions, StaticBoard board) {
        if (player == null || board == null || actions == null || actions.isEmpty()) {
            return null;
        }

        // Constraint: more than 7 cards -> spend cards if possible
        if (countCards(player) > 7) {
            List<Action> spendingActions = filterSpendingActions(actions);
            if (!spendingActions.isEmpty()) {
                return chooseBestByValue(player, spendingActions, board);
            }
        }

        // Constraint: if there is a road-connecting opportunity, prefer it
        List<Action> connectorRoads = filterRoadActionsThatConnect(player, actions, board);
        if (!connectorRoads.isEmpty()) {
            return chooseBestByValue(player, connectorRoads, board);
        }

        // Constraint: defend longest road if another player is close
        if (anotherPlayerIsCloseToLongestRoad(player, board)) {
            List<Action> connectedRoads = filterRoadActionsThatConnect(player, actions, board);
            if (!connectedRoads.isEmpty()) {
                return chooseBestByValue(player, connectedRoads, board);
            }
        }

        return chooseBestByValue(player, actions, board);
    }

    private boolean anotherPlayerIsCloseToLongestRoad(Player player, StaticBoard board) {
        int myLength = board.getLongestRoadLength(player.getPlayerID());

        for (PlayerID other : PlayerID.values()) {
            if (other == player.getPlayerID()) {
                continue;
            }

            int otherLength = board.getLongestRoadLength(other);
            if (otherLength >= myLength - 1) {
                return true;
            }
        }

        return false;
    }

    private List<Action> filterRoadActionsThatConnect(Player player, List<Action> actions, StaticBoard board) {
        List<Action> result = new ArrayList<>();

        for (Action action : actions) {
            if (action instanceof BuildAction buildAction
                    && buildAction.getPieceType() == PieceTypes.ROAD
                    && board.canConnectRoads(action, player.getPlayerID())) {
                result.add(action);
            }
        }

        return result;
    }

    private Action chooseBestByValue(Player player, List<Action> actions, StaticBoard board) {
        double bestValue = Double.NEGATIVE_INFINITY;
        List<Action> bestActions = new ArrayList<>();

        for (Action action : actions) {
            double value = evaluateAction(player, action);

            if (value > bestValue) {
                bestValue = value;
                bestActions.clear();
                bestActions.add(action);
            } else if (value == bestValue) {
                bestActions.add(action);
            }
        }

        return bestActions.get(rng.nextInt(bestActions.size()));
    }

    private double evaluateAction(Player player, Action action) {
        if (action instanceof BuildAction buildAction) {
            if (buildAction.getPieceType() == PieceTypes.SETTLEMENT
                    || buildAction.getPieceType() == PieceTypes.CITY) {
                return 1.0;
            }

            if (buildAction.getPieceType() == PieceTypes.ROAD) {
                return 0.8;
            }
        }

        if (spendsCardsAndLeavesBelowFive(player, action)) {
            return 0.5;
        }

        return 0.0;
    }

    private List<Action> filterSpendingActions(List<Action> actions) {
        List<Action> result = new ArrayList<>();
        for (Action action : actions) {
            if (actionCost(action) > 0) {
                result.add(action);
            }
        }
        return result;
    }

    private boolean spendsCardsAndLeavesBelowFive(Player player, Action action) {
        int currentCards = countCards(player);
        int cost = actionCost(action);
        return cost > 0 && (currentCards - cost) < 5;
    }

    private int actionCost(Action action) {
        if (action instanceof BuildAction buildAction) {
            return countResources(buildAction.getPieceType().getCost());
        }

        return 0;
    }

    private int countCards(Player player) {
        return countResources(player.getResourceCatalog());
    }

    private int countResources(Catalog<Resource> catalog) {
        int total = 0;
        for (Resource resource : Resource.values()) {
            total += catalog.getCount(resource);
        }
        return total;
    }
}