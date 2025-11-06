package fragrant.feature.structure.generator.piece.endcity;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.EndCity;

public class ThirdFloor2 extends EndCity.Piece {
    public ThirdFloor2(ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super();
        this.size = new BPos(13, 7, 13);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    public void setChest() {
        this.chest.add((this.applyVecTransform(new Vec3i(6,1,2))).toBPos());
    }
}
