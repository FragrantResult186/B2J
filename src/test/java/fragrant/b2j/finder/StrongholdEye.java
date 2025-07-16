package fragrant.b2j.finder;

import fragrant.b2j.worldfeature.structure.overworld.underground.Stronghold;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.FeaturePos;

public class StrongholdEye {
    public static void main(String[] args) {
        int radiusChunk = 500;
        int minEye = 7;

        for (long seed = 0; seed < 1L << 32; seed++) {
            int minRegX = Math.floorDiv(-radiusChunk, 200);
            int maxRegX = Math.floorDiv(radiusChunk, 200);
            int minRegZ = Math.floorDiv(-radiusChunk, 200);
            int maxRegZ = Math.floorDiv(radiusChunk, 200);
            for (int regX = minRegX; regX <= maxRegX; regX++) {
                for (int regZ = minRegZ; regZ <= maxRegZ; regZ++) {
                    FeaturePos stronghold = Stronghold.getStaticStronghold(seed, regX, regZ);
                    if (stronghold != null) {
                        int blockX = stronghold.getX();
                        int blockZ = stronghold.getZ();
                        int chunkX = blockX >> 4;
                        int chunkZ = blockZ >> 4;

                        if (Math.abs(chunkX) <= radiusChunk && Math.abs(chunkZ) <= radiusChunk) {
                            int eyeCount = Stronghold.countPortalEyes(seed, chunkX, chunkZ);
                            if (eyeCount >= minEye) {
                                BlockPos portalPos = Stronghold.getEndPortalPos(seed, chunkX, chunkZ);
                                if (portalPos != null)
                                    System.out.printf("%d %d /tp %d ~ %d\n", seed, eyeCount, portalPos.getX(), portalPos.getZ());
                            }
                        }
                    }
                }
            }
        }
    }

}