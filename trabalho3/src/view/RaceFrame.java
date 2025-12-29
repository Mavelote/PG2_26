package view;

import model.RaceModel;
import model.Racer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class RaceFrame extends JFrame implements RaceView {

    private final RaceModel model;

    private final JComboBox<String> betCombo = new JComboBox<>();
    private final JButton startBtn = new JButton("Start Race");
    private final JButton topTenBtn = new JButton("Top Ten");

    // Bets values
    private final JLabel totalRacesVal = bigValueLabel("0");
    private final JLabel correctBetsVal = bigValueLabel("0");

    // Wins values (local, no changes in Model)
    private final Map<String, Integer> winsCount = new LinkedHashMap<>();
    private final Map<String, JLabel> winsValues = new LinkedHashMap<>();

    private final RacePanel racePanel;
    private Timer timer = null;

    public RaceFrame(RaceModel model) {
        super("Car racing");
        this.model = model;

        setLayout(new BorderLayout());

        // ---------- TOP ----------
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Racer r : model.getRacers()) betCombo.addItem(r.getIdentifier());
        top.add(betCombo);
        top.add(startBtn);
        top.add(topTenBtn);
        add(top, BorderLayout.NORTH);

        // ---------- CENTER ----------
        racePanel = new RacePanel(model.getRacers(), model.getFinishLine());
        add(new JScrollPane(racePanel), BorderLayout.CENTER);

        // ---------- SOUTH (boxes bottom-right) ----------
        JPanel betsBox = createBetsBox();            // contains 2 small panels
        JPanel winsBox = createWinCountersBox();     // contains 3 small panels

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        bottomRight.add(betsBox);
        bottomRight.add(winsBox);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        bottom.add(bottomRight, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // ---------- TIMER ----------
         timer = new Timer(80, e -> {
            Racer winner = model.step();
            racePanel.updatePositions(model.getRacers(), model.getFinishLine());

            if (winner != null) {
                timer.stop();

                updateBetsUI();
                updateWinsUI();

                JOptionPane.showMessageDialog(this,
                        "Winner: " + winner.getIdentifier(),
                        "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ---------- LISTENERS ----------
        startListener();
        topTenListener();

        // initial UI
        updateBetsUI();
        updateWinsUI();
        racePanel.updatePositions(model.getRacers(), model.getFinishLine());

        // close + save
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                model.saveStatistics();
            }
        });

        setSize(1000, 520);
        setLocationRelativeTo(null);
    }

    // =========================
    //   BOXES (nested panels)
    // =========================

    private JPanel createBetsBox() {
        JPanel box = titledBox("bets");
        box.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));

        box.add(statCard("races", totalRacesVal));
        box.add(statCard("correct", correctBetsVal));

        return box;
    }

    private JPanel createWinCountersBox() {
        JPanel box = titledBox("Win Counters");
        box.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));

        for (Racer r : model.getRacers()) {
            String id = r.getIdentifier();

            JLabel val = bigValueLabel("0"); // valor inicial provisório
            winsValues.put(id, val);

            box.add(statCard(id, val));
        }
        return box;
    }

    private static JPanel titledBox(String title) {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder(title));
        return p;
    }

    private static JPanel statCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JLabel t = new JLabel(title);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(t);
        card.add(Box.createVerticalStrut(4));
        card.add(valueLabel);

        return card;
    }

    private static JLabel bigValueLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 20f));
        return l;
    }

    // =========================
    //   UI UPDATE
    // =========================

    private void updateBetsUI() {
        var s = model.getPlayerStatistics();
        totalRacesVal.setText(String.valueOf(s.getTotalRaces()));
        correctBetsVal.setText(String.valueOf(s.getCorrectBets()));
    }
    private void updateWinsUI() {
        var wins = model.getPlayerStatistics().getVictories();

        for (var entry : winsValues.entrySet()) {
            String id = entry.getKey();
            int v = wins.getOrDefault(id, 0);
            entry.getValue().setText(String.valueOf(v));
        }
    }



    // =========================
    //   LISTENERS (RaceView)
    // =========================

    @Override
    public void startListener() {
        startBtn.addActionListener(e -> {
            String bet = (String) betCombo.getSelectedItem();
            model.start(bet);

            racePanel.updatePositions(model.getRacers(), model.getFinishLine());
            timer.start();
        });
    }

    @Override
    public void stepListener() {
        // not used (Timer drives the race)
    }

    @Override
    public void topTenListener() {
        topTenBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("TOP 10 (por % apostas certas)\n\n");
            int i = 1;
            for (var st : model.getTopTen()) {
                sb.append(i++)
                        .append(". ")
                        .append(st.getPlayerName())
                        .append(" - ")
                        .append(st.percentageCorrectBets())
                        .append("% (")
                        .append(st.getCorrectBets())
                        .append("/")
                        .append(st.getTotalRaces())
                        .append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Top Ten", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void updateStatsFields(Racer winner) {
        updateBetsUI();
        updateWinsUI();
    }
}
