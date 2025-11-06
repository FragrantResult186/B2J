package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class LeftTurn extends Stronghold.Piece {

	public LeftTurn(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) { //SHLeftTurn
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
		BlockDirection facing = this.getFacing();

		if(facing != BlockDirection.NORTH && facing != BlockDirection.EAST) {
			this.generateChildrenRight(gen, start, pieces, rand, 1, 1);
		} else {
			this.generateChildrenLeft(gen, start, pieces, rand, 1, 1);
		}
	}

	public static LeftTurn createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, facing.getRotation());
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new LeftTurn(pieceId, rand, box, facing) : null;
	}

}
