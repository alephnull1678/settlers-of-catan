package catansim;

import java.util.Random;

public class AgentPlayer extends Player {


	private final Random rng = new Random();

    public AgentPlayer(PlayerID playerID) {
        super(playerID);
    }
	
	//Overwriting abstract choose method to randomly choose from list of actions
	public Action chooseAction(Action[] actions)
	{
	if (actions == null || actions.length == 0) return null;
	        return actions[rng.nextInt(actions.length)];
	}
        
}