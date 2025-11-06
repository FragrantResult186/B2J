package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class PineTree extends TreeBase {

    public PineTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(random.nextInt(7, 12));

        int layerHeight = tree.getHeight() - random.nextInt(0, 2);
        int startHeight = layerHeight - 3;
        int layers = tree.getHeight() - startHeight + 1;
        int offset = random.nextInt(1, layers + 1);

        tree.height(startHeight + layers - 2);
    }
}
