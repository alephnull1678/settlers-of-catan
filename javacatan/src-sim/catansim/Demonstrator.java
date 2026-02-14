// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

public class Demonstrator {

	public static void main(String[] args) {

        //Create core components
        Board board = new HardWiredBoard();
        Validator validator = new Validator();
        
        //Create dice
        MultiDice dice = new MultiDice();
        dice.addDice(new RegularDice());
        dice.addDice(new RegularDice());

        //Create players
        Player[] players = new Player[] {
                new Player(PlayerID.Player1),
                new Player(PlayerID.Player2),
                new Player(PlayerID.Player3),
                new Player(PlayerID.Player4)
        };

        //Run game
        int maxRounds = 8000;
        Game game = new Game(players, board, validator, dice, maxRounds);
        game.run();
    }
}
