package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;

public class NoopSurfaceBuilder extends SurfaceBuilder {
	public NoopSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}
	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		return null;
	}
}
