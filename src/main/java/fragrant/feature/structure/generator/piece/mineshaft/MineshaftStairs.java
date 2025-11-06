package fragrant.feature.structure.generator.piece.mineshaft;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Mineshaft;
import fragrant.feature.structure.generator.structure.MineshaftGenerator;

import java.util.List;

public class MineshaftStairs extends Mineshaft.Piece {
    public MineshaftStairs(int pieceId, BlockBox boundingBox, BlockDirection direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.setOrientation(direction);
    }

    public static BlockBox findStairs(List<Mineshaft.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection direction) {
        BlockBox boundingBox;
        switch (direction) {
            case NORTH:
            default:
                boundingBox = new BlockBox(0, -5, -8, 2, 2, 0);
                break;
            case SOUTH:
                boundingBox = new BlockBox(0, -5, 0, 2, 2, 8);
                break;
            case WEST:
                boundingBox = new BlockBox(-8, -5, 0, 0, 2, 2);
                break;
            case EAST:
                boundingBox = new BlockBox(0, -5, 0, 8, 2, 2);
                break;
        }
        boundingBox.move(x, y, z);
        return Mineshaft.Piece.getNextIntersectingPiece(pieces, boundingBox) != null ? null : boundingBox;
    }

    @Override
    public void addChildren(MineshaftGenerator gen, MineshaftRoom start, Mineshaft.Piece piece, ChunkRand rand) {
        int i = this.getGenDepth();
        BlockDirection direction = this.getOrientation();
        if (direction != null) {
            switch (direction) {
                case NORTH:
                default:
                    gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                    break;
                case SOUTH:
                    gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                    break;
                case WEST:
                    gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, BlockDirection.WEST, i, start);
                    break;
                case EAST:
                    gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, BlockDirection.EAST, i, start);
            }
        }
    }
}
