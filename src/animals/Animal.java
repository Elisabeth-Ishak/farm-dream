package animals;

import data.ConsoleColor;
import data.Personality;
import farm.Production;
import java.util.Random;

public abstract class Animal implements HungerManager, Health, Production {
    String name;
    int hunger;
    int age;
    Personality personality;
    boolean isHealthy = true;

    public Animal() {
        Personality[] personalities = Personality.values();
        this.personality = personalities[new Random().nextInt(personalities.length)];
    }

    public Animal(String name, int hunger, int age) {
        this();
        this.name = name;
        this.hunger = hunger;
        this.age = age;
    }

    @Override
    public boolean feed() {
        if (!isHealthy) {
            System.out.println(name + " ist krank und frisst nicht so gut.");
            hunger -= 10 * personality.getFeedingFactor();
        } else {
            hunger -= 20 * personality.getFeedingFactor();
        }
        if (hunger < 0) hunger = 0;
        return true;
    }

    @Override
    public int hunger() {
        double hungerIncrease;
        if (!isHealthy) {
            hungerIncrease = hungerValue * personality.getHungerFactor() * 1.5;
        } else {
            hungerIncrease = hungerValue * personality.getHungerFactor();
        }
        hunger += (int) hungerIncrease;
        if (hunger > 100) hunger = 100;
        return hunger;
    }

    @Override
    public boolean isHealthy() {
        return isHealthy;
    }

    @Override
    public void setHealthy(boolean healthy) {
        this.isHealthy = healthy;
    }

    @Override
    public void heal() {
        this.isHealthy = true;
        System.out.println(name + " wurde geheilt und ist wieder gesund!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Personality getPersonality() {
        return personality;
    }

    @Override
    public String toString() {


        String healthStatus = isHealthy ? ConsoleColor.applyColor(ConsoleColor.GREEN,"Gesund") : ConsoleColor.applyColor(ConsoleColor.RED, "Krank ⚠️");

        return name + " (" + personality.getColorName() + ")\n" +
                "Alter: " + ConsoleColor.applyColor(ConsoleColor.BLUE,  age + " Jahre") + "\n"  +
                "Hunger: " + ConsoleColor.applyColor(ConsoleColor.YELLOW,  hunger + "%") + "\n" +
                "Status: " + healthStatus;
    }
}
