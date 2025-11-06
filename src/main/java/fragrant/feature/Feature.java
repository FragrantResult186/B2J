package fragrant.feature;

import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;

public abstract class Feature<C extends Feature.Config, D extends Feature.Data<?>> implements GenerationContext {
    private Context context = null;
    private final C config;
    private final MCVersion version;

    public Feature(C config, MCVersion version) {
        this.config = config;
        this.version = version;
    }

    public C getConfig() {
        return this.config;
    }

    public MCVersion getVersion() {
        return this.version;
    }

    public abstract String getName();

    public static String name() {
        return "unknown";
    }

    public abstract boolean canStart(D data, long structureSeed, ChunkRand rand);

    public abstract boolean canSpawn(D data, BiomeSource source);

    public boolean isValidDimension(Dimension dimension) {
        return this.getValidDimension() == dimension;
    }

    public abstract Dimension getValidDimension();

    @Override
    public Context getContext(long worldSeed) {
        if (this.getContext() == null || this.getContext().getWorldSeed() != worldSeed) {
            this.setContext(GenerationContext.super.getContext(worldSeed));
        }
        return this.getContext();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public static class Config {

    }

    public static class Data<T extends Feature> {
        public final T feature;
        public final int chunkX;
        public final int chunkZ;

        public Data(T feature, int chunkX, int chunkZ) {
            this.feature = feature;
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }
    }

}
