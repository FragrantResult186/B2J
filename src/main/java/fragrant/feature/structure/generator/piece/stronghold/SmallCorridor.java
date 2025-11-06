package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import java.util.List;
import fragrant.core.rand.ChunkRand;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;

public class SmallCorridor extends Stronghold.Piece {

    public SmallCorridor(int pieceId, ChunkRand rand, BlockBox boundingBox, BlockDirection facing) { //SHFillerCorridor
		super(pieceId);
		this.setOrientation(facing);
		this.boundingBox = boundingBox;
	}

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {

    }

	public static BlockBox createBox(List<Stronghold.Piece> pieces, ChunkRand rand, int x, int y, int z, BlockDirection facing) {
		BlockBox box = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 4, facing.getRotation());
		Stronghold.Piece piece = Stronghold.Piece.getNextIntersectingPiece(pieces, box);

		if(piece != null && piece.getBoundingBox().minY == box.minY) {
			for(int int_5 = 3; int_5 >= 1; --int_5) {
				box = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, int_5 - 1, facing.getRotation());
				if(!piece.getBoundingBox().intersects(box)) {
					return BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, int_5, facing.getRotation());
				}
			}
		}

		return null;
	}
}
