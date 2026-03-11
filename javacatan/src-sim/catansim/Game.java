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

    private final int maxRounds;
    private int roundNumber = 0;

    private PlayerID longestRoadHolder = null;

    public Game(Player[] players, Board board, Validator validator, Dice dice, int maxRounds) {
        if (players == null || players.length == 0) {
            throw new IllegalArgumentException("players cannot be null/empty");
        }
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (validator == null) {
            throw new IllegalArgumentException("validator cannot be null");
        }
        if (dice == null) {
            throw new IllegalArgumentException("dice cannot be null");
        }

        this.players = players;
        this.board = board;
        this.validator = validator;
        this.dice = dice;

        if (maxRounds < 1) {
            maxRounds = 1;
        }
        if (maxRounds > MAX_ROUNDS_LIMIT) {
            maxRounds = MAX_ROUNDS_LIMIT;
        }
        this.maxRounds = maxRounds;
    }

    /**
     * Return all settlement actions that are legal under setup rules.
     */
    private List<Action> getValidSetupSettlements(StaticBoard staticBoard, PlayerID pid) {
        List<Action> actions = new ArrayList<>();

        for (Node n : staticBoard.getNodes()) {
            if (n.getBuilding() != null) {
                continue;
            }

            boolean adjacentHasBuilding = false;
            for (Node neighbour : n.getNeighbours()) {
                if (neighbour.getBuilding() != null) {
                    adjacentHasBuilding = true;
                    break;
                }
            }
            if (adjacentHasBuilding) {
                continue;
            }

            actions.add(new BuildAction(n, PieceTypes.SETTLEMENT));
        }

        return actions;
    }

    public void run() {
        doInitialPlacements();
        updateLongestRoadAward();

        while (roundNumber < maxRounds && getWinner() == null) {
            roundNumber++;

            int diceNum = dice.roll();
            System.out.println("DICE ROLL: " + diceNum);

            if (diceNum != 7) {
                for (Player p : players) {
                    PlayerID pid = p.getPlayerID();
                    Catalog<Resource> gained = board.collect(diceNum, pid);
                    p.dealResources(gained.snapshot());
                }
            }

            for (Player p : players) {
                if (getWinner() != null) {
                    break;
                }
                takeTurn(p);
            }

            printVictoryPoints();
        }
    }

    // ===============================
    // Setup Phase Logic
    // ===============================

    private void doInitialPlacements() {
        StaticBoard staticBoard = (StaticBoard) board;

        for (Player p : players) {
            doOneSetupTurn(p, staticBoard);
        }

        for (int i = players.length - 1; i >= 0; i--) {
            doOneSetupTurn(players[i], staticBoard);
        }
    }

    private void doOneSetupTurn(Player player, StaticBoard staticBoard) {
        PlayerID pid = player.getPlayerID();

        Catalog<PieceTypes> piecesOwned = player.getPieceCatalog();

        BuildAction settlement = chooseSetupAction(player, staticBoard, piecesOwned, PieceTypes.SETTLEMENT, null);
        Piece sPiece = player.consumeFreePiece(PieceTypes.SETTLEMENT);

        board.placePiece((Building) sPiece, pid, settlement.getNodes()[0]);
        player.addVP(1);

        printAction(pid, "Setup: " + describeAction(PieceTypes.SETTLEMENT, settlement.getNodes()));

        BuildAction road = chooseSetupAction(player, staticBoard, piecesOwned, PieceTypes.ROAD, settlement.getNodes()[0]);
        Piece rPiece = player.consumeFreePiece(PieceTypes.ROAD);

        board.placePiece((Road) rPiece, pid, road.getNodes()[0], road.getNodes()[1]);
        updateLongestRoadAward();

        printAction(pid, "Setup: " + describeAction(PieceTypes.ROAD, road.getNodes()));
    }

    private BuildAction chooseSetupAction(Player player, StaticBoard staticBoard, Catalog<PieceTypes> piecesOwned,
                                          PieceTypes type, Node sourceNode) {
        PlayerID pid = player.getPlayerID();

        List<Action> valid;

        if (sourceNode != null) {
            valid = validator.getAllActionsFromNode(sourceNode);
        } else {
            if (type == PieceTypes.SETTLEMENT) {
                valid = getValidSetupSettlements(staticBoard, pid);
            } else {
                valid = validator.getValidActions(staticBoard, pid, null, piecesOwned);
            }
        }

        List<Action> filtered = new ArrayList<>();
        for (Action a : valid) {
            if (a instanceof BuildAction) {
                BuildAction build = (BuildAction) a;
                if (build.getPieceType() == type) {
                    filtered.add(build);
                }
            }
        }

        Action chosen = player.chooseAction(filtered.toArray(new Action[0]));

        if (chosen == null) {
            throw new IllegalStateException(
                "Player " + pid + " chose no action during setup for type " + type
                + ". availableOptions=" + filtered.size()
            );
        }

        return (BuildAction) chosen;
    }

    // ===============================
    // Normal Turn Logic
    // ===============================

    private void takeTurn(Player player) {
        PlayerID pid = player.getPlayerID();
        StaticBoard staticBoard = (StaticBoard) board;

        Catalog<Resource> resourcesOwned = player.getResourceCatalog();
        Catalog<PieceTypes> piecesOwned = player.getPieceCatalog();

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

        if (chosen.getActionType() != ActionTypes.BUILD) {
            printAction(pid, "Unsupported action type: " + chosen.getActionType());
            return;
        }

        BuildAction build = (BuildAction) chosen;
        PieceTypes type = build.getPieceType();
        Node[] nodes = build.getNodes();

        Piece piece = player.consumePiece(type);
        if (piece == null) {
            printAction(pid, "Tried to build " + type + " but had no pieces");
            return;
        }

        if (piece.getType() == PieceTypes.ROAD) {
            board.placePiece((Road) piece, pid, nodes[0], nodes[1]);
        } else {
            board.placePiece((Building) piece, pid, nodes[0]);
        }

        if (type != PieceTypes.ROAD) {
            player.addVP(1);
        }

        if (type == PieceTypes.ROAD) {
            updateLongestRoadAward();
        }

        printAction(pid, describeAction(type, nodes));
    }

    private void updateLongestRoadAward() {
        PlayerID newHolder = board.checkLongestRoad();

        if (newHolder == longestRoadHolder) {
            return;
        }

        if (longestRoadHolder != null) {
            Player oldP = getPlayer(longestRoadHolder);
            if (oldP != null) {
                oldP.removeVP(LONGEST_ROAD_BONUS);
            }
        }

        if (newHolder != null) {
            Player newP = getPlayer(newHolder);
            if (newP != null) {
                newP.addVP(LONGEST_ROAD_BONUS);
            }
        }

        longestRoadHolder = newHolder;
        System.out.println("Longest Road Award has been transferred to " + newHolder);
    }

    private Player getPlayer(PlayerID id) {
        for (Player p : players) {
            if (p.getPlayerID() == id) {
                return p;
            }
        }
        return null;
    }

    private Player getWinner() {
        for (Player p : players) {
            if (p.getVP() >= WIN_VP) {
                return p;
            }
        }
        return null;
    }

    private void printAction(PlayerID pid, String actionText) {
        System.out.println("[" + roundNumber + "] / [" + pid + "]: " + actionText);
    }

    private void printVictoryPoints() {
        StringBuilder sb = new StringBuilder();
        sb.append("VPs end of round ").append(roundNumber).append(": ");
        for (Player p : players) {
            sb.append(p.getPlayerID()).append("=").append(p.getVP()).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    private String describeAction(PieceTypes type, Node[] nodes) {
        if (nodes == null) {
            return "Placed " + type;
        }

        if (type == PieceTypes.ROAD && nodes.length >= 2) {
            return "Built ROAD between " + nodeLabel(nodes[0]) + " and " + nodeLabel(nodes[1]);
        }

        if (type == PieceTypes.SETTLEMENT) {
            return "Built SETTLEMENT at " + nodeLabel(nodes[0]);
        }

        if (type == PieceTypes.CITY) {
            return "Upgraded to CITY at " + nodeLabel(nodes[0]);
        }

        return "Placed " + type;
    }

    private String nodeLabel(Node n) {
        if (n == null) {
            return "null";
        }
        return "Node " + n.getNodeID();
    }
}