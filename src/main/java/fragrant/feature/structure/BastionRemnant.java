package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class BastionRemnant extends UniformStructure<BastionRemnant> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_16_0, new Config(30, 26, 30084232));

    public BastionRemnant(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public BastionRemnant(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "bastion_remnant";
    }

    @Override
    public boolean canStart(Data<BastionRemnant> data, long structureSeed, ChunkRand rand) {
        return super.canStart(data, structureSeed, rand) && rand.nextInt(6) >= 2;
    }

    @Override
    public CPos getInRegion(long structureSeed, int regionX, int regionZ, ChunkRand rand) {
        CPos bastion = super.getInRegion(structureSeed, regionX, regionZ, rand);
        return rand.nextInt(6) >= 2 ? bastion : null;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.NETHER;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.NETHER_WASTES || biome == Biomes.SOUL_SAND_VALLEY || biome == Biomes.WARPED_FOREST
                || biome == Biomes.CRIMSON_FOREST;
    }

}
