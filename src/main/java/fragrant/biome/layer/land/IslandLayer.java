package fragrant.biome.layer.land;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;
import fragrant.biome.layer.composite.CrossLayer;

public class IslandLayer extends CrossLayer {

	public IslandLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		return Biome.applyAll(v -> Biome.isShallowOcean(v, this.getVersion()), center, n, e, s, w)
			&& this.nextInt(2) == 0 ? Biomes.PLAINS.getId() : center;
	}

}
