package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.List;

public class CastleSmallCorridorLeftTurn extends Fortress.Piece {

    public CastleSmallCorridorLeftTurn(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
        if (rand.nextInt(3) == 0) {
            this.chest = new BPos(this.applyVecTransform(new Vec3i(5,4,7)));
        }
    }

    @Override
    public void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand) {
        this.generateChildrenLeft(gen, start, pieces, rand, 0, 1, true);
    }

    public static CastleSmallCorridorLeftTurn createPiece(List<Fortress.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, facing.getRotation());
        return Fortress.Piece.isHighEnough(box) && Fortress.Piece.getNextIntersectingPiece(pieces, box) == null ? new CastleSmallCorridorLeftTurn(pieceId, rand, box, facing) : null;
    }
}