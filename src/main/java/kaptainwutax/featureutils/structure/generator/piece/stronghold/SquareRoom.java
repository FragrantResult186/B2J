package kaptainwutax.featureutils.structure.generator.piece.stronghold;

import kaptainwutax.featureutils.structure.Stronghold;
import kaptainwutax.featureutils.structure.generator.StrongholdGenerator;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.BlockBox;
import kaptainwutax.seedutils.util.Direction;

import java.util.List;

public class SquareRoom extends Stronghold.Piece { //Called SHRoomCrossing in mojmap

	private boolean isStoreRoom;

	public SquareRoom(int pieceId, JRand rand, BlockBox boundingBox, Direction facing) {
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.isStoreRoom = rand.nextInt(5) == 2;
	}

	public boolean isStoreRoom() {
		return isStoreRoom;
	}

	@Override
	public void populatePieces(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, JRand rand) {
		this.connectStraight(gen, start, pieces, rand, 4, 1);
		this.connectLeft(gen, start, pieces, rand, 1, 4);
		this.connectRight(gen, start, pieces, rand, 1, 4);
	}

	public static SquareRoom createPiece(List<Stronghold.Piece> pieces, JRand rand, int x, int y, int z, Direction facing, int pieceId) {
		BlockBox blockBox_1 = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 7, 11, facing);
		return Stronghold.Piece.isHighEnough(blockBox_1) && Stronghold.Piece.getNextIntersectingPiece(pieces, blockBox_1) == null ? new SquareRoom(pieceId, rand, blockBox_1, facing) : null;
	}

}