package fragrant.noise.perlin;

import fragrant.noise.noise.NoiseSampler;
import fragrant.noise.utils.MathHelper;
import fragrant.core.util.data.Pair;
import fragrant.core.rand.BedrockRandom;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OctavePerlinNoiseSampler implements NoiseSampler {
    private final PerlinNoiseSampler[] octaveSamplers;
    private final double[] amplitudes;
    private final double[] lacunarities;

    public OctavePerlinNoiseSampler(BedrockRandom rand, IntStream octaves) {
        this(rand, octaves.boxed().collect(Collectors.toList()));
    }

    public OctavePerlinNoiseSampler(BedrockRandom rand, List<Integer> octaves) {
        if(octaves.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }

        octaves = octaves.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        int minOctave = octaves.getFirst();
        int maxOctave = octaves.getLast();
        int length = maxOctave - minOctave + 1;

        this.octaveSamplers = new PerlinNoiseSampler[length];
        this.amplitudes = new double[length];
        this.lacunarities = new double[length];

        double persistence = 1.0 / ((1L << length) - 1.0);
        double lacunarity = Math.pow(2.0, minOctave + length - 1);

        for(int i = 0; i < length; i++) {
            int octaveIndex = minOctave + i;
            if(octaves.contains(octaveIndex)) {
                this.octaveSamplers[i] = new PerlinNoiseSampler(rand);
                this.amplitudes[i] = persistence;
                this.lacunarities[i] = lacunarity;
            } else {
                rand.skip(262);
                this.amplitudes[i] = 0.0;
                this.lacunarities[i] = lacunarity;
            }
            persistence *= 2.0;
            lacunarity *= 0.5;
        }
    }

    public OctavePerlinNoiseSampler(BedrockRandom rand, Pair<Integer, List<Double>> octaveParams) {
        List<Double> amplitudeList = octaveParams.getSecond();
        int length = amplitudeList.size();
        int start = octaveParams.getFirst();

        this.octaveSamplers = new PerlinNoiseSampler[length];
        this.amplitudes = new double[length];
        this.lacunarities = new double[length];

        PerlinNoiseSampler perlin = new PerlinNoiseSampler(rand);

        if(start >= 0 && start < length) {
            double d0 = amplitudeList.get(start);
            if(d0 != 0.0D) {
                this.octaveSamplers[start] = perlin;
            }
        }

        for(int idx = start - 1; idx >= 0; --idx) {
            if(idx < length) {
                double d1 = amplitudeList.get(idx);
                if(d1 != 0.0D) {
                    this.octaveSamplers[idx] = new PerlinNoiseSampler(rand);
                } else {
                    rand.skip(262);
                }
            } else {
                rand.skip(262);
            }
        }

        if(start < length - 1) {
            long noiseSeed = (long)(perlin.sample(0.0D, 0.0D, 0.0D, 0.0D, 0.0D) * (double)9.223372E18F);
            rand.setSeed(noiseSeed);

            for(int l = start + 1; l < length; ++l) {
                if(l >= 0) {
                    double d2 = amplitudeList.get(l);
                    if(d2 != 0.0D) {
                        this.octaveSamplers[l] = new PerlinNoiseSampler(rand);
                    } else {
                        rand.skip(262);
                    }
                } else {
                    rand.skip(262);
                }
            }
        }

        double persistence = Math.pow(2.0D, -start);
        double lacunarity = Math.pow(2.0D, start);

        for(int i = 0; i < length; i++) {
            this.amplitudes[i] = amplitudeList.get(i) * persistence;
            this.lacunarities[i] = lacunarity;
            persistence /= 2.0D;
            lacunarity *= 2.0D;
        }
    }

    public double sample(double x, double y, double z) {
        return this.sample(x, y, z, 0.0D, 0.0D, false);
    }

    public double sample(double x, double y, double z, double yAmplification, double minY, boolean useDefaultY) {
        double noise = 0.0D;

        for(int idx = 0; idx < this.octaveSamplers.length; idx++) {
            PerlinNoiseSampler sampler = this.octaveSamplers[idx];
            if(sampler != null && this.amplitudes[idx] != 0.0) {
                double lac = this.lacunarities[idx];
                double sample = sampler.sample(
                        MathHelper.maintainPrecision(x * lac),
                        useDefaultY ? -sampler.originY : MathHelper.maintainPrecision(y * lac),
                        MathHelper.maintainPrecision(z * lac),
                        yAmplification * lac,
                        minY * lac
                );
                noise += this.amplitudes[idx] * sample;
            }
        }

        return noise;
    }

    public int getCount() {
        return this.octaveSamplers.length;
    }

    public PerlinNoiseSampler getOctave(int octave) {
        return this.octaveSamplers[octave];
    }

    @Override
    public double sample(double x, double y, double yAmplification, double minY) {
        return this.sample(x, y, 0.0D, yAmplification, minY, false);
    }
}