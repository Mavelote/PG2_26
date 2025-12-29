package model;

public class Crawler implements Racer {
    private final String id;
    private int pos = 0;

    public Crawler(String id) { this.id = id; }

    @Override public String getIdentifier() { return id; }
    @Override public int getPosition() { return pos; }
    @Override public void reset() { pos = 0; }

    @Override
    public void walk() {
        // devagar mas constante
        pos += 8;
    }
}

