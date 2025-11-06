package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.util.pos.RPos;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Village extends TriangularStructure<Village> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(27, 17, 10387312, 0.2F))
            .add(MCVersion.v1_17_0, new Config(34, 26, 10387312, 0.02F));

    public Village(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Village(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "village";
    }

    public float getZombieChance() {
        return CONFIGS.getAsOf(this.getVersion()).zombieChance;
    }

    public boolean isZombieVillage(long structureSeed, CPos cPos, ChunkRand rand) {
        RPos rPos = cPos.toRegionPos(34);
        rand.setRegionSeed(structureSeed, rPos.getX(), rPos.getZ(), getSalt(), this.getVersion());
        rand.nextInt(/*this.getSeparation()*/);
        rand.nextInt(/*this.getSeparation()*/);
        rand.nextInt(/*this.getSeparation()*/);
        rand.nextInt(/*this.getSeparation()*/);
        int orientation = rand.nextInt();;
        return rand.nextFloat() < this.getZombieChance();
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        if (biome == Biomes.PLAINS || biome == Biomes.DESERT || biome == Biomes.SAVANNA) return true;
        if (biome == Biomes.TAIGA && this.getVersion().isNewerOrEqualTo(MCVersion.v1_10_0)) return true;
        return biome == Biomes.SNOWY_TUNDRA && this.getVersion().isNewerOrEqualTo(MCVersion.v1_14_0);
    }

    public static class Config extends RegionStructure.Config {
        public final float zombieChance;

        public Config(int spacing, int separation, int salt, float zombieChance) {
            super(spacing, separation, salt);
            this.zombieChance = zombieChance;
        }
    }
}
