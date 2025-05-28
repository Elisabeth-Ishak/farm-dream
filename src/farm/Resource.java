package farm;

import data.ConsoleColor;

public class Resource {
    public enum ResourceType {
        MILK("Milch", 15),
        EGGS("Eier", 5),
        WOOL("Wolle", 20);

        private final String name;
        private final int baseValue;

        ResourceType(String name, int baseValue) {
            this.name = name;
            this.baseValue = baseValue;
        }

        public String getName() {
            return name;
        }

        public int getBaseValue() {
            return baseValue;
        }
    }

    private ResourceType type;
    private int quantity;
    private int quality;

    public Resource(ResourceType type, int quantity, int quality) {
        this.type = type;
        this.quantity = quantity;
        this.quality = quality;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getValue() {
        return (int)(type.getBaseValue() * quantity * (quality / 100.0));
    }

    @Override
    public String toString() {
        return ConsoleColor.applyColor(ConsoleColor.YELLOW, quantity + " " + type.getName() + " (Qualität: " + quality + "%)");
    }
}
