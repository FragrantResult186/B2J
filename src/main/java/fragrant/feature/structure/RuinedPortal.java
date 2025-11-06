package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class RuinedPortal extends UniformStructure<RuinedPortal> {

	public static final VersionMap<Config> OVERWORLD_CONFIGS = new VersionMap<Config>()
		.add(MCVersion.v1_16_0, new Config(40,  25, 40552231));

	public static final VersionMap<Config> NETHER_CONFIGS = new VersionMap<Config>()
		.add(MCVersion.v1_16_0, new Config(25, 15, 40552231));

	private final Dimension dimension;

	public RuinedPortal(Dimension dimension, MCVersion version) {
		this(dimension, getConfigs(dimension).getAsOf(version), version);
	}

	public RuinedPortal(Dimension dimension, Config config, MCVersion version) {
		super(config, version);
		this.dimension = dimension;
	}

	public static String name() {
		return "ruined_portal";
	}

	public static VersionMap<Config> getConfigs(Dimension dimension) {
		switch(dimension) {
			case OVERWORLD:
				return OVERWORLD_CONFIGS;
			case NETHER:
				return NETHER_CONFIGS;
		}

		return new VersionMap<>();
	}

	@Override
	public Dimension getValidDimension() {
		return this.dimension;
	}

	@Override
	public boolean isValidBiome(Biome biome) {
		return biome != Biomes.THE_VOID && biome.getCategory() != Biome.Category.THE_END;
	}

}
