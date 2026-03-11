// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

import java.util.Scanner;

public class Demonstrator {

	public static void main(String[] args) {

		//The game requires a list of all players in the game, the board to be used, the validator used to validate actions, the dice to be used, and the maximum number of rounds.
		//For this simulation, we will use a hard-wired board that creates the same collection of tiles, resources and dice numbers every time.
		//We will also use basic Player Agents that will do a linear search through every possible valid move (provided by the Validator) and choose randomly.
		//Furthermore, we will be using a Human Player that can read player commands and execute them in-game.
		//We will be sending a MultiDice made up of two dice (the range of the rolls for this sim will be 1-12.)
		
        //Create board and validator
        Board board = new HardWiredBoard();
        Validator validator = new Validator();
        
        //Create dice
        MultiDice dice = new MultiDice();
        dice.addDice(new RegularDice());
        dice.addDice(new RegularDice());

        //Create scanner for human input
        Scanner scanner = new Scanner(System.in);

        //Create state machine
        StateMachine stateMachine = new StateMachine();

        //Create players
        Player[] players = new Player[] {
<<<<<<< Updated upstream
                new HumanPlayer(PlayerID.BLUE, scanner),
                new AgentPlayer(PlayerID.RED),
                new AgentPlayer(PlayerID.YELLOW),
                new AgentPlayer(PlayerID.GREEN)
=======
                new Player(PlayerID.BLUE),
                new Player(PlayerID.RED),
                new Player(PlayerID.WHITE),
                new Player(PlayerID.ORANGE)
>>>>>>> Stashed changes
        };

        //Run the game. The game will end either when a Player reaches 10 victory points or the game reaches the max number of rounds as assigned by maxRounds.
        //Every round, the game will output to the terminal:
        //The dice roll
        //The round and the player's chosen actions
        //If applicable: an awarding of a player's Longest Road award
        //The number of victory points for each player
        
        //Note that due to this simulation not implementing trading, development cards, etc. there is a possibility that the players may choose to place their starting settlements in areas where
        //they do not receive the necessary resources to place any pieces down. In this case, rounds may often reach the maxRounds threshold without a winner. This is to be expected.
        
        int maxRounds = 8192;
<<<<<<< Updated upstream
        Game game = new Game(players, board, validator, dice, stateMachine, maxRounds);
=======
        Game game = new Game(players, board, validator, dice, maxRounds);
        game.addVisualizer(new GamePythonVisualizer(board));
>>>>>>> Stashed changes
        game.run();

        scanner.close();
    }
}