package view;

import race.model.Racer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RacePanel extends JPanel {

    int SPACING = 50;
    private final int BASE_Y = SPACING/2;
    private final int BASE_X = 20;
    private final int LABEL_HEIGHT = 25;
    private final int LABEL_WIDTH = 50;

    private ArrayList<JLabel> labels = new ArrayList<>();


    public RacePanel(Iterable<Racer> competitors, int width) {
        setLayout(null);

        for (Racer c : competitors) {
            JLabel lbl = new JLabel(c.getIdentifier().substring(0,2));
            lbl.setBounds(width+2*BASE_X, BASE_Y + labels.size()*SPACING, LABEL_WIDTH, LABEL_HEIGHT);            labels.add(lbl);
            add(lbl);
            lbl.setFont(getFont().deriveFont(Font.BOLD, 32f));
        }

        setPreferredSize(new Dimension(width+2*BASE_X+LABEL_WIDTH, SPACING*(labels.size()+1)));
    }

    public void updatePositions(Iterable<Racer> competitors) {
        int index=0;
        for (Racer c: competitors) {
            labels.get(index).setLocation(getWidth()-LABEL_WIDTH - c.getPosition(),
                    BASE_Y + index * SPACING);
            ++index;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Linha de chegada
        g2.setColor(Color.RED);
        g2.drawLine(LABEL_WIDTH, BASE_Y, LABEL_WIDTH,
                 (labels.size()+1) * SPACING - BASE_Y);

        // Linhas entre os corredores (faixas)
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < labels.size(); i++) {
            int y = BASE_Y + i * SPACING + SPACING / 2;
            g2.drawLine(0, y, getWidth(), y);
        }
    }
}
