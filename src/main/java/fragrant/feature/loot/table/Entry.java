package fragrant.feature.loot.table;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;
import fragrant.feature.loot.Item;

import java.util.function.Predicate;

public record Entry(String name, int weight, String type, Function[] functions, Predicate<MCVersion> condition) {
    public Entry(String name, int weight, String type, Function[] functions) {
        this(name, weight, type, functions, v -> true);
    }

    public Entry when(Predicate<MCVersion> condition) {
        return new Entry(name, weight, type, functions, condition);
    }

    public Entry since(MCVersion version) {
        return new Entry(name, weight, type, functions, v -> v.isNewerOrEqualTo(version));
    }

    public Entry until(MCVersion version) {
        return new Entry(name, weight, type, functions, v -> v.isOlderThan(version));
    }

    public Entry between(MCVersion from, MCVersion to) {
        return new Entry(name, weight, type, functions, v -> v.isBetween(from, to));
    }

    public boolean isActiveFor(MCVersion version) {
        return condition.test(version);
    }

    public Item createItem(BedrockRandom random) {
        Item item = new Item(name, 1);
        if (functions != null) {
            for (Function func : functions) {
                func.apply(item, random);
            }
        }
        return item;
    }
}
