package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class AcaciaTree extends TreeBase {

    public AcaciaTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(random.nextInt(0, 3) + random.nextInt(0, 3) + 5);
        int leavesBase = tree.getHeight() - random.nextInt(-1, 3);
        int direction = random.nextInt(0, 4);
        int leafRadius = random.nextInt(1, 4);

        int step = random.nextInt(3);
        int bendStart = tree.getHeight() - random.nextInt(0, 4);

        int sideDir = random.nextInt(0, 4);
        if (sideDir != direction) {
            int branchLen = random.nextInt(1, 4);
            int branchY = leavesBase - random.nextInt(-1, 1);
        }
    }
}
