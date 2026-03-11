package catansim;

public enum PieceTypes {

    ROAD(
        new CostSpec()
            .add(Resource.WOOD, 1)
            .add(Resource.BRICK, 1),
        15,
        0
    ) {
        @Override
        public Piece createPiece(PlayerID owner) {
            return new Road(owner);
        }
    },

    SETTLEMENT(
        new CostSpec()
            .add(Resource.WOOD, 1)
            .add(Resource.BRICK, 1)
            .add(Resource.SHEEP, 1)
            .add(Resource.WHEAT, 1),
        5,
        1
    ) {
        @Override
        public Piece createPiece(PlayerID owner) {
            return new Building(owner, this);
        }
    },

    CITY(
        new CostSpec()
            .add(Resource.ORE, 3)
            .add(Resource.WHEAT, 2),
        4,
        2
    ) {
        @Override
        public Piece createPiece(PlayerID owner) {
            return new Building(owner, this);
        }
    };

    private final Catalog<Resource> cost;
    private final int maxCount;
    private final int resourceAmount;

    PieceTypes(CostSpec spec, int maxCount, int resourceAmount) {
        this.cost = spec.toCatalogSnapshot();
        this.maxCount = maxCount;
        this.resourceAmount = resourceAmount;
    }

    public Catalog<Resource> getCost() {
        return cost;
    }

    public int maxCount() {
        return maxCount;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public abstract Piece createPiece(PlayerID owner);

    private static final class CostSpec {

        private final Catalog<Resource> mutable = new HashMapCatalog<>();

        CostSpec add(Resource r, int n) {
            mutable.add(r, n);
            return this;
        }

        Catalog<Resource> toCatalogSnapshot() {
            return mutable.snapshot();
        }
    }
}