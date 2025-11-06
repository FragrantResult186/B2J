package fragrant.feature.carver;

import fragrant.biome.biome.Biome;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.feature.Feature;
import fragrant.core.util.pos.BPos;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Canyon extends Feature<Canyon.Config, Canyon.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_2_0, new Config(150))
            .add(MCVersion.v1_21_60, new Config(100));

    public Canyon(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Canyon(Config config, MCVersion version) {
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
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        rand.setPopulationSeed(structureSeed, data.chunkX, data.chunkZ, this.getVersion());
        return rand.nextInt(this.getChance()) == 0;
    }

    @Override
    public boolean canSpawn(Data data, BiomeSource source) {
        if (getVersion().isNewerOrEqualTo(MCVersion.v1_21_60)) return true;
        return source.getBiome((int) data.feature.x, (int) data.feature.y, (int) data.feature.z).getCategory() != Biome.Category.OCEAN;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand) {
        rand.setPopulationSeed(worldSeed, chunkX, chunkZ, this.getVersion());
        this.positions = new ArrayList<>();
        this.giant = false;
        boolean bool = _addFeature(chunkX, chunkZ, rand);
        _addTunnel(rand);
        return bool;
    }

    private boolean _addFeature(int chunkX, int chunkZ, ChunkRand rand) {
        if (rand.nextInt(this.getChance()) != 0) {
            return false;
        }
        this.x = rand.nextInt(16) + chunkX * 16;
        if (getVersion().isNewerOrEqualTo(MCVersion.v1_21_60)) {
            this.y = rand.nextInt(10, 68);
            rand.nextInt(); // burn a one call
        } else {
            this.y = rand.nextInt(rand.nextInt(8, 48)) + 20;
        }
        rand.nextInt(); // burn a one call
        this.z = rand.nextInt(16) + chunkZ * 16;
        this.yaw = rand.nextFloat() * Math.PI * 2;
        this.pitch = (rand.nextFloat() - 0.5F) / 4.0F;
        this.thick = (rand.nextFloat() + rand.nextFloat()) * 3.0;
        if (rand.nextFloat() < 0.05F) {
            this.giant = true;
            this.thick *= 2.0;
        }
        return true;
    }

    private void _addTunnel(ChunkRand rand) {
        int canyonSeed = rand.nextInt();
        rand.setSeed(canyonSeed);
        int length = 112 - rand.nextInt(28);
        for (int i = 0; i < 128; i++) {
            if (i == 0 || rand.nextInt(3) == 0) {
                rand.nextFloat(); // for carving
                rand.nextFloat(); // for carving
            }
        }
        double A = 0.0F;
        double B = 0.0F;
        for (int step = 0; step < length; step++) {
            this.positions.add(new BPos(this.x, this.y, this.z));
            rand.nextFloat(); // for carving
            rand.nextFloat(); // for carving
            this.x += cos(this.pitch) * cos(this.yaw);
            this.y += sin(this.pitch);
            this.z += cos(this.pitch) * sin(this.yaw);
            this.pitch *= 0.7F;
            this.pitch += B * 0.05F;
            this.yaw += A * 0.05F;
            B *= 0.8F;
            A *= 0.5F;
            B += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            A += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;
            rand.nextInt(4); // maybe this is used for early breaks
        }
    }

    public double getThickness() {
        return this.thick;
    }

    public boolean isGiant() {
        return this.giant;
    }

    public List<BPos> getPositions() {
        return this.positions;
    }

    public BPos getStartPos() {
        return this.positions.getFirst();
    }

    public BPos getMidPos() {
        return this.positions.get(this.positions.size() / 2);
    }

    public BPos getEndPos() {
        return this.positions.getLast();
    }

    private List<BPos> positions;
    private double x, y, z;
    private double thick;
    private double yaw, pitch;
    private boolean giant;

    public static class Config extends Feature.Config {
        public final int chance;

        public Config(int chance) {
            this.chance = chance;
        }
    }

    public static class Data extends Feature.Data<Canyon> {
        public Data(Canyon feature, int chunkX, int chunkZ) {
            super(feature, chunkX, chunkZ);
        }
    }
}