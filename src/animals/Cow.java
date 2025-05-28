package animals;

import data.ConsoleColor;
import farm.Production;
import java.util.Random;

public class Cow extends Animal implements Production {
    private int milk;
    private static final int MILK_VALUE = 15;

    public Cow(String name, int hunger, int age, int milk) {
        super(name, hunger, age);
        this.milk = milk;
    }

    @Override
    public String produceName() {
        return "Milch";
    }

    @Override
    public int produceResources() {
        if (!isHealthy) return 0;

        int baseProduction = milk;
        if (hunger > 70) {
            baseProduction /= 2;
        }
        return baseProduction;
    }

    @Override
    public int getValue() {
        return MILK_VALUE;
    }

    @Override
    public boolean feed() {
        if (super.feed()) {
            if (new Random().nextInt(100) < 40) {
                milk++;
                System.out.println(name + " gibt mehr Milch!");
            }
            return true;
        }
        return false;
    }

    @Override
    public int hunger() {
        return super.hunger();
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\uD83E\uDD5BMilchproduktion: " + ConsoleColor.applyColor(ConsoleColor.MAGENTA, produceResources() + " Liter");
    }
}
