package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class OceanRuin extends UniformStructure<OceanRuin> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_17_0, new Config(20, 12, 14357621));

    public OceanRuin(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public OceanRuin(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "ocean_ruin";
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.OCEAN;
    }

}
