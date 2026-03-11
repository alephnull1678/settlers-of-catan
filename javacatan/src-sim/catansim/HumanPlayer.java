package catansim;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner input;

    //Needs a scanner to be sent in
    public HumanPlayer(PlayerID playerID, Scanner input) {
        super(playerID);
        this.input = input;
    }

    @Override
    //Choose action using parser
    public Action chooseAction(Action[] actions) {
        if (actions == null || actions.length == 0) return null;

        while (true) {

            printAvailableActions(actions);

            System.out.print(getPlayerID() + " > ");
            String line = input.nextLine().trim();

            try {

                Action chosen = Parser.parse(line, actions);

                if (chosen == null) {
                    System.out.println("That action is not available.");
                    continue;
                }

                return chosen;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printAvailableActions(Action[] actions) {

        System.out.println();
        System.out.println("Available actions:");

        for (int i = 0; i < actions.length; i++) {
            System.out.println((i + 1) + ". " + actions[i]);
        }

        System.out.println();
    }
}