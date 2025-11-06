package fragrant.feature.loot.table;

import fragrant.core.rand.BedrockRandom;
import fragrant.feature.loot.Item;

public record Function(String function, Range range, String string) {
    public void apply(Item item, BedrockRandom random) {
        switch (function) {
            case "set_count" -> {
                if (range != null) item.count = random.nextInt(range.min(), range.max() + 1);
            }
            case "set_damage" -> {
                random.nextInt(); // TODO
            }
            case "set_potion" -> {
                if (string != null) item.name = "minecraft:potion_" + string;
            }
            case "set_data" -> {
                item.data = random.nextInt(range.min(), range.max());
            }
            case "random_aux_value" -> {
                item.data = random.nextInt(range.min(), range.max());
            }
            case "enchant_randomly" -> {
                random.nextInt(); // TODO
            }
        }
    }
}
