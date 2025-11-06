package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class SpruceTree extends TreeBase {

    public SpruceTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        if (version.isNewerOrEqualTo(MCVersion.v1_21_2)) {
            tree.fallen(random.nextInt(80) == 0);
            tree.vines(random.nextInt(12) == 0);
            tree.height(random.nextInt(6, 10));
            int leafLayers = tree.getHeight() - random.nextInt(-1, 1);
            int leafRadius = random.nextInt(2, 4);
            tree.height(tree.getHeight() - random.nextInt(0, 3));
            if (tree.isFallen()) {
                placeFallenTrunk(tree, random);
                tree.height(1);
            } else {
                random.skip(2);
            }
        } else { // < V1_21_2_2
            tree.height(random.nextInt(6, 10));
            int leafLayers = tree.getHeight() - random.nextInt(-1, 1);
            int leafRadius = random.nextInt(2, 4);
            tree.height(tree.getHeight() - random.nextInt(0, 3));

            placeTrunk(tree, random);
            random.nextInt(2);
        }
    }





















}
