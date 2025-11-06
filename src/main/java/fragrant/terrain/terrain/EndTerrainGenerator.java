package fragrant.terrain.terrain;


import fragrant.biome.source.BiomeSource;
import fragrant.biome.source.EndBiomeSource;
import fragrant.core.block.Block;
import fragrant.core.block.Blocks;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.terrain.utils.NoiseSettings;

public class EndTerrainGenerator extends SurfaceGenerator {

	public EndTerrainGenerator(BiomeSource biomeSource) {
		super(biomeSource, 128, 2, 1,
			biomeSource.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0)
                    ? NoiseSettings.create(2.0, 1.0, 80.0, 160.0)
                    .addTopSlide(-3000, 64, -46)
                    .addBottomSlide(-30, 7, 1)
                    : NoiseSettings.create(2.0, 1.0, 80.0,160.0 )
                    .addTopSlide(-3000, 64, 0)
                    .addBottomSlide(-30, 1, 0),
                0.0, 0.0, true
		);
	}

    @Override
    public int getSeaLevel() {
        return 0;
    }

	@Override
	public Block getDefaultBlock() {
		return Blocks.END_STONE;
	}

	@Override
	public Block getDefaultFluid() {
		return Blocks.AIR;
	}

    @Override
    public Dimension getDimension() {
        return Dimension.END;
    }

    @Override
	public int getBedrockRoofPosition() {
		return -10;
	}

	@Override
	public int getBedrockFloorPosition() {
		return -10;
	}

	@Override
	protected double[] getDepthAndScale(int x, int z) {
		double height = ((EndBiomeSource)this.biomeSource).height.getNoiseValueAt(x, z);
		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0)) {
			double[] depthAndScale = new double[2];
			depthAndScale[0] = height - 8.0f;
			depthAndScale[1] = depthAndScale[0] > 0.0 ? 0.25 : 1.0;
			return depthAndScale;
		}
		return new double[] {height, 0.0D};
	}

	@Override
	protected double computeNoiseFalloff(double depth, double scale, int y) {
		return this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0) ? super.computeNoiseFalloff(depth, scale, y) : this.getMinNoiseY() - depth;
	}


	@Override
	public double getMaxNoiseY() {
		return this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0) ? super.getMaxNoiseY() : (double)((int)super.getMaxNoiseY() / 2);
	}

	@Override
	public double getMinNoiseY() {
		return this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0) ? super.getMinNoiseY() : 8.0D;
	}
}
