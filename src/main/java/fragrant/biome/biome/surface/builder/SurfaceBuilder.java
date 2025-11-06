package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;

public abstract class SurfaceBuilder {
	private SurfaceConfig surfaceConfig;

	public SurfaceBuilder(SurfaceConfig surfaceConfig) {
		this.surfaceConfig = surfaceConfig;
	}

	public SurfaceConfig getSurfaceConfig() {
		return surfaceConfig;
	}

	public void setSurfaceConfig(SurfaceConfig surfaceConfig) {
		this.surfaceConfig = surfaceConfig;
	}

	public SurfaceBuilder getNoiseSurfaceBuilder(BiomeSource source, double noise) {
		return this;
	}

	public abstract Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid);

	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid){
		return this.applyToColumn(source,rand,column,biome,x,z,maxY,0,noise,seaLevel,defaultBlock,defaultFluid);
	}

}
