package fragrant.biome.source;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.BiomePoint;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;
import fragrant.biome.layer.composite.VoronoiLayer;
import fragrant.biome.layer.noise.MultiNoiseLayer17;
import fragrant.core.util.pos.BPos;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;

public abstract class MultiNoiseBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {
	protected final BiomePoint[] biomePoints;
	public MultiNoiseLayer17 full;
	public VoronoiLayer voronoi;
	protected boolean threeDimensional;

	public MultiNoiseBiomeSource(MCVersion version, long worldSeed, BiomePoint... biomePoints) {
		super(version, worldSeed);
		this.biomePoints = biomePoints;
		this.build();
	}

	@Override
	public abstract Dimension getDimension();

	// I have no idea what this function is supposed to represent...
	public MultiNoiseBiomeSource addDimension() {
		this.threeDimensional = true;
		this.full = null;
		this.layers.clear();
		this.build();
		return this;
	}

	protected abstract void build();

	public boolean is3D() {
		return threeDimensional;
	}

	public BiomePoint[] getBiomePoints() {
		return biomePoints;
	}

	@Override
	public Biome getBiome(BPos bpos) {
		return Biomes.REGISTRY.get(this.voronoi.get(bpos.getX(), bpos.getY(), bpos.getZ()));
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.voronoi.get(x, y, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.full.get(x, y, z));
	}

}
