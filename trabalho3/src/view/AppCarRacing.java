package view;

import model.*;

import javax.swing.*;

public class AppCarRacing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog(null, "Nome do jogador:");
            if (playerName == null || playerName.isBlank()) playerName = "Anon";

            RaceModel game = new RaceGame(playerName, 800,
                    new Sprinter("🚗 sprinter"),
                    new Crawler("🚙 crawler"),
                    new Robot("🤖 robot")
            );

            new RaceFrame(game).setVisible(true);
        });
    }
}
