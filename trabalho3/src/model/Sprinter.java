package model;

import java.util.Random;

public class Sprinter implements Racer {

    private final String id;
    private int position = 0;
    private final Random rnd = new Random();

    public Sprinter(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void reset() {
        position = 0;
    }

    @Override
    public void walk() {
        position += rnd.nextBoolean() ? 20 : 8; // média equilibrada
    }
}
