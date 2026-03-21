package catansim;

public final class BoardMemento implements Memento {

    public static final class NodeSnapshot {
        private final Building building;
        private final Road[] roads;

        public NodeSnapshot(Building building, Road[] roads) {
            this.building = building;

            if (roads == null) {
                this.roads = new Road[0];
            } else {
                this.roads = roads.clone();
            }
        }

        public Building getBuilding() {
            return building;
        }

        public Road[] getRoads() {
            return roads.clone();
        }
    }

    private final NodeSnapshot[] nodeSnapshots;
    private final int robberTileID;

    public BoardMemento(NodeSnapshot[] nodeSnapshots, int robberTileID) {
        if (nodeSnapshots == null) {
            throw new IllegalArgumentException("nodeSnapshots cannot be null");
        }

        this.nodeSnapshots = new NodeSnapshot[nodeSnapshots.length];
        for (int i = 0; i < nodeSnapshots.length; i++) {
            if (nodeSnapshots[i] == null) {
                throw new IllegalArgumentException("nodeSnapshots cannot contain null");
            }
            this.nodeSnapshots[i] = nodeSnapshots[i];
        }

        this.robberTileID = robberTileID;
    }

    public NodeSnapshot[] getNodeSnapshots() {
        return nodeSnapshots.clone();
    }

    public NodeSnapshot getNodeSnapshot(int index) {
        return nodeSnapshots[index];
    }

    public int size() {
        return nodeSnapshots.length;
    }

    public int getRobberTileID() {
        return robberTileID;
    }
}