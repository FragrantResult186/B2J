package fragrant.b2j.util.position;

import fragrant.b2j.util.Formatter;

import java.util.HashMap;
import java.util.Map;

public class FeaturePos {
    private final int x;
    private final int z;
    private Integer y;
    private String type;
    private Float size;
    private Boolean isGiant;
    private Integer featureType;
    private final Map<String, Object> metadata = new HashMap<>();

    public FeaturePos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public FeaturePos(int x, int z, String type) {
        this.x = x;
        this.z = z;
        this.type = type;
    }

    public FeaturePos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public FeaturePos(int x, int y, int z, float size, boolean isGiant) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.isGiant = isGiant;
        this.type = "RAVINE";
    }

    public void setMeta(String key, Object value) {
        metadata.put(key, value);
    }

    public <T> T getMeta(String key, Class<T> type) {
        return type.cast(metadata.get(key));
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getSize() {
        return size;
    }

    public Boolean isGiant() {
        return isGiant;
    }

    public Integer getFeatureType() {
        return featureType;
    }

    public void setFeatureType(int featureType) {
        this.featureType = featureType;
    }

    public String format() {
        if (featureType != null) {
            return Formatter.formatType(this, featureType);
        }
        return defaultFormat();
    }

    public String defaultFormat() {
        return String.format("[X=%d, Z=%d]", x, z);
    }

    @Override
    public String toString() {
        return format();
    }

}