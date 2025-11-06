package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class Corridor extends Stronghold.Piece { //Called SHStraight in bedrock

	private final boolean leftExitExists;
	private final boolean rightExitExists;

	public Corridor(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.leftExitExists = rand.nextInt(2) == 0;
		this.rightExitExists = rand.nextInt(2) == 0;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
		this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
		if(this.leftExitExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, 1, 2);
		}
		if(this.rightExitExists) {
			this.generateChildrenRight(gen, start, pieces, rand, 1, 2);
		}
	}

	public static Corridor createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, facing.getRotation());
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new Corridor(pieceId, rand, box, facing) : null;
	}

}
