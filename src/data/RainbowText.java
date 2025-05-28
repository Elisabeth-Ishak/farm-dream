package data;

public class RainbowText {
        private static final String[] RAINBOW_COLORS = {
                "\u001B[38;5;196m", // Rot
                "\u001B[38;5;202m", // Orange
                "\u001B[38;5;226m", // Gelb
                "\u001B[38;5;46m",  // Grün
                "\u001B[38;5;51m",  // Cyan
                "\u001B[38;5;21m",  // Blau
                "\u001B[38;5;201m", // Magenta
        };

        private static final String RESET = "\u001B[0m";

        public static String toRainbow(String text) {
            StringBuilder builder = new StringBuilder();
            int colorIndex = 0;
            for (char c : text.toCharArray()) {
                // Leerzeichen oder Sonderzeichen behalten ihre Farbe
                if (Character.isWhitespace(c)) {
                    builder.append(c);
                } else {
                    builder.append(RAINBOW_COLORS[colorIndex % RAINBOW_COLORS.length])
                            .append(c)
                            .append(RESET);
                    colorIndex++;
                }
            }
            return builder.toString();
        }

        public static void main(String[] args) {
            System.out.println(toRainbow("Willkommen auf deiner bunten Konsole!"));
        }
}
