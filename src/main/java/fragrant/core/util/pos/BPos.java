package fragrant.core.util.pos;

import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.block.BlockMirror;
import fragrant.core.util.block.BlockRotation;
import fragrant.core.util.math.Vec3i;

@SuppressWarnings("unused")
public class BPos {

    public static final BPos ORIGIN = new BPos(0, 0, 0);

    private final double x, y, z;

    public BPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BPos(int x, int z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BPos(float x, float z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BPos(double x, double z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BPos(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public int getX() {
        return (int) Math.floor(this.x);
    }

    public int getY() {
        return (int) Math.floor(this.y);
    }

    public int getZ() {
        return (int) Math.floor(this.z);
    }

    public double getXDouble() {
        return this.x;
    }

    public double getYDouble() {
        return this.y;
    }

    public double getZDouble() {
        return this.z;
    }

    public BPos add(BPos pos) {
        return this.add(pos.getXDouble(), pos.getYDouble(), pos.getZDouble());
    }

    public BPos subtract(BPos pos) {
        return this.subtract(pos.getXDouble(), pos.getYDouble(), pos.getZDouble());
    }

    public BPos add(int x, int y, int z) {
        return new BPos(this.x + x, this.y + y, this.z + z);
    }

    public BPos subtract(int x, int y, int z) {
        return new BPos(this.x - x, this.y - y, this.z - z);
    }

    public BPos add(double x, double y, double z) {
        return new BPos(this.x + x, this.y + y, this.z + z);
    }

    public BPos subtract(double x, double y, double z) {
        return new BPos(this.x - x, this.y - y, this.z - z);
    }

    public BPos multiply(double scalar) {
        return new BPos(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public BPos multiply(double x, double y, double z) {
        return new BPos(this.x * x, this.y * y, this.z * z);
    }

    public BPos divide(double scalar) {
        return new BPos(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public BPos shl(int amount) {
        return this.shl(amount, amount, amount);
    }

    public BPos shr(int amount) {
        return this.shr(amount, amount, amount);
    }

    public BPos shl(int bx, int by, int bz) {
        return new BPos(this.getX() << bx, this.getY() << by, this.getZ() << bz);
    }

    public BPos shr(int bx, int by, int bz) {
        return new BPos(this.getX() >> bx, this.getY() >> by, this.getZ() >> bz);
    }

    public double distanceTo(BPos other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSqTo(BPos other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double manhattanDistanceTo(BPos other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z);
    }

    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double getMagnitudeSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public BPos normalize() {
        double mag = this.getMagnitude();
        return mag == 0 ? ORIGIN : this.divide(mag);
    }

    public double dot(BPos other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public BPos cross(BPos other) {
        return new BPos(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public BPos floor() {
        return new BPos(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public BPos ceil() {
        return new BPos(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
    }

    public BPos round() {
        return new BPos(Math.round(this.x), Math.round(this.y), Math.round(this.z));
    }

    public BPos toChunkCorner() {
        return new BPos(this.getX() & -16, this.getY(), this.getZ() & -16);
    }

    public CPos toChunkPos() {
        return new CPos(this.getX() >> 4, this.getZ() >> 4);
    }

    public BPos toNetherPos() {
        return new BPos(this.getX() / 8, this.getZ() / 8);
    }

    public BPos relative(BlockDirection direction) {
        return new BPos(
                this.x + direction.getVector().getX(),
                this.y + direction.getVector().getY(),
                this.z + direction.getVector().getZ()
        );
    }

    public BPos relative(BlockDirection direction, int offset) {
        return offset == 0 ? this : new BPos(
                this.x + direction.getVector().getX() * offset,
                this.y + direction.getVector().getY() * offset,
                this.z + direction.getVector().getZ() * offset
        );
    }

    public BPos relative(BlockDirection.Axis axis, int offset) {
        if (offset == 0) {
            return this;
        } else {
            int i = axis == BlockDirection.Axis.X ? offset : 0;
            int j = axis == BlockDirection.Axis.Y ? offset : 0;
            int k = axis == BlockDirection.Axis.Z ? offset : 0;
            return new BPos(this.x + i, this.y + j, this.z + k);
        }
    }

    public RPos toRegionPos(int regionSize) {
        int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
        int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
        return new RPos(x / regionSize, z / regionSize, regionSize);
    }

    public BPos transform(BlockMirror mirror, BlockRotation rotation, BPos pivot) {
        return rotation.rotate(mirror.mirror(this), pivot);
    }

    public Vec3i toVec3i() {
        return new Vec3i(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPos)) return false;
        BPos pos = (BPos) o;
        return Double.compare(pos.x, this.x) == 0 &&
                Double.compare(pos.y, this.y) == 0 &&
                Double.compare(pos.z, this.z) == 0;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(this.x);
        long yBits = Double.doubleToLongBits(this.y);
        long zBits = Double.doubleToLongBits(this.z);
        return (int) (xBits ^ (xBits >>> 32) ^ yBits ^ (yBits >>> 32) ^ zBits ^ (zBits >>> 32));
    }

    public String toTPCommand() {
        return "/tp" + " " + (int) this.x + " " + (int) this.y + " " + (int) this.z;
    }

    public String toTPCommandDouble() {
        return "/tp" + " " + this.x + " " + this.y + " " + this.z;
    }

    @Override
    public String toString() {
        return "BPos{" + "x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}