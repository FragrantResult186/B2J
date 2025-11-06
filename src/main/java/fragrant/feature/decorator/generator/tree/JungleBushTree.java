package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class JungleBushTree extends TreeBase {

    public JungleBushTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(1);

        // TODO
    }
}
