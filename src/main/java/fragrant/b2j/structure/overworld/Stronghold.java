package fragrant.b2j.structure.overworld;

import fragrant.b2j.biome.BiomeCache;
import fragrant.b2j.biome.BiomeVerifier;
import fragrant.b2j.structure.BedrockStructure;
import fragrant.b2j.structure.BedrockStructureType;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.ChunkPos;
import fragrant.b2j.util.position.StructurePos;
import fragrant.b2j.util.random.BedrockRandom;
import kaptainwutax.featureutils.structure.generator.piece.StructurePiece;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.Library;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.PortalRoom;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.SquareRoom;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.BlockBox;
import nl.jellejurre.biomesampler.minecraft.Biome;
import nl.kallestruik.noisesampler.minecraft.Dimension;

import java.util.*;

public class Stronghold {

    private static final int villageStrongholdCount = 3;
    private static final int gridSize = 200;
    private static final int gridSizeMinusMargin = 150;

    private static final Map<String, Integer> consumptionRNG = new HashMap<>() {{ // Thanks for Crackedmagnet the explanation about the RNG consumption
        put("PortalRoom", 760);
        put("FiveWayCrossing", 595);
        put("Library", 1392);
        put("Library_short", 1157);
        put("PrisonHall", 318);
        put("SquareRoom_chest", 443);
        put("SquareRoom", 442);
        put("RightTurn", 98);
        put("LeftTurn", 98);
        put("Stairs", 278);
        put("SpiralStaircase", 194); put("Start", 194);
        put("Corridor", 134);
        put("ChestCorridor", 131);
        put("SmallCorridor", 0);
    }};

    public static StructurePos getStaticStronghold(long worldSeed, int regX, int regZ) {
        int regX100 = gridSize * regX + 100;
        int regZ100 = gridSize * regZ + 100;
        long xMul = 784295783249L * regX100;
        long zMul = 827828252345L * regZ100;
        long seed = xMul + zMul + worldSeed + 97858791L;
        BedrockRandom mt = new BedrockRandom(seed);

        int minX = gridSize * regX + gridSize - gridSizeMinusMargin;
        int minZ = gridSize * regZ + gridSize - gridSizeMinusMargin;
        int maxX = gridSize * regX + gridSizeMinusMargin;
        int maxZ = gridSize * regZ + gridSizeMinusMargin;

        int x = mt.nextInt(minX, maxX);
        int z = mt.nextInt(minZ, maxZ);

        /* 25% chance */
        return mt.nextFloat() < 0.25f ? new StructurePos(x << 4, z << 4) : null;
    }

    public static BlockPos[] getVillageStrongholds(long worldSeed, int version) {
        BlockPos[] positions = new BlockPos[villageStrongholdCount];
        BedrockRandom mt = new BedrockRandom(worldSeed);
        BiomeCache biomeCache = new BiomeCache(worldSeed);

        float angle = mt.nextFloat() * (float) Math.PI * 2.0f;
        int radius = mt.nextInt(16) + 40;

        int cnt = 0;
        while (cnt < villageStrongholdCount) {
            int cx = (int) Math.floor(radius * Math.cos(angle));
            int cz = (int) Math.floor(radius * Math.sin(angle));

            boolean found = false;
            for (int x = cx - 8; x < cx + 8 && !found; x++) {
                for (int z = cz - 8; z < cz + 8 && !found; z++) {
                    if (BedrockStructure.isBedrockStructureChunk(BedrockStructureType.VILLAGE, version, worldSeed, x, z) != null)
                    {
                        /* Village Biome Check */
                        int blockX = x << 4, blockZ = z << 4;
                        Biome biome = biomeCache.getBiomeAt(blockX, 64, blockZ, Dimension.OVERWORLD);
                        if (BiomeVerifier.VILLAGE_BIOMES.contains(biome))
                        {
                            positions[cnt++] = new BlockPos(blockX, blockZ);
                            found = true;
                        }
                    }
                }
            }
            angle += (found ? 0.6f : 0.25f) * (float) Math.PI;
            radius += (found ? 8 : 4);
        }

        return positions;
    }

    public static BlockPos getEndPortalPos(long worldSeed, int blockX, int blockZ) {
        kaptainwutax.featureutils.structure.generator.StrongholdGenerator generator = generateStrongholdLayout(worldSeed, blockX, blockZ);

        for (StructurePiece<?> piece : generator.pieceList) {
            if (piece instanceof PortalRoom portalRoom)
            {
                BlockBox frameBB = portalRoom.getEndFrameBB();
                int centerX = (frameBB.minX + frameBB.maxX) / 2;
                int centerZ = (frameBB.minZ + frameBB.maxZ) / 2;
                int y = frameBB.minY;
                return new BlockPos(centerX, y, centerZ);
            }
        }
        return null;
    }

    /* Overlap handling isn't perfect; eye count may be wrong */
    public static Map<String, List<int[]>> getEndPortalEyes(long worldSeed, int blockX, int blockZ) {
        kaptainwutax.featureutils.structure.generator.StrongholdGenerator generator = generateStrongholdLayout(worldSeed, blockX, blockZ);
        BlockBox portalFrameBB = null;
        PortalRoom portalRoom = null;

        for (StructurePiece<?> piece : generator.pieceList) {
            if (piece instanceof PortalRoom)
            {
                portalRoom = (PortalRoom) piece;
                portalFrameBB = portalRoom.getEndFrameBB();
                break;
            }
        }

        if (portalFrameBB == null) return new HashMap<>();

        List<int[]> frameBlocks = getEndFrameBlockPositions(portalFrameBB);
        Map<String, List<int[]>> frameBlocksByChunk = new HashMap<>();

        for (int[] blockPos : frameBlocks) {
            ChunkPos chunkPos = new BlockPos(blockPos[0], blockPos[2]).toChunk();
            String chunkKey = chunkPos.getX() + "," + chunkPos.getZ();

            frameBlocksByChunk.computeIfAbsent(chunkKey, k -> new ArrayList<>()).add(blockPos);
        }

        Map<String, List<int[]>> results = new HashMap<>();

        for (Map.Entry<String, List<int[]>> entry : frameBlocksByChunk.entrySet()) {
            String chunkKey = entry.getKey();
            List<int[]> blocksInChunk = entry.getValue();
            String[] parts = chunkKey.split(",");
            int frameChunkX = Integer.parseInt(parts[0]);
            int frameChunkZ = Integer.parseInt(parts[1]);

            List<StructurePiece<?>> sortedPieces = new ArrayList<>(generator.pieceList);
            sortedPieces.sort(Comparator.comparingInt(p -> p.getBoundingBox().minX * 1000 + p.getBoundingBox().minZ));

            List<StructurePiece<?>> overlappingPieces = new ArrayList<>();
            for (StructurePiece<?> piece : sortedPieces) {
                if (piece == portalRoom) continue;
                if (overlapsChunk(frameChunkX, frameChunkZ, piece))
                {
                    overlappingPieces.add(piece);
                }
            }

            long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, frameChunkX, frameChunkZ);
            BedrockRandom mt = new BedrockRandom(popSeed);

            /* Skip RNG for portalRoom & otherRooms in the same chunk */
            overlappingPieces.forEach(piece ->
                    mt.skipNextInt(consumptionRNG.get("PortalRoom") + getRngCount(piece))
            );

            /* Eye Check */
            List<int[]> eyePositions = new ArrayList<>();
            for (int[] pos : blocksInChunk) {
                /* 10% chance */
                boolean hasEye = mt.nextFloat() > 0.9f;
                eyePositions.add(new int[]{pos[0], pos[1], pos[2], hasEye ? 1 : 0});
            }

            results.put(chunkKey, eyePositions);
        }

        return results;
    }

    public static int countPortalEyes(long worldSeed, int blockX, int blockZ) {
        Map<String, List<int[]>> eyeResults = getEndPortalEyes(worldSeed, blockX, blockZ);
        if (eyeResults.isEmpty()) return 0;

        int totalEyes = -1;
        for (List<int[]> positions : eyeResults.values()) {
            for (int[] pos : positions) {
                if (pos[3] == 1) totalEyes++;
            }
        }
        return totalEyes;
    }

    private static kaptainwutax.featureutils.structure.generator.StrongholdGenerator generateStrongholdLayout(long worldSeed, int chunkX, int chunkZ) {
        kaptainwutax.featureutils.structure.generator.StrongholdGenerator generator = new kaptainwutax.featureutils.structure.generator.StrongholdGenerator(MCVersion.v1_16);
        generator.generate((int) worldSeed, chunkX, chunkZ);
        return generator;
    }

    private static List<int[]> getEndFrameBlockPositions(BlockBox frameBB) {
        List<int[]> blocks = new ArrayList<>();
        int centerX = (frameBB.minX + frameBB.maxX) / 2;
        int centerZ = (frameBB.minZ + frameBB.maxZ) / 2;
        int y = frameBB.minY;

        /* North */
        blocks.add(new int[] { centerX - 2, y, centerZ - 2 });
        blocks.add(new int[] { centerX    , y, centerZ - 2 });
        blocks.add(new int[] { centerX + 2, y, centerZ - 2 });

        /* South */
        blocks.add(new int[] { centerX - 2, y, centerZ + 2 });
        blocks.add(new int[] { centerX    , y, centerZ + 2 });
        blocks.add(new int[] { centerX + 2, y, centerZ + 2 });

        /* East */
        blocks.add(new int[] { centerX + 2, y, centerZ - 1 });
        blocks.add(new int[] { centerX + 2, y, centerZ     });
        blocks.add(new int[] { centerX + 2, y, centerZ + 1 });

        /* West */
        blocks.add(new int[] { centerX - 2, y, centerZ - 1 });
        blocks.add(new int[] { centerX - 2, y, centerZ     });
        blocks.add(new int[] { centerX - 2, y, centerZ + 1 });

        return blocks;
    }

    private static boolean overlapsChunk(int chunkX, int chunkZ, StructurePiece<?> piece) {
        BlockBox box = piece.getBoundingBox();
        int minBlockX = chunkX << 4;
        int minBlockZ = chunkZ << 4;
        int maxBlockX = minBlockX + 15;
        int maxBlockZ = minBlockZ + 15;

        return box.intersectsXZ(minBlockX, minBlockZ, maxBlockX, maxBlockZ) &&
                Math.max(minBlockX, box.minX) <= Math.min(maxBlockX, box.maxX) &&
                Math.max(minBlockZ, box.minZ) <= Math.min(maxBlockZ, box.maxZ);
    }

    private static int getRngCount(StructurePiece<?> piece) {
        String className = piece.getClass().getSimpleName();
        if (className.equals("Library"))
        {
            Library lib = (Library) piece;
            return lib.isTall() ? consumptionRNG.get("Library") : consumptionRNG.get("Library_short");
        }
        if (className.equals("SquareRoom"))
        {
            SquareRoom room = (SquareRoom) piece;
            return room.isStoreRoom() ? consumptionRNG.get("SquareRoom_chest") : consumptionRNG.get("SquareRoom");
        }
        return consumptionRNG.getOrDefault(className, 0);
    }

    public static String format(StructurePos pos) {
        int x = pos.getX() + 4;
        int z = pos.getZ() + 4;

        BlockPos portalPos = getEndPortalPos(pos.getMeta("worldSeed", Long.class), pos.getX() >> 4, pos.getZ() >> 4);
        if (portalPos != null) {
            return String.format("[X=%d, Z=%d] (portal x=%d, z=%d)",
                    x,
                    z,
                    portalPos.getX(),
                    portalPos.getZ()
            );
        }
        return String.format("[X=%d, Z=%d]",
                x,
                z
        );
    }

}