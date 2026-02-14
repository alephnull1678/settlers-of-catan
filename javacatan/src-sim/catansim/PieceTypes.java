// --------------------------------------------------------
// Manual Implementation
// --------------------------------------------------------

package catansim;

public enum PieceTypes {

    ROAD(
        new CostSpec()
            .add(Resource.WOOD, 1)
            .add(Resource.BRICK, 1)
    ) {
        public Piece createPiece(PlayerID owner) {
            return new Road(owner);
        }

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
        public Piece createPiece(PlayerID owner) {
            return new Settlement(owner);
        }

        
        public int maxCount() {
            return 5;
        }
    },

    CITY(
        new CostSpec()
            .add(Resource.ORE, 3)
            .add(Resource.WHEAT, 2)
    ) {
        
        public Piece createPiece(PlayerID owner) {
            return new City(owner);
        }

        
        public int maxCount() {
            return 4;
        }
    };

    // --------------------------------------------------
    // Shared enum logic
    // --------------------------------------------------

    private final Catalog<Resource> cost;

    PieceTypes(CostSpec spec) {
        this.cost = spec.toCatalogSnapshot();
    }

    public Catalog<Resource> getCost() {
        return cost;
    }

    public abstract Piece createPiece(PlayerID owner);
    public abstract int maxCount();

    // --------------------------------------------------
    // Helper builder for resource catalogs
    // --------------------------------------------------

    private static final class CostSpec {

        private final Catalog<Resource> mutable = new MapCatalog<>();

        CostSpec add(Resource r, int n) {
            mutable.add(r, n);
            return this;
        }

        Catalog<Resource> toCatalogSnapshot() {
            return mutable.snapshot();
        }
    }
}
