import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SoccerManager window = new SoccerManager();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}