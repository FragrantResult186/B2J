package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class JungleTemple extends UniformStructure<JungleTemple> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(32, 24, 14357617));

    public JungleTemple(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public JungleTemple(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "jungle_temple";
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.JUNGLE || biome == Biomes.JUNGLE_HILLS ||
                biome == Biomes.BAMBOO_JUNGLE || biome == Biomes.BAMBOO_JUNGLE_HILLS;
    }

}
