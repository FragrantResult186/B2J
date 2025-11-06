package fragrant.feature.structure.generator;

import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.data.Pair;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.loot.table.LootTable;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.piece.StructurePiece;
import fragrant.terrain.TerrainGenerator;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class Generator {
    protected final MCVersion version;
    public static final ConcurrentHashMap<String, LootTable> LOOT_TABLE_CACHE = new ConcurrentHashMap<>();

    public Generator(MCVersion version) {
        this.version = version;
    }

    public MCVersion getVersion() {
        return version;
    }

    public boolean generate(long worldSeed, Dimension dimension, int chunkX, int chunkZ) {
        BiomeSource biomeSource = BiomeSource.of(dimension, this.getVersion(), worldSeed);
        TerrainGenerator generator = TerrainGenerator.of(dimension, biomeSource);
        return this.generate(generator, chunkX, chunkZ);
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ) {
        BiomeSource biomeSource = BiomeSource.of(null, this.getVersion(), worldSeed);
        TerrainGenerator generator = TerrainGenerator.of(null, biomeSource);
        return this.generate(generator, chunkX, chunkZ);
    }

    public boolean generate(long worldSeed, CPos cPos) {
        return this.generate(worldSeed, cPos.getX(), cPos.getZ());
    }

    public boolean generate(TerrainGenerator generator, CPos cPos) {
        return this.generate(generator, cPos, new ChunkRand());
    }

    public boolean generate(TerrainGenerator generator, int chunkX, int chunkZ) {
        return this.generate(generator, chunkX, chunkZ, new ChunkRand());
    }

    public boolean generate(TerrainGenerator generator, CPos cPos, ChunkRand rand) {
        return this.generate(generator, cPos.getX(), cPos.getZ(), rand);
    }

    public abstract boolean generate(TerrainGenerator generator, int chunkX, int chunkZ, ChunkRand rand);

    protected <T extends StructurePiece<T>> void moveBelowSeaLevel(
            List<T> pieces,
            BedrockRandom rand,
            int seaLevel,
            int minWorldHeight,
            int offset
    ) {
        if (pieces == null || pieces.isEmpty()) return;

        BlockBox totalBox = BlockBox.empty();
        for (T piece : pieces) {
            totalBox.encompass(piece.boundingBox);
        }

        int y = totalBox.getYSpan() + minWorldHeight + 1;
        if (y < seaLevel - offset) {
            y += rand.nextInt((seaLevel - offset) - y);
        }

        int dy = y - totalBox.maxY;
        offsetPiecesVertically(pieces, dy);
    }

    protected <T extends StructurePiece<T>> void moveInsideHeights(
            List<T> pieces,
            ChunkRand rand,
            int minY,
            int maxY
    ) {
        if (pieces == null || pieces.isEmpty()) return;

        BlockBox totalBox = BlockBox.empty();
        for (T piece : pieces) {
            totalBox.encompass(piece.boundingBox);
        }

        int structureHeight = totalBox.getYSpan();
        int availableSpace = (maxY - minY + 1) - structureHeight;

        int randomOffset = (availableSpace > 1) ? rand.nextInt(availableSpace) : 0;
        int dy = minY - totalBox.minY + randomOffset;

        offsetPiecesVertically(pieces, dy);
    }

    protected <T extends StructurePiece<T>> void offsetPiecesVertically(List<T> pieces, int dy) {
        for (T piece : pieces) {
            piece.boundingBox.move(0, dy, 0);

            if (piece instanceof Fortress.Piece fortressPiece) {
                if (fortressPiece.chest != null) {
                    fortressPiece.chest = fortressPiece.chest.add(0, dy, 0);
                }
                if (fortressPiece.spawner != null) {
                    fortressPiece.spawner = fortressPiece.spawner.add(0, dy, 0);
                }
            }
        }
    }

    @FunctionalInterface
    public interface GeneratorFactory<T extends Generator> {
        T create(MCVersion version);
    }
}