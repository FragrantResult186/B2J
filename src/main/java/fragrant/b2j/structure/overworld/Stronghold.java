package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructure;
import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.BedrockStructureType;
import fragrant.b2j.biome.BiomeCache;
import fragrant.b2j.biome.BiomeVerifier;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.ChunkPos;
import fragrant.b2j.util.position.StructurePos;
import nl.jellejurre.biomesampler.minecraft.Biome;
import nl.kallestruik.noisesampler.minecraft.Dimension;

public class Stronghold extends StructureGenerator {

    public static StructurePos getStaticStronghold(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature stronghold = getFeatureChunkInRegion(config, worldSeed, regX, regZ);

        /* 25% Chance */
        if (stronghold.mt().nextFloat() < 0.25f) {
            int blockX = stronghold.position().getX() << 4;
            int blockZ = stronghold.position().getZ() << 4;
            StructurePos pos = new StructurePos(blockX, blockZ, "Static");
            pos.setStructureType(BedrockStructureType.STRONGHOLD);
            return pos;
        }
        return null;
    }

    public static BlockPos[] getVillageStronghold(long worldSeed, int version) {
        BlockPos[] pos = new BlockPos[3];
        BedrockRandom mt = new BedrockRandom(worldSeed);

        double angle = mt.nextFloat() * (float) Math.PI * 2.0f;
        int dist = mt.nextInt(16) + 40;

        int cnt = 0;
        while (cnt < 3) {
            int cx = (int) Math.floor(Math.cos(angle) * dist);
            int cz = (int) Math.floor(Math.sin(angle) * dist);

            /* isVillage */
            boolean village = false;
            for (int x = cx - 8; x < cx + 8; x++) {
                for (int z = cz - 8; z < cz + 8; z++) {
                    ChunkPos chunkPos = new ChunkPos(x, z);

                    if (isVillageChunk(worldSeed, chunkPos, version)) {
                        pos[cnt++] = new BlockPos(x << 4, z << 4);
                        village = true;
                        break;
                    }
                }
                if (village) break;
            }
            if (village) {
                angle += 3 * Math.PI / 5;
                dist += 8;
            } else {
                angle += Math.PI / 4;
                dist += 4;
            }
        }
        return pos;
    }

    public static boolean isVillageChunk(long worldSeed, ChunkPos pos, int version) {
        StructurePos structurePos = BedrockStructure.isBedrockStructureChunk(BedrockStructureType.VILLAGE, version, worldSeed, pos.getX(), pos.getZ());
        if (structurePos == null) { return false; }
        int blockX = pos.getX() << 4;
        int blockZ = pos.getZ() << 4;
        BiomeCache biomeCache = new BiomeCache(worldSeed);
        Biome biome = biomeCache.getBiomeAt(blockX, 64, blockZ, Dimension.OVERWORLD);
        return BiomeVerifier.VILLAGE_BIOMES.contains(biome);
    }

    public static String format(StructurePos pos) {
        String type = pos.getType().equals("Static") ? "" : "(village)";
        return String.format(
                "[X=%d, Z=%d] %s",
                pos.getX() + 4,
                pos.getZ() + 4,
                type
        );
    }

}