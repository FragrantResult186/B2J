package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class SwampHut extends UniformStructure<SwampHut> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(32, 24, 14357617));

    public SwampHut(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public SwampHut(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "swamp_hut";
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.SWAMP;
    }

}
