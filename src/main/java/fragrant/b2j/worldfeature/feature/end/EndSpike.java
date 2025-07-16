package fragrant.b2j.worldfeature.feature.end;

import fragrant.b2j.util.random.BedrockRandom;

import java.util.*;
import java.util.stream.IntStream;

public class EndSpike {
    private static final int SPIKE_COUNT = 10;
    private static final float ANGLE_STEP = 6553.6f, ANGLE_OFFSET = 65536.0f;
    private static final float RAD_OFFSET = 16384.0f, RAD_DIVISOR = 182.044f;
    private static final float DISTANCE = 42.0f;
    private static final int BASE_RADIUS = 2;
    private static final int BASE_HEIGHT = 76;

    public record Spike(int x, int z, int radius, int height, boolean guarded) {}

    public static class TheEndSpikeHelper {
        public static List<Spike> getSpikesForLevel(long worldSeed) {
            BedrockRandom mt = new BedrockRandom(worldSeed);
            List<Integer> indices = new ArrayList<>(IntStream.range(0, SPIKE_COUNT).boxed().toList());
            IntStream.range(1, SPIKE_COUNT).forEach(i -> Collections.swap(indices, i, mt.nextInt(i + 1)));
            return generateSpikes(indices);
        }
    }

    private static List<Spike> generateSpikes(List<Integer> indices) {
        List<Spike> spikes = new ArrayList<>(SPIKE_COUNT);
        for (int i = 0; i < SPIKE_COUNT; i++) {
            float angle = i * ANGLE_STEP - ANGLE_OFFSET;
            float rad1 = (float) Math.toRadians((angle + RAD_OFFSET) / RAD_DIVISOR);
            float rad2 = (float) Math.toRadians(angle / RAD_DIVISOR);
            int x = (int) (Math.sin(rad1) * DISTANCE);
            int z = (int) (Math.sin(rad2) * DISTANCE);
            int index = indices.get(i);
            int radius = index / 3 + BASE_RADIUS;
            int height = index * 3 + BASE_HEIGHT;
            boolean guarded = index - 1 < 2;
            spikes.add(new Spike(x, z, radius, height, guarded));
        }
        return spikes;
    }
}