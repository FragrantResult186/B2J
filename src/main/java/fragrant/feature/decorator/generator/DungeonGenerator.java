package fragrant.feature.decorator.generator;

import fragrant.core.block.Block;
import fragrant.core.block.BlockState;
import fragrant.core.block.Blocks;
import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.core.version.MCVersion;

import java.util.*;

public class DungeonGenerator {

    private final MCVersion version;
    public List<Data> data;

    public DungeonGenerator(MCVersion version) {
        this.version = version;
    }

    public void generate(long worldSeed, int chunkX, int chunkZ) {
        this.data = new ArrayList<>();

        ChunkRand rand = new ChunkRand();
        rand.setPopulationSeed(worldSeed, chunkX, chunkZ, this.version);

        for (int i = 0; i < 8; i++) {
            int x = rand.nextInt(8, 24) + chunkX * 16;
            int y = rand.nextInt(-58, 0);
            int z = rand.nextInt(8, 24) + chunkZ * 16;
            BPos pos = new BPos(x, y, z);
            this.data.add(place(pos, rand));
        }
    }

    private Data place(BPos spawnerPos, ChunkRand random) {
        int roomWidth = random.nextInt(2) + 2;
        int roomDepth = random.nextInt(2) + 2;
        BlockBox roomBox = createRoomBox(spawnerPos, roomWidth, roomDepth);

        Data roomData = new Data(spawnerPos, "unknown", roomBox, roomWidth, roomDepth);

        if (!isValidRoom(spawnerPos, roomBox, roomWidth, roomDepth, roomData)) {
            return null;
        }

        buildRoom(spawnerPos, roomBox, roomWidth, roomDepth, random, roomData);
        placeChests(spawnerPos, roomWidth, roomDepth, random, roomData);
        placeSpawner(spawnerPos, random, roomData);

        return roomData;
    }

    private BlockBox createRoomBox(BPos spawnerPos, int roomWidth, int roomDepth) {
        int minX = spawnerPos.getX() - roomWidth - 1;
        int maxX = spawnerPos.getX() + roomWidth + 1;
        int minY = spawnerPos.getY() - 1;
        int maxY = spawnerPos.getY() + 3;
        int minZ = spawnerPos.getZ() - roomDepth - 1;
        int maxZ = spawnerPos.getZ() + roomDepth + 1;

        return new BlockBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private boolean isValidRoom(BPos spawnerPos, BlockBox roomBox, int roomWidth, int roomDepth, Data roomData) {
        int air = 0;
        for (int x = roomBox.minX; x <= roomBox.maxX; x++) {
            for (int y = roomBox.minY; y <= roomBox.maxY; y++) {
                for (int z = roomBox.minZ; z <= roomBox.maxZ; z++) {
                    BPos pos = new BPos(x, y, z);
                    Block block = roomData.getBlock(pos);

                    if (isWallPosition(pos, spawnerPos, roomWidth, roomDepth) && y == spawnerPos.getY()) {
                        BPos abovePos = new BPos(x, y + 1, z);
                        if (roomData.isAir(pos) && roomData.isAir(abovePos)) {
                            air++;
                        }
                    }
                }
            }
        }

        return true; // TODO
    }

    private void buildRoom(BPos spawnerPos, BlockBox roomBox, int roomWidth, int roomDepth, ChunkRand random, Data roomData) {
        for (int x = roomBox.minX; x <= roomBox.maxX; x++) {
            for (int y = roomBox.minY; y <= roomBox.maxY; y++) {
                for (int z = roomBox.minZ; z <= roomBox.maxZ; z++) {
                    BPos pos = new BPos(x, y, z);

                    if (isInteriorPosition(pos, spawnerPos, roomWidth, roomDepth)) {
                        roomData.setBlockState(pos, new BlockState(Blocks.AIR));
                        continue;
                    }

                    Block cobblestone;
                    if (y == spawnerPos.getY() - 1 && random.nextInt(4) != 0) {
                        cobblestone = Blocks.MOSSY_COBBLESTONE;
                    } else {
                        cobblestone = Blocks.COBBLESTONE;
                    }

                    roomData.setBlockState(pos, new BlockState(cobblestone));
                }
            }
        }
    }

    private void placeChests(BPos spawnerPos, int roomWidth, int roomDepth, ChunkRand random, Data roomData) {
        int place = 0;
        int maxChests = 2;

        int attempts = 0;
        while (attempts < 3 && place < maxChests) {
            for (int i = 0; i < maxChests && place < maxChests; i++) {
                int chestZ = spawnerPos.getZ() + random.nextInt(roomDepth * 2 + 1) - roomDepth;
                int chestX = spawnerPos.getX() + random.nextInt(roomWidth * 2 + 1) - roomWidth;
                BPos chestPos = new BPos(chestX, spawnerPos.getY(), chestZ);

                if (canPlaceChest(chestPos, roomData)) {
                    String lootTable = "loot_tables/chests/simple_dungeon.json";
                    int lootSeed = random.nextInt();
                    roomData.setBlockState(chestPos, new BlockState(Blocks.CHEST));
                    roomData.setChest(chestPos, new ChestData(lootTable, lootSeed));
                    place++;
                }
            }
            attempts++;
        }
    }

    private boolean canPlaceChest(BPos chestPos, Data roomData) {
        if (!roomData.isAir(chestPos)) return false;

        BPos[] neighbors = {
                chestPos.add(-1, 0, 0),
                chestPos.add(1, 0, 0),
                chestPos.add(0, 0, -1),
                chestPos.add(0, 0, 1)
        };

        int solidNeighbors = 0;
        for (BPos neighbor : neighbors) {
            Block block = roomData.getBlock(neighbor);
            if (block.getId() != 0) {
                solidNeighbors++;
            }
        }

        return solidNeighbors == 1;
    }

    private void placeSpawner(BPos spawnerPos, ChunkRand random, Data roomData) {
        roomData.mobType = selectRandomMobType(random);
        roomData.setBlockState(spawnerPos, new BlockState(Blocks.SPAWNER));
    }

    // TODO maybe this is wrong
    private String selectRandomMobType(ChunkRand random) {
        int mobType = random.nextInt(4);
        if (mobType == 0) {
            return "minecraft:skeleton";
        } else if (mobType == 2) {
            return "minecraft:zombie";
        } else if (mobType == 3) {
            return "minecraft:spider";
        } else {
            return "minecraft:zombie";
        }
    }

    private boolean isWallPosition(BPos pos, BPos spawnerPos, int roomWidth, int roomDepth) {
        int x = pos.getX(), z = pos.getZ();
        return (x == spawnerPos.getX() - roomWidth - 1 || x == spawnerPos.getX() + roomWidth + 1) ||
                (z == spawnerPos.getZ() - roomDepth - 1 || z == spawnerPos.getZ() + roomDepth + 1);
    }

    private boolean isInteriorPosition(BPos pos, BPos spawnerPos, int roomWidth, int roomDepth) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        return (x != spawnerPos.getX() - roomWidth - 1 && x != spawnerPos.getX() + roomWidth + 1) &&
                (y != spawnerPos.getY() - 1) &&
                (z != spawnerPos.getZ() - roomDepth - 1 && z != spawnerPos.getZ() + roomDepth + 1);
    }

    public static class Data {
        private final BPos spawnerPos;
        private final BlockBox roomBox;
        private final int roomWidth;
        private final int roomDepth;
        private final Map<BPos, BlockState> blockStates;
        private final Map<BPos, ChestData> chests;
        private String mobType;

        public Data(BPos spawnerPos, String mobType, BlockBox roomBox, int roomWidth, int roomDepth) {
            this.spawnerPos = spawnerPos;
            this.mobType = mobType;
            this.roomBox = roomBox;
            this.roomWidth = roomWidth;
            this.roomDepth = roomDepth;
            this.blockStates = new HashMap<>();
            this.chests = new HashMap<>();
        }

        public BPos getSpawnerPos() { return spawnerPos; }
        public String getMobType() { return mobType; }
        public BlockBox getRoomBox() { return roomBox; }
        public int getRoomWidth() { return roomWidth; }
        public int getRoomDepth() { return roomDepth; }
        public Map<BPos, BlockState> getBlockStates() { return new HashMap<>(blockStates); }
        public Map<BPos, ChestData> getChests() { return new HashMap<>(chests); }

        void setBlockState(BPos pos, BlockState blockState) {
            blockStates.put(pos, blockState);
        }

        void setChest(BPos pos, ChestData chestData) {
            chests.put(pos, chestData);
        }

        public BlockState getBlockState(BPos pos) {
            return blockStates.getOrDefault(pos, new BlockState(Blocks.AIR));
        }

        public Block getBlock(BPos pos) {
            return getBlockState(pos).getBlock();
        }

        public ChestData getChestData(BPos pos) {
            return chests.get(pos);
        }

        public boolean isAir(BPos pos) {
            Block block = getBlock(pos);
            return block == null || block == Blocks.AIR;
        }

        public List<BPos> getChestPositions() {
            return new ArrayList<>(chests.keySet());
        }

        public List<BPos> getBlockPositions() {
            return new ArrayList<>(blockStates.keySet());
        }

        public boolean containsPosition(Vec3i pos) {
            return roomBox.contains(pos);
        }
    }

    public static class ChestData {
        private final String lootTable;
        private final long seed;

        public ChestData(String lootTable, long seed) {
            this.lootTable = lootTable;
            this.seed = seed;
        }

        public String getLootTable() {
            return lootTable;
        }

        public long getSeed() {
            return seed;
        }
    }
}