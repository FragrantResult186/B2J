package fragrant.feature.structure;


import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.util.block.BlockRotation;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Igloo extends UniformStructure<Igloo> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Igloo.Config(32, 24, 14357617, 0.5F));

    public Igloo(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Igloo(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "igloo";
    }

    public float getBasementChance() {
        return CONFIGS.getAsOf(this.getVersion()).basementChance;
    }

    public boolean hasBasement(long structureSeed, CPos cPos, ChunkRand rand) {
        rand.setPopulationSeed(structureSeed, cPos.getX(), cPos.getZ(), this.getVersion());
        BlockRotation rotation = BlockRotation.getRandom(rand);
        return rand.nextFloat() >= this.getBasementChance();
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.SNOWY_TAIGA || biome == Biomes.SNOWY_TUNDRA;
    }

    public static class Config extends RegionStructure.Config {
        public final float basementChance;

        public Config(int spacing, int separation, int salt, float basementChance) {
            super(spacing, separation, salt);
            this.basementChance = basementChance;
        }
    }

}
