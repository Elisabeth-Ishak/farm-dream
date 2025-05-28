package farm;
import animals.Animal;
import animals.Chicken;
import animals.Cow;
import animals.Sheep;
import data.ConsoleColor;
import data.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Farm {

    private List<Cow> cowList;
    private List<Sheep> sheepList;
    private List<Chicken> chickenList;
    private int roundCounter = 1;
    private final Scanner scanner = new Scanner(System.in);
    private List<? extends Animal> fedAnimals = null;
    private int money = 50;
    private int feed = 10;
    private Weather currentWeather = Weather.SUNNY;

    public Farm() {
        cowList = new ArrayList<>();
        sheepList = new ArrayList<>();
        chickenList = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        if (animal instanceof Sheep) {
            sheepList.add((Sheep) animal);
        } else if (animal instanceof Cow) {
            cowList.add((Cow) animal);
        } else if (animal instanceof Chicken) {
            chickenList.add((Chicken) animal);
        }
    }

    public void createNewAnimal(String type, String name, int hunger, int age) {
        switch (type.toLowerCase()) {
            case "schaf":
                sheepList.add(new Sheep(name, hunger, age, new Random().nextInt(5) + 1));
                break;
            case "kuh":
                cowList.add(new Cow(name, hunger, age, new Random().nextInt(20) + 5));
                break;
            case "huhn":
                chickenList.add(new Chicken(name, hunger, age, new Random().nextInt(3) + 1));
                break;
            default:
                System.out.println("Unbekannter Tier-Typ.");
        }
    }

    public void printStatus() {
        System.out.println(ConsoleColor.applyColor(ConsoleColor.ORANGE, "\n==================================="));
        System.out.println(ConsoleColor.applyColor(ConsoleColor.LIGHTGREEN, "FARM STATUS - RUNDE " + roundCounter));
        System.out.println(ConsoleColor.applyColor(ConsoleColor.ORANGE, "==================================="));
        System.out.println(ConsoleColor.applyColor(ConsoleColor.GOLD,"Kontostand: " + money + " €"));
        System.out.println(ConsoleColor.applyColor(ConsoleColor.GOLD,"Futtervorrat: " + feed + " Einheiten"));
        System.out.println(ConsoleColor.applyColor(ConsoleColor.GOLD,"Aktuelles Wetter: " + currentWeather.getName()));

        printAnimalStatus("\uD83D\uDC11", sheepList);
        printAnimalStatus("\uD83D\uDC04", cowList);
        printAnimalStatus("\uD83D\uDC24", chickenList);
    }

    private void printAnimalStatus(String animalType, List<? extends Animal> animals) {
        System.out.println("\n" + animalType + " (" + animals.size() + "):");
        if (animals.isEmpty()) {
            System.out.println("Keine " + animalType.toLowerCase() + " vorhanden.");
        } else {
            for (Animal animal : animals) {
                System.out.println(animal.toString());
                System.out.println(ConsoleColor.applyColor(ConsoleColor.ORANGE,"-------------------"));
            }
        }
    }

    public String readAnimalInput() {
        System.out.println("Gib ein Tier oder eine Gruppe ein (Schaf/Kuh/Huhn/alle Schafe/alle Kühe/alle Hühner/alle Tiere): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Keine Eingabe erkannt, bitte erneut versuchen.");
            return readAnimalInput();
        }
        return input;
    }

    public void feedAnimalOrGroup(String input) {
        if (feed <= 0) {
            System.out.println("Du hast kein Futter mehr! Kaufe neues Futter.");
            return;
        }

        switch (input.toLowerCase()) {
            case "schaf":
                feedSingleAnimal(sheepList, "Schaf");
                break;
            case "kuh":
                feedSingleAnimal(cowList, "Kuh");
                break;
            case "huhn":
                feedSingleAnimal(chickenList, "Huhn");
                break;
            case "alle schafe":
                feedAllAnimals(sheepList, "Schafe");
                break;
            case "alle kühe":
                feedAllAnimals(cowList, "Kühe");
                break;
            case "alle hühner":
                feedAllAnimals(chickenList, "Hühner");
                break;
            case "alle tiere":
                int totalAnimals = sheepList.size() + cowList.size() + chickenList.size();
                if (feed >= totalAnimals) {
                    feedAllAnimals(sheepList, "Schafe");
                    feedAllAnimals(cowList, "Kühe");
                    feedAllAnimals(chickenList, "Hühner");
                    System.out.println("Alle Tiere wurden gefüttert. Verbleibendes Futter: " + feed);
                } else {
                    System.out.println("Nicht genug Futter für alle Tiere! Du brauchst " + totalAnimals + " Einheiten.");
                }
                break;
            default:
                System.out.println("Ungültige Eingabe.");
                return;
        }
        increaseHunger();
    }

    private void feedSingleAnimal(List<? extends Animal> animals, String type) {
        if (!animals.isEmpty()) {
            if (feed > 0) {
                animals.get(0).feed();
                fedAnimals = animals;
                feed--;
                System.out.println(animals.get(0).getName() + " wurde gefüttert. Verbleibendes Futter: " + feed);
            } else {
                System.out.println("Du hast kein Futter mehr!");
            }
        } else {
            System.out.println("Keine " + type.toLowerCase() + " vorhanden.");
        }
    }

    private void feedAllAnimals(List<? extends Animal> animals, String type) {
        if (!animals.isEmpty()) {
            if (feed >= animals.size()) {
                for (Animal animal : animals) {
                    animal.feed();
                    feed--;
                }
                fedAnimals = animals;
                System.out.println("Alle " + type + " wurden gefüttert. Verbleibendes Futter: " + feed);
            } else {
                System.out.println("Nicht genug Futter für alle " + type + "! Du brauchst " + animals.size() + " Einheiten.");
            }
        } else {
            System.out.println("Keine " + type + " vorhanden.");
        }
    }

    public void increaseHunger() {
        if (fedAnimals != sheepList) {
            for (Sheep sheep : sheepList) {
                sheep.hunger();
            }
        }
        if (fedAnimals != cowList) {
            for (Cow cow : cowList) {
                cow.hunger();
            }
        }
        if (fedAnimals != chickenList) {
            for (Chicken chicken : chickenList) {
                chicken.hunger();
            }
        }
    }

    public boolean isAnimalStarving() {
        return checkStarvation(sheepList, "Schaf") || checkStarvation(cowList, "Kuh") || checkStarvation(chickenList, "Huhn");
    }

    private boolean checkStarvation(List<? extends Animal> animals, String type) {
        for (Animal animal : animals) {
            if (animal.getHunger() >= 100) {
                System.out.println("Das " + type + " " + animal.getName() + " ist verhungert!");
                animals.remove(animal);
                return true;
            }
        }
        return false;
    }

    public void harvestAllResources() {
        int totalEarnings = 0;

        for (Cow cow : cowList) {
            int milk = cow.produceResources();
            int milkValue = milk * cow.getValue();
            totalEarnings += milkValue;
            System.out.println(cow.getName() + " produziert " + milk + " Liter Milch (+" + milkValue + "€)");
            cow.setMilk(0);
        }

        for (Sheep sheep : sheepList) {
            int wool = sheep.produceResources();
            int woolValue = wool * sheep.getValue();
            totalEarnings += woolValue;
            System.out.println(sheep.getName() + " produziert " + wool + " kg Wolle (+" + woolValue + "€)");
            sheep.setWool(0);
        }

        for (Chicken chicken : chickenList) {
            int eggs = chicken.produceResources();
            int eggValue = eggs * chicken.getValue();
            totalEarnings += eggValue;
            System.out.println(chicken.getName() + " legt " + eggs + " Eier (+" + eggValue + "€)");
            chicken.setEggs(0);
        }

        System.out.println("Gesamteinnahmen durch Ernte: " + totalEarnings + "€");
        money += totalEarnings;
    }

    public void treatSickAnimals() {
        int totalCost = 0;
        int healCost = 50;

        for (Animal animal : getAnimalList()) {
            if (!animal.isHealthy()) {
                if (money >= healCost) {
                    animal.heal();
                    money -= healCost;
                    totalCost += healCost;
                    System.out.println(animal.getName() + " wurde vom Tierarzt behandelt (-" + healCost + "€)");
                } else {
                    System.out.println("Nicht genug Geld, um " + animal.getName() + " zu behandeln!");
                }
            }
        }

        if (totalCost > 0) {
            System.out.println("Gesamtkosten für tierärztliche Behandlung: " + totalCost + "€");
        }
    }

    public void buyFeed(int amount) {
        int feedCost = 10 * amount;
        if (money >= feedCost) {
            money -= feedCost;
            feed += amount;
            System.out.println("Du hast " + amount + " Einheiten Futter gekauft (-" + feedCost + "€)");
        } else {
            System.out.println("Nicht genug Geld, um Futter zu kaufen!");
        }
    }

    public List<Animal> getAnimalList() {
        List<Animal> animalList = new ArrayList<>();
        animalList.addAll(chickenList);
        animalList.addAll(cowList);
        animalList.addAll(sheepList);
        return animalList;
    }

    public List<Cow> getCowList() {
        return cowList;
    }

    public List<Sheep> getSheepList() {
        return sheepList;
    }

    public List<Chicken> getChickenList() {
        return chickenList;
    }

    public int getMoney() {
        return money;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    public void nextRound() {
        roundCounter++;

        Weather[] weathers = Weather.values();
        currentWeather = weathers[new Random().nextInt(weathers.length)];

        for (Animal animal : getAnimalList()) {
            currentWeather.applyEffect(animal);
        }
        increaseHunger();

        fedAnimals = null;
    }
}