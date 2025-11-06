package fragrant.feature.loot;

import fragrant.core.version.MCVersion;
import fragrant.feature.loot.table.Pool;

public interface ILoot {
    Pool[] getPools();

    default String getName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    default boolean isActiveFor(MCVersion version) {
        return true;
    }
}