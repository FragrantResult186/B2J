package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.List;

public class BridgeEndFiller extends Fortress.Piece {

    private final int selfSeed;

    public BridgeEndFiller(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.selfSeed = rand.nextInt();
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand) {

    }

    public static BridgeEndFiller createPiece(List<Fortress.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -1, -3, 0, 5, 10, 8, facing.getRotation());
        return isHighEnough(box) && getNextIntersectingPiece(pieces, box) == null ? new BridgeEndFiller(pieceId, rand, box, facing) : null;
    }
}