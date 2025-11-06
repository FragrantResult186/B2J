package fragrant.feature.structure.generator;

import fragrant.core.util.block.BlockBox;
import fragrant.core.util.pos.CPos;
import fragrant.feature.structure.generator.piece.StructurePiece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class ChunkRngTracker<T extends StructurePiece<T>> {
    private final Map<CPos, Integer> chunkRngCounts = new HashMap<>();
    private final BiFunction<T, CPos, Integer> rngCountFunction;

    public ChunkRngTracker(BiFunction<T, CPos, Integer> rngCountFunction) {
        this.rngCountFunction = rngCountFunction;
    }

    public void addPiece(T piece) {
        BlockBox box = piece.getBoundingBox();
        if (box == null) return;

        Set<CPos> overlappingChunks = getOverlappingChunks(box);

        for (CPos chunk : overlappingChunks) {
            int rngCount = rngCountFunction.apply(piece, chunk);
            chunkRngCounts.merge(chunk, rngCount, Integer::sum);
        }
    }

    private Set<CPos> getOverlappingChunks(BlockBox box) {
        Set<CPos> chunks = new HashSet<>();

        int minChunkX = box.minX >> 4;
        int maxChunkX = box.maxX >> 4;
        int minChunkZ = box.minZ >> 4;
        int maxChunkZ = box.maxZ >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                chunks.add(new CPos(cx, cz));
            }
        }

        return chunks;
    }

    public int getRngCount(CPos chunk) {
        return chunkRngCounts.getOrDefault(chunk, 0);
    }

    public int getTrackedChunkCount() {
        return chunkRngCounts.size();
    }
}