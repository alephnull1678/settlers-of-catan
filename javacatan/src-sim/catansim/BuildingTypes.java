package catansim;

public enum BuildingTypes {

    ROAD(
        new CostSpec()
            .add(Resource.WOOD, 1)
            .add(Resource.BRICK, 1)
    ) {
        @Override
        public Piece createPiece() {
            return new Road();
        }

        @Override
        public int maxCount() {
            return 15;
        }
    },

    SETTLEMENT(
        new CostSpec()
            .add(Resource.WOOD, 1)
            .add(Resource.BRICK, 1)
            .add(Resource.SHEEP, 1)
            .add(Resource.WHEAT, 1)
    ) {
        @Override
        public Piece createPiece() {
            return new Settlement();
        }

        @Override
        public int maxCount() {
            return 5;
        }
    },

    CITY(
        new CostSpec()
            .add(Resource.ORE, 3)
            .add(Resource.WHEAT, 2)
    ) {
        @Override
        public Piece createPiece() {
            return new City();
        }

        @Override
        public int maxCount() {
            return 4;
        }
    };

    // --------------------------------------------------
    // Shared enum logic
    // --------------------------------------------------

    private final Catalog<Resource> cost;

    BuildingTypes(CostSpec spec) {
        this.cost = spec.toCatalogSnapshot();
    }

    public Catalog<Resource> getCost() {
        return cost;
    }

    public abstract Piece createPiece();
    public abstract int maxCount();

    // --------------------------------------------------
    // Helper builder for resource catalogs
    // --------------------------------------------------

    private static final class CostSpec {

        private final MapCatalog<Resource> mutable = new MapCatalog<>();

        CostSpec add(Resource r, int n) {
            mutable.add(r, n);
            return this;
        }

        Catalog<Resource> toCatalogSnapshot() {
            return mutable.snapshot();
        }
    }
}
