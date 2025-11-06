package fragrant.biome.layer.end;

import fragrant.biome.layer.BoolBiomeLayer;
import fragrant.noise.simplex.SimplexNoiseSampler;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;

public class EndSimplexLayer extends BoolBiomeLayer {

    protected final SimplexNoiseSampler simplex;

	public EndSimplexLayer(MCVersion version, long worldSeed) {
		super(version);
		BedrockRandom rand = new BedrockRandom(worldSeed);
		rand.skip(17292);
		this.simplex = new SimplexNoiseSampler(rand);
	}

	@Override
	public boolean sample(int x, int y, int z) {
		return this.simplex.sample2D(x, z) < (double)-0.9F;
	}

}
