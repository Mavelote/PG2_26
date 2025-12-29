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
        return new ArrayList<Statistics>(allStats.values());
    }


    @Override
    public Iterable<Statistics> getTopTen() {
        List<PlayerStatistics> list = new ArrayList<>(allStats.values());

        list.sort(
                Comparator.comparingInt(PlayerStatistics::percentageCorrectBets).reversed()
                        .thenComparing(Comparator.comparingInt(PlayerStatistics::getTotalRaces).reversed())
                        .thenComparing(PlayerStatistics::getPlayerName, String.CASE_INSENSITIVE_ORDER)
        );

        int end = Math.min(10, list.size());
        List<Statistics> top = new ArrayList<>(end);
        for (int i = 0; i < end; i++) {
            top.add(list.get(i)); // upcast para Statistics
        }
        return top;
    }


    @Override
    public void start(String bet) {
        currentBet = bet == null ? betDefault : bet;
        for (Racer r : racers) r.reset();
    }

    @Override
    public Racer step() {
        // 1) todos andam primeiro
        for (Racer r : racers) {
            r.walk();
        }

        // 2) ver quem chegou e escolher o que ficou mais à frente
        Racer best = null;
        for (Racer r : racers) {
            if (r.getPosition() >= finishLine) {
                if (best == null || r.getPosition() > best.getPosition()) {
                    best = r;
                }
            }
        }

        if (best == null) return null;

        // 3) empate (mesma posição) -> escolhe aleatoriamente
        int bestPos = best.getPosition();
        List<Racer> tied = new ArrayList<>();
        for (Racer r : racers) {
            if (r.getPosition() >= finishLine && r.getPosition() == bestPos) {
                tied.add(r);
            }
        }
        Racer winner = tied.get(new Random().nextInt(tied.size()));

        // 4) atualizar stats (no Model, como o enunciado pede)
        playerStats.finishRace(winner.getIdentifier(), currentBet);

        return winner;
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
