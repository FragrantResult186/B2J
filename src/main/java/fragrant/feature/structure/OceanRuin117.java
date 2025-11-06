package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class OceanRuin117 extends TriangularStructure<OceanRuin117> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_16_0, new Config(12, 5, 14357621));

    public OceanRuin117(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public OceanRuin117(Config config, MCVersion version) {
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
