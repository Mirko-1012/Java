import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(ConfiguratorUI::new);
    }
}
