package view;

import model.Racer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RacePanel extends JPanel {

    private static final int SPACING = 50;
    private static final int BASE_X = 20;

    private final List<JLabel> labels = new ArrayList<>();
    private final int finishLine;

    public RacePanel(Iterable<Racer> racers, int finishLine) {
        this.finishLine = finishLine;
        setLayout(null);

        int i = 0;
        for (Racer r : racers) {
            JLabel lbl = new JLabel(r.getIdentifier());
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 24f));
            lbl.setBounds(BASE_X + finishLine, 20 + i * SPACING, 150, 30);
            labels.add(lbl);
            add(lbl);
            i++;
        }

        setPreferredSize(new Dimension(finishLine + 200, i * SPACING + 40));
    }

    public void updatePositions(Iterable<Racer> racers, int finishLine) {
        int i = 0;
        for (Racer r : racers) {
            labels.get(i).setLocation(BASE_X + finishLine - r.getPosition(), 20 + i * SPACING);
            i++;
        }
        repaint();
    }
}
