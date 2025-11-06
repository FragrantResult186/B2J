package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.math.Vec3i;
import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;

import java.util.Collections;
import java.util.List;

public class ChestCorridor extends Stronghold.Piece {

    public ChestCorridor(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        rand.nextInt(5);
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
    }

    public static ChestCorridor createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, facing.getRotation());
        return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new ChestCorridor(pieceId, rand, box, facing) : null;
    }

    @Override
    protected List<Vec3i> getChestOffsets() {
        return Collections.singletonList(new Vec3i(3, 1, 3));
    }
}