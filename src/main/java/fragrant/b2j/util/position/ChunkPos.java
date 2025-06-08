package fragrant.b2j.util.position;

public record ChunkPos(int x, int z) {

    public BlockPos toBlock() {
        return new BlockPos(x << 4, z << 4);
    }

    @Override
    public String toString() {
        return String.format("ChunkPos{x=%d, z=%d}", x, z);
    }

}