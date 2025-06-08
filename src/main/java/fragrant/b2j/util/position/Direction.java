package fragrant.b2j.util.position;

public class Direction {

    public static Offset getRotationPos(int rotation, int sizeX, int sizeZ) {
        int offsetX, offsetZ;

        switch (rotation) {
            case 0: offsetX =  sizeX; offsetZ =  sizeZ; break;
            case 1: offsetX = -sizeZ; offsetZ =  sizeX; break;
            case 2: offsetX = -sizeX; offsetZ = -sizeZ; break;
            case 3: offsetX =  sizeZ; offsetZ = -sizeX; break;
            default: throw new IllegalArgumentException("Rotation must be between 0 and 3, got: " + rotation);
        }

        return new Offset(offsetX, offsetZ);
    }

    public record Offset(int x, int z) {}

    public static String getFacing(int rotation) {
        return switch (rotation) {
            case 0 -> "west";
            case 1 -> "north";
            case 2 -> "east";
            case 3 -> "south";
            default -> throw new IllegalArgumentException("Rotation must be between 0 and 3, got: " + rotation);
        };
    }

}