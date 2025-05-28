import animals.*;
import data.ConsoleColor;
import data.RainbowText;
import farm.Farm;
import logic.RandomEvents;

import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final Farm farm = new Farm();
    private final RandomEvents randomEvents = new RandomEvents(farm);
    private int dayCount = 1;


    public void start() {
        System.out.println("\uD83C\uDF3E\uD83D\uDC69\u200D\uD83C\uDF3E☀\uFE0F " + RainbowText.toRainbow("WILLKOMMEN AUF DEINER FARM ") + "\uD83D\uDC04\uD83D\uDC24\uD83D\uDC11");
        initializeAnimals();

        while (true) {
            System.out.println(ConsoleColor.applyColor(ConsoleColor.ORANGE,"\n=== TAG " + dayCount + " ==="));
            farm.printStatus();
            System.out.println(ConsoleColor.applyColor(ConsoleColor.GOLD, "\nGeld: " + farm.getMoney() + "€"));

            printOptions();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> handleFeeding();
                case "2" -> handleAddAnimal();
                case "3" -> farm.harvestAllResources();
                case "4" -> farm.treatSickAnimals();
                case "5" -> handleBuyAnimal();
                case "6" -> handleBuyFeed();
                case "7" -> {
                    farm.nextRound();
                    randomEvents.triggerRandomEvent();
                    dayCount++;

                    if (farm.isAnimalStarving()) {
                        System.out.println("\n😢" + ConsoleColor.applyColor(ConsoleColor.RED,"Eines der Tiere ist verhungert. Game over."));
                        System.out.println(ConsoleColor.applyColor(ConsoleColor.GOLD, "Du hast " + dayCount + " Tage überlebt."));
                        return;
                    }
                }
                case "8" -> {
                    System.out.println("Spiel beendet.");
                    return;
                }
                default -> System.out.println("Ungültige Option.");
            }
        }
    }

    private void initializeAnimals() {
        farm.addAnimal(new Sheep("Luna", 50, 2, 4));
        farm.addAnimal(new Cow("Molly", 50, 6, 25));
        farm.addAnimal(new Chicken("ChiChi", 40, 3, 1));
        farm.addAnimal(new Cow("Miki", 30, 1, 15));
    }

    private void printOptions() {
        System.out.println(ConsoleColor.applyColor(ConsoleColor.GREEN, "\nOptionen:"));
        System.out.println("1) Tier oder Gruppe füttern");
        System.out.println("2) Neues Tier hinzufügen");
        System.out.println("3) Ressourcen ernten (Milch, Eier, Wolle)");
        System.out.println("4) Kranke Tiere behandeln");
        System.out.println("5) Tier kaufen");
        System.out.println("6) Futter kaufen");
        System.out.println("7) Nächsten Tag starten");
        System.out.println("8) Spiel beenden");
        System.out.print("Auswahl: ");
    }

    private void handleFeeding() {
        String animalInput = farm.readAnimalInput();
        farm.feedAnimalOrGroup(animalInput);
    }
    private void handleBuyFeed() {
        int amount = readIntInput("Wie viele Einheiten Futter möchtest du kaufen? (10€/Einheit): ");
        farm.buyFeed(amount);
    }

    private void handleAddAnimal() {
        System.out.println("\nTier-Typ (Schaf/Kuh/Huhn): ");
        String type = scanner.nextLine().trim();

        System.out.println("Name: ");
        String name = scanner.nextLine().trim();

        int hunger = readIntInput("Hunger (0-100): ");
        int age = readIntInput("Alter: ");

        farm.createNewAnimal(type, name, hunger, age);
        System.out.println("Neues Tier hinzugefügt!");
    }

    private void handleBuyAnimal() {
        System.out.println("\nWelches Tier möchtest du kaufen?");
        System.out.println("1) Schaf (200€)");
        System.out.println("2) Kuh (350€)");
        System.out.println("3) Huhn (100€)");
        System.out.print("Auswahl (oder '0' zum Abbrechen): ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("0")) {
            System.out.println("Kauf abgebrochen.");
            return;
        }

        System.out.println("Name für das neue Tier: ");
        String name = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                if (farm.getMoney() >= 200) {
                    farm.addAnimal(new Sheep(name, 50, 1, 2));
                    farm.decreaseMoney(200);
                    System.out.println("Neues Schaf " + name + " wurde gekauft (-200€)");
                } else {
                    System.out.println("Nicht genug Geld für ein Schaf!");
                }
            }
            case "2" -> {
                if (farm.getMoney() >= 350) {
                    farm.addAnimal(new Cow(name, 50, 1, 10));
                    farm.decreaseMoney(350);
                    System.out.println("Neue Kuh " + name + " wurde gekauft (-350€)");
                } else {
                    System.out.println("Nicht genug Geld für eine Kuh!");
                }
            }
            case "3" -> {
                if (farm.getMoney() >= 100) {
                    farm.addAnimal(new Chicken(name, 50, 1, 2));
                    farm.decreaseMoney(100);
                    System.out.println("Neues Huhn " + name + " wurde gekauft (-100€)");
                } else {
                    System.out.println("Nicht genug Geld für ein Huhn!");
                }
            }
            default -> System.out.println("Ungültige Auswahl.");
        }
    }

    private int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }
    }
}
