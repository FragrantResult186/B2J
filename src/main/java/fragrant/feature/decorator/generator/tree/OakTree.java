package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class OakTree extends TreeBase {

    public OakTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        if (version.isNewerOrEqualTo(MCVersion.v1_21_2)) {
            if (biome == TreeBiome.FLOWER_FOREST) { // no fallen? no vines?
                tree.height(random.nextInt(4, 7));
                generateLeavesPattern(tree, random);
            } else {
                tree.fallen(random.nextInt(80) == 0);
                tree.vines(random.nextInt(12) == 0);
                tree.height(random.nextInt(4, 7));
                if (tree.isFallen()) {
                    placeFallenTrunk(tree, random);
                    tree.height(1);
                } else {
                    generateLeavesPattern(tree, random);
                }
            }
        } else { // < V1_21_2
            tree.height(random.nextInt(4, 7));

            placeTrunk(tree, random);
            if (!tree.isFallen()) {
                generateLeavesPattern(tree, random);
                random.skip(4);
            }
        }
    }
}
