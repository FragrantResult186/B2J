package fragrant.b2j.util.position;

/**
 * Represents a position in the world at block level
 */
public class BlockPos {
    private final int x;
    private final int z;
    private final Integer y;

    public BlockPos(int x, int z) {
        this.x = x;
        this.z = z;
        this.y = null;
    }

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public ChunkPos toChunk() {
        return new ChunkPos(x >> 4, z >> 4);
    }

    @Override
    public String toString() {
        return String.format("BlockPos{x=%d, z=%d}", x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockPos blockPos = (BlockPos) o;
        return x == blockPos.x && z == blockPos.z &&
                (y == null ? blockPos.y == null : y.equals(blockPos.y));
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

}