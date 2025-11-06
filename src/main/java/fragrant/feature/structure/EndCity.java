package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;
import fragrant.feature.structure.generator.piece.StructurePiece;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.block.BlockRotation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EndCity extends TriangularStructure<EndCity> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_9_0, new Config(20, 9, 10387313));

    public EndCity(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public EndCity(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "end_city";
    }

    @Override
    public boolean canStart(Data<EndCity> data, long structureSeed, ChunkRand rand) {
        return super.canStart(data, structureSeed, rand);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.END;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.END_MIDLANDS || biome == Biomes.END_HIGHLANDS;
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        return true;
    }

    public static abstract class Piece extends StructurePiece<Piece> {
        public Class<? extends Piece> pieceClass;
        public BlockRotation rotation;
        public BPos pos;
        public List<BPos> chest;
        protected BPos size;

        public Piece(int pieceId) {
            super(pieceId);
            this.size = new BPos(0, 0, 0);
        }

        public Piece() {
            super(0);
            this.chest = new ArrayList<>();
            this.size = new BPos(0, 0, 0);
        }

        public BPos getSize() {
            return size;
        }
    }
}