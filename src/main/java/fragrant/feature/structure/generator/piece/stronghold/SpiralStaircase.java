package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class SpiralStaircase extends Stronghold.Piece { //SHStairsDown

	private final boolean isStructureStart;

	public SpiralStaircase(int pieceType, ChunkRand rand, int x, int z) {
		super(pieceType);
		this.isStructureStart = true;
		BlockDirection dir = BlockDirection.randomHorizontal(rand);
		this.setOrientation(dir);
        this.boundingBox = new BlockBox(x, 64, z, x + 5 - 1, 64 + 11 - 1, z + 5 - 1);
	}

	public SpiralStaircase(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
		super(pieceId);
		this.isStructureStart = false;
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
		if (this.isStructureStart) {
            gen.currentPiece = FiveWayCrossing.class;
        }
		this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
	}

	public static SpiralStaircase createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 5, facing.getRotation());
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new SpiralStaircase(pieceId, rand, box, facing) : null;
	}

}
