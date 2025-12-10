package view;

import model.Racer;

/**
 * Represents the graphical user interface of the racing game
 * (View component in simplified MVC architecture).
 * This interface defines the operations that a visual component
 * (such as a JFrame) must implement to interact with the model
 * and update the game display.
 */
public interface RaceView {

    /**
     * Registers the listener responsible for handling the "Start" action.
     * Connected to a button that begins the race.
     */
    void startListener();

    /**
     * Registers the listener responsible for handling race progression steps.
     * Triggered by a timer that advances the race one tick.
     */
    void stepListener();

    /**
     * Registers the listener that handles displaying the top-ten players'
     * statistics of leaderboard.
     */
    void topTenListener();

    /**
     * Updates the on-screen fields with the winner of the race, as well
     * as other relevant game statistics.
     *
     * @param winner the racer who won the current race
     */
    void updateStatsFields(Racer winner);
}

