package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.List;

public class RoomCrossing extends Fortress.Piece {

    public RoomCrossing(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 2, 0, false);
        this.generateChildrenLeft(gen, start, pieces, rand, 0, 2, false);
        this.generateChildrenRight(gen, start, pieces, rand, 0, 2, false);
    }

    public static RoomCrossing createPiece(List<Fortress.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 9, 7, facing.getRotation());
        return Fortress.Piece.isHighEnough(box) && Fortress.Piece.getNextIntersectingPiece(pieces, box) == null ? new RoomCrossing(pieceId, rand, box, facing) : null;
    }
}