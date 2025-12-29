package model;

import java.io.*;
import java.util.*;

public class RaceGame implements RaceModel {

    private final int finishLine;
    private final List<Racer> racers = new ArrayList<>();
    private final String betDefault = "";
    private String currentBet = betDefault;

    private final File statsFile = new File("race_stats.dat");

    private final Map<String, PlayerStatistics> allStats = new HashMap<>();
    private final PlayerStatistics playerStats;

    public RaceGame(String playerName, int finishLine, Racer... racers) {
        this.finishLine = finishLine;
        this.racers.addAll(Arrays.asList(racers));

        loadFromDisk();

        playerStats = allStats.computeIfAbsent(playerName, PlayerStatistics::new);
    }

    @Override public int getFinishLine() { return finishLine; }

    @Override public Statistics getPlayerStatistics() { return playerStats; }

    @Override
    public Iterable<Racer> getRacers() {
        return Collections.unmodifiableList(racers);
    }

    @Override
    public Iterable<Statistics> getAllStatistics() {
        return new ArrayList<>(allStats.values());
    }

    @Override
    public Iterable<Statistics> getTopTen() {
        List<PlayerStatistics> list = new ArrayList<>(allStats.values());
        list.sort(Comparator
                .comparingInt(PlayerStatistics::percentageCorrectBets).reversed()
                .thenComparingInt(PlayerStatistics::getTotalRaces).reversed()
                .thenComparing(PlayerStatistics::getPlayerName));
        return list.subList(0, Math.min(10, list.size()));
    }

    @Override
    public void start(String bet) {
        currentBet = bet == null ? betDefault : bet;
        for (Racer r : racers) r.reset();
    }

    @Override
    public Racer step() {
        for (Racer r : racers) {
            r.walk();
            if (r.getPosition() >= finishLine) {
                // corrida terminou
                playerStats.finishRace(r.getIdentifier(), currentBet);
                return r;
            }
        }
        return null;
    }

    @Override
    public boolean saveStatistics() {
        return saveToDisk();
    }

    // ---------- Persistência (simples e suficiente) ----------

    @SuppressWarnings("unchecked")
    private void loadFromDisk() {
        if (!statsFile.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(statsFile))) {
            Object obj = in.readObject();
            if (obj instanceof Map<?, ?> map) {
                allStats.clear();
                allStats.putAll((Map<String, PlayerStatistics>) map);
            }
        } catch (Exception ignored) {
            // se falhar, começa vazio
        }
    }

    private boolean saveToDisk() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(statsFile))) {
            out.writeObject(allStats);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
