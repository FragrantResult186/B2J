package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;

public class ShatteredSavannaSurfaceBuilder extends DefaultSurfaceBuilder {
	public ShatteredSavannaSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		if(noise > 1.75D) {
			setSurfaceConfig(SurfaceConfig.CONFIG_STONE);
		} else if(noise > -0.5D) {
			setSurfaceConfig(SurfaceConfig.CONFIG_COARSE_DIRT);
		} else {
			setSurfaceConfig(SurfaceConfig.CONFIG_GRASS);
		}

		return super.applyToColumn(source, rand, column, biome, x, z, maxY, minY, noise, seaLevel, defaultBlock, defaultFluid);
	}
}
