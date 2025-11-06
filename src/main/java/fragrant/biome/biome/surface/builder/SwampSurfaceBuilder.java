package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.biome.source.StaticNoiseSource;
import fragrant.core.rand.ChunkRand;

public class SwampSurfaceBuilder extends DefaultSurfaceBuilder {
	public SwampSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}
	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		double d0 = StaticNoiseSource.BIOME_INFO_NOISE.sample((double) x * 0.25D, (double) z * 0.25D, false);
		if (d0 > 0.0D) {
			for(int y = maxY; y >= minY; --y) {
				if (!Block.IS_AIR.test(source.getVersion(),column[y])) {
					if (y == 62 && column[y]!=defaultFluid) {
						column[y]=defaultFluid;
					}
					break;
				}
			}
		}
		return super.applyToColumn(source, rand, column, biome, x, z, maxY, minY, noise, seaLevel, defaultBlock, defaultFluid);
	}
}
