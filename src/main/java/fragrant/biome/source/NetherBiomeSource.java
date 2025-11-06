package fragrant.biome.source;

import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.biome.biome.Biome;
import fragrant.biome.biome.BiomePoint;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.composite.VoronoiLayer;
import fragrant.biome.layer.noise.MultiNoiseLayer17;

public class NetherBiomeSource extends MultiNoiseBiomeSource {

    private static final BiomePoint[] DEFAULT_BIOME_POINTS = {
            new BiomePoint(Biomes.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
            new BiomePoint(Biomes.SOUL_SAND_VALLEY, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
            new BiomePoint(Biomes.CRIMSON_FOREST, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
            new BiomePoint(Biomes.WARPED_FOREST, 0.0F, 0.5F, 0.0F, 0.0F, 0.141F), // 0.375F -> 0.141F
            new BiomePoint(Biomes.BASALT_DELTAS, -0.5F, 0.0F, 0.0F, 0.0F, 0.031F) // 0.175F -> 0.031F
    };

    public NetherBiomeSource(MCVersion version, long worldSeed) {
        super(version, worldSeed, DEFAULT_BIOME_POINTS);
    }

    @Override
    public Dimension getDimension() {
        return Dimension.NETHER;
    }

    protected void build() {
        if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0)) {
            this.layers.add(this.full = new MultiNoiseLayer17(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints));
            this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), true, this.full));
        } else {
            this.layers.add(this.full = new MultiNoiseLayer17(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints) {
                @Override
                public int sample(int x, int y, int z) {
                    return Biomes.NETHER_WASTES.getId();
                }
            });
            this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
                @Override
                public int sample(int x, int y, int z) {
                    return Biomes.NETHER_WASTES.getId();
                }
            });
        }

        this.layers.setScales();
    }

}