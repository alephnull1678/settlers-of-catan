package catansim;

public final class PlayerMemento implements Memento {

    private final Catalog<PieceTypes> pieceCatalog;
    private final Catalog<Resource> resourceCatalog;
    private final int victoryPoints;

    public PlayerMemento(Catalog<PieceTypes> pieceCatalog,
                         Catalog<Resource> resourceCatalog,
                         int victoryPoints) {
        if (pieceCatalog == null) {
            throw new IllegalArgumentException("pieceCatalog cannot be null");
        }
        if (resourceCatalog == null) {
            throw new IllegalArgumentException("resourceCatalog cannot be null");
        }

        this.pieceCatalog = pieceCatalog.snapshot();
        this.resourceCatalog = resourceCatalog.snapshot();
        this.victoryPoints = victoryPoints;
    }

    public Catalog<PieceTypes> getPieceCatalog() {
        return pieceCatalog.snapshot();
    }

    public Catalog<Resource> getResourceCatalog() {
        return resourceCatalog.snapshot();
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}