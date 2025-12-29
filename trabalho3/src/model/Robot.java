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
        int r = rnd.nextInt(4);
        // valores: 0, 14, 14, 28  => média = 14
        pos += (r == 0) ? 0 : (r == 3) ? 28 : 14;
    }
}
