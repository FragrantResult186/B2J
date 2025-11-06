package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class FancyOakTree extends TreeBase {

    public FancyOakTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(random.nextInt(5, 17));

        int branchLayerCount = (int)((0x876654432110L >> ((tree.getHeight() - 5) * 4)) & 0xf);
        int branchesPerLayer = tree.getHeight() >= 11 ? 2 : 1;
        int branchCount = branchLayerCount * branchesPerLayer;

        for (int i = 0; i < branchCount; i++) {
            float angle = random.nextFloat();
            float dist = random.nextFloat();
            // ...
        }
    }
}
