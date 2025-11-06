package fragrant.feature.structure.generator.piece.endcity;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.EndCity;

public class Ship extends EndCity.Piece {
    public Ship(ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super();
        this.size = new BPos(12, 23, 28);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    public void setChest() {
        this.chest.add(this.applyVecTransform(new Vec3i(5,4,7)).toBPos());
        this.chest.add(this.applyVecTransform(new Vec3i(7,4,7)).toBPos());
    }
}