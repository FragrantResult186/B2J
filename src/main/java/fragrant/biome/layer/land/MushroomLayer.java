package fragrant.biome.layer.land;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;
import fragrant.biome.layer.composite.XCrossLayer;

public class MushroomLayer extends XCrossLayer {

	public MushroomLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int sw, int se, int ne, int nw, int center) {
		return Biome.applyAll(v -> Biome.isShallowOcean(v, this.getVersion()), center, sw, se, ne, nw) &&
			this.nextInt(100) == 0 ? Biomes.MUSHROOM_FIELDS.getId() : center;
	}

}
