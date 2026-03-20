package catansim;

import java.util.Arrays;

public class AgentPlayer extends Player {

    private DecisionStrategy decisionStrategy;

    public AgentPlayer(PlayerID playerID) {
        super(playerID);
        this.decisionStrategy = new RandomDecisionStrategy(); // default behaviour
    }
    
    //Alternate
    public AgentPlayer(PlayerID playerID, DecisionStrategy decisionStrategy) {
        super(playerID);

        if (decisionStrategy == null) {
            throw new IllegalArgumentException("decisionStrategy cannot be null");
        }

        this.decisionStrategy = decisionStrategy;
    }

    public void setDecisionStrategy(DecisionStrategy decisionStrategy) {
        if (decisionStrategy == null) {
            throw new IllegalArgumentException("decisionStrategy cannot be null");
        }
        this.decisionStrategy = decisionStrategy;
    }

    public DecisionStrategy getDecisionStrategy() {
        return decisionStrategy;
    }

    @Override
    public Action chooseAction(Action[] actions, StaticBoard board) {
        if (actions == null || actions.length == 0) {
            return null;
        }

        return decisionStrategy.decideAction(this, Arrays.asList(actions), board);
    }
}