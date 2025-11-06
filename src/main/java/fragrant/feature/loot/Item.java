package fragrant.feature.loot;

public class Item {
    public String name;
    public int count;
    public int data;
    public String enchantment;

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append(" x").append(count);
        if (enchantment != null) sb.append(" (").append(enchantment).append(")");
        return sb.toString();
    }
}
