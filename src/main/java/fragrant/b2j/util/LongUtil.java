package fragrant.b2j.util;

import java.math.BigInteger;

public class LongUtil {
    private final int low;
    private final long high;
    private final boolean unsigned;
    private static final LongUtil ZERO = new LongUtil(0, 0, false);
    private static final LongUtil NEGATIVE_ONE = new LongUtil(-1, -1, false);
    private static final LongUtil MAX_VALUE = new LongUtil(-1, 0x7FFFFFFF, false);

    public LongUtil(int low, long high, boolean unsigned) {
        this.low = low;
        this.high = high;
        this.unsigned = unsigned;
    }

    public static LongUtil fromNumber(long value) {
        return new LongUtil((int) (value & 0xFFFFFFFFL), (value >> 32) & 0xFFFFFFFFL, false);
    }

    public long getUpperBits() {
        return this.high;
    }

    public int getLowBits() {
        return this.low;
    }

    public boolean isZero() {
        return low == 0 && high == 0;
    }

    public boolean isNegative() {
        return !unsigned && high < 0;
    }

    public boolean isOdd() {
        return (low & 1) == 1;
    }

    public boolean eq(LongUtil other) {
        return low == other.low && high == other.high;
    }

    public boolean lt(LongUtil other) {
        return compare(other) < 0;
    }

    public int compare(LongUtil other) {
        if (unsigned) {
            long thisHigh = high & 0xFFFFFFFFL;
            long otherHigh = other.high & 0xFFFFFFFFL;
            if (thisHigh != otherHigh) {
                return thisHigh < otherHigh ? -1 : 1;
            }
            long thisLow = low & 0xFFFFFFFFL;
            long otherLow = other.low & 0xFFFFFFFFL;
            if (thisLow != otherLow) {
                return thisLow < otherLow ? -1 : 1;
            }
        } else {
            if (high != other.high) {
                return high < other.high ? -1 : 1;
            }
            if (low != other.low) {
                return (low & 0xFFFFFFFFL) < (other.low & 0xFFFFFFFFL) ? -1 : 1;
            }
        }
        return 0;
    }

    public long toNumber() {
        return (high << 32) | (low & 0xFFFFFFFFL);
    }

    public LongUtil neg() {
        return isZero() ? this : new LongUtil(~low + 1, ~high + (low == 0 ? 1 : 0), unsigned);
    }

    public LongUtil xor(LongUtil other) {
        return new LongUtil(low ^ other.low, high ^ other.high, unsigned);
    }

    public LongUtil multiply(LongUtil a) {
        if (this.isZero() || a.isZero()) return ZERO;
        if (this.eq(NEGATIVE_ONE)) return a.isOdd() ? NEGATIVE_ONE : ZERO;
        if (a.eq(NEGATIVE_ONE)) return this.isOdd() ? NEGATIVE_ONE : ZERO;

        if (this.isNegative()) {
            return a.isNegative()
                    ? this.neg().multiply(a.neg())
                    : this.neg().multiply(a).neg();
        }
        if (a.isNegative()) return this.multiply(a.neg()).neg();

        if (this.lt(MAX_VALUE) && a.lt(MAX_VALUE)) {
            return fromNumber(this.toNumber() * a.toNumber());
        }

        int b = (int)(this.high >>> 16);
        int d = (int)(this.high & 0xFFFF);
        int g = this.low >>> 16;
        int j = this.low & 0xFFFF;

        int k = (int)(a.high >>> 16);
        int l = (int)(a.high & 0xFFFF);
        int m = a.low >>> 16;
        int n = a.low & 0xFFFF;

        int o, r, s, t;

        t = j * n;
        s = (t >>> 16);
        s += g * n;
        r = (s >>> 16);
        s &= 0xFFFF;
        s += j * m;
        r += (s >>> 16);
        r += d * n;
        o = (r >>> 16);
        r &= 0xFFFF;
        r += g * m;
        o += (r >>> 16);
        r &= 0xFFFF;
        r += j * l;
        o += (r >>> 16);
        o += b * n + d * m + g * l + j * k;

        long lowResult = ((long) (s & 0xFFFF) << 16) | (t & 0xFFFF);
        long highResult = ((long) (o & 0xFFFF) << 16) | (r & 0xFFFF);

        return new LongUtil((int) lowResult, highResult, this.unsigned);
    }

    @Override
    public String toString() {
        if (this.unsigned) {
            if (this.high < 0) {
                BigInteger bigInt = BigInteger.valueOf(this.high & 0x7FFFFFFFL);
                bigInt = bigInt.add(BigInteger.valueOf(0x80000000L));
                bigInt = bigInt.shiftLeft(32);
                bigInt = bigInt.add(BigInteger.valueOf(this.low & 0xFFFFFFFFL));
                return bigInt.toString();
            }
        }
        return String.valueOf((this.high << 32) | (this.low & 0xFFFFFFFFL));
    }

}