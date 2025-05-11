package fragrant.b2j.random;

import java.util.Random;

/**
 * An RNG implementation equivalent to that of Minecraft: Bedrock Edition.
 *
 * This is a version of the m19937 mersenne twister algorithm.
 * This version contains the improved initialization from http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/MT2002/CODES/mt19937ar.c
 * It also contains an optimization to only generate some of the MT array when the RNG is first initialized.
 *
 * Modified to be a drop-in replacement for OldBedrockRandom.
 *
 * @author Earthcomputer
 * @author Fragrant (Migration)
 */
public class BedrockRandom extends Random {
    private static final int N = 624;
    private static final int M = 397;
    private static final int MATRIX_A = 0x9908b0df;
    private static final int UPPER_MASK = 0x80000000;
    private static final int LOWER_MASK = 0x7fffffff;
    private static final int[] MAG_01 = {0, MATRIX_A};
    private static final double TWO_POW_M32 = 1.0 / (1L << 32);

    private int seed; // (DWORD*) this + 0
    private int[] mt = new int[N]; // (DWORD*) this + 1
    private int mti; // (DWORD*) this + 625
    private int mtiFast; // (DWORD*) this + 628

    private boolean valid; // Hackfix for setSeed being called too early in the superconstructor


    public BedrockRandom() {
        this(new Random().nextInt());
    }

    public BedrockRandom(long worldSeed) {
        this((int) worldSeed);
    }

    public BedrockRandom(int worldSeed) {
        valid = true;
        _setSeed(worldSeed);
    }


    // ===== PUBLIC INTERFACE METHODS ===== //

    public int getSeed() {
        return seed;
    }

    /**
     * This overload exists to override the method in the base class.
     * Although it accepts a 64-bit long, it will be cast to a 32-bit int.
     */
    @Override
    public void setSeed(long worldSeed) {
        if (valid) // Hackfix for this being called too early in the superconstructor
            _setSeed((int) worldSeed);
    }

    /**
     * Generates a non-negative signed integer
     */
    @Override
    public int nextInt() {
        return _genRandInt32() >>> 1;
    }

    /**
     * Returns the next 31 bits from this mt number generator.
     */
    public int nextInt31() {
        return nextInt() & 0x7FFFFFFF;
    }

    /**
     * Returns a pseudo-mt, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive).
     */
    @Override
    public int nextInt(int bound) {
        if (bound > 0)
            return (int) (Integer.toUnsignedLong(_genRandInt32()) % bound);
        else
            return 0;
    }

    /**
     * Generates a mt integer k such that a <= k < b
     */
    public int nextInt(int min, int max) {
        if (min < max)
            return min + nextInt(max - min);
        else
            return min;
    }

    /**
     * Include Max val
     */
    public static int genRandIntRange(int min, int max, BedrockRandom random) {
        if (min >= max) {
            return min;
        }
        return random.nextInt(min, max + 1);
    }

    /**
     * Generates a mt integer k such that a <= k <= b
     */
    public int nextIntInclusive(int a, int b) {
        return nextInt(a, b + 1);
    }

    /**
     * Generates a mt long k such that 0 <= k < 2^32, mti.e. a mt unsigned int
     */
    public long nextUnsignedInt() {
        return Integer.toUnsignedLong(_genRandInt32());
    }

    /**
     * Generates a mt short k such that 0 <= k < 256, mti.e. a mt unsigned byte
     */
    public short nextUnsignedChar() {
        return (short) (_genRandInt32() & 0xff);
    }

    /**
     * Generates a mt boolean value.
     */
    @Override
    public boolean nextBoolean() {
        return (_genRandInt32() & 0x8000000) != 0;
    }

    /**
     * Generates a uniform mt float k such that 0 <= k < 1
     */
    @Override
    public float nextFloat() {
        return (float) _genRandReal2();
    }

    /**
     * Generates a uniform mt float k such that 0 <= k < bound
     */
    public float nextFloat(float bound) {
        return nextFloat() * bound;
    }

    /**
     * Generates a uniform mt float k such that a <= k < b
     */
    public float nextFloat(float a, float b) {
        return a + (nextFloat() * (b - a));
    }

    /**
     * Generates a uniform mt double k such that 0 <= k < 1.
     *
     * Note that unlike the Java RNG, there are only 2^32 possible return values for this function
     */
    @Override
    public double nextDouble() {
        return _genRandReal2();
    }

    /**
     * Returns the next mt value with the specified number of bits.
     */
    @Override
    protected int next(int bits) {
        return _genRandInt32() >>> (32 - bits);
    }

    /**
     * Extends int to long.
     */
    public static long toUnsignedLong(int value) {
        return value & 0xFFFFFFFFL;
    }

    public static double int2Float(int x) {
        return (x & 0xFFFFFFFFL) * 2.328306436538696e-10;
    }

    /**
     * Advances the RNG by the specified number of steps.
     */
    public void skip(long count) {
        for (long i = 0; i < count; i++) {
            nextInt();
        }
    }

    // ===== m19937 MERSENNE TWISTER IMPLEMENTATION ===== //

    private void _setSeed(int worldSeed) {
        this.seed = worldSeed;
        this.mti = N + 1; // uninitialized
        _initGenRandFast(worldSeed);
    }

    private void _initGenRand(int initialValue) {
        this.mt[0] = initialValue;
        for (this.mti = 1; this.mti < N; this.mti++) {
            this.mt[mti] = 1812433253
                    * ((this.mt[this.mti - 1] >>> 30) ^ this.mt[this.mti - 1])
                    + this.mti;
        }
        this.mtiFast = N;
    }

    private void _initGenRandFast(int initialValue) {
        this.mt[0] = initialValue;
        for (this.mtiFast = 1; this.mtiFast <= M; this.mtiFast++) {
            this.mt[this.mtiFast] = 1812433253
                    * ((this.mt[this.mtiFast - 1] >>> 30) ^ this.mt[this.mtiFast - 1])
                    + this.mtiFast;
        }
        this.mti = N;
    }

    public int _genRandInt32() {
        if (this.mti == N) {
            this.mti = 0;
        } else if (this.mti > N) {
            _initGenRand(5489);
            this.mti = 0;
        }

        if (this.mti >= N - M) {
            if (this.mti >= N - 1) {
                this.mt[N - 1] = MAG_01[this.mt[0] & 1]
                        ^ ((this.mt[0] & LOWER_MASK | this.mt[N - 1] & UPPER_MASK) >>> 1)
                        ^ this.mt[M - 1];
            } else {
                this.mt[this.mti] = MAG_01[this.mt[this.mti + 1] & 1]
                        ^ ((this.mt[this.mti + 1] & LOWER_MASK | this.mt[this.mti] & UPPER_MASK) >>> 1)
                        ^ this.mt[this.mti - (N - M)];
            }
        } else {
            this.mt[this.mti] = MAG_01[this.mt[this.mti + 1] & 1]
                    ^ ((this.mt[this.mti + 1] & LOWER_MASK | this.mt[this.mti] & UPPER_MASK) >>> 1)
                    ^ this.mt[this.mti + M];

            if (this.mtiFast < N) {
                this.mt[this.mtiFast] = 1812433253
                        * ((this.mt[this.mtiFast - 1] >>> 30) ^ this.mt[this.mtiFast - 1])
                        + this.mtiFast;
                this.mtiFast++;
            }
        }

        int ret = this.mt[this.mti++];
        ret = ((ret ^ (ret >>> 11)) << 7) & 0x9d2c5680 ^ ret ^ (ret >>> 11);
        ret = (ret << 15) & 0xefc60000 ^ ret ^ (((ret << 15) & 0xefc60000 ^ ret) >>> 18);
        return ret;
    }

    private double _genRandReal2() {
        return Integer.toUnsignedLong(_genRandInt32()) * TWO_POW_M32;
    }

}