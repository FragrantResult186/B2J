package fragrant.feature.structure.generator.piece.endcity;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockRotation;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.EndCity;

public class BaseFloor extends EndCity.Piece {

    public BaseFloor(int pieceType, ChunkRand rand, int x, int z) {
        super(pieceType);
        this.pos = new BPos(x, 60, z);
        this.rotation = BlockRotation.getRandom(rand);
        this.boundingBox = BlockBox.rotated(x, 60, z, 0, 0, 0, this.getSize().getX(), this.getSize().getY(), this.getSize().getZ(), this.rotation.getOpposite());
    }

    public BaseFloor() {
        super();
        this.size = new BPos(9, 3, 9);
    }

}
