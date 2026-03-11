package catansim;

import java.util.Objects;

public class Action {

    private ActionTypes actionType;

    public Action(ActionTypes actionType) {
        if (actionType == null) {
            throw new IllegalArgumentException("actionType cannot be null");
        }
        this.actionType = actionType;
    }

    public ActionTypes getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypes actionType) {
        if (actionType == null) {
            throw new IllegalArgumentException("actionType cannot be null");
        }
        this.actionType = actionType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Action other = (Action) obj;
        return actionType == other.actionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType);
    }
}