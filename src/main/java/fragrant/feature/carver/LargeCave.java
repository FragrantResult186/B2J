package fragrant.feature.carver;

import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.BPos;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;
import fragrant.feature.Feature;

import java.util.ArrayList;
import java.util.List;

// -v1_17
public class LargeCave extends Feature<LargeCave.Config, LargeCave.Data> {

    public static final VersionMap<LargeCave.Config> CONFIGS = new VersionMap<LargeCave.Config>()
            .add(MCVersion.v1_13_0, new LargeCave.Config(15));

    public LargeCave(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public LargeCave(LargeCave.Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "canyon";
    }

    @Override
    public String getName() {
        return name();
    }

    private int getChance() {
        return this.getConfig().chance;
    }

    @Override
    public boolean canStart(LargeCave.Data data, long structureSeed, ChunkRand rand) {
        rand.nextInt(); // numberOfCaves
        rand.nextInt(); // numberOfCaves
        rand.nextInt(); // numberOfCaves
        return rand.nextInt(this.getChance()) == 0;
    }

    @Override
    public boolean canSpawn(LargeCave.Data data, BiomeSource source) {
        return true;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public void generate(long worldSeed, int chunkX, int chunkZ) {
        this.positions = new ArrayList<>();
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_18_0)) {
            return;
        }
        ChunkRand rand = new ChunkRand();
        rand.setPopulationSeed(worldSeed, chunkX, chunkZ, this.getVersion());
        this.generate(chunkX, chunkZ, rand);
    }

    public void generate(int chunkX, int chunkZ, ChunkRand rand) {
        int numberOfCaves = rand.nextInt(40);
        numberOfCaves = rand.nextInt(numberOfCaves + 1);
        numberOfCaves = rand.nextInt(numberOfCaves + 1);

        if (rand.nextInt(this.getChance()) != 0) {
            return;
        }

        for (int i = 0; i < numberOfCaves; i++) {
            int z = rand.nextInt(16) + chunkZ * 16;
            int j = rand.nextInt(120) + 8;
            int y = rand.nextInt(j);
            int x = rand.nextInt(16) + chunkX * 16;
            positions.add(new BPos(x, y, z));

            int tunnels = 1;
            if (rand.nextInt(4) != 0) {
                rand.nextInt();
                rand.nextInt();
                tunnels += rand.nextInt(4);
            }

            for (int k = 0; k < tunnels; k++) {
                rand.nextInt();
                rand.nextInt();
                rand.nextInt();
                rand.nextInt();
                rand.nextInt();
            }
        }
    }

    private List<BPos> positions;

    public List<BPos> getPositions() {
        return this.positions;
    }

    public static class Config extends Feature.Config {
        public final int chance;

        public Config(int chance) {
            this.chance = chance;
        }
    }

    public static class Data extends Feature.Data<LargeCave> {
        public Data(LargeCave feature, int chunkX, int chunkZ) {
            super(feature, chunkX, chunkZ);
        }
    }
}
