package fragrant.b2j.worldfeature.structure.overworld.underground;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.ChunkPos;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;
import kaptainwutax.featureutils.structure.generator.piece.StructurePiece;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.Library;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.PortalRoom;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.SquareRoom;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.BlockBox;

import java.util.*;

public class Stronghold {

    private static final int villageStrongholdCount = 3;
    private static final int gridSize = 200;
    private static final int gridSizeMinusMargin = 150;

    public static FeaturePos getStaticStronghold(long worldSeed, int regX, int regZ) {
        int regX100 = gridSize * regX + 100;
        int regZ100 = gridSize * regZ + 100;
        int xMul = -1683231919 * regX100;
        int zMul = -1100435783 * regZ100;
        long seed = xMul + zMul + worldSeed + 97858791;
        BedrockRandom mt = new BedrockRandom(seed);

        int minX = gridSize * regX + gridSize - gridSizeMinusMargin;
        int minZ = gridSize * regZ + gridSize - gridSizeMinusMargin;
        int maxX = gridSize * regX + gridSizeMinusMargin;
        int maxZ = gridSize * regZ + gridSizeMinusMargin;

        int x = mt.nextInt(minX, maxX);
        int z = mt.nextInt(minZ, maxZ);

        if (mt.nextFloat() < 0.25f) {
            return new FeaturePos(x << 4, z << 4);
        }
        return null;
    }

    public static BlockPos[] getVillageStrongholds(long worldSeed, int version, boolean skipBiomeCheck) {
        BlockPos[] pos = new BlockPos[villageStrongholdCount];
        BedrockRandom mt = new BedrockRandom(worldSeed);

        float angle = mt.nextFloat() * (float) Math.PI * 2.0f;
        int radius = mt.nextInt(16) + 40;

        int cnt = 0;
        while (cnt < villageStrongholdCount) {
            int cx = (int) Math.floor(radius * Math.cos(angle));
            int cz = (int) Math.floor(radius * Math.sin(angle));

            boolean found = false;
            for (int x = cx - 8; x < cx + 8 && !found; x++) {
                for (int z = cz - 8; z < cz + 8 && !found; z++) {
                    if (BedrockFeature.isFeatureChunk(BedrockFeatureType.VILLAGE, version, worldSeed, x, z, skipBiomeCheck) != null) {
                        int blockX = x << 4;
                        int blockZ = z << 4;

                        if (skipBiomeCheck) {
                            pos[cnt++] = new BlockPos(blockX, blockZ);
                            found = true;
                        } else {
                            Biome bedrockBiome = Biome.getBiomeCache(worldSeed, version);
                            BiomeID biome = bedrockBiome.getBiomeAt(blockX, 64, blockZ, Dimension.DIM_OVERWORLD);
                            Set<BiomeID> validBiomes = version >= BedrockVersion.MC_1_18
                                    ? Biome.VILLAGE_BIOMES_1_18
                                    : Biome.VILLAGE_BIOMES_1_14;

                            if (validBiomes.contains(biome)) {
                                pos[cnt++] = new BlockPos(blockX, blockZ);
                                found = true;
                            }
                        }
                    }
                }
            }
            angle += (found ? 0.6f : 0.25f) * (float) Math.PI;
            radius += (found ? 8 : 4);
        }

        return pos;
    }

    public static BlockPos getEndPortalPos(long worldSeed, int blockX, int blockZ) {
        kaptainwutax.featureutils.structure.generator.StrongholdGenerator generator = generateStrongholdLayout(worldSeed, blockX, blockZ);

        for (StructurePiece<?> piece : generator.pieceList) {
            if (piece instanceof PortalRoom portalRoom) {
                BlockBox frameBB = portalRoom.getEndFrameBB();
                int centerX = (frameBB.minX + frameBB.maxX) / 2;
                int centerZ = (frameBB.minZ + frameBB.maxZ) / 2;
                int y = frameBB.minY;
                return new BlockPos(centerX, y, centerZ);
            }
        }
        return null;
    }



    /*
     *
     * So yeah, this code is basically just a bunch of guesses I made.
     * I tried a bunch of things and, somehow, it ended up working.
     *
     */

    public static Map<String, List<int[]>> getEndPortalEyes(long worldSeed, int blockX, int blockZ) {
        kaptainwutax.featureutils.structure.generator.StrongholdGenerator generator = generateStrongholdLayout(worldSeed, blockX, blockZ);
        BlockBox portalFrameBB = null;
        PortalRoom portalRoom;

        for (StructurePiece<?> piece : generator.pieceList) {
            if (piece instanceof PortalRoom) {
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
            String chunkKey = chunkPos.x() + "," + chunkPos.z();
            frameBlocksByChunk.computeIfAbsent(chunkKey, k -> new ArrayList<>()).add(blockPos);
        }

        Map<String, List<int[]>> results = new HashMap<>();

        for (Map.Entry<String, List<int[]>> entry : frameBlocksByChunk.entrySet()) {
            String chunkKey = entry.getKey();
            List<int[]> blocksInChunk = entry.getValue();
            String[] parts = chunkKey.split(",");
            int frameChunkX = Integer.parseInt(parts[0]);
            int frameChunkZ = Integer.parseInt(parts[1]);

            results.put(chunkKey, skipToEyeRNG(worldSeed, frameChunkX, frameChunkZ, blocksInChunk, generator.pieceList));
        }

        return results;
    }

    /* Skip RNG for portalRoom & otherRooms in the same chunk */
    private static List<int[]> skipToEyeRNG(long worldSeed, int chunkX, int chunkZ,
                                            List<int[]> frameBlocks,
                                            List<kaptainwutax.featureutils.structure.Stronghold.Piece> allPieces) {

        Box chunkBox = new Box(chunkX << 4, 0, chunkZ << 4, (chunkX << 4) + 15, 128, (chunkZ << 4) + 15);

        long seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

        for (StructurePiece<?> piece : allPieces) {
            if (overlapsChunk(chunkX, chunkZ, piece)) {
                int rngCount = getRngCount(piece, chunkBox);

                mt.skipNextInt(rngCount); // Skip Room RNG

                if (piece instanceof PortalRoom) {
                    break;
                }
            }
        }
        List<int[]> eyePos = new ArrayList<>();

        frameBlocks.sort((a, b) -> {
            if (a[2] != b[2]) return Integer.compare(a[2], b[2]);
            return Integer.compare(a[0], b[0]);
        });

        /* Eye Check */
        for (int[] pos : frameBlocks) {
            /* 10% chance */
            boolean hasEye = mt.nextFloat() > 0.9f;
            eyePos.add(new int[]{pos[0], pos[1], pos[2], hasEye ? 1 : 0});
        }

        return eyePos;
    }

    private static int getRngCount(StructurePiece<?> piece, Box chunkBox) {
        String className = piece.getClass().getSimpleName();
        BlockBox pieceBB = piece.getBoundingBox();
        Box pieceBox = new Box(pieceBB.minX, pieceBB.minY, pieceBB.minZ, pieceBB.maxX, pieceBB.maxY, pieceBB.maxZ);
        if (!pieceBox.overlaps(chunkBox)) {
            return 0;
        }
        int rngCnt;
        switch (className) {
            case "PortalRoom":
                return 760;

            case "FiveWayCrossing":
                return 595;

            case "Library":
                Library lib = (Library) piece;
                /* tall library = 1426, short library = 1156 */
                rngCnt = lib.isTall() ? 1426 : 1156;
                int[] chestPos = getLibraryChestPos(piece);

                if (chunkBox.contains(chestPos)) {
                    rngCnt += 1; // chest adds 1 RNG call
                }
                if (lib.isTall()) {
                    int[] tallChestPos = getLibraryTallChestPos(piece);
                    if (chunkBox.contains(tallChestPos)) {
                        rngCnt += 1; // chest adds 1 RNG call
                    }
                }
                return rngCnt;

            case "SquareRoom":
                SquareRoom room = (SquareRoom) piece;
                rngCnt = 442;
                if (room.isStoreRoom()) {
                    int[] storeChestPos = getStoreRoomChestPos(piece);
                    if (chunkBox.contains(storeChestPos)) {
                        rngCnt += 1; // chest adds 1 RNG call
                    }
                }
                return rngCnt;

            case "ChestCorridor":
                rngCnt = 130;
                int[] corridorChestPos = getChestCorridorChestPos(piece);
                if (chunkBox.contains(corridorChestPos)) {
                    rngCnt += 1; // chest adds 1 RNG call
                }
                return rngCnt;

            case "RightTurn":
            case "LeftTurn":
                return 98;

            case "Stairs":
                return 278;

            case "SpiralStaircase":
                return 194;

            case "Corridor":
                return 134;

            case "PrisonHall":
                return 318;

            default:
                return 0;
        }
    }

    private static int[] getLibraryChestPos(StructurePiece<?> piece) {
        int x = piece.applyXTransform(3, 5);
        int y = piece.applyYTransform(0);
        int z = piece.applyZTransform(3, 5);
        return new int[]{x, y, z};
    }

    private static int[] getLibraryTallChestPos(StructurePiece<?> piece) {
        int x = piece.applyXTransform(12, 1);
        int y = piece.applyYTransform(0);
        int z = piece.applyZTransform(12, 1);
        return new int[]{x, y, z};
    }

    private static int[] getChestCorridorChestPos(StructurePiece<?> piece) {
        int x = piece.applyXTransform(3, 3);
        int y = piece.applyYTransform(0);
        int z = piece.applyZTransform(3, 3);
        return new int[]{x, y, z};
    }

    private static int[] getStoreRoomChestPos(StructurePiece<?> piece) {
        int x = piece.applyXTransform(3, 8);
        int z = piece.applyZTransform(3, 8);
        int y = piece.applyYTransform(0);
        return new int[]{x, y, z};
    }

    public static int countPortalEyes(long worldSeed, int blockX, int blockZ) {
        Map<String, List<int[]>> eyeResults = getEndPortalEyes(worldSeed, blockX, blockZ);
        if (eyeResults.isEmpty()) return 0;

        int eyeCount = 0;
        for (List<int[]> positions : eyeResults.values()) {
            for (int[] pos : positions) {
                if (pos[3] == 1) {
                    eyeCount++;
                }
            }
        }
        return eyeCount;
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
        blocks.add(new int[]{centerX - 2, y, centerZ - 2});
        blocks.add(new int[]{centerX, y, centerZ - 2});
        blocks.add(new int[]{centerX + 2, y, centerZ - 2});
        /* South */
        blocks.add(new int[]{centerX - 2, y, centerZ + 2});
        blocks.add(new int[]{centerX, y, centerZ + 2});
        blocks.add(new int[]{centerX + 2, y, centerZ + 2});
        /* East */
        blocks.add(new int[]{centerX + 2, y, centerZ - 1});
        blocks.add(new int[]{centerX + 2, y, centerZ});
        blocks.add(new int[]{centerX + 2, y, centerZ + 1});
        /* West */
        blocks.add(new int[]{centerX - 2, y, centerZ - 1});
        blocks.add(new int[]{centerX - 2, y, centerZ});
        blocks.add(new int[]{centerX - 2, y, centerZ + 1});

        return blocks;
    }

    public static class Box {
        public int minX, minY, minZ, maxX, maxY, maxZ;

        public Box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public boolean overlaps(Box box2) {
            return !(maxX < box2.minX ||
                    minX > box2.maxX ||
                    maxY < box2.minY ||
                    minY > box2.maxY ||
                    maxZ < box2.minZ ||
                    minZ > box2.maxZ);
        }

        public boolean contains(int[] pos) {
            return pos[0] >= minX && pos[0] <= maxX &&
                   pos[2] >= minZ && pos[2] <= maxZ;
        }
    }

    private static boolean overlapsChunk(int chunkX, int chunkZ, StructurePiece<?> piece) {
        BlockBox structureBox = piece.getBoundingBox();

        int minBlockX = chunkX << 4;
        int minBlockZ = chunkZ << 4;
        int maxBlockX = minBlockX + 15;
        int maxBlockZ = minBlockZ + 15;

        Box chunkBox = new Box(
                minBlockX, 0, minBlockZ,
                maxBlockX, 128, maxBlockZ
        );
        Box pieceBox = new Box(
                structureBox.minX, structureBox.minY, structureBox.minZ,
                structureBox.maxX, structureBox.maxY, structureBox.maxZ
        );

        return pieceBox.overlaps(chunkBox);
    }

    public static String format(FeaturePos pos) {
        BlockPos portalPos = getEndPortalPos(pos.getMeta("worldSeed", Long.class), pos.getX() >> 4, pos.getZ() >> 4);
        if (portalPos != null) {
            return String.format("chunkPos{X=%d, Z=%d} (portal x=%d, z=%d, eye=%d) /tp %d ~ %d",
                    pos.getX() >> 4,
                    pos.getZ() >> 4,
                    portalPos.getX(),
                    portalPos.getZ(),
                    countPortalEyes(pos.getMeta("worldSeed", Long.class), pos.getX() >> 4, pos.getZ() >> 4),
                    pos.getX() + 4,
                    pos.getZ() + 4
            );
        }
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + 4,
                pos.getZ() + 4
        );
    }

}