package fragrant.b2j.loot;

import java.util.List;

public class LootType {
    public record LootPool(Object rolls, List<LootEntry> entries) {}

    public record LootTable(List<LootPool> pools) {}

    public record LootEntry(String name, int weight, String type, List<LootFunction> functions) {}

    public record LootFunction(String function, CountRange count, String potionType) {}

    public record RollRange(int min, int max) {}

    public record CountRange(int min, int max) {}

    public record Enchantment(String name, int level) {
        @Override
        public String toString() {
            return name + " Lv." + level;
        }
    }

    public static class LootItem {
        private String name;
        private int count;
        private int dataValue = -1;
        private String variant;
        private Enchantment enchantment;
        private float damage = -1f;

        public LootItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDataValue() {
            return dataValue;
        }

        public void setDataValue(int dataValue) {
            this.dataValue = dataValue;
        }

        public String getVariant() {
            return variant;
        }

        public void setVariant(String variant) {
            this.variant = variant;
        }

        public float getDamage() {
            return damage;
        }

        public void setDamage(float damage) {
            this.damage = damage;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public void setEnchantment(Enchantment enchantment) {
            this.enchantment = enchantment;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (variant != null) {
                sb.append(name).append(" (").append(variant).append(")");
            } else {
                sb.append(name);
            }

            sb.append(" x").append(count);

            if (dataValue != -1) {
                sb.append(" (data: ").append(dataValue).append(")");
            }

            if (damage > 0.0) {
                sb.append(" (damage: ").append(String.format("%.1f%%", damage * 100)).append(")");
            }

            if (enchantment != null) {
                sb.append(" (").append(enchantment).append(")");
            }

            return sb.toString();
        }
    }

}