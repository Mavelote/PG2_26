package model;

/**
 * Represents a racer in the racing game.
 *
 * Each racer has an identifier and a position on the track.
 * Different types of racers implement their own movement behavior
 * using polymorphism (walk method).
 */
public interface Racer {
    /**
     * Returns the unique identifier of the racer.
     *
     * @return the racer's identifier
     */
    String getIdentifier();

    /**
     * Returns the current position of the racer on the track.
     *
     * @return the current position
     */
    int getPosition();

    /**
     * Resets the racer's position to the start line.
     */
    void reset();

    /**
     * Moves the racer forward according to its specific behavior.
     * This method should be overridden by different racer types.
     */
    void walk();
}

