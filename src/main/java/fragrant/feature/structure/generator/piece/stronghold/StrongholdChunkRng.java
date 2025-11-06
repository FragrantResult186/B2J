package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.CPos;
import fragrant.feature.structure.Stronghold;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrongholdChunkRng {
    private static final Map<Class<? extends Stronghold.Piece>, Integer> RNG_COUNTS = new HashMap<>();

    static {// Thanks to crackedMagnet for letting me know about rng consumption
        RNG_COUNTS.put(ChestCorridor.class, 130);
        RNG_COUNTS.put(FiveWayCrossing.class, 595);
        RNG_COUNTS.put(LeftTurn.class, 98);
        RNG_COUNTS.put(RightTurn.class, 98);
        RNG_COUNTS.put(Library.class, 1156);
        RNG_COUNTS.put(PortalRoom.class, 760);
        RNG_COUNTS.put(RoomCrossing.class, 442);
        RNG_COUNTS.put(Corridor.class, 134);
        RNG_COUNTS.put(Stairs.class, 278);
        RNG_COUNTS.put(Start.class, 194);
        RNG_COUNTS.put(SpiralStaircase.class, 194);
        RNG_COUNTS.put(PrisonHall.class, 318);
        RNG_COUNTS.put(SmallCorridor.class, 0);
    }

    public static int getRngCountForChunk(Stronghold.Piece piece, CPos chunk) {
        int rngCount = getRngCount(piece);

        for (Vec3i chestPos : piece.getChestPositions()) {
            if (chestPos != null && chestPos.toBPos().toChunkPos().equals(chunk)) {
                rngCount++;
            }
        }

        return rngCount;
    }

    public static int getRngCount(Stronghold.Piece piece) {
        int count = RNG_COUNTS.get(piece.getClass());

        if (piece instanceof Library library) {
            if (library.isTall()) {
                count += 270;
            }
        }

        return count;
    }

}