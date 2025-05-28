package logic;

import animals.*;
import data.ConsoleColor;
import data.RainbowText;
import farm.Farm;

import java.util.List;
import java.util.Random;

public class RandomEvents {
    private Random random = new Random();
    private Farm farm;

    public RandomEvents(Farm farm) {
        this.farm = farm;
    }

    public void triggerRandomEvent() {
        if (random.nextInt(4) == 0) { // 25% Chance
            int eventType = random.nextInt(5);
            switch (eventType) {
                case 0 -> foxAttack();
                case 1 -> wolfScare();
                case 2 -> thunderstorm();
                case 3 -> rainbow();
                case 4 -> diseaseOutbreak();
            }
        }
    }

    private void foxAttack() {
        List<Chicken> chickens = farm.getChickenList();
        if (!chickens.isEmpty()) {
            int index = random.nextInt(chickens.size());
            String chickenName = chickens.get(index).getName();
            chickens.remove(index);
            System.out.println("\n🦊 ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.RED, "Ein Fuchs hat " + chickenName + " gestohlen!") +"\uD83D\uDEA8");
        } else {
            System.out.println("\n🦊 ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.RED, "Ein Fuchs schleicht um den Hof, findet aber keine Hühner!"));
        }
    }

    private void wolfScare() {
        List<Sheep> sheep = farm.getSheepList();
        if (!sheep.isEmpty()) {
            System.out.println("\n🐺ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.RED, "Ein Wolf hat die Schafe erschreckt! Ihr Hunger ist gestiegen." + "\uFE0F"));
            for (Sheep s : sheep) {
                s.setHunger(s.getHunger() + 15);
                if (s.getHunger() > 100) s.setHunger(100);
            }
        } else {
            System.out.println("\n🐺ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.RED,"Ein Wolf wurde in der Nähe gesichtet!"));
        }
    }

    private void thunderstorm() {
        List<Cow> cows = farm.getCowList();
        if (!cows.isEmpty()) {
            System.out.println("\n⛈️ ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.BLUE, "Ein Gewitter macht die Kühe nervös! Ihr Hunger ist leicht gestiegen."));
            for (Cow c : cows) {
                c.setHunger(c.getHunger() + 10);
                if (c.getHunger() > 100) c.setHunger(100);
            }
        } else {
            System.out.println("\n⛈️ ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.BLUE, "Ein Gewitter zieht über den Hof!"));
        }
    }

    private void rainbow() {
        System.out.println("\n🌈ZUFALLSEREIGNIS: " + RainbowText.toRainbow("Ein Regenbogen erscheint am Himmel! Alle Tiere sind glücklich und weniger hungrig."));
        for (Animal animal : farm.getAnimalList()) {
            animal.setHunger(animal.getHunger() - 10);
            if (animal.getHunger() < 0) animal.setHunger(0);
        }
    }

    private void diseaseOutbreak() {
        System.out.println("\n🦠 ZUFALLSEREIGNIS: " + ConsoleColor.applyColor(ConsoleColor.RED, "Eine Krankheit breitet sich auf dem Hof aus! Ein zufälliges Tier wird krank.") + "☣\uFE0F");
        List<Animal> animals = farm.getAnimalList();
        if (!animals.isEmpty()) {
            int index = random.nextInt(animals.size());
            Animal sickAnimal = animals.get(index);
            sickAnimal.setHealthy(false);
            System.out.println(sickAnimal.getName() + " ist jetzt krank und braucht Pflege!");
        }
    }
}
