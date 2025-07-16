package fragrant.b2j;

import fragrant.b2j.worldfeature.feature.end.EndSpike;
import fragrant.b2j.worldfeature.feature.end.EndSpike.Spike;

import java.util.List;

public class BedrockEndSpikeTest {
    public static void main(String[] args) {
        long seed = 123L;
        List<Spike> spikes = EndSpike.TheEndSpikeHelper.getSpikesForLevel(seed);
        for (Spike spike : spikes) {
            System.out.printf("/tp %d %d %d radius=%d guarded=%b%n", spike.x(), spike.height(), spike.z(), spike.radius(), spike.guarded());
        }
    }
}