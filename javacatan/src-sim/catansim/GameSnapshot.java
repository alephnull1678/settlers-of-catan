package catansim;

public class GameSnapshot {

    private final BoardMemento boardMemento;
    private final PlayerMemento[] playerMementos;
    private final int roundNumber;
    private final int currentPlayerIndex;
    private final PlayerID longestRoadHolder;

    public GameSnapshot(
            BoardMemento boardMemento,
            PlayerMemento[] playerMementos,
            int roundNumber,
            int currentPlayerIndex,
            PlayerID longestRoadHolder) {

        if (boardMemento == null) {
            throw new IllegalArgumentException("boardMemento cannot be null");
        }
        if (playerMementos == null) {
            throw new IllegalArgumentException("playerMementos cannot be null");
        }

        this.boardMemento = boardMemento;
        this.playerMementos = playerMementos;
        this.roundNumber = roundNumber;
        this.currentPlayerIndex = currentPlayerIndex;
        this.longestRoadHolder = longestRoadHolder;
    }

    public BoardMemento getBoardMemento() {
        return boardMemento;
    }

    public PlayerMemento getPlayerMemento(int index) {
        return playerMementos[index];
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public PlayerID getLongestRoadHolder() {
        return longestRoadHolder;
    }
}