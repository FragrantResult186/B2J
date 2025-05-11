package fragrant.b2j.generator.loot;

import java.util.List;

public class LootType {
    public record LootTable(List<LootPool> pools) { }

    public record LootEntry(String name, int weight, String type, List<LootFunction> functions) { }

    public record LootFunction(String function, CountRange count) { }

    public record CountRange(int min, int max) { }

    public record RollRange(int min, int max) { }

    public static class LootPool {
        private final Object rolls;
        private final List<LootEntry> entries;

        public LootPool(Object rolls, List<LootEntry> entries) {
            this.rolls = rolls;
            this.entries = entries;
        }

        public LootPool(int rolls, List<LootEntry> entries) {
            this.rolls = rolls;
            this.entries = entries;
        }

        public Object getRolls() {
            return rolls;
        }

        public List<LootEntry> getEntries() {
            return entries;
        }
    }

    public static class LootItem {
        private final String name;
        private int count;
        private Enchantment enchantment;

        public LootItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public void setEnchantment(Enchantment enchantment) {
            this.enchantment = enchantment;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(name).append(" x").append(count);
            if (enchantment != null) {
                sb.append(" (").append(enchantment).append(")");
            }
            return sb.toString();
        }
    }

    public record Enchantment(String name, int level) {

        @Override
        public String toString() {
            return name + " Lv." + level;
        }
    }
}