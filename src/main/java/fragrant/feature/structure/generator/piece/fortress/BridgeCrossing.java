package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.List;

public class BridgeCrossing extends Fortress.Piece {

    public BridgeCrossing(int pieceType, ChunkRand rand, int x, int z) {
        super(pieceType);
        BlockDirection dir = BlockDirection.randomHorizontal(rand);
        this.setOrientation(dir);
        this.boundingBox = new BlockBox(x, 64, z, x + 19 - 1, 64 + 10 - 1, z + 19 - 1);
    }

    public BridgeCrossing(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 8, 3, false);
        this.generateChildrenLeft(gen, start, pieces, rand, 3, 8, false);
        this.generateChildrenRight(gen, start, pieces, rand, 3, 8, false);
    }

    public static BridgeCrossing createPiece(List<Fortress.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -8, -3, 0, 19, 10, 19, facing.getRotation());
        return Fortress.Piece.isHighEnough(box) && Fortress.Piece.getNextIntersectingPiece(pieces, box) == null ? new BridgeCrossing(pieceId, rand, box, facing) : null;
    }
}