package fragrant.biome.biome;

import java.util.Objects;

public class BiomePoint {

	public final Biome biome;
	public final float temperature;
	public final float humidity;
	public final float altitude;
	public final float weirdness;
	public final float weight;

	public BiomePoint(Biome biome, float temperature, float humidity, float altitude, float weirdness, float weight) {
		this.biome = biome;
		this.temperature = temperature;
		this.humidity = humidity;
		this.altitude = altitude;
		this.weirdness = weirdness;
		this.weight = weight;
	}

	public Biome getBiome() {
		return this.biome;
	}

    public float distanceTo(BiomePoint other) {
        float dx = this.temperature - other.temperature;
        float dy = this.humidity - other.humidity;
        return (dx * dx + dy * dy) + this.weight;
    }

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof BiomePoint other)) return false;
        return this.biome == other.biome && this.temperature == other.temperature && this.humidity == other.humidity
			&& this.altitude == other.altitude && this.weirdness == other.weirdness && this.weight == other.weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.biome, this.temperature, this.humidity, this.altitude, this.weirdness, this.weight);
	}

	@Override
	public String toString() {
		return "BiomePoint{" +
			"biome=" + this.biome.getName() +
			", temperature=" + this.temperature +
			", humidity=" + this.humidity +
			", altitude=" + this.altitude +
			", weirdness=" + this.weirdness +
			", weight=" + this.weight +
			'}';
	}

}
