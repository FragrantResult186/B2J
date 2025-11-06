package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.util.math.Vec3i;
import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;

import java.util.Collections;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class RoomCrossing extends Stronghold.Piece {

    private final int variants;

    public RoomCrossing(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        rand.nextInt(5);
        this.boundingBox = boundingBox;
        this.variants = rand.nextInt(5);
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 4, 1);
        this.generateChildrenLeft(gen, start, pieces, rand, 1, 4);
        this.generateChildrenRight(gen, start, pieces, rand, 1, 4);
    }

    public static RoomCrossing createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 7, 11, facing.getRotation());
        return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new RoomCrossing(pieceId, rand, box, facing) : null;
    }

    public int getVariants() {
        return variants;
    }

    @Override
    protected List<Vec3i> getChestOffsets() {
        return variants == 2
                ? Collections.singletonList(new Vec3i(3, 1, 8))
                : Collections.emptyList();
    }
}