package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class BirchTree extends TreeBase {

    public BirchTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        generate(tree, random, false);
    }

    protected void generate(Tree.Data tree, BedrockRandom random, boolean isSuper) {
        if (version.isNewerOrEqualTo(MCVersion.v1_21_2)) {
            tree.fallen(random.nextInt(80) == 0);

            int trunkHeight = random.nextInt(5, 8);
            if (isSuper) {
                trunkHeight += random.nextInt(0, 7);
            }
            tree.height(trunkHeight);

            if (tree.isFallen()) {
                placeFallenTrunk(tree, random);
            } else {
                generateLeavesPattern(tree, random);
                if (random.nextFloat() < 0.035f) {
                    tree.beehive(true);
                }
            }
        } else { // < V1_21_2
            int trunkHeight = random.nextInt(5, 8);
            if (isSuper) {
                trunkHeight += random.nextInt(0, 7);
            }
            tree.height(trunkHeight);
            tree.fallen(random.nextInt(80) == 0);

            if (tree.isFallen()) {
                placeFallenTrunk(tree, random);
            } else {
                generateLeavesPattern(tree, random);
                random.skip(4);
            }
        }
    }
}
