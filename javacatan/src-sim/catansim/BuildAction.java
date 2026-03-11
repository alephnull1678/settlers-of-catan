package catansim;

import java.util.Arrays;
import java.util.Objects;

public class BuildAction extends Action {

    private final Node[] nodes;
    private final PieceTypes pieceType;

    // Existing constructor (used by Validator / Game)
    public BuildAction(Node node, PieceTypes pieceType) {
        super(ActionTypes.BUILD);

        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        if (pieceType == null) {
            throw new IllegalArgumentException("pieceType cannot be null");
        }

        this.nodes = new Node[]{node};
        this.pieceType = pieceType;
    }

    // Existing constructor (used by Validator / Game)
    public BuildAction(Node node1, Node node2, PieceTypes pieceType) {
        super(ActionTypes.BUILD);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("nodes cannot be null");
        }
        if (pieceType == null) {
            throw new IllegalArgumentException("pieceType cannot be null");
        }

        this.nodes = new Node[]{node1, node2};
        this.pieceType = pieceType;
    }

    // NEW constructor used by Parser
    public BuildAction(ActionTypes type, Node node, PieceTypes pieceType) {
        super(type);

        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        if (pieceType == null) {
            throw new IllegalArgumentException("pieceType cannot be null");
        }

        this.nodes = new Node[]{node};
        this.pieceType = pieceType;
    }

    // NEW constructor used by Parser
    public BuildAction(ActionTypes type, Node node1, Node node2, PieceTypes pieceType) {
        super(type);

        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("nodes cannot be null");
        }
        if (pieceType == null) {
            throw new IllegalArgumentException("pieceType cannot be null");
        }

        this.nodes = new Node[]{node1, node2};
        this.pieceType = pieceType;
    }

    public Node[] getNodes() {
        return nodes.clone();
    }

    public PieceTypes getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;

        BuildAction other = (BuildAction) obj;

        return pieceType == other.pieceType &&
               Arrays.equals(nodes, other.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pieceType, Arrays.hashCode(nodes));
    }
}