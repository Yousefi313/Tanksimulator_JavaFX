package de.futurehome.tanksimulator;

public class Tank {

    public static final double MAX_FUELLSTAND = 200.0;

    private double fuellstand;

    public Tank(double startwert) {
        this.fuellstand = clamp(startwert);
    }

    public double getFuellstand() {
        return fuellstand;
    }

    // +5 Liter
    public void fuellen() {
        fuellstand = clamp(fuellstand + 5);
    }

    // variabler Verbrauch (1–4 Liter)
    public void verbrauchen(double liter) {
        fuellstand = clamp(fuellstand - liter);
    }

    public void reset() {
        fuellstand = 0;
    }

    // Sicherheit: 0–200 Liter
    private double clamp(double value) {
        if (value < 0) return 0;
        if (value > MAX_FUELLSTAND) return MAX_FUELLSTAND;
        return value;
    }
}
