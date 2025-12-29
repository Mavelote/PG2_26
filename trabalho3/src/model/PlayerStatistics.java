package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlayerStatistics implements RaceModel.Statistics, Serializable {

    private final String playerName;
    private int totalRaces = 0;
    private int correctBets = 0;

    // key = identifier do Racer (ex: "sprinter", "crawler", "robot")
    private final Map<String, Integer> victories = new HashMap<>();

    public PlayerStatistics(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getTotalRaces() {
        return totalRaces;
    }

    @Override
    public int getCorrectBets() {
        return correctBets;
    }

    @Override
    public Map<String, Integer> getVictories() {
        return victories;
    }

    @Override
    public void finishRace(String winner, String bet) {
        totalRaces++;

        if (winner != null) {
            victories.put(winner, victories.getOrDefault(winner, 0) + 1);
            if (winner.equals(bet)) {
                correctBets++;
            }
        }
    }

    @Override
    public int percentageCorrectBets() {
        if (totalRaces == 0) return 0;
        return (int) Math.round(100.0 * correctBets / totalRaces);
    }
}
