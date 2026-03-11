package catansim;

public class StateMachine {

    private GameStates currentState;

    public StateMachine() {
        currentState = GameStates.SETUP_SETTLEMENT;
    }

    public GameStates getCurrentState() {
        return currentState;
    }

    //Reads action, returns next action and advances through the FSM
    public GameStates read(Action a) {
        if (a == null) {
            throw new IllegalArgumentException("action cannot be null");
        }

        ActionTypes type = a.getActionType();

        switch (currentState) {

            case SETUP_SETTLEMENT:
                if (type == ActionTypes.BUILD) {
                    currentState = GameStates.SETUP_ROAD;
                    return currentState;
                }
                return null;

            case SETUP_ROAD:
                if (type == ActionTypes.BUILD) {
                    currentState = GameStates.SETUP_SETTLEMENT;
                    return currentState;
                }
                return null;

            case WAITING_FOR_ROLL:
                if (type == ActionTypes.ROLL) {
                    currentState = GameStates.NEW_TURN;
                    return currentState;
                }
                return null;

            case NEW_TURN:
                if (type == ActionTypes.GO) {
                    currentState = GameStates.PLAYER_ACTIONS;
                    return currentState;
                }
                return null;

            case PLAYER_ACTIONS:
                if (type == ActionTypes.LIST || type == ActionTypes.BUILD) {
                    return null;
                }
                if (type == ActionTypes.GO) {
                    currentState = GameStates.NEW_TURN;
                    return currentState;
                }
                return null;

            default:
                return null;
        }
    }

    //Force transition back to the roll phase (used by Game when a new round starts)
    public GameStates goRoll() {
        currentState = GameStates.WAITING_FOR_ROLL;
        return currentState;
    }

    //Switch from setup FSM to main FSM
    public GameStates goMain() {
        currentState = GameStates.WAITING_FOR_ROLL;
        return currentState;
    }
}