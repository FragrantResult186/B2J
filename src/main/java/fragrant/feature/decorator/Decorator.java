package fragrant.feature.decorator;

import fragrant.core.rand.ChunkRand;
import fragrant.feature.BiomeValidatedFeature;
import fragrant.feature.Feature;
import fragrant.core.version.MCVersion;

import java.util.HashMap;
import java.util.Map;

public abstract class Decorator<C extends Feature.Config, D extends Feature.Data<?>> extends BiomeValidatedFeature<C, D> {

    public static final Map<Class<? extends Decorator<?, ?>>, String> CLASS_TO_NAME = new HashMap<>();

    private int numerator = 0;
    private int denominator = 0;

    public Decorator(C config, MCVersion version) {
        super(config, version);
    }

    public void setChance(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static String getName(Class<? extends Decorator> decoratorClass) {
        return CLASS_TO_NAME.get(decoratorClass);
    }

    public static String name() {
        return "decorator";
    }

    @Override
    public String getName() {
        return getName(this.getClass());
    }

    public D getData(long structureSeed, int chunkX, int chunkZ) {
        return this.getData(structureSeed, chunkX, chunkZ, new ChunkRand());
    }

    public abstract D getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand);

    public boolean shouldGenerate(ChunkRand rand) {
        return rand.nextInt(this.denominator) < this.numerator;
    }

    public int getHash(String name) {
        int hash = -2078137563;
        for (char c : name.toCharArray()) {
            hash = hash * 435 ^ c;
        }
        return hash;
    }
}
