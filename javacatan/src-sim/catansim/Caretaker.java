package catansim;

import java.util.Stack;

public class Caretaker {

    private final Stack<GameSnapshot> undoStack = new Stack<>();
    private final Stack<GameSnapshot> redoStack = new Stack<>();

    public void pushUndo(GameSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }

        undoStack.push(snapshot);
        redoStack.clear(); // standard behavior: new action clears redo history
    }

    public GameSnapshot popUndo() {
        if (undoStack.isEmpty()) {
            return null;
        }
        return undoStack.pop();
    }

    public GameSnapshot peekUndo() {
        if (undoStack.isEmpty()) {
            return null;
        }
        return undoStack.peek();
    }

    public int undoSize() {
        return undoStack.size();
    }

    public void pushRedo(GameSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot cannot be null");
        }

        redoStack.push(snapshot);
    }

    public GameSnapshot popRedo() {
        if (redoStack.isEmpty()) {
            return null;
        }
        return redoStack.pop();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}