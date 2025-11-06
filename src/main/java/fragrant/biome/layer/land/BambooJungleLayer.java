package fragrant.biome.layer.land;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;

public class BambooJungleLayer extends IntBiomeLayer {

	public BambooJungleLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int value = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return value == Biomes.JUNGLE.getId() && this.nextInt(10) == 0 ? Biomes.BAMBOO_JUNGLE.getId() : value;
	}

}
