package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.List;

public class CastleCorridorTBalcony extends Fortress.Piece {

    public CastleCorridorTBalcony(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand) {
        BlockDirection facing = this.getFacing();
        int y = (facing == BlockDirection.WEST || facing == BlockDirection.NORTH) ? 5 : 1;
        this.generateChildrenLeft(gen, start, pieces, rand, 0, y, rand.nextInt(8) != 0);
        this.generateChildrenRight(gen, start, pieces, rand, 0, y, rand.nextInt(8) != 0);
    }

    public static CastleCorridorTBalcony createPiece(List<Fortress.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -3, 0, 0, 9, 7, 9, facing.getRotation());
        return Fortress.Piece.isHighEnough(box) && Fortress.Piece.getNextIntersectingPiece(pieces, box) == null ? new CastleCorridorTBalcony(pieceId, rand, box, facing) : null;
    }
}