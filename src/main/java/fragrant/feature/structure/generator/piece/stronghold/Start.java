package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.rand.ChunkRand;
import fragrant.feature.structure.generator.piece.PieceWeight;

public class Start extends SpiralStaircase { //Can only presume SHStart

	public PieceWeight pieceWeight;
	public PortalRoom portalRoom;

	public Start(ChunkRand rand, int x, int z) {
		super(0, rand, x, z);
	}

}
