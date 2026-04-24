import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ParkingUI {

    // ── colori ───────────────────────────────────────────────
    private static final Color BG          = new Color(245, 245, 240);
    private static final Color CARD_BG     = Color.WHITE;
    private static final Color FREE_BG     = new Color(234, 243, 222);
    private static final Color FREE_BORDER = new Color(59,  109, 17);
    private static final Color OCC_BG      = new Color(252, 235, 235);
    private static final Color OCC_BORDER  = new Color(162, 45,  45);
    private static final Color ACCENT      = new Color(59,  109, 17);
    private static final Color TEXT_DARK   = new Color(30,  30,  30);
    private static final Color TEXT_MUTED  = new Color(110, 110, 100);
    private static final Color MSG_OK_FG   = new Color(39,  80,  10);
    private static final Color MSG_ERR_FG  = new Color(121, 31,  31);

    // ── stato ────────────────────────────────────────────────
    private final Parking     parking = new Parking();
    private JPanel[]          spotPanels;
    private JLabel            lblOcc, lblFree, lblFull;
    private JComboBox<String> cmbPlace;
    private JTextField        txtPlate;
    private JLabel            lblMsg;
    private Timer             msgTimer;

    // ── entry point chiamato da Main ─────────────────────────
    public void show() {
        JFrame frame = new JFrame("Gestione Parcheggio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(680, 620);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(20, 24, 20, 24));

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildForm(),   BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setVisible(true);
        refreshAll();
    }

    // ── HEADER con contatori ─────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel title = new JLabel("Parcheggio");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);

        lblOcc  = valueLabel("0");
        lblFree = valueLabel("10");
        lblFull = valueLabel("Aperto");

        JPanel stats = new JPanel(new GridLayout(1, 3, 10, 0));
        stats.setOpaque(false);
        stats.add(wrapStat("Occupati", lblOcc));
        stats.add(wrapStat("Liberi",   lblFree));
        stats.add(wrapStat("Stato",    lblFull));

        p.add(title, BorderLayout.WEST);
        p.add(stats, BorderLayout.EAST);
        return p;
    }

    private JLabel valueLabel(String val) {
        JLabel l = new JLabel(val, SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JPanel wrapStat(String label, JLabel valLabel) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 2));
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 190), 1, true),
            new EmptyBorder(6, 14, 6, 14)
        ));
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(TEXT_MUTED);
        p.add(lbl);
        p.add(valLabel);
        return p;
    }

    // ── GRIGLIA 2×5 dei posti ────────────────────────────────
    private JPanel buildCenter() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel sec = new JLabel("MAPPA PARCHEGGIO");
        sec.setFont(new Font("SansSerif", Font.BOLD, 11));
        sec.setForeground(TEXT_MUTED);
        sec.setBorder(new EmptyBorder(0, 0, 8, 0));
        outer.add(sec, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 5, 10, 10));
        grid.setOpaque(false);
        spotPanels = new JPanel[parking.getSize()];

        for (int i = 0; i < parking.getSize(); i++) {
            spotPanels[i] = new JPanel(new GridBagLayout());
            spotPanels[i].setBackground(FREE_BG);
            spotPanels[i].setBorder(new LineBorder(FREE_BORDER, 1, true));
            spotPanels[i].setPreferredSize(new Dimension(110, 90));
            grid.add(spotPanels[i]);
        }
        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    private void updateSpot(int idx) {
        JPanel p = spotPanels[idx];
        p.removeAll();
        Car car = parking.getCarAt(idx);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(2, 4, 2, 4);

        JLabel numLbl = new JLabel("Posto " + idx);
        numLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
        numLbl.setForeground(TEXT_MUTED);

        if (car == null) {
            p.setBackground(FREE_BG);
            p.setBorder(new LineBorder(FREE_BORDER, 1, true));

            JLabel icon = new JLabel("P");
            icon.setFont(new Font("SansSerif", Font.BOLD, 22));
            icon.setForeground(new Color(150, 170, 130));

            JLabel freeLbl = new JLabel("Libero");
            freeLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
            freeLbl.setForeground(new Color(90, 120, 60));

            p.add(numLbl, c);
            p.add(icon,   c);
            p.add(freeLbl, c);
        } else {
            p.setBackground(OCC_BG);
            p.setBorder(new LineBorder(OCC_BORDER, 1, true));

            JLabel icon = new JLabel("🚗");
            icon.setFont(new Font("SansSerif", Font.PLAIN, 18));

            JLabel plate = new JLabel(car.getPlate());
            plate.setFont(new Font("Monospaced", Font.BOLD, 12));
            plate.setForeground(new Color(100, 20, 20));

            JButton btn = new JButton("Rimuovi");
            btn.setFont(new Font("SansSerif", Font.PLAIN, 10));
            btn.setForeground(OCC_BORDER);
            btn.setBackground(OCC_BG);
            btn.setBorder(new LineBorder(OCC_BORDER, 1, true));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            final int i = idx;
            btn.addActionListener(e -> removeCar(i));

            p.add(numLbl, c);
            p.add(icon,   c);
            p.add(plate,  c);
            c.insets = new Insets(4, 4, 2, 4);
            p.add(btn, c);
        }
        p.revalidate();
        p.repaint();
    }

    // ── FORM aggiunta veicolo ────────────────────────────────
    private JPanel buildForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(210, 210, 200));
        outer.add(sep, BorderLayout.NORTH);

        JPanel inner = new JPanel(new BorderLayout(0, 10));
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(14, 0, 0, 0));

        JLabel sec = new JLabel("AGGIUNGI VEICOLO");
        sec.setFont(new Font("SansSerif", Font.BOLD, 11));
        sec.setForeground(TEXT_MUTED);
        inner.add(sec, BorderLayout.NORTH);

        txtPlate = new JTextField();
        txtPlate.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtPlate.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 190), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtPlate.setToolTipText("Inserisci la targa (es. AA123BB)");
        txtPlate.addActionListener(e -> addCar());

        cmbPlace = new JComboBox<>();
        cmbPlace.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cmbPlace.setBackground(CARD_BG);

        JButton btnAdd = new JButton("Aggiungi");
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAdd.setBackground(ACCENT);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorder(new EmptyBorder(8, 18, 8, 18));
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> addCar());

        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 8);
        c.weightx = 0.4; c.gridx = 0; row.add(txtPlate, c);
        c.weightx = 0.4; c.gridx = 1; row.add(cmbPlace, c);
        c.weightx = 0;   c.gridx = 2; c.insets = new Insets(0, 0, 0, 0);
        row.add(btnAdd, c);
        inner.add(row, BorderLayout.CENTER);

        lblMsg = new JLabel(" ");
        lblMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMsg.setBorder(new EmptyBorder(6, 0, 0, 0));
        inner.add(lblMsg, BorderLayout.SOUTH);

        outer.add(inner, BorderLayout.CENTER);
        return outer;
    }

    // ── LOGICA ───────────────────────────────────────────────
    private void addCar() {
        String plate = txtPlate.getText().trim().toUpperCase();
        if (plate.isEmpty()) { showMsg("Inserisci una targa valida.", false); return; }

        Object sel = cmbPlace.getSelectedItem();
        if (sel == null || sel.toString().startsWith("—")) {
            showMsg("Seleziona un posto libero.", false); return;
        }
        int place = Integer.parseInt(sel.toString().replace("Posto ", ""));

        Car car = new Car(plate);
        if (parking.contains(car))         { showMsg("La targa " + plate + " è già presente.", false); return; }
        if (parking.getCarAt(place) != null) { showMsg("Posto già occupato.", false); return; }
        if (parking.isFull())               { showMsg("Parcheggio pieno!", false); return; }

        parking.addCar(car, place);
        txtPlate.setText("");
        showMsg("Veicolo " + plate + " aggiunto al posto " + place + ".", true);
        refreshAll();
    }

    private void removeCar(int place) {
        Car car = parking.getCarAt(place);
        String plate = (car != null) ? car.getPlate() : "?";
        parking.removeCar(place);
        showMsg("Veicolo " + plate + " rimosso dal posto " + place + ".", true);
        refreshAll();
    }

    private void refreshAll() {
        for (int i = 0; i < parking.getSize(); i++) updateSpot(i);
        lblOcc.setText(String.valueOf(parking.occupatedPlaces()));
        lblFree.setText(String.valueOf(parking.availablePlaces()));
        lblFull.setText(parking.isFull() ? "Pieno" : "Aperto");
        lblFull.setForeground(parking.isFull() ? OCC_BORDER : ACCENT);

        cmbPlace.removeAllItems();
        cmbPlace.addItem("— Scegli posto —");
        for (int i = 0; i < parking.getSize(); i++) {
            if (parking.getCarAt(i) == null) cmbPlace.addItem("Posto " + i);
        }
    }

    private void showMsg(String text, boolean ok) {
        lblMsg.setText(text);
        lblMsg.setForeground(ok ? MSG_OK_FG : MSG_ERR_FG);
        if (msgTimer != null && msgTimer.isRunning()) msgTimer.stop();
        msgTimer = new Timer(3500, e -> lblMsg.setText(" "));
        msgTimer.setRepeats(false);
        msgTimer.start();
    }
}