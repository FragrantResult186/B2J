package fragrant.feature.structure.generator.piece;

public class PieceWeight<T extends StructurePiece<T>> {

    public Class<? extends T> pieceClass;
    public final int pieceWeight;
    public int instancesSpawned;
    public int instancesLimit;
    public final boolean consecutive;

    public PieceWeight(Class<? extends T> pieceClass, int pieceWeight, int instancesLimit) {
        this(pieceClass, pieceWeight, instancesLimit, false);
    }

    public PieceWeight(Class<? extends T> pieceClass, int pieceWeight, int instancesLimit, boolean consecutive) {
        this.pieceClass = pieceClass;
        this.pieceWeight = pieceWeight;
        this.instancesLimit = instancesLimit;
        this.consecutive = consecutive;
    }

    public boolean canSpawnMoreStructuresOfType(int placedPieces) {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }

    public boolean canSpawnMoreStructures() {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }

}
