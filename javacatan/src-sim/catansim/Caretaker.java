package catansim;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker {

    public static final class GameSnapshot {
        private final BoardMemento boardMemento;
        private final PlayerMemento[] playerMementos;
        private final int roundNumber;
        private final int currentPlayerIndex;
        private final PlayerID longestRoadHolder;

        public GameSnapshot(BoardMemento boardMemento,
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
            this.playerMementos = playerMementos.clone();
            this.roundNumber = roundNumber;
            this.currentPlayerIndex = currentPlayerIndex;
            this.longestRoadHolder = longestRoadHolder;
        }

        public BoardMemento getBoardMemento() {
            return boardMemento;
        }

        public PlayerMemento[] getPlayerMementos() {
            return playerMementos.clone();
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

    private final Deque<GameSnapshot> undoStack;
    private final Deque<GameSnapshot> redoStack;

    public Caretaker() {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    public void save(GameSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }

        undoStack.push(snapshot);
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public GameSnapshot popUndo() {
        if (!canUndo()) {
            return null;
        }
        return undoStack.pop();
    }
    
    public GameSnapshot peekUndo() {
        if (!canUndo()) {
            return null;
        }
        return undoStack.peek();
    }

    public int undoSize() {
        return undoStack.size();
    }

    public GameSnapshot popRedo() {
        if (!canRedo()) {
            return null;
        }
        return redoStack.pop();
    }

    public void pushUndo(GameSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }
        undoStack.push(snapshot);
    }

    public void pushRedo(GameSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }
        redoStack.push(snapshot);
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}