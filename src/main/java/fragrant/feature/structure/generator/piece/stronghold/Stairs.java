package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class Stairs extends Stronghold.Piece { //SHStraightStairsDown

	public Stairs(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
		this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
	}

	public static Stairs createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 8, facing.getRotation());
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new Stairs(pieceId, rand, box, facing) : null;
	}

}
