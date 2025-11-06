package fragrant.feature.structure.generator.piece.mineshaft;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.Mineshaft;
import fragrant.feature.structure.generator.structure.MineshaftGenerator;

import java.util.List;

public class MineshaftCorridor extends Mineshaft.Piece {
    private final boolean hasRails;
    private final boolean spiderCorridor;
    private boolean hasPlacedSpider;
    private final int numSections;
    private Vec3i spawnerPos;

    public MineshaftCorridor(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.setOrientation(direction);
        this.hasRails = rand.nextInt(3) == 0;
        this.spiderCorridor = !this.hasRails && rand.nextInt(23) == 0;
        if (this.getOrientation().getAxis() == BlockDirection.Axis.Z) {
            this.numSections = boundingBox.getZSpan() / 5;
        } else {
            this.numSections = boundingBox.getXSpan() / 5;
        }
    }

    public static BlockBox findCorridorSize(List<Mineshaft.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection direction) {
        for (int i = rand.nextInt(3) + 2; i > 0; i--) {
            int j = i * 5 - 1;

            BlockBox boundingBox;
            switch (direction) {
                case NORTH:
                default:
                    boundingBox = new BlockBox(0, 0, -j, 2, 2, 0);
                    break;
                case SOUTH:
                    boundingBox = new BlockBox(0, 0, 0, 2, 2, j);
                    break;
                case WEST:
                    boundingBox = new BlockBox(-j, 0, 0, 0, 2, 2);
                    break;
                case EAST:
                    boundingBox = new BlockBox(0, 0, 0, j, 2, 2);
                    break;
            }
            boundingBox.move(x, y, z);
            if (Mineshaft.Piece.getNextIntersectingPiece(pieces, boundingBox) == null) {
                return boundingBox;
            }
        }

        return null;
    }

    @Override
    public void addChildren(MineshaftGenerator gen, MineshaftRoom start, Mineshaft.Piece piece, ChunkRand rand) {
        int i = this.getGenDepth();
        int j = rand.nextInt(4);
        BlockDirection direction = this.getOrientation();
        if (direction != null) {
            switch (direction) {
                case NORTH:
                default:
                    if (j <= 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, direction, i, start);
                    } else if (j == 2) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, BlockDirection.WEST, i, start);
                    } else {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, BlockDirection.EAST, i, start);
                    }
                    break;
                case SOUTH:
                    if (j <= 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, direction, i, start);
                    } else if (j == 2) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, BlockDirection.WEST, i, start);
                    } else {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, BlockDirection.EAST, i, start);
                    }
                    break;
                case WEST:
                    if (j <= 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, direction, i, start);
                    } else if (j == 2) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                    } else {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                    }
                    break;
                case EAST:
                    if (j <= 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, direction, i, start);
                    } else if (j == 2) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, BlockDirection.NORTH, i, start);
                    } else {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i, start);
                    }
            }
        }

        if (i < 8) {
            if (direction != BlockDirection.NORTH && direction != BlockDirection.SOUTH) {
                for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5) {
                    int j1 = rand.nextInt(5);
                    if (j1 == 0) {
                        gen.generateAndAddPiece(gen.pieceList, rand, i1, this.boundingBox.minY, this.boundingBox.minZ - 1, BlockDirection.NORTH, i + 1, start);
                    } else if (j1 == 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, i1, this.boundingBox.minY, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, i + 1, start);
                    }
                }
            } else {
                for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5) {
                    int l = rand.nextInt(5);
                    if (l == 0) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.minX - 1, this.boundingBox.minY, k, BlockDirection.WEST, i + 1, start);
                    } else if (l == 1) {
                        gen.generateAndAddPiece(gen.pieceList, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, k, BlockDirection.EAST, i + 1, start);
                    }
                }
            }
        }
    }

    public void postProcess(
            ChunkRand rand,
            BlockBox box
    ) {
        int length = this.numSections * 5 - 1;
        this.generateMaybeBox(rand, 0.8F, 0, 2, 0, 2, 2, length);
        if (this.spiderCorridor) {
            this.generateMaybeBox(rand, 0.6F, 0, 0, 0, 2, 1, length);
        }

        int var15 = 3;
        for (int i = 0; i < this.numSections; i++) {

            this.placeSupport(box, 0, 0, var15 - 1, 2, 2, rand);
            this.maybePlaceCobWeb(box, rand, 0.1F, 0, 2, var15 - 1);
            this.maybePlaceCobWeb(box, rand, 0.1F, 2, 2, var15 - 2);
            this.maybePlaceCobWeb(box, rand, 0.1F, 0, 2, var15);
            this.maybePlaceCobWeb(box, rand, 0.1F, 2, 2, var15);
            this.maybePlaceCobWeb(box, rand, 0.05F, 0, 2, var15 - 3);
            this.maybePlaceCobWeb(box, rand, 0.05F, 2, 2, var15 - 3);
            this.maybePlaceCobWeb(box, rand, 0.05F, 0, 2, var15 + 1);
            this.maybePlaceCobWeb(box, rand, 0.05F, 2, 2, var15 + 1);

            if (this.spiderCorridor && !this.hasPlacedSpider) {
                int z = var15 - 2 + rand.nextInt(3);
                this.spawnerPos = new Vec3i(1, 0, z);
                this.hasPlacedSpider = true;
            }

            /*
            if (rand.nextInt(100) == 0) {
                System.out.println("Generated Chest");
                //this.createChest(box, rand, 2, 0, var15 - 1);
            }
            if (rand.nextInt(100) == 0) {
                System.out.println("Generated Chest");
                //this.createChest(box, rand, 0, 0, var15 + 1);
            }
            */

            var15 += 5;
        }

        if (this.hasRails) {
            for (int j3 = 0; j3 <= length; j3++) {
                float f = /*this.isInterior(level, 1, 0, j3, box) ?*/ 0.7F /*: 0.9F*/;
                this.maybeGenerateBlock(box, rand, f, 1, 0, j3);
            }
        }
    }

    protected void generateMaybeBox(
            ChunkRand rand,
            float prob,
            int iVar1,
            int iVar2,
            int iVar3,
            int iVar4,
            int iVar5,
            int iVar6
    ) {
        for (int i = iVar2; i <= iVar5; i++) {
            for (int j = iVar1; j <= iVar4; j++) {
                for (int k = iVar3; k <= iVar6; k++) {
                    if (!(rand.nextFloat() > prob)) {

                    }
                }
            }
        }
    }

    protected void maybeGenerateBlock(
            BlockBox box,
            ChunkRand rand,
            float prob,
            int iVar1,
            int iVar2,
            int iVar3
    ) {
        if (rand.nextFloat() < prob) {

        }
    }

    private void placeSupport(
            BlockBox box,
            int iVar1,
            int iVar2,
            int iVar3,
            int iVar4,
            int iVar5,
            ChunkRand rand
    ) {
        if (rand.nextInt(4) == 0) {

        } else {
            this.maybeGenerateBlock(box, rand, 0.05F, iVar1 + 1, iVar4, iVar3 - 1);
            this.maybeGenerateBlock(box, rand, 0.05F, iVar1 + 1, iVar4, iVar3 + 1);
        }
    }

    private void maybePlaceCobWeb(
            BlockBox box,
            ChunkRand rand,
            float prob,
            int iVar1,
            int iVar2,
            int iVar3
    ) {
        if (rand.nextFloat() < prob) {

        }
    }

    public boolean isSpiderCorridor() {
        return spiderCorridor;
    }

    public boolean hasRails() {
        return hasRails;
    }

    public Vec3i spawnerPos() {
        return spawnerPos;
    }
}
