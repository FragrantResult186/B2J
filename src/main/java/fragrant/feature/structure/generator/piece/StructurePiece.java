package fragrant.feature.structure.generator.piece;

import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StructurePiece<T extends StructurePiece<T>> {

    public int pieceId;
    public List<T> children = new ArrayList<>();

    public T previousPiece = null;
    public T nextPiece = null;

    public BlockBox boundingBox;
    protected BlockDirection facing;

    protected int applyXTransform(int x, int z) {
        if (this.facing == null) {
            return x;
        } else {
            switch (this.facing) {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX + x;
                case WEST:
                    return this.boundingBox.maxX - z;
                case EAST:
                    return this.boundingBox.minX + z;
                default:
                    return x;
            }
        }
    }

    public int applyYTransform(int y) {
        return this.getFacing() == null ? y : y + this.boundingBox.minY;
    }

    protected int applyZTransform(int x, int z) {
        if (this.facing == null) {
            return z;
        } else {
            switch (this.facing) {
                case NORTH:
                    return this.boundingBox.maxZ - z;
                case SOUTH:
                    return this.boundingBox.minZ + z;
                case WEST:
                case EAST:
                    return this.boundingBox.minZ + x;
                default:
                    return z;
            }
        }
    }

    public Vec3i applyVecTransform(Vec3i vector) {
        int x = vector.getX();
        int y = vector.getY();
        int z = vector.getZ();
        return new Vec3i(applyXTransform(x, z), applyYTransform(y), applyZTransform(x, z));
    }

    public StructurePiece(int pieceId) {
        this.pieceId = pieceId;
    }

    public BlockDirection getFacing() {
        return this.facing;
    }

    public BlockBox getBoundingBox() {
        return this.boundingBox;
    }

    public void setOrientation(BlockDirection facing) {
        this.facing = facing;
    }

    public void setChest() {

    }

    public void setNextPiece(T next) {
        this.nextPiece = next;
        if (next != null) {
            next.previousPiece = (T) this;
        }
    }

    protected List<Vec3i> getChestOffsets() {
        return Collections.emptyList();
    }

    public List<Vec3i> getChestPositions() {
        if (this.boundingBox == null) return Collections.emptyList();
        List<Vec3i> offsets = this.getChestOffsets();
        if (offsets.isEmpty()) return Collections.emptyList();

        List<Vec3i> positions = new ArrayList<>(offsets.size());
        for (Vec3i offset : offsets) {
            positions.add(this.applyVecTransform(offset));
        }
        return positions;
    }

    protected BlockDirection getOrientation() {
        return this.facing;
    }

    protected int getGenDepth() {
        return this.pieceId;
    }
}