package fragrant.biome.layer.noise;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biome;
import fragrant.biome.layer.IntBiomeLayer;

public class NoiseLayer extends IntBiomeLayer {

	public NoiseLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int i = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return Biome.isShallowOcean(i, this.getVersion()) ? i : this.nextInt(this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_2) ? 2 : 299999) + 2;
	}

}
