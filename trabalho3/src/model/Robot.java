package model;

import java.util.Random;

public class Robot implements Racer {

    private final String id;
    private int position = 0;
    private final Random rnd = new Random();

    public Robot(String id) {
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
        int r = rnd.nextInt(4);
        position += (r == 0) ? 0 : (r == 3) ? 28 : 14;
    }
}
