package fragrant.feature.decorator.generator;


import fragrant.core.rand.ChunkRand;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.IceSpike;

import java.util.ArrayList;
import java.util.List;

public class IceSpikeGenerator {//TODO

    private final MCVersion version;
    private final IceSpike decorator;

    public IceSpikeGenerator(MCVersion version) {
        this.version = version;
        this.decorator = new IceSpike(version);
    }

    public List<IceSpike.Data> generate(long worldSeed, CPos cPos) {
        return this.generate(worldSeed, cPos.getX(), cPos.getZ());
    }

    public List<IceSpike.Data> generate(long worldSeed, int chunkX, int chunkZ) {
        List<IceSpike.Data> spikes = new ArrayList<>();

        ChunkRand rand = new ChunkRand();
        rand.setDecorationSeed(worldSeed, chunkX, chunkZ, -491660340, this.version);

        ChunkRand localRand = new ChunkRand(rand);

        for (int i = 0; i < 3; i++) {
            int x = rand.nextInt(16) + chunkX * 16;
            int z = rand.nextInt(16) + chunkZ * 16;
            BPos spikePos = new BPos(x, z);
            int above = localRand.nextInt(4);
            int height = localRand.nextInt(4) + 7;
            int radius = localRand.nextInt(2) + height / 4;
            boolean isSuper = localRand.nextInt(60) == 0;
            if (radius > 1 && isSuper) {
                height += localRand.nextInt(30) + 10;
            }
            spikePos.add(0, above, 0);
            spikes.add(new IceSpike.Data(this.decorator, x, z));
        }

        return spikes;
    }

}