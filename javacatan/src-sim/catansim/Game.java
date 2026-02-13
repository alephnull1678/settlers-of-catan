package catansim;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int WIN_VP = 10;
    private static final int MAX_ROUNDS_LIMIT = 8192;
    private static final int LONGEST_ROAD_BONUS = 2;

    private final Player[] players;
    private final Board board;
    private final Validator validator;
    private final Dice dice;

    private final int maxRounds;              // from config "turns"
    private int roundNumber = 0;

    private PlayerID longestRoadHolder = null;

    public Game(Player[] players, Board board, Validator validator, Dice dice, int maxRounds) {
        if (players == null || players.length == 0) throw new IllegalArgumentException("players cannot be null/empty");
        if (board == null) throw new IllegalArgumentException("board cannot be null");
        if (validator == null) throw new IllegalArgumentException("validator cannot be null");
        if (dice == null) throw new IllegalArgumentException("dice cannot be null");

        this.players = players;
        this.board = board;
        this.validator = validator;
        this.dice = dice;

        if (maxRounds < 1) maxRounds = 1;
        if (maxRounds > MAX_ROUNDS_LIMIT) maxRounds = MAX_ROUNDS_LIMIT;
        this.maxRounds = maxRounds;
    }

    public void run() {
        //SETUP PHASE
        doInitialPlacements();

        //In case the board starts with a longest-road holder (usually null), sync once.
        updateLongestRoadAward();

        while (roundNumber < maxRounds && getWinner() == null) {
            roundNumber++;

            int diceNum = dice.roll();

            //Resource collection phase: collect for EVERY player
            // If dice == 7, do nothing
            if (diceNum != 7) {
                for (Player p : players) {
                    PlayerID pid = p.getPlayerID();
                    Catalog<Resource> gained = board.collect(diceNum, pid);
                    p.dealResources(gained.snapshot()); // snapshot = immutable copy
                }
            }

            //Turn phase: each player acts onc
            for (Player p : players) {
                if (getWinner() != null) break;
                takeTurn(p);
            }

            //End-of-round VP print
            printVictoryPoints();
        }
    }

    // ===============================
    // Setup Phase Logic
    // ===============================

    private void doInitialPlacements() {
        StaticBoard staticBoard = (StaticBoard) board;

        // Forward order
        for (Player p : players) {
            doOneSetupTurn(p, staticBoard);
        }

        // Reverse order
        for (int i = players.length - 1; i >= 0; i--) {
            doOneSetupTurn(players[i], staticBoard);
        }
    }

    private void doOneSetupTurn(Player player, StaticBoard staticBoard) {
        PlayerID pid = player.getPlayerID();

        Catalog<BuildingTypes> piecesOwned = player.getPieceCatalog();

        //Settlement
        Action settlement = chooseSetupAction(player, staticBoard, piecesOwned, BuildingTypes.SETTLEMENT, null);
        Piece sPiece = player.consumeFreePiece(BuildingTypes.SETTLEMENT);

        board.placePiece((Building)sPiece, pid, settlement.getNodes()[0]);
        player.addVP(1);

        printAction(pid, "Setup: " + describeAction(BuildingTypes.SETTLEMENT, settlement.getNodes()));

        //Road
        Action road = chooseSetupAction(player, staticBoard, piecesOwned, BuildingTypes.ROAD, settlement.getNodes()[0]);
        Piece rPiece = player.consumeFreePiece(BuildingTypes.ROAD);

        board.placePiece((Road)rPiece, pid, road.getNodes()[0], road.getNodes()[1]);
        updateLongestRoadAward();

        printAction(pid, "Setup: " + describeAction(BuildingTypes.ROAD, road.getNodes()));
    }

    private Action chooseSetupAction(Player player, StaticBoard staticBoard, Catalog<BuildingTypes> piecesOwned, BuildingTypes type, Node sourceNode) {
        PlayerID pid = player.getPlayerID();

        List<Action> valid;
        if (sourceNode != null) valid = validator.getAllActionsFromNode(sourceNode);
        else valid = validator.getValidActions(staticBoard, pid, null, piecesOwned);

        List<Action> filtered = new ArrayList<>();
        for (Action a : valid) {
            if (a.getPieceType() == type) {
                filtered.add(a);
            }
        }

        return player.chooseAction(filtered.toArray(new Action[0]));
    }

    // ===============================
    // Normal Turn Logic
    // ===============================

    private void takeTurn(Player player) {
        PlayerID pid = player.getPlayerID();

        //Cast ONLY for validator input
        StaticBoard staticBoard = (StaticBoard) board;

        Catalog<Resource> resourcesOwned = player.getResourceCatalog();
        Catalog<BuildingTypes> piecesOwned = player.getPieceCatalog();

        List<Action> valid = validator.getValidActions(staticBoard, pid, resourcesOwned, piecesOwned);

        if (valid == null || valid.isEmpty()) {
            printAction(pid, "No valid actions");
            return;
        }

        Action chosen = player.chooseAction(valid.toArray(new Action[0]));
        if (chosen == null) {
            printAction(pid, "Chose no action");
            return;
        }

        BuildingTypes type = chosen.getPieceType();
        Node[] nodes = chosen.getNodes();

        //Consume returns the Piece instance to place
        Piece piece = player.consumePiece(type);
        if (piece == null) {
            printAction(pid, "Tried to build " + type + " but had no pieces");
            return;
        }


        //Bit of code smell here but only two types of piece so it's inconsequential
        if (piece.getType() == BuildingTypes.ROAD) board.placePiece((Road)piece, pid, nodes[0], nodes[1]);
        else board.placePiece((Building)piece, pid, nodes[0]);

        //Update VP
        if (type != BuildingTypes.ROAD) {
            player.addVP(1);
        }

        //Longest road can change after roads are placed
        if (type == BuildingTypes.ROAD) updateLongestRoadAward();

        printAction(pid, describeAction(type, nodes));
    }

    private void updateLongestRoadAward() {
        PlayerID newHolder = board.checkLongestRoad(); //null if nobody qualifies (>5)

        if (newHolder == longestRoadHolder) return;

        if (longestRoadHolder != null) {
            Player oldP = getPlayer(longestRoadHolder);
            if (oldP != null) oldP.removeVP(LONGEST_ROAD_BONUS);
        }

        if (newHolder != null) {
            Player newP = getPlayer(newHolder);
            if (newP != null) newP.addVP(LONGEST_ROAD_BONUS);
        }

        longestRoadHolder = newHolder;
    }

    private Player getPlayer(PlayerID id) {
        for (Player p : players) {
            if (p.getPlayerID() == id) return p;
        }
        return null;
    }

    private Player getWinner() {
        for (Player p : players) {
            if (p.getVP() >= WIN_VP) return p;
        }
        return null;
    }

    //Printing helpers
    private void printAction(PlayerID pid, String actionText) {
        // Required format: [RoundNumber] / [PlayerID]: [Action]
        System.out.println("[" + roundNumber + "] / [" + pid + "]: " + actionText);
    }

    private void printVictoryPoints() {
        //Must print current VPs at end of each round
        StringBuilder sb = new StringBuilder();
        sb.append("VPs end of round ").append(roundNumber).append(": ");
        for (Player p : players) {
            sb.append(p.getPlayerID()).append("=").append(p.getVP()).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    private String describeAction(BuildingTypes type, Node[] nodes) {
        if (nodes == null) return "Placed " + type;

        if (type == BuildingTypes.ROAD && nodes.length >= 2) {
            return "Built ROAD between " + nodeLabel(nodes[0]) + " and " + nodeLabel(nodes[1]);
        }

        if (type == BuildingTypes.SETTLEMENT) {
            return "Built SETTLEMENT at " + nodeLabel(nodes[0]);
        }

        if (type == BuildingTypes.CITY) {
            return "Upgraded to CITY at " + nodeLabel(nodes[0]);
        }

        return "Placed " + type;
    }

    private String nodeLabel(Node n) {
    	if (n == null) return "null";
        return "Node " + n.getNodeID();
    }
}
