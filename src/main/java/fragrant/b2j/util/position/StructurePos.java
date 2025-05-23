package fragrant.b2j.util.position;

import fragrant.b2j.util.Formatter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a structure position in the world.
 * This replaces the Position.Pos class.
 */
public class StructurePos {
    private final int x;
    private final int z;
    private Integer y;
    private String type;
    private Float size;
    private Boolean isGiant;
    private Integer structureType;
    private final Map<String, Object> metadata = new HashMap<>();

    public StructurePos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public StructurePos(int x, int z, String type) {
        this.x = x;
        this.z = z;
        this.type = type;
    }

    public StructurePos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public StructurePos(int x, int y, int z, float size, boolean isGiant) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.isGiant = isGiant;
        this.type = "RAVINE";
    }

    public void setMeta(String key, Object value) {
        metadata.put(key, value);
    }

    public <T> T getMeta(String key, Class<T> type) {
        return type.cast(metadata.get(key));
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

    public void setY(Integer y) {
        this.y = y;
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

    public BlockPos toBlockPos() {
        return new BlockPos(x, y != null ? y : 0, z);
    }

    public ChunkPos toChunkPos() {
        return new ChunkPos(x >> 4, z >> 4);
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

    @Override
    public String toString() {
        return format();
    }

}