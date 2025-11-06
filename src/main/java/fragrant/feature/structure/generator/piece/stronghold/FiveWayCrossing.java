package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;

public class FiveWayCrossing extends Stronghold.Piece {

	private final boolean lowerLeftExists;
	private final boolean upperLeftExists;
	private final boolean lowerRightExists;
	private final boolean upperRightExists;

	public FiveWayCrossing(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) { //Called SHFiveWayCrossing in bedrock
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.lowerLeftExists = rand.nextBoolean();
		this.upperLeftExists = rand.nextBoolean();
		this.lowerRightExists = rand.nextBoolean();
		this.upperRightExists = rand.nextInt(3) > 0;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
		int a = 3;
		int b = 5;
		BlockDirection facing = this.getFacing();
		if (facing == BlockDirection.WEST || facing == BlockDirection.NORTH) {
			a = 8 - a;
			b = 8 - b;
		}
		this.generateChildrenForward(gen, start, pieces, rand, 5, 1);
		if(this.lowerLeftExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, a, 1);
		}
		if(this.upperLeftExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, b, 7);
		}
		if(this.lowerRightExists) {
			this.generateChildrenRight(gen, start, pieces, rand, a, 1);
		}
		if(this.upperRightExists) {
			this.generateChildrenRight(gen, start, pieces, rand, b, 7);
		}
	}

	public static FiveWayCrossing createPiece(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, facing.getRotation());
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new FiveWayCrossing(pieceId, rand, box, facing) : null;
	}

}
