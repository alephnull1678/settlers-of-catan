package catansim;

import java.util.List;
import java.util.Random;

public class RandomDecisionStrategy implements DecisionStrategy {

    private final Random rng = new Random();

    @Override
    public Action decideAction(Player player, List<Action> actions, StaticBoard board) {
        if (actions == null || actions.isEmpty()) {
            return null;
        }

        return actions.get(rng.nextInt(actions.size()));
    }
}