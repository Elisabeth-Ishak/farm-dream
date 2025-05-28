package data;

import animals.Animal;

public enum Weather {
    SUNNY("Sonnig", animal -> {}),
    RAINY("Regnerisch", animal -> {
        if (!animal.isHealthy()) return;
        animal.setHunger(animal.getHunger() + 5);
    }),
    STORMY("Stürmisch", animal -> {
        if (!animal.isHealthy()) return;
        animal.setHunger(animal.getHunger() + 10);
    }),
    RAINBOW("Regenbogen", animal -> {
        if (!animal.isHealthy()) return;
        animal.setHunger(Math.max(0, animal.getHunger() - 10));
    }),
    FOGGY("Neblig", animal -> {
        if (!animal.isHealthy()) return;
        animal.setHunger(animal.getHunger() + 3);
    });

    private final String name;
    private final WeatherEffect effect;

    Weather(String name, WeatherEffect effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public void applyEffect(Animal animal) {
        effect.apply(animal);
    }

    @FunctionalInterface
    private interface WeatherEffect {
        void apply(Animal animal);
    }
}
