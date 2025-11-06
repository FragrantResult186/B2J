package fragrant.feature.structure.generator.structure;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.data.Pair;
import fragrant.core.util.pos.BPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.structure.Mineshaft;
import fragrant.feature.structure.generator.Generator;
import fragrant.feature.structure.generator.piece.mineshaft.MineshaftCorridor;
import fragrant.feature.structure.generator.piece.mineshaft.MineshaftCrossing;
import fragrant.feature.structure.generator.piece.mineshaft.MineshaftStairs;
import fragrant.feature.structure.generator.piece.mineshaft.MineshaftRoom;
import fragrant.terrain.TerrainGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MineshaftGenerator extends Generator {

    protected TerrainGenerator generator;
    protected Mineshaft.Type type;

    public List<Mineshaft.Piece> pieceList = null;

    private Predicate<Mineshaft.Piece> loopPredicate;
    private boolean halted;

    public MineshaftGenerator(MCVersion version) {
        this(version, Mineshaft.Type.NORMAL);
    }

    public MineshaftGenerator(MCVersion version, Mineshaft.Type type) {
        super(version);
        this.type = type;
    }

    @Override
    public boolean generate(TerrainGenerator generator, int chunkX, int chunkZ, ChunkRand rand) {
        this.generator = generator;
        return this.generate(generator.getWorldSeed(), chunkX, chunkZ, piece -> true);
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ, Predicate<Mineshaft.Piece> shouldContinue) {
        this.pieceList = new ArrayList<>();
        this.halted = false;
        this.loopPredicate = shouldContinue;

        ChunkRand rand = new ChunkRand();
        if (getVersion().isNewerOrEqualTo(MCVersion.v1_11_0)) {
            rand.setCarverSeed(worldSeed, chunkX, chunkZ, this.getVersion());
        } else {
            rand.setSeed(worldSeed ^ chunkZ ^ chunkX);
        }
        rand.nextInt(); // burn a one call
        rand.nextFloat();
        rand.nextInt(80);

        int x = (chunkX << 4) + 2;
        int z = (chunkZ << 4) + 2;
        MineshaftRoom startPiece = new MineshaftRoom(0, rand, x, z, this.type);
        this.pieceList.add(startPiece);
        startPiece.addChildren(this, startPiece, startPiece, rand);

        int i = generator.getSeaLevel();
        if (this.type == Mineshaft.Type.MESA) {
            // TODO
        } else {
            this.moveBelowSeaLevel(this.pieceList, rand, i, this.generator.getMinWorldHeight(), 10);
        }

        return !this.halted;
    }

    private Mineshaft.Piece createRandomShaftPiece(ChunkRand randomSource, int x, int y, int z, BlockDirection direction, int genDepth) {
        int i = randomSource.nextInt(100);
        if (i >= 80) {
            BlockBox box1 = MineshaftCrossing.findCrossing(this.pieceList, randomSource, x, y, z, direction);
            if (box1 != null) {
                return new MineshaftCrossing(genDepth, box1, direction);
            }
        } else if (i >= 70) {
            BlockBox box2 = MineshaftStairs.findStairs(this.pieceList, randomSource, x, y, z, direction);
            if (box2 != null) {
                return new MineshaftStairs(genDepth, box2, direction);
            }
        } else {
            BlockBox box3 = MineshaftCorridor.findCorridorSize(this.pieceList, randomSource, x, y, z, direction);
            if (box3 != null) {
                MineshaftCorridor corridor = new MineshaftCorridor(genDepth, randomSource, box3, direction);
                //ChunkRand chunkRand = new ChunkRand();
                //chunkRand.setPopulationSeed(this.generator.getWorldSeed(), box3.getCenter().getX(), box3.getCenter().getZ(), this.version);
                //corridor.postProcess(new ChunkRand(), box3);
                return corridor;
            }
        }

        return null;
    }

    public Mineshaft.Piece generateAndAddPiece(List<Mineshaft.Piece> pieces, ChunkRand randomSource, int x, int y, int z, BlockDirection direction, int genDepth, MineshaftRoom start) {
        if (genDepth > 8) {
            return null;
        } else if (Math.abs(x - start.getBoundingBox().minX) <= 80 && Math.abs(z - start.getBoundingBox().minZ) <= 80) {
            Mineshaft.Piece shaftPiece = createRandomShaftPiece(randomSource, x, y, z, direction, genDepth + 1);
            if (shaftPiece != null) {
                pieces.add(shaftPiece);

                if (!this.loopPredicate.test(shaftPiece)) {
                    this.halted = true;
                }

                shaftPiece.addChildren(this, start, shaftPiece, randomSource);
            }

            return shaftPiece;
        } else {
            return null;
        }
    }

    public Mineshaft.Type getType() {
        return type;
    }
}