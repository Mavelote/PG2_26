package model;

import java.util.Random;

public class Robot implements Racer {
    private final String id;
    private int pos = 0;
    private final Random rnd = new Random();

    public Robot(String id) { this.id = id; }

    @Override public String getIdentifier() { return id; }
    @Override public int getPosition() { return pos; }
    @Override public void reset() { pos = 0; }

    @Override
    public void walk() {
        // irregular: alterna avanços grandes com hesitações
        int r = rnd.nextInt(100);
        if (r < 25) pos += 0;          // hesita
        else if (r < 60) pos += 10;    // normal
        else pos += 25;               // rápido
    }
}
