package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class JungleTree extends TreeBase {

    public JungleTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(random.nextInt(4, 11));
        generateLeavesPattern(tree, random);
        addVines(tree, random);
        if (random.nextInt(5) == 0 && tree.getHeight() > 5) {
            this.addCocoaPlants(tree, random);
        }
    }

    private void addVines(Tree.Data tree, BedrockRandom random) {
        for (int dir = 0; dir < 4; dir++) {
            if (random.nextInt(4) == 0) {
                // add vine
            }
        }
    }

    private void addCocoaPlants(Tree.Data tree, BedrockRandom random) {
        for (int y = 0; y < 2; y++) {
            int chance = 4 - y;
            for (int dir = 0; dir < 4; dir++) {
                if (random.nextInt(chance) == 0) {
                    int age = random.nextInt(0, 2);
                    tree.cocoa(true);
                }
            }
        }
    }
}
