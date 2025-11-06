package fragrant.biome.layer.land;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;

public class SunflowerPlainsLayer extends IntBiomeLayer {

	public SunflowerPlainsLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int value = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return value == Biomes.PLAINS.getId() && this.nextInt(57) == 0 ? Biomes.SUNFLOWER_PLAINS.getId() : value;
	}

}
