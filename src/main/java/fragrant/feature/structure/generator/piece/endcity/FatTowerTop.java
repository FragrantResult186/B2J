package fragrant.feature.structure.generator.piece.endcity;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.EndCity;

public class FatTowerTop extends EndCity.Piece {
    public FatTowerTop(ChunkRand rand, BlockBox boundingBox, BlockDirection facing) {
        super();
        this.size = new BPos(16, 5, 16);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    public void setChest() {
        this.chest.add(this.applyVecTransform(new Vec3i(5,1,3)).toBPos());
        this.chest.add(this.applyVecTransform(new Vec3i(3,1,5)).toBPos());
    }
}
