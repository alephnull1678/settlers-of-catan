package catansim;
import java.util.List;

public interface DecisionStrategy{
	
	public Action decideAction(Player player, List<Action> actions, StaticBoard board);
	
}
