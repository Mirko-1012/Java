import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ParkingUI extends JFrame {

    // ── Palette ──────────────────────────────────────────────────────────────
    private static final Color BG_DARK    = new Color(0x0D0F14);
    private static final Color BG_CARD    = new Color(0x161A23);
    private static final Color BG_INPUT   = new Color(0x1E2330);
    private static final Color ACCENT     = new Color(0x39FF9A);
    private static final Color ACCENT_DIM = new Color(0x1A7A47);
    private static final Color FG_PRIMARY = new Color(0xEAEFF8);
    private static final Color FG_MUTED   = new Color(0x6B7591);
    private static final Color ROW_ODD    = new Color(0x161A23);
    private static final Color ROW_EVEN   = new Color(0x1B2030);
    private static final Color SEL_BG     = new Color(0x1A3A2A);
    private static final Color EXIT_RED   = new Color(0xFF4D6A);
    private static final Color EXIT_DIM   = new Color(0x7A2030);
    private static final Color BORDER_CLR = new Color(0x2A3045);

    private static final Font FONT_MONO   = new Font("Monospaced", Font.PLAIN, 13);
    private static final Font FONT_MONO_B = new Font("Monospaced", Font.BOLD, 13);
    private static final Font FONT_LABEL  = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font FONT_TITLE  = new Font("SansSerif", Font.BOLD, 22);
    private static final Font FONT_STAT   = new Font("Monospaced", Font.BOLD, 26);
    private static final Font FONT_STAT_S = new Font("SansSerif", Font.PLAIN, 10);

    // ── State ─────────────────────────────────────────────────────────────────
    private Parking parking;
    private DefaultTableModel tableModel;
    private JLabel lblFreePlaces, lblAmount;
    private JTextField tfPlate, tfSpot, tfExitPlate;
    private JComboBox<String> cbType;

    // ══════════════════════════════════════════════════════════════════════════
    public ParkingUI() {
        parking = new Parking();
        setTitle("PARKING MANAGER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 640);
        setMinimumSize(new Dimension(700, 560));
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setContentPane(root);

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildCenter(),  BorderLayout.CENTER);
        root.add(buildActions(), BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(BG_DARK);

        JLabel title = new JLabel("● PARKING MANAGER");
        title.setFont(FONT_TITLE);
        title.setForeground(FG_PRIMARY);

        JLabel sub = new JLabel("sistema di gestione parcheggio");
        sub.setFont(FONT_LABEL);
        sub.setForeground(FG_MUTED);

        left.add(title);
        left.add(Box.createVerticalStrut(2));
        left.add(sub);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        stats.setBackground(BG_DARK);
        stats.add(buildStatCard("POSTI LIBERI", "-", true));
        stats.add(buildStatCard("INCASSO €", "-", false));

        p.add(left, BorderLayout.WEST);
        p.add(stats, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_DARK);
        wrapper.add(p, BorderLayout.CENTER);
        wrapper.add(buildDivider(), BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel buildStatCard(String label, String value, boolean isPlaces) {
        JPanel card = new RoundPanel(12, BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_STAT_S);
        lbl.setForeground(FG_MUTED);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(FONT_STAT);
        val.setForeground(ACCENT);
        val.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lbl);
        card.add(Box.createVerticalStrut(4));
        card.add(val);

        if (isPlaces) lblFreePlaces = val;
        else          lblAmount     = val;

        return card;
    }

    // ── Table area ────────────────────────────────────────────────────────────
    private JPanel buildCenter() {
        String[] cols = {"  TARGA", "  TIPO", "  POSTO", "  STATO", "  ORARIO INGRESSO"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setBackground(BG_CARD);
        table.setForeground(FG_PRIMARY);
        table.setFont(FONT_MONO);
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(SEL_BG);
        table.setSelectionForeground(ACCENT);

        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_DARK);
        header.setForeground(FG_MUTED);
        header.setFont(new Font("SansSerif", Font.BOLD, 10));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_CLR));
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, value, sel, foc, row, col);
                setBackground(sel ? SEL_BG : (row % 2 == 0 ? ROW_EVEN : ROW_ODD));
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                if (col == 0) { setFont(FONT_MONO_B); setForeground(sel ? ACCENT : FG_PRIMARY); }
                else if (col == 3) {
                    boolean inSosta = "In sosta".equals(value);
                    setForeground(sel ? ACCENT : (inSosta ? ACCENT : EXIT_RED));
                    setFont(FONT_MONO);
                } else {
                    setForeground(sel ? ACCENT : FG_MUTED);
                    setFont(FONT_MONO);
                }
                return this;
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(ROW_ODD);
        scroll.setBackground(BG_DARK);

        JPanel card = new RoundPanel(14, BG_CARD);
        card.setLayout(new BorderLayout());
        card.add(scroll, BorderLayout.CENTER);

        JLabel sec = new JLabel("  VEICOLI NEL PARCHEGGIO");
        sec.setFont(new Font("SansSerif", Font.BOLD, 10));
        sec.setForeground(FG_MUTED);
        sec.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_CLR),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        sec.setBackground(BG_CARD);
        sec.setOpaque(true);
        card.add(sec, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_DARK);
        wrapper.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    // ── Form panel ────────────────────────────────────────────────────────────
    private JPanel buildActions() {
        JPanel p = new JPanel(new GridLayout(1, 2, 14, 0));
        p.setBackground(BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        p.add(buildEntryCard());
        p.add(buildExitCard());
        return p;
    }

    private JPanel buildEntryCard() {
        RoundPanel card = new RoundPanel(14, BG_CARD);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill   = GridBagConstraints.HORIZONTAL;

        JLabel title = sectionLabel("↓  ENTRATA");
        g.gridx=0; g.gridy=0; g.gridwidth=2; card.add(title, g);

        g.gridwidth=1;
        g.gridx=0; g.gridy=1; card.add(fieldLabel("TARGA"), g);
        tfPlate = styledField(); g.gridx=1; card.add(tfPlate, g);

        g.gridx=0; g.gridy=2; card.add(fieldLabel("TIPO"), g);
        cbType = styledCombo(new String[]{"Auto", "Camion"});
        g.gridx=1; card.add(cbType, g);

        g.gridx=0; g.gridy=3; card.add(fieldLabel("POSTO N°"), g);
        tfSpot = styledField(); g.gridx=1; card.add(tfSpot, g);

        JButton btn = accentButton("PARCHEGGIA", ACCENT, ACCENT_DIM);
        g.gridx=0; g.gridy=4; g.gridwidth=2; card.add(btn, g);

        btn.addActionListener(e -> {
            String plate = tfPlate.getText().trim().toUpperCase();
            String spotS = tfSpot.getText().trim();
            if (plate.isEmpty() || spotS.isEmpty()) {
                flash(plate.isEmpty() ? tfPlate : tfSpot);
                showMsg("Inserisci targa e numero posto."); return;
            }
            try {
                int spot = Integer.parseInt(spotS);
                if (spot < 1 || spot > parking.places) {
                    showMsg("Posto non valido (1–" + parking.places + ")."); return;
                }
                String tipo = (String) cbType.getSelectedItem();
                Vehicle v = "Camion".equals(tipo) ? new Truck(plate) : new Car(plate);
                boolean ok = parking.addVehicle(v, spot);
                if (ok) { tfPlate.setText(""); tfSpot.setText(""); refresh(); }
                else    showMsg("Posto " + spot + " già occupato.");
            } catch (NumberFormatException ex) { showMsg("Il posto deve essere un numero."); }
        });
        return card;
    }

    private JPanel buildExitCard() {
        RoundPanel card = new RoundPanel(14, BG_CARD);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill   = GridBagConstraints.HORIZONTAL;

        JLabel title = sectionLabel("↑  USCITA");
        g.gridx=0; g.gridy=0; g.gridwidth=2; card.add(title, g);

        g.gridwidth=1;
        g.gridx=0; g.gridy=1; card.add(fieldLabel("TARGA"), g);
        tfExitPlate = styledField(); g.gridx=1; card.add(tfExitPlate, g);

        g.gridx=0; g.gridy=2; g.gridwidth=2;
        card.add(Box.createVerticalStrut(28), g);

        JButton btn = accentButton("FAI USCIRE", EXIT_RED, EXIT_DIM);
        g.gridx=0; g.gridy=3; card.add(btn, g);

        btn.addActionListener(e -> {
            String plate = tfExitPlate.getText().trim().toUpperCase();
            if (plate.isEmpty()) { showMsg("Inserisci la targa."); return; }
            parking.exitVehicle(plate);
            tfExitPlate.setText("");
            refresh();
        });
        return card;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void refresh() {
        tableModel.setRowCount(0);
        for (Stopover s : parking.Stopovers) {
            String stato = s.isCarIntoTheParking() ? "In sosta" : "Uscita";
            String tipo  = (s.getVehicle() instanceof Truck) ? "Camion" : "Auto";
            java.util.Date d = new java.util.Date(s.getStartTime());
            String ts = String.format("%tH:%tM:%tS", d, d, d);
            tableModel.addRow(new Object[]{
                "  " + s.getVehicle().getPlate(),
                "  " + tipo,
                "  " + s.getPosition(),
                stato,
                "  " + ts
            });
        }
        lblFreePlaces.setText(String.valueOf(parking.getAvailablePlaces()));
        lblAmount.setText(String.format("%.2f", parking.getAmount()));
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Avviso",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void flash(JComponent c) {
        Color orig = c.getBackground();
        c.setBackground(new Color(0x5A1A2A));
        Timer t = new Timer(300, ev -> c.setBackground(orig));
        t.setRepeats(false); t.start();
    }

    private JSeparator buildDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_CLR);
        sep.setBackground(BG_DARK);
        return sep;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(FG_MUTED);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        return l;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_STAT_S);
        l.setForeground(FG_MUTED);
        return l;
    }

    private JTextField styledField() {
        JTextField tf = new JTextField(10);
        tf.setBackground(BG_INPUT);
        tf.setForeground(FG_PRIMARY);
        tf.setFont(FONT_MONO);
        tf.setCaretColor(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_DIM, 1),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
            }
            public void focusLost(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_CLR, 1),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
            }
        });
        return tf;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setBackground(BG_INPUT);
        cb.setForeground(FG_PRIMARY);
        cb.setFont(FONT_MONO);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? SEL_BG : BG_INPUT);
                setForeground(isSelected ? ACCENT : FG_PRIMARY);
                setFont(FONT_MONO);
                setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
                return this;
            }
        });
        return cb;
    }

    private JButton accentButton(String text, Color fg, Color hover) {
        JButton btn = new JButton(text) {
            private Color current = BG_INPUT;
            { setContentAreaFilled(false); setFocusPainted(false); }
            @Override protected void paintComponent(Graphics g2) {
                Graphics2D g = (Graphics2D) g2.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(current);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g.setColor(fg);
                g.setStroke(new BasicStroke(1.5f));
                g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g.dispose();
                super.paintComponent(g2);
            }
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { current = hover; repaint(); }
                public void mouseExited(MouseEvent e)  { current = BG_INPUT; repaint(); }
            }); }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Rounded panel ─────────────────────────────────────────────────────────
    static class RoundPanel extends JPanel {
        private final int radius;
        private final Color bg;
        RoundPanel(int radius, Color bg) {
            this.radius = radius; this.bg = bg;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g0) {
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(bg);
            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g.dispose();
            super.paintComponent(g0);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(ParkingUI::new);
    }
}