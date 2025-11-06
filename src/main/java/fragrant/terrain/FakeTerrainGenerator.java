package fragrant.terrain;

import fragrant.biome.source.BiomeSource;
import fragrant.core.block.Block;
import fragrant.core.state.Dimension;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.data.Pair;
import fragrant.core.util.pos.BPos;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FakeTerrainGenerator extends TerrainGenerator {

    public FakeTerrainGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    public Dimension getDimension() {
        return null;
    }

    @Override
    public int getWorldHeight() {
        return 0;
    }

    @Override
    public Block getDefaultBlock() {
        return null;
    }

    @Override
    public Block getDefaultFluid() {
        return null;
    }

    @Override
    protected void sampleNoiseColumnOld(double[] buffer, int x, int z, double depth, double scale) {

    }

    @Override
    public int getHeightOnGround(int x, int z) {
        return 0;
    }

    @Override
    public int getFirstHeightInColumn(int x, int z, Predicate<Block> predicate) {
        return 0;
    }

    @Override
    public Block[] getColumnAt(int x, int z) {
        return new Block[0];
    }

    @Override
    public Block[] getColumnAt(int x, int z, List<Pair<Supplier<Integer>, BlockBox>> jigsawBoxes, List<BPos> jigsawJunction) {
        return new Block[0];
    }

    @Override
    public Block[] getBiomeColumnAt(int x, int z) {
        return new Block[0];
    }

    @Override
    public Block[] getBiomeColumnAt(int x, int z, List<Pair<Supplier<Integer>, BlockBox>> jigsawBoxes, List<BPos> jigsawJunction) {
        return new Block[0];
    }

    @Override
    public Block[] getBedrockColumnAt(int x, int z) {
        return new Block[0];
    }

    @Override
    public Block[] getBedrockColumnAt(int x, int z, List<Pair<Supplier<Integer>, BlockBox>> jigsawBoxes, List<BPos> jigsawJunction) {
        return new Block[0];
    }

    @Override
    public Optional<Block> getBlockAt(int x, int y, int z) {
        return Optional.empty();
    }

}