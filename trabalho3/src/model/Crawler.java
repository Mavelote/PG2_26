package model;

public class Crawler implements Racer {

    private final String id;
    private int position = 0;

    public Crawler(String id) {
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
        position += 14;
    }
}
