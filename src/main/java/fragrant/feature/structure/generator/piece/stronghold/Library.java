package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.math.Vec3i;
import fragrant.feature.structure.Stronghold;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Library extends Stronghold.Piece {

    private final boolean isTall;

    public Library(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        rand.nextInt(5);
        this.boundingBox = boundingBox;
        this.isTall = this.boundingBox.getYSpan() > 6;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {

    }

    public static Library createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, facing.getRotation());
        if (!Stronghold.Piece.isHighEnough(box) || Stronghold.Piece.getNextIntersectingPiece(pieces, box) != null) {
            box = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, facing.getRotation());
            if (!Stronghold.Piece.isHighEnough(box) || Stronghold.Piece.getNextIntersectingPiece(pieces, box) != null) {
                return null;
            }
        }
        return new Library(pieceId, rand, box, facing);
    }

    public boolean isTall() {
        return isTall;
    }

    @Override
    protected List<Vec3i> getChestOffsets() {
        return isTall
                ? Arrays.asList(new Vec3i(3, 1, 5), new Vec3i(12, 5, 1))
                : Collections.singletonList(new Vec3i(3, 1, 5));
    }
}