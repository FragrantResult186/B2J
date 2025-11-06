package fragrant.feature.structure.generator.piece.mineshaft;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Mineshaft;
import fragrant.feature.structure.generator.structure.MineshaftGenerator;

import java.util.List;

public class MineshaftCrossing extends Mineshaft.Piece {
    private final BlockDirection direction;
    private final boolean isTwoFloored;

    public MineshaftCrossing(int pieceId, BlockBox boundingBox, BlockDirection direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.direction = direction;
        this.isTwoFloored = boundingBox.getYSpan() > 3;
    }

    public static BlockBox findCrossing(List<Mineshaft.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection direction) {
        int i;
        if (rand.nextInt(4) == 0) {
            i = 6;
        } else {
            i = 2;
        }
        BlockBox boundingBox;
        switch (direction) {
            case NORTH:
            default:
                boundingBox = new BlockBox(-1, 0, -4, 3, i, 0);
                break;
            case SOUTH:
                boundingBox = new BlockBox(-1, 0, 0, 3, i, 4);
                break;
            case WEST:
                boundingBox = new BlockBox(-4, 0, -1, 0, i, 3);
                break;
            case EAST:
                boundingBox = new BlockBox(0, 0, -1, 4, i, 3);
                break;
        }
        boundingBox.move(x, y, z);
        return Mineshaft.Piece.getNextIntersectingPiece(pieces, boundingBox) != null ? null : boundingBox;
    }

    @Override
    public void addChildren(MineshaftGenerator gen, MineshaftRoom start, Mineshaft.Piece piece, ChunkRand rand) {
        int i = this.getGenDepth();
        switch (this.direction) {
            case NORTH:
            default:
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.WEST, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.EAST, i, start);
                break;
            case SOUTH:
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.WEST, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.EAST, i, start);
                break;
            case WEST:
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.WEST, i, start);
                break;
            case EAST:
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, BlockDirection.EAST, i, start);
        }

        if (this.isTwoFloored) {
            if (rand.nextBoolean()) {
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
            }

            if (rand.nextBoolean()) {
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, BlockDirection.WEST, i, start);
            }

            if (rand.nextBoolean()) {
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, BlockDirection.EAST, i, start);
            }

            if (rand.nextBoolean()) {
                gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
            }
        }
    }
}
