package data;

public enum ConsoleColor {
    // Grün für gesund oder erfolgreich, Rot für krank oder Fehler, Orange für Warnungen, Blau für neutrale Infos

    GREEN("\u001B[32m"),
    LIGHTGREEN("\u001B[38;5;46m"),
    RED("\u001B[31m"),
    YELLOW("\u001B[38;5;214m"),
    ORANGE("\u001B[38;5;202m"),
    BLUE("\u001B[34m"),
    GOLD("\u001B[38;5;220m"),
    MAGENTA("\u001B[38;5;201m"),
    RESET("\u001B[0m"),

    FRIENDLY("\u001B[38;5;213m"), // Rosa für freundlich
    STUBBORN("\u001B[91m"),  // Dunkelrot für störrisch
    PLAYFUL("\u001B[38;5;118m"), // Neongürn für verspielt
    LAZY("\u001B[38;5;230m");      // Beige für faul


    private final String colorCode;

    ConsoleColor(String colorCode) {
        this.colorCode = colorCode;
    }
    public String getColorCode() {
        return colorCode;
    }
    public static String applyColor(ConsoleColor color, String text) {
        return color.getColorCode() + text + RESET.getColorCode();
    }
}
