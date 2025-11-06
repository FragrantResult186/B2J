package fragrant.biome.biome.surface.builder;

import fragrant.core.block.Block;
import fragrant.core.block.Blocks;
import fragrant.biome.biome.surface.SurfaceConfig;
import fragrant.biome.source.BiomeSource;
import fragrant.core.util.data.Triplet;
import fragrant.noise.perlin.OctavePerlinNoiseSampler;

import java.util.Arrays;
import java.util.List;

public class SoulSandValleySurfaceBuilder extends ValleySurfaceBuilder {
	public static final List<Block> FLOOR_BLOCK_STATES = Arrays.asList(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
	public static final List<Block> CEILING_BLOCK_STATES = Arrays.asList(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);

	public SoulSandValleySurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Triplet<List<OctavePerlinNoiseSampler>,List<OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> getNoises(BiomeSource source) {
		return source.getStaticNoiseSource().getSoulSandValleyNoise();
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
