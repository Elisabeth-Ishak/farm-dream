package animals;

import data.ConsoleColor;

import java.util.Random;

public class Sheep extends Animal {
    private int wool;
    private static final int WOOL_VALUE = 20;
    //private int woolProduction = 1;

    public Sheep(String name, int hunger, int age, int wool) {
        super(name, hunger, age);
        this.wool = wool;
    }

    @Override
    public String produceName() {
        return "Wolle";
    }

    @Override
    public int produceResources() {
        if (!isHealthy) return 0;

        int baseProduction = wool;
        if (hunger > 70) {
            baseProduction /= 2;
        }
        wool += baseProduction;
        return baseProduction;
    }

    @Override
    public int getValue() {
        return WOOL_VALUE;
    }

    @Override
    public boolean feed() {
        if (super.feed()) {
            if (new Random().nextInt(100) < 25) {
                wool++;
                System.out.println(name + " produziert mehr Wolle!");
            }
            return true;
        }
        return false;
    }

    @Override
    public int hunger() {
        return super.hunger();
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\uD83D\uDC11Wollproduktion: " + ConsoleColor.applyColor(ConsoleColor.MAGENTA,  produceResources() + " kg");
    }
}
