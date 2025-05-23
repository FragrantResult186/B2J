package fragrant.b2j.util.position;

/**
 * Represents a position in the world at chunk level
 */
public class ChunkPos {
    private final int x;
    private final int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public BlockPos toBlock() {
        return new BlockPos(x << 4, z << 4);
    }

    @Override
    public String toString() {
        return String.format("ChunkPos{x=%d, z=%d}", x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkPos chunkPos = (ChunkPos) o;
        return x == chunkPos.x && z == chunkPos.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }

}