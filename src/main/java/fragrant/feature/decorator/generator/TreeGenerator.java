package fragrant.feature.decorator.generator;


import fragrant.core.rand.BedrockRandom;
import fragrant.core.rand.ChunkRand;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.generator.tree.*;
import fragrant.feature.decorator.Tree;
import fragrant.feature.decorator.generator.tree.TreeBiome;
import fragrant.feature.decorator.generator.tree.TreeFeature;

import java.util.ArrayList;
import java.util.List;

public class TreeGenerator {

    private final MCVersion version;
    private final TreeBiome biome;
    private final Tree decorator;

    public TreeGenerator(MCVersion version, TreeBiome biome) {
        this.version = version;
        this.biome = biome;
        this.decorator = new Tree(version);
    }

    public List<Tree.Data> generate(long worldSeed, CPos cPos) {
        return this.generate(worldSeed, cPos.getX(), cPos.getZ());
    }

    public List<Tree.Data> generate(long worldSeed, int chunkX, int chunkZ) {
        List<Tree.Data> trees = new ArrayList<>();
        ChunkRand random = new ChunkRand();

        setTreeSeed(worldSeed, chunkX, chunkZ, random);

        int treeCount = setupTreeCount(random);
        for (int i = 0; i < treeCount; i++) {
            Tree.Data tree = generateTree(random, chunkX, chunkZ);
            // generation failed
            if (tree != null) {
                trees.add(tree);
            }
        }

        return trees;
    }

    private Tree.Data generateTree(BedrockRandom random, int chunkX, int chunkZ) {
        TreeFeature treeFeature = getTreeFeature(random);

        int z = random.nextInt(1, 15);
        int x = random.nextInt(1, 15);

        BPos treePos = new BPos(x + chunkX * 16, 0, z + chunkZ * 16);

        Tree.Data tree = decorator.at(chunkX * 16, chunkZ * 16, treePos, treeFeature);
        generateTrees(random, treeFeature, tree);

        // TallGrassFeature
        if (version.isOlderThan(MCVersion.v1_21_90)) {
            int tries = 23, spread = 3;
            for (int i = 0; i < tries; i++) {
                int offsetZ = random.nextInt(spread) - random.nextInt(spread);
                int offsetY = random.nextInt(4) - random.nextInt(4);
                int offsetX = random.nextInt(spread) - random.nextInt(spread);
                // ...
            }
        }

        return tree;
    }

    private void generateTrees(BedrockRandom random, TreeFeature treeFeature, Tree.Data tree) {
        switch (treeFeature) {
            case OAK: new OakTree(version, biome).generate(tree, random); break;
            case FANCY_OAK: new FancyOakTree(version, biome).generate(tree, random); break;
            case SPRUCE: new SpruceTree(version, biome).generate(tree, random); break;
            case MEGA_SPRUCE: new MegaSpruceTree(version, biome).generate(tree, random); break;
            case PINE: new PineTree(version, biome).generate(tree, random); break;
            case ACACIA: new AcaciaTree(version, biome).generate(tree, random); break;
            case BIRCH: new BirchTree(version, biome).generate(tree, random); break;
            case SUPER_BIRCH: new SuperBirchTree(version, biome).generate(tree, random); break;
            case ROOF: new RoofTree(version, biome).generate(tree, random); break;
            case JUNGLE: new JungleTree(version, biome).generate(tree, random); break;
            case JUNGLE_BUSH: new JungleBushTree(version, biome).generate(tree, random); break;
            case MEGA_JUNGLE: new MegaJungleTree(version, biome).generate(tree, random); break;
        }
    }

    public float getBiomeTreeCount(TreeBiome biome) {
        switch (biome) {
            case FOREST: return 10.0f;
            case FLOWER_FOREST: return 10.0f;
            case TAIGA: return 10.0f;
            case MEGA_TAIGA: return 10.0f;
            case SAVANNA: return 1.0f;
            case BIRCH_FOREST: return 10.0f;
            case BIRCH_FOREST_MUTATED: return 10.0f;
            //case DARK_FOREST: return 10.0f;
            case PLAINS: return 0.05f;
            case SUNFLOWER_PLAINS: return 0.05f;
            case ICE_PLAINS: return 0.05f;
            case JUNGLE: return 10.0f;
            case MESA_PLATEAU_STONE: return 1.0f;
            case EXTREME_HILLS_PLUS_TREES: return 1.0f;
            case GROVE: return 10.0f;
            default:
                throw new IllegalArgumentException("Invalid biome: " + biome);
        }
    }

    public void setTreeSeed(long seed, int chunkX, int chunkZ, ChunkRand random) {
        int populationSeed = random.setPopulationSeed(seed, chunkX, chunkZ, version);
        if (version.isOlderThan(MCVersion.v1_13_0)) {
            random.setSeed(populationSeed);
            random.skip(2814); // skip decorate rng
        } else { // v113+
            int salt = TreeBiome.getFeatureSalt(version, biome);
            int featureSeed = random.setDecorationSeed(populationSeed, salt, version);
            random.setSeed(featureSeed);
        }
    }

    private int setupTreeCount(BedrockRandom random) {
        float base = getBiomeTreeCount(biome);

        if (random.nextInt(10) == 0) {
            return (int) (base * random.nextFloat());
        }

        if (base >= 1.0f) {
            int add = random.nextInt(-7, 3);
            if (add < 1) add = 0;
            return (int)base + add;
        }

        return base < random.nextFloat() ? 0 : 1;
    }

    private TreeFeature getTreeFeature(BedrockRandom random) {
        switch (biome) {
            case FOREST, FLOWER_FOREST:
                if (random.nextInt(5) == 0) return TreeFeature.BIRCH;
                if (random.nextInt(10) == 0) return TreeFeature.FANCY_OAK;
                return TreeFeature.OAK;

            case TAIGA, GROVE:
                if (random.nextInt(3) == 0) return TreeFeature.PINE;
                return TreeFeature.SPRUCE;

            case MEGA_TAIGA:
                if (random.nextInt(3) == 0 && random.nextInt(13) != 0) return TreeFeature.MEGA_SPRUCE;
                if (random.nextInt(3) == 0) return TreeFeature.PINE;
                return TreeFeature.SPRUCE;

            case SAVANNA:
                if (random.nextInt(5) == 0) return TreeFeature.OAK;
                return TreeFeature.ACACIA;

            case BIRCH_FOREST:
                return TreeFeature.BIRCH;

            case BIRCH_FOREST_MUTATED:
                if (random.nextInt(5) == 0) return TreeFeature.BIRCH;
                if (random.nextBoolean()) return TreeFeature.SUPER_BIRCH;
                return TreeFeature.BIRCH;

            case PLAINS, SUNFLOWER_PLAINS:
                if (random.nextInt(3) == 0) return TreeFeature.FANCY_OAK;
                return TreeFeature.OAK;

            case ICE_PLAINS:
                return TreeFeature.SPRUCE;

            case JUNGLE:
                if (random.nextInt(10) == 0) return TreeFeature.FANCY_OAK;
                if (random.nextBoolean()) return TreeFeature.JUNGLE_BUSH;
                if (random.nextInt(10) == 0) return TreeFeature.MEGA_JUNGLE; // TODO maybe wrong
                return TreeFeature.JUNGLE;

            case MESA_PLATEAU_STONE:
                return TreeFeature.OAK;

            case EXTREME_HILLS_PLUS_TREES:
                if (random.nextInt(3) != 0) return TreeFeature.SPRUCE;
                if (random.nextInt(10) == 0) return TreeFeature.FANCY_OAK;
                return TreeFeature.OAK;

            default:
                throw new IllegalArgumentException("Invalid biome: " + biome);
        }
    }
}