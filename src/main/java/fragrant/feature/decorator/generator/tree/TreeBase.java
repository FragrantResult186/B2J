package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public abstract class TreeBase {

    protected final MCVersion version;
    protected final TreeBiome biome;

    protected TreeBase(MCVersion version, TreeBiome biome) {
        this.version = version;
        this.biome = biome;
    }

    public abstract void generate(Tree.Data tree, BedrockRandom random);

    public void generateLeavesPattern(Tree.Data tree, BedrockRandom random) {
        int pattern = 0;
        int layer = 3, corner = 4;
        for (int i = 0; i < layer * corner; i++) {
            pattern |= random.nextInt(0, 1) << i; // 0 == air
        }
        tree.leavesPattern(pattern);
    }

    protected void placeTrunk(Tree.Data tree, BedrockRandom random) {
        tree.fallen(random.nextInt(80) == 0);

        float vineChance;
        if (tree.isFallen()) {
            vineChance = 0.75F;
            this.placeFallenTrunk(tree, random);
            tree.height(1);
        } else {
            if (random.nextInt(12) == 0) {
                vineChance = 1.0F; // always generated
            } else {
                vineChance = 0.0F; // not generated
            }
        }
        this.placeVines(tree, random, vineChance);
    }

    protected void placeFallenTrunk(Tree.Data tree, BedrockRandom random) {
        int direction = random.nextInt(0, 4);
        int trunkLength = tree.getHeight() - 2;
        int distance = random.nextInt(2, 4);
        for (int i = 0; i < trunkLength; i++) {
            boolean hasMushroom = random.nextInt(0, 10) == 0;
            if (hasMushroom) {
                if (random.nextFloat() < 0.5F) {
                    tree.mushroom("brown_mushroom");
                } else {
                    tree.mushroom("red_mushroom");
                }
            }
        }
    }

    protected void placeVines(Tree.Data tree, BedrockRandom random, float vineChance) {
        if (vineChance > 0.0F) {
            for (int y = 0; y < tree.getHeight(); y++) {
                for (int dir = 0; dir < 4; dir++) {
                    if (random.nextFloat() < vineChance) {
                        tree.vines(true);
                    }
                }
            }
        }
    }

}