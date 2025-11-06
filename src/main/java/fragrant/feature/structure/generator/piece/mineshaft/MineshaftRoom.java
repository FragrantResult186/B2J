package fragrant.feature.structure.generator.piece.mineshaft;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.feature.structure.Mineshaft;
import fragrant.feature.structure.generator.structure.MineshaftGenerator;

import java.util.ArrayList;
import java.util.List;

public class MineshaftRoom extends Mineshaft.Piece {
    private final List<BlockBox> childEntranceBoxes = new ArrayList<>();
    private final float generationProbability;

    public MineshaftRoom(int pieceId, ChunkRand rand, int minX, int minZ, Mineshaft.Type type) {
        super(pieceId);
        this.generationProbability = type == Mineshaft.Type.MESA ? 0.5F: 1.0F;
        // ZYX in bedrock
        int maxZ = rand.nextInt(6) + minZ + 7;
        int maxY = rand.nextInt(6);
        int maxX = rand.nextInt(6) + minX + 7;
        this.boundingBox = new BlockBox(minX, 50, minZ, maxX, 54 + maxY, maxZ);
    }

    @Override
    public void addChildren(MineshaftGenerator gen, MineshaftRoom start, Mineshaft.Piece piece, ChunkRand rand) {
        if (rand.nextFloat() >= this.generationProbability) return;

        int i = this.getGenDepth();
        int k = this.boundingBox.getYSpan() - 3 - 1;
        if (k <= 0) {
            k = 1;
        }

        int j = 0;
        while (j < this.boundingBox.getXSpan()) {
            j += rand.nextInt(this.boundingBox.getXSpan());
            if (j + 3 > this.boundingBox.getXSpan()) {
                break;
            }
            Mineshaft.Piece piece2 = gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + j, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
            if (piece2 != null) {
                BlockBox box = piece2.getBoundingBox();
                this.childEntranceBoxes.add(new BlockBox(box.minX, box.minY, this.boundingBox.minZ, box.maxX, box.maxY, this.boundingBox.minZ + 1));
            }
            j += 4;
        }

        j = 0;
        while (j < this.boundingBox.getXSpan()) {
            j += rand.nextInt(this.boundingBox.getXSpan());
            if (j + 3 > this.boundingBox.getXSpan()) {
                break;
            }
            Mineshaft.Piece piece2 = gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX + j, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
            if (piece2 != null) {
                BlockBox box = piece2.getBoundingBox();
                this.childEntranceBoxes.add(new BlockBox(box.minX, box.minY, this.boundingBox.maxZ - 1, box.maxX, box.maxY, this.boundingBox.maxZ));
            }
            j += 4;
        }

        j = 0;
        while (j < this.boundingBox.getZSpan()) {
            j += rand.nextInt(this.boundingBox.getZSpan());
            if (j + 3 > this.boundingBox.getZSpan()) {
                break;
            }
            Mineshaft.Piece piece2 = gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ + j, BlockDirection.WEST, i, start);
            if (piece2 != null) {
                BlockBox box = piece2.getBoundingBox();
                this.childEntranceBoxes.add(new BlockBox(this.boundingBox.minX, box.minY, box.minZ, this.boundingBox.minX + 1, box.maxY, box.maxZ));
            }
            j += 4;
        }

        j = 0;
        while (j < this.boundingBox.getZSpan()) {
            j += rand.nextInt(this.boundingBox.getZSpan());
            if (j + 3 > this.boundingBox.getZSpan()) {
                break;
            }
            Mineshaft.Piece piece2 = gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(k) + 1, this.boundingBox.minZ + j, BlockDirection.EAST, i, start);
            if (piece2 != null) {
                BlockBox box = piece2.getBoundingBox();
                this.childEntranceBoxes.add(new BlockBox(this.boundingBox.maxX - 1, box.minY, box.minZ, this.boundingBox.maxX, box.maxY, box.maxZ));
            }
            j += 4;
        }
    }
}
