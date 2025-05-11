package fragrant.b2j.util;

import java.util.HashMap;
import java.util.Map;

public class Position {

    public static class Pos {
        private final int x;
        private final int z;
        private String type;
        private Integer y;
        private Float size;
        private Boolean isGiant;
        private Integer structureType;
        private final Map<String, Object> metadata = new HashMap<>();

        public void setMeta(String key, Object value) {
            metadata.put(key, value);
        }

        public <T> T getMeta(String key, Class<T> type) {
            return type.cast(metadata.get(key));
        }

        public Pos(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public Pos(int x, int z, String type) {
            this.x = x;
            this.z = z;
            this.type = type;
        }

        public Pos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Pos(int x, int y, int z, float size, boolean isGiant) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = size;
            this.isGiant = isGiant;
            this.type = "RAVINE";
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }

        public Integer getY() {
            return y;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Float getSize() {
            return size;
        }

        public Boolean isGiant() {
            return isGiant;
        }

        public Integer getStructureType() {
            return structureType;
        }

        public void setStructureType(Integer structureType) {
            this.structureType = structureType;
        }

        public String format() {
            if (structureType != null) {
                return Formatter.formatType(this, structureType);
            }
            return defaultFormat();
        }

        public String defaultFormat() {
            return String.format("[X=%d, Z=%d]", x, z);
        }

    }

    public record ChunkPos(int x, int z) {
        public BlockPos toBlock() {
            return new BlockPos(x << 4, z << 4);
        }
        @Override public String toString() {
            return String.format("ChunkPos{x=%d, z=%d}", x, z);
        }
    }

    public record BlockPos(int x, int z) {
        public ChunkPos toChunk() {
            return new ChunkPos(x >> 4, z >> 4);
        }
        @Override public String toString() {
            return String.format("BlockPos{x=%d, z=%d}", x, z);
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }

}