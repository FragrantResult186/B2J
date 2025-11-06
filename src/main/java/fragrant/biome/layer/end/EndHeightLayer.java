package fragrant.biome.layer.end;

import fragrant.core.version.MCVersion;
import fragrant.biome.layer.BoolBiomeLayer;
import fragrant.biome.layer.FloatBiomeLayer;
import fragrant.math.Mth;

public class EndHeightLayer extends FloatBiomeLayer {

	public EndHeightLayer(MCVersion version, BoolBiomeLayer parent) {
		super(version, parent);
	}

	@Override
	public float sample(int x, int y, int z) {
		return this.getNoiseValueAt(x, z);
	}

	public float getNoiseValueAt(int x, int z) {
		int scaledX = x / 2;
		int scaledZ = z / 2;
        float height;

		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_14_0)) {
			height = 100.0F - (float)Math.sqrt((float)(x * x + z * z)) * 8.0F;
		} else {
			height = 100.0F - (float)Math.sqrt((float)x * (float)x + (float)z * (float)z) * 8.0F;
		}

		height = Mth.clamp(height, -100.0F, 80.0F);

		for(int rx = -12; rx <= 12; ++rx) {
			for(int rz = -12; rz <= 12; ++rz) {
				long shiftedX = scaledX + rx;
				long shiftedZ = scaledZ + rz;
				if(shiftedX * shiftedX + shiftedZ * shiftedZ > 4096L
                        && this.getParent(BoolBiomeLayer.class).get((int)shiftedX, 0, (int)shiftedZ)) {
                    float noise;
					noise = Mth.clamp(height, -100.0F, 80.0F);
					height = Math.max(height, noise);
				}
			}
		}

		return height;
	}

}
