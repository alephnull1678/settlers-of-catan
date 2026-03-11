package catansim;

public class StateMachine {

    private GameStates currentState;

    public StateMachine() {
        currentState = GameStates.SETUP_WAIT;
    }

    public GameStates getCurrentState() {
        return currentState;
    }

    // Reads action, returns next state and advances through the FSM
    public GameStates read(Action a) {
        if (a == null) {
            throw new IllegalArgumentException("action cannot be null");
        }

        ActionTypes type = a.getActionType();

        switch (currentState) {

            case SETUP_WAIT:
                if (type == ActionTypes.GO) {
                    currentState = GameStates.SETUP_SETTLEMENT;
                    return currentState;
                }
                return null;

            case SETUP_SETTLEMENT:
                if (type == ActionTypes.BUILD) {
                    currentState = GameStates.SETUP_ROAD;
                    return currentState;
                }
                return null;

            case SETUP_ROAD:
                if (type == ActionTypes.BUILD) {
                    currentState = GameStates.SETUP_WAIT;
                    return currentState;
                }
                return null;

            case NEW_TURN:
                if (type == ActionTypes.GO) {
                    currentState = GameStates.WAITING_FOR_ROLL;
                    return currentState;
                }
                return null;

            case WAITING_FOR_ROLL:
                if (type == ActionTypes.ROLL) {
                    currentState = GameStates.PLAYER_ACTIONS;
                    return currentState;
                }
                return null;

            case PLAYER_ACTIONS:
                if (type == ActionTypes.LIST || type == ActionTypes.BUILD) {
                    return currentState; // stay in PLAYER_ACTIONS
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

    // Force transition back to the start of a player's turn
    public GameStates goRoll() {
        currentState = GameStates.NEW_TURN;
        return currentState;
    }

    // Switch from setup FSM to main FSM
    public GameStates goMain() {
        currentState = GameStates.NEW_TURN;
        return currentState;
    }
}