package fragrant.feature.structure;

import fragrant.feature.BiomeValidatedFeature;
import fragrant.feature.Feature;
import fragrant.core.version.MCVersion;

import java.util.HashMap;
import java.util.Map;

public abstract class Structure<C extends Feature.Config, D extends Feature.Data<?>>
        extends BiomeValidatedFeature<C, D> {

    public static final Map<Class<? extends Structure<?, ?>>, String> CLASS_TO_NAME =
            new HashMap<>();

    protected Structure(C config, MCVersion version) {
        super(config, version);
    }

    public static String getName(Class<? extends Structure> structureClass) {
        return CLASS_TO_NAME.getOrDefault(structureClass, structureClass.getSimpleName());
    }

    public static String name() {
        return "structure";
    }

    @Override
    public String getName() {
        return getName(this.getClass());
    }
}
