package fragrant.feature.decorator.generator.tree;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.Tree;

public class RoofTree extends TreeBase {

    public RoofTree(MCVersion version, TreeBiome biome) {
        super(version, biome);
    }

    @Override
    public void generate(Tree.Data tree, BedrockRandom random) {
        tree.height(random.nextInt(0, 3) + random.nextInt(0, 2) + 6);

        int bendDir = random.nextInt(0, 4);
        int bendStart = tree.getHeight() - random.nextInt(0, 4);
        int bendLength = 2 - random.nextInt(0, 3);
        boolean vines = random.nextInt(0, 6) == 0;
        placeVines(tree, random, vines);
        placeLeafs(tree, random);
    }

    public static void placeVines(Tree.Data tree, BedrockRandom random, boolean placeExtra) {
        if (placeExtra) {
            if (random.nextInt(7) != 0) {
                // placeVine
            }
            if (random.nextInt(7) != 0) {
                // placeVine
            }
        }
    }

    public static void placeLeafs(Tree.Data tree, BedrockRandom random) {
        for (int i = 0; i < 4; i++) {
            if (random.nextInt(3) == 0) {
                int branchHeight = random.nextInt(0, 3) + 2;
                int x = random.nextInt(0, 3) - 1;
                int z = random.nextInt(0, 3) - 1;
            }
        }
    }
}
