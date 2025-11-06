package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;

import java.util.*;
import java.util.stream.IntStream;

public class EndSpike extends Decorator<EndSpike.Config, EndSpike.Data> {

    public EndSpike(MCVersion version) {
        super(null, version);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        return null;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.THE_END;
    }

    public static String name() {
        return "end_spike";
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        return true;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.END;
    }

    public List<Data> generate(long worldSeed) {
        ChunkRand rand = new ChunkRand();
        rand.setSeed(worldSeed);
        List<Integer> indices = new ArrayList<>(IntStream.range(0, 10).boxed().toList());
        IntStream.range(1, 10).forEach(i -> Collections.swap(indices, i, rand.nextInt(i + 1)));
        return this.generate(indices);
    }

    private List<Data> generate(List<Integer> indices) {
        List<Data> data = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            double angle = i * 6553.6F - 65536.0F;
            double rad1 = Math.toRadians((angle + 16384.0F) / 182.044F);
            double rad2 = Math.toRadians(angle / 182.044F);
            int centerX = (int) (Math.sin(rad1) * 42.0F);
            int centerZ = (int) (Math.sin(rad2) * 42.0F);
            int index = indices.get(i);
            int radius = index / 3 + 2;
            int height = index * 3 + 76;
            boolean guarded = index == 1 || index == 2;
            data.add(new Data(this, centerX, centerZ, radius, height, guarded));
        }
        return data;
    }

    public static class Data extends Decorator.Data<EndSpike> {
        public final int centerX;
        public final int centerZ;
        public final int radius;
        public final int height;
        public final boolean guarded;

        public Data(EndSpike decorator, int centerX, int centerZ, int radius, int height, boolean guarded) {
            super(decorator, centerX >> 4, centerZ >> 4);
            this.centerX = centerX;
            this.centerZ = centerZ;
            this.radius = radius;
            this.height = height;
            this.guarded = guarded;
        }
    }
}