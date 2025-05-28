package animals;

import data.ConsoleColor;

import java.util.Random;

public class Chicken extends Animal {

    private int eggs;
    private static final int EGG_VALUE = 5;

    public Chicken(String name, int hunger, int age, int eggs) {
        super(name, hunger, age);
        this.eggs = eggs;
    }

    @Override
    public String produceName() {
        return "Eier";
    }

    @Override
    public int produceResources() {
        if (!isHealthy) return 0;

        int baseProduction = eggs;
        if (hunger > 70) {
            baseProduction = 0;
        }
        return baseProduction;
    }

    @Override
    public int getValue() {
        return EGG_VALUE;
    }

    @Override
    public boolean feed() {
        if (super.feed()) {
            if (new Random().nextInt(100) < 30) {
                eggs++;
                System.out.println(name + " wird mehr Eier legen!");
            }
            return true;
        }
        return false;
    }

    @Override
    public int hunger() {
        return super.hunger();
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\uD83E\uDD5AEierproduktion: " + ConsoleColor.applyColor(ConsoleColor.MAGENTA, produceResources() + " Stück");
    }
}
