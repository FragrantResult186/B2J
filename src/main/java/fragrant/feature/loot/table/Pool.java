package fragrant.feature.loot.table;

import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;

import java.util.Arrays;
import java.util.function.Predicate;

public record Pool(Object rolls, Entry[] entries, Predicate<MCVersion> condition) {
    public Pool(Object rolls, Entry[] entries) {
        this(rolls, entries, v -> true);
    }

    public Pool when(Predicate<MCVersion> condition) {
        return new Pool(rolls, entries, condition);
    }

    public Pool since(MCVersion version) {
        return new Pool(rolls, entries, v -> v.isNewerOrEqualTo(version));
    }

    public Pool until(MCVersion version) {
        return new Pool(rolls, entries, v -> v.isOlderThan(version));
    }

    public Pool between(MCVersion from, MCVersion to) {
        return new Pool(rolls, entries, v -> v.isBetween(from, to));
    }

    public boolean isActiveFor(MCVersion version) {
        return condition.test(version);
    }

    public int getRolls(BedrockRandom random) {
        if (rolls instanceof Integer) {
            random.nextInt();
            return (Integer) rolls;
        } else {
            Range range = (Range) rolls;
            return random.nextInt(range.min(), range.max() + 1);
        }
    }

    public Entry[] getActiveEntries(MCVersion version) {
        return Arrays.stream(entries)
                .filter(entry -> entry.isActiveFor(version))
                .toArray(Entry[]::new);
    }
}
