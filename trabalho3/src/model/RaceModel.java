package model;

import java.io.Serializable;
import java.util.Map;

/**
 * The RaceModel interface defines the core logic of the "Car Race" game.
 * It represents the Model component in a simplified MVC architecture.
 *
 * This interface manages the racers, game progression, player statistics,
 * and persistence of race results.
 */
public interface RaceModel {

    /**
     * Represents the statistics of a single player, including race history,
     * bet accuracy, and victories per racer.
     */
    public interface Statistics extends Serializable {

        /**
         * @return the name of the player associated with these statistics
         */
        String getPlayerName();

        /**
         * @return the total number of races played by the player
         */
        int getTotalRaces();

        /**
         * @return the number of races in which the player's bet was correct
         */
        int getCorrectBets();

        /**
         * Returns a map storing how many times each racer has won.
         * The key is the racer's identifier, and the value is the victory count.
          * @return a map of racer identifiers to their number of victories
         */
        Map<String, Integer> getVictories();

        /**
         * Updates the player's statistics after a race ends.
         * @param winner the identifier of the racer that won the race
         * @param bet the identifier of the racer the player bet on
         */
        void finishRace(String winner, String bet);

        /**
         * Calculates the player's percentage of correct bets.
         *
         * @return the percentage (0–100) of correct bets
         */
        int percentageCorrectBets();
    }

    /**
     * @return the position of the finish line that racers must reach
     */
    int getFinishLine();

    /**
     * @return the statistics of the currently active player
     */
    Statistics getPlayerStatistics();

    /**
     * @return an iterable collection of the racers participating in the race
     */
    Iterable<Racer> getRacers();

    /**
     * @return statistics of all recorded players
     */
    Iterable<Statistics> getAllStatistics();

    /**
     * @return statistics of the top ten players ranked by performance
     */
    Iterable<Statistics> getTopTen();

    /**
     * Starts a new race. All racers are reset to the initial position,
     * and the player’s bet is registered.
     *
     * @param bet the identifier of the racer the player is betting on
     */
    void start(String bet);

    /**
     * Executes a single step (tick) of the race.
     * Each racer performs its movement according to its own behavior
     * (implemented through polymorphism in the walk() method).
      * @return the racer that has crossed the finish line, if any;
     *         otherwise returns null
     */
    Racer step();

    /**
     * Saves all player statistics to persistent storage.
      * @return true if the save operation was successful; false otherwise
     */
    boolean saveStatistics();
}
