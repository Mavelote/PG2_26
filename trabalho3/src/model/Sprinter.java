package model;

import java.util.Random;

public class Sprinter implements Racer {
    private final String id;
    private int pos = 0;
    private final Random rnd = new Random();

    public Sprinter(String id) { this.id = id; }

    @Override public String getIdentifier() { return id; }
    @Override public int getPosition() { return pos; }
    @Override public void reset() { pos = 0; }

    @Override
    public void walk() {
        // acelera muito, mas às vezes perde estabilidade
        pos += (rnd.nextBoolean() ? 20 : 8);
    }
}
