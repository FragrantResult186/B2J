package fragrant.feature.structure;

import fragrant.core.util.pos.CPos;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;

public abstract class UniformStructure<T extends UniformStructure<T>> extends RegionStructure<RegionStructure.Config, RegionStructure.Data<T>> {

    public UniformStructure(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "uniform_structure";
    }

    @Override
    public boolean canStart(Data<T> data, long structureSeed, ChunkRand rand) {
        rand.setSeed(data.baseRegionSeed + structureSeed);
        return rand.nextInt(this.getSeparation()) == data.offsetX && rand.nextInt(this.getSeparation()) == data.offsetZ;
    }

    @Override
    public CPos getInRegion(long structureSeed, int regionX, int regionZ, ChunkRand rand) {
        rand.setRegionSeed(structureSeed, regionX, regionZ, this.getSalt(), this.getVersion());

        return new CPos(
                regionX * this.getSpacing() + rand.nextInt(this.getSeparation()),
                regionZ * this.getSpacing() + rand.nextInt(this.getSeparation())
        );
    }

}
