package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.core.block.Blocks;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.core.util.data.Triplet;
import fragrant.noise.perlin.OctavePerlinNoiseSampler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasaltDeltasSurfaceBuilder extends ValleySurfaceBuilder {
	public static final List<Block> FLOOR_BLOCK_STATES = Arrays.asList(Blocks.BASALT, Blocks.BLACKSTONE);
	public static final List<Block> CEILING_BLOCK_STATES = Collections.singletonList(Blocks.BASALT);

	public BasaltDeltasSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Triplet<List<OctavePerlinNoiseSampler>,List<OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> getNoises(BiomeSource source) {
		return source.getStaticNoiseSource().getBasaltDeltasNoise();
	}

	@Override
	protected List<Block> getFloorBlockStates() {
		return FLOOR_BLOCK_STATES;
	}

	@Override
	protected List<Block> getCeilingBlockStates() {
		return CEILING_BLOCK_STATES;
	}

	@Override
	protected Block getPatchBlock() {
		return Blocks.GRAVEL;
	}
}
