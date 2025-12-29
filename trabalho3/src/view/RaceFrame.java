package view;

import model.RaceModel;
import model.Racer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RaceFrame extends JFrame implements RaceView {

    private final RaceModel model;

    private final JComboBox<String> betCombo = new JComboBox<>();
    private final JButton startBtn = new JButton("Start Race");
    private final JButton topTenBtn = new JButton("Top Ten");

    private final JLabel totalRacesLbl = new JLabel("0");
    private final JLabel correctBetsLbl = new JLabel("0");
    private final JLabel pctLbl = new JLabel("0%");

    private final RacePanel racePanel;

    private Timer timer = null;

    public RaceFrame(RaceModel model) {
        super("Corrida Improvável");
        this.model = model;

        // topo (controles)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Aposta em:"));
        for (Racer r : model.getRacers()) betCombo.addItem(r.getIdentifier());
        top.add(betCombo);
        top.add(startBtn);
        top.add(topTenBtn);

        // stats
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stats.add(new JLabel("Corridas:")); stats.add(totalRacesLbl);
        stats.add(new JLabel("Certas:")); stats.add(correctBetsLbl);
        stats.add(new JLabel("%:")); stats.add(pctLbl);

        // pista
        racePanel = new RacePanel(model.getRacers(), model.getFinishLine());

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(stats, BorderLayout.SOUTH);
        add(new JScrollPane(racePanel), BorderLayout.CENTER);

        // timer (event-driven)
       timer = new Timer(80, e -> {
            Racer winner = model.step();
            racePanel.updatePositions(model.getRacers(), model.getFinishLine());
            if (winner != null) {
                timer.stop();
                updateStatsFields(winner);
                JOptionPane.showMessageDialog(this,
                        "Vencedor: " + winner.getIdentifier(),
                        "Resultado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        startListener();
        topTenListener();

        updateStatsUI();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);

        // guardar stats ao sair
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                model.saveStatistics();
            }
        });
    }

    private void updateStatsUI() {
        var s = model.getPlayerStatistics();
        totalRacesLbl.setText(String.valueOf(s.getTotalRaces()));
        correctBetsLbl.setText(String.valueOf(s.getCorrectBets()));
        pctLbl.setText(s.percentageCorrectBets() + "%");
    }

    @Override
    public void startListener() {
        startBtn.addActionListener(e -> {
            String bet = (String) betCombo.getSelectedItem();
            model.start(bet);
            racePanel.updatePositions(model.getRacers(), model.getFinishLine());
            timer.start();
        });
    }

    // Nota: no teu interface RaceView existe "stepListener()".
    // Aqui o step é automático (Timer). Podes deixar vazio ou ligar a um botão se existir.
    @Override
    public void stepListener() {}

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
        updateStatsUI();
    }
}
