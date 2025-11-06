package fragrant.feature.structure.generator.piece.fortress;

import fragrant.core.rand.ChunkRand;
import fragrant.feature.structure.generator.piece.PieceWeight;

public class Start extends BridgeCrossing { //Can only presume SHStart

	public PieceWeight pieceWeight;

    public Start(ChunkRand rand, int x, int z) {
		super(0, rand, x, z);
	}

}
