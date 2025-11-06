package fragrant.feature.loot.table.builder;

import fragrant.core.util.data.Pair;
import fragrant.feature.loot.table.Entry;
import fragrant.feature.loot.table.Function;
import fragrant.feature.loot.table.Pool;
import fragrant.feature.loot.table.Range;
import fragrant.core.version.MCVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class LootBuilder {

    public static EntryBuilder item(String name) {
        return new EntryBuilder(name, "item");
    }

    public static EntryBuilder empty() {
        return new EntryBuilder(null, "empty");
    }

    public static class EntryBuilder {
        private final String name;
        private final String type;
        private int weight = 1;
        private final List<Function> functions = new ArrayList<>();
        private Predicate<MCVersion> condition = v -> true;

        private EntryBuilder(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public EntryBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public EntryBuilder count(int min, int max) {
            functions.add(new Function("set_count", new Range(min, max), null));
            return this;
        }

        public EntryBuilder count(int exact) {
            return count(exact, exact);
        }

        public EntryBuilder damage() {
            functions.add(new Function("set_damage", null, null));
            return this;
        }

        public EntryBuilder potion(String potionType) {
            functions.add(new Function("set_potion", null, potionType));
            return this;
        }

        public EntryBuilder auxValue(int min, int max) {
            functions.add(new Function("random_aux_value", new Range(min, max), null));
            return this;
        }

        public EntryBuilder enchant() {
            functions.add(new Function("enchant_randomly", null, null));
            return this;
        }

        public EntryBuilder since(MCVersion version) {
            this.condition = v -> v.isNewerOrEqualTo(version);
            return this;
        }

        public EntryBuilder until(MCVersion version) {
            this.condition = v -> v.isOlderThan(version);
            return this;
        }

        public EntryBuilder between(MCVersion from, MCVersion to) {
            this.condition = v -> v.isBetween(from, to);
            return this;
        }

        public EntryBuilder when(Predicate<MCVersion> condition) {
            this.condition = condition;
            return this;
        }

        public Entry build() {
            Function[] funcArray = functions.isEmpty() ? null : functions.toArray(new Function[0]);
            return new Entry(name, weight, type, funcArray, condition);
        }
    }

    public static PoolBuilder pool(int rolls) {
        return new PoolBuilder(rolls);
    }

    public static PoolBuilder pool(int minRolls, int maxRolls) {
        return new PoolBuilder(new Range(minRolls, maxRolls));
    }

    public static class PoolBuilder {
        private final Object rolls;
        private final List<Entry> entries = new ArrayList<>();
        private Predicate<MCVersion> condition = v -> true;

        private PoolBuilder(Object rolls) {
            this.rolls = rolls;
        }

        public PoolBuilder add(EntryBuilder... builders) {
            for (EntryBuilder builder : builders) {
                entries.add(builder.build());
            }
            return this;
        }

        public PoolBuilder add(Entry... entries) {
            Collections.addAll(this.entries, entries);
            return this;
        }

        public PoolBuilder since(MCVersion version) {
            this.condition = v -> v.isNewerOrEqualTo(version);
            return this;
        }

        public PoolBuilder until(MCVersion version) {
            this.condition = v -> v.isOlderThan(version);
            return this;
        }

        public PoolBuilder between(MCVersion from, MCVersion to) {
            this.condition = v -> v.isBetween(from, to);
            return this;
        }

        public PoolBuilder when(Predicate<MCVersion> condition) {
            this.condition = condition;
            return this;
        }

        public Pool build() {
            return new Pool(rolls, entries.toArray(new Entry[0]), condition);
        }
    }

    public static Pair<String, Integer> weighted(String item, int weight) {
        return new Pair<>(item, weight);
    }
}