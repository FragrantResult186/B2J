package fragrant.core.rand;

import fragrant.core.version.MCVersion;
import fragrant.core.version.UnsupportedVersion;

import java.util.Collection;

@SuppressWarnings("unused")
public class ChunkRand extends BedrockRandom {

	public ChunkRand() {
		super(0);
	}

    public ChunkRand(int seed) {
        super(seed);
    }

	public ChunkRand(long seed) {
		super((int) seed);
	}

    public ChunkRand(ChunkRand rand) {
        super(rand);
    }

    public int setTerrainSeed(int chunkX, int chunkZ, MCVersion version) {
        int a = chunkX * (int) 341873128712L;
        int b = chunkZ * (int) 132897987541L;
        int seed = a + b;
        this.setSeed(seed);
        return seed;
    }

	public int setPopulationSeed(long worldSeed, int x, int z, MCVersion version) {
		this.setSeed(worldSeed);
		int a = this.nextInt() | 1;
		int b = this.nextInt() | 1;
		int seed = (x * a + z * b) ^ (int) worldSeed;
		this.setSeed(seed);
		return seed;
	}

	public int setDecorationSeed(int populationSeed, int salt, MCVersion version) {
		if (version.isOlderThan(MCVersion.v1_13_0)) {
			throw new UnsupportedVersion(version, "decorator seed");
		}

        int seed = (populationSeed >>> 2) + (populationSeed << 6) + salt - 1640531527 ^ populationSeed;
		this.setSeed(seed);
		return seed;
	}

	public int setDecorationSeed(long worldSeed, int chunkX, int chunkZ, int salt, MCVersion version) {
		int populationSeed = this.setPopulationSeed(worldSeed, chunkX, chunkZ, version);
		return this.setDecorationSeed(populationSeed, salt, version);
	}

	public int setCarverSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		this.setSeed(worldSeed);
		int a = this.nextInt();
        int b = this.nextInt();
        int seed = (chunkX * a) ^ (chunkZ * b) ^ (int) worldSeed;
		this.setSeed(seed);
		return seed;
	}

	public int setRegionSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
        int a = regionX * (int) 341873128712L;
        int b = regionZ * (int) 132897987541L;
        int seed = a + b + (int) worldSeed + salt;
		this.setSeed(seed);
		return seed;
	}

    public int setStrongholdSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
        int a = regionX * (int) 784295783249L;
        int b = regionZ * (int) 827828252345L;
        int seed = a + b + (int) worldSeed + salt;
        this.setSeed(seed);
        return seed;
    }

    public int setFortressSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		int sX = chunkX >> 4;
		int sZ = chunkZ >> 4;
        int seed = (sX ^ sZ << 4) ^ (int) worldSeed;
		this.setSeed(seed);
		return seed;
	}

	public int seedSlimeChunk(int chunkX, int chunkZ) {
        int seed = (chunkX * 522133279) ^ chunkZ;
        this.setSeed(seed);
        return seed;
    }

	@SuppressWarnings("unchecked")
	public <T> T getRandom(Collection<T> list) {
		return (T) getRandom(list.toArray(), this);
	}

	public <T> T getRandom(T[] list) {
		return getRandom(list, this);
	}

	public static <T> T getRandom(T[] list, ChunkRand rand) {
		return list[rand.nextInt(list.length)];
	}

	public int getInt(int minimum, int maximum) {
		return getInt(this, minimum, maximum);
	}

	public static int getInt(ChunkRand rand, int minimum, int maximum) {
		return minimum >= maximum ? minimum : rand.nextInt(maximum - minimum + 1) + minimum;
	}
}
