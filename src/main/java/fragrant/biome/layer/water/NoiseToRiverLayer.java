package fragrant.biome.layer.water;

import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.IntBiomeLayer;
import fragrant.biome.layer.composite.CrossLayer;

public class NoiseToRiverLayer extends CrossLayer {

	public NoiseToRiverLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_2)) {
			return center != 0 && Biome.applyAll(v -> center == v, w, n, e, s) ? -1 : Biomes.RIVER.getId();
		}
		int validCenter = isValidForRiver(center);
		return Biome.applyAll(v -> validCenter == isValidForRiver(v), w, n, e, s) ? -1 : Biomes.RIVER.getId();
	}

	private static int isValidForRiver(int value) {
		return value >= 2 ? 2 + (value & 1) : value;
	}

}
