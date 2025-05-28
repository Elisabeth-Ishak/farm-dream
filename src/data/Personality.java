package data;

public enum Personality {
    FRIENDLY("Freundlich", 0.8, 1.0, ConsoleColor.FRIENDLY),
    STUBBORN("Störrisch", 1.2, 1.0, ConsoleColor.STUBBORN),
    PLAYFUL("Verspielt", 1.0, 1.3, ConsoleColor.PLAYFUL),
    LAZY("Faul", 1.0, 0.7, ConsoleColor.LAZY),;

    private final String name;
    private final double feedingFactor;
    private final double hungerFactor;
    private final ConsoleColor color;

    Personality(String name, double feedingFactor, double hungerFactor, ConsoleColor color) {
        this.name = name;
        this.feedingFactor = feedingFactor;
        this.hungerFactor = hungerFactor;
        this.color = color;
    }
    public String getColorName(){
        return ConsoleColor.applyColor(color, name);
    }

    public ConsoleColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public double getFeedingFactor() {
        return feedingFactor;
    }

    public double getHungerFactor() {
        return hungerFactor;
    }
}
