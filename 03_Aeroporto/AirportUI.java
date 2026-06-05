import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AirportUI extends JFrame {

    // ── Palette ──────────────────────────────────────────────────────────────
    private static final Color BG_DARK    = new Color(0x0D1117);
    private static final Color BG_PANEL   = new Color(0x161B22);
    private static final Color BG_CARD    = new Color(0x1C2330);
    private static final Color ACCENT     = new Color(0x58A6FF);
    private static final Color ACCENT2    = new Color(0x3FB950);
    private static final Color CARGO_CLR  = new Color(0xF0883E);
    private static final Color TEXT_MAIN  = new Color(0xE6EDF3);
    private static final Color TEXT_DIM   = new Color(0x8B949E);
    private static final Color BORDER_CLR = new Color(0x30363D);
    private static final Color FIELD_BG   = new Color(0x0D1117);
    private static final Color ERROR_CLR  = new Color(0xFF6B6B);

    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final int    MAX_SEATS        = 853;
    private static final double MAX_CAPACITY_TON = 640.0;

    // ── Model ─────────────────────────────────────────────────────────────────
    // FIX: Airport ora ha un nome
    private final Airport airport = new Airport("Airport Manager");

    // ── Widgets ───────────────────────────────────────────────────────────────
    private final JTextField txtDeparture  = styledField("e.g.  Rome (FCO)");
    private final JTextField txtArrival    = styledField("e.g.  London (LHR)");

    // FIX: campi data/ora di partenza
    private final JTextField txtDepDate    = styledField("DD/MM/YYYY");
    private final JTextField txtDepTime    = styledField("HH:MM");

    // FIX: campi data/ora di ARRIVO — prima mancanti
    private final JTextField txtArrDate    = styledField("DD/MM/YYYY");
    private final JTextField txtArrTime    = styledField("HH:MM");

    private final JTextField txtCode       = styledField("e.g.  AZ123");
    private final JTextField txtModel      = styledField("e.g.  Airbus A320");
    private final JTextField txtExtra      = styledField("180");
    private final JLabel     lblExtraName  = dimLabel("Seats");

    private final JComboBox<String> cmbType =
            new JComboBox<>(new String[]{"✈  Airline", "📦  Cargo"});

    private final FlightListPanel flightListPanel = new FlightListPanel();
    private final JLabel lblCount = dimLabel("0 flights scheduled");

    public AirportUI() {
        super("Airport Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(920, 720);
        setMinimumSize(new Dimension(780, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(),    BorderLayout.CENTER);

        cmbType.addActionListener(e -> {
            boolean isCargo = cmbType.getSelectedIndex() == 1;
            lblExtraName.setText(isCargo ? "Capacity (t)" : "Seats");
            txtExtra.setText(isCargo ? "100.5" : "180");
        });
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x1C2330), getWidth(), 0, new Color(0x161B22));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p.setOpaque(false);
        p.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_CLR));
        p.setPreferredSize(new Dimension(0, 68));

        JLabel logo = new JLabel("✈  AIRPORT MANAGER");
        logo.setFont(new Font("Monospaced", Font.BOLD, 18));
        logo.setForeground(ACCENT);
        logo.setBorder(new EmptyBorder(0, 24, 0, 0));

        JLabel sub = new JLabel("Flight Scheduling System");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(TEXT_DIM);
        sub.setBorder(new EmptyBorder(0, 0, 0, 24));

        p.add(logo, BorderLayout.WEST);
        p.add(sub,  BorderLayout.EAST);
        return p;
    }

    // ── Sidebar (form) ────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG_PANEL);
        outer.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_CLR));
        outer.setPreferredSize(new Dimension(300, 0));

        JPanel form = new JPanel();
        form.setBackground(BG_PANEL);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(20, 18, 20, 18));

        // ── ROUTE ─────────────────────────────────────────────────────────
        form.add(sectionLabel("ROUTE"));
        form.add(Box.createVerticalStrut(6));
        form.add(fieldRow("Departure city", txtDeparture));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Arrival city", txtArrival));
        form.add(Box.createVerticalStrut(14));

        // ── DEPARTURE date + time ──────────────────────────────────────────
        form.add(sectionLabel("DEPARTURE DATE & TIME"));
        form.add(Box.createVerticalStrut(6));
        form.add(dateTimeRow(txtDepDate, txtDepTime));
        form.add(Box.createVerticalStrut(14));

        // FIX: sezione ARRIVAL DATE & TIME — prima mancante
        form.add(sectionLabel("ARRIVAL DATE & TIME"));
        form.add(Box.createVerticalStrut(6));
        form.add(dateTimeRow(txtArrDate, txtArrTime));
        form.add(Box.createVerticalStrut(22));

        // ── AIRCRAFT ──────────────────────────────────────────────────────
        form.add(sectionLabel("AIRCRAFT"));
        form.add(Box.createVerticalStrut(6));

        styleCombo(cmbType);
        JPanel cmbRow = new JPanel(new BorderLayout());
        cmbRow.setOpaque(false);
        JLabel cmbLbl = dimLabel("Type");
        cmbLbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        cmbRow.add(cmbLbl,  BorderLayout.NORTH);
        cmbRow.add(cmbType, BorderLayout.CENTER);
        cmbRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        form.add(cmbRow);

        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Code", txtCode));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Model", txtModel));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow(lblExtraName, txtExtra));
        form.add(Box.createVerticalStrut(24));

        // Add button
        JButton btnAdd = accentButton("Add Flight");
        btnAdd.addActionListener(e -> addFlight());
        form.add(btnAdd);

        // Clear all button
        form.add(Box.createVerticalStrut(8));
        JButton btnClear = ghostButton("Clear All Flights");
        form.add(btnClear);
        // FIX: clear usa il metodo corretto senza accedere direttamente alla lista interna
        btnClear.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Remove all scheduled flights?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                airport.getAllFlights().clear();
                refreshList();
            }
        });

        JScrollPane sp = new JScrollPane(form);
        sp.setBorder(null);
        sp.setBackground(BG_PANEL);
        sp.getViewport().setBackground(BG_PANEL);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        styleScrollBar(sp.getVerticalScrollBar());

        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }

    // ── Date/time row helper (parametric) ─────────────────────────────────────
    private JPanel dateTimeRow(JTextField dateField, JTextField timeField) {
        JPanel row = new JPanel(new GridLayout(1, 2, 8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel dateCol = new JPanel(new BorderLayout(0, 4));
        dateCol.setOpaque(false);
        JLabel dateLbl = dimLabel("Date");
        dateLbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        dateCol.add(dateLbl,    BorderLayout.NORTH);
        dateCol.add(dateField,  BorderLayout.CENTER);

        JPanel timeCol = new JPanel(new BorderLayout(0, 4));
        timeCol.setOpaque(false);
        JLabel timeLbl = dimLabel("Time");
        timeLbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        timeCol.add(timeLbl,    BorderLayout.NORTH);
        timeCol.add(timeField,  BorderLayout.CENTER);

        row.add(dateCol);
        row.add(timeCol);
        return row;
    }

    // ── Main panel (flight list) ──────────────────────────────────────────────
    private JPanel buildMain() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(BG_DARK);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_DARK);
        topBar.setBorder(new EmptyBorder(16, 20, 12, 20));

        JLabel title = new JLabel("Scheduled Flights");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_MAIN);

        topBar.add(title,    BorderLayout.WEST);
        topBar.add(lblCount, BorderLayout.EAST);

        JScrollPane sp = new JScrollPane(flightListPanel);
        sp.setBorder(null);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        styleScrollBar(sp.getVerticalScrollBar());

        p.add(topBar, BorderLayout.NORTH);
        p.add(sp,     BorderLayout.CENTER);
        return p;
    }

    // ── Add flight logic ──────────────────────────────────────────────────────
    private void addFlight() {
        String dep   = txtDeparture.getText().trim();
        String arr   = txtArrival.getText().trim();
        String depD  = txtDepDate.getText().trim();
        String depT  = txtDepTime.getText().trim();
        String arrD  = txtArrDate.getText().trim();   // FIX: lettura data arrivo
        String arrT  = txtArrTime.getText().trim();   // FIX: lettura ora arrivo
        String code  = txtCode.getText().trim();
        String model = txtModel.getText().trim();
        String extra = txtExtra.getText().trim();

        // 1. Controllo campi vuoti
        if (dep.isEmpty() || arr.isEmpty() || depD.isEmpty() || depT.isEmpty()
                || arrD.isEmpty() || arrT.isEmpty()
                || code.isEmpty() || model.isEmpty() || extra.isEmpty()) {
            shake(this);
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields before adding a flight.",
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Parsing data di partenza
        LocalDateTime departureDateTime;
        try {
            departureDateTime = LocalDateTime.parse(depD + " " + depT, INPUT_FMT);
        } catch (DateTimeParseException ex) {
            highlightError(txtDepDate);
            highlightError(txtDepTime);
            JOptionPane.showMessageDialog(this,
                "<html>Invalid <b>departure</b> date or time.<br>"
                + "Use <b>DD/MM/YYYY</b> and <b>HH:MM</b>.</html>",
                "Invalid Departure Date/Time", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // FIX: parsing data di arrivo
        LocalDateTime arrivalDateTime;
        try {
            arrivalDateTime = LocalDateTime.parse(arrD + " " + arrT, INPUT_FMT);
        } catch (DateTimeParseException ex) {
            highlightError(txtArrDate);
            highlightError(txtArrTime);
            JOptionPane.showMessageDialog(this,
                "<html>Invalid <b>arrival</b> date or time.<br>"
                + "Use <b>DD/MM/YYYY</b> and <b>HH:MM</b>.</html>",
                "Invalid Arrival Date/Time", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // FIX: validazione che l'arrivo sia DOPO la partenza
        if (!arrivalDateTime.isAfter(departureDateTime)) {
            highlightError(txtArrDate);
            highlightError(txtArrTime);
            JOptionPane.showMessageDialog(this,
                "<html>Arrival date/time must be <b>after</b> departure date/time.</html>",
                "Invalid Arrival Date/Time", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validazione posti / capacità
        try {
            Plane plane;
            if (cmbType.getSelectedIndex() == 0) {
                int seats = Integer.parseInt(extra);
                if (seats <= 0 || seats > MAX_SEATS) {
                    highlightError(txtExtra);
                    JOptionPane.showMessageDialog(this,
                        "<html>Invalid number of seats.<br>"
                        + "Must be between <b>1</b> and <b>" + MAX_SEATS + "</b>.</html>",
                        "Invalid Seats", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                plane = new Lines(code, model, seats);
            } else {
                double tons = Double.parseDouble(extra);
                if (tons <= 0 || tons > MAX_CAPACITY_TON) {
                    highlightError(txtExtra);
                    JOptionPane.showMessageDialog(this,
                        "<html>Invalid cargo capacity.<br>"
                        + "Must be between <b>0.1 t</b> and <b>" + MAX_CAPACITY_TON + " t</b>.</html>",
                        "Invalid Capacity", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                plane = new Cargo(code, model, tons);
            }

            // FIX: costruttore Flight ora riceve anche arrivalDateTime
            airport.addFlight(new Flight(dep, arr, departureDateTime, arrivalDateTime, plane));
            refreshList();
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid number for Seats / Capacity.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // FIX: usa getFlightsSorted() invece di getAllFlights() per mostrare voli ordinati
    private void refreshList() {
        flightListPanel.setFlights(airport.getFlightsSorted(), this);
        int n = airport.getAllFlights().size();
        lblCount.setText(n + (n == 1 ? " flight scheduled" : " flights scheduled"));
    }

    private void clearFields() {
        txtDeparture.setText("");
        txtArrival.setText("");
        txtDepDate.setText("");
        txtDepTime.setText("");
        txtArrDate.setText("");
        txtArrTime.setText("");
        txtCode.setText("");
        txtModel.setText("");
        txtExtra.setText("");
    }

    private static void highlightError(JTextField field) {
        Border errorBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ERROR_CLR, 1, true),
            new EmptyBorder(6, 10, 6, 10));
        Border normalBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(6, 10, 6, 10));
        field.setBorder(errorBorder);
        Timer t = new Timer(1800, e -> field.setBorder(normalBorder));
        t.setRepeats(false);
        t.start();
    }

    private static void shake(Component c) {
        Point origin = c.getLocation();
        Timer t = new Timer(30, null);
        int[] step    = {0};
        int[] offsets = {0, -8, 8, -6, 6, -4, 4, -2, 2, 0};
        t.addActionListener(e -> {
            if (step[0] >= offsets.length) { t.stop(); c.setLocation(origin); return; }
            c.setLocation(origin.x + offsets[step[0]++], origin.y);
        });
        t.start();
    }

    // ── UI helpers ────────────────────────────────────────────────────────────
    private static JTextField styledField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(0x4A5568));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    Insets ins = getInsets();
                    g2.drawString(placeholder, ins.left,
                        getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
                }
            }
        };
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT_MAIN);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("Monospaced", Font.PLAIN, 13));
        Border normal  = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(6, 10, 6, 10));
        Border focused = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT, 1, true),
            new EmptyBorder(6, 10, 6, 10));
        f.setBorder(normal);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { f.setBorder(focused); }
            public void focusLost (FocusEvent e)  { f.setBorder(normal);  }
        });
        return f;
    }

    private static JLabel dimLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(TEXT_DIM);
        return l;
    }

    private static JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Monospaced", Font.BOLD, 10));
        l.setForeground(ACCENT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(new EmptyBorder(0, 0, 2, 0));
        return l;
    }

    private static JPanel fieldRow(String label, JTextField field) {
        return fieldRow(dimLabel(label), field);
    }

    private static JPanel fieldRow(JLabel label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        label.setBorder(new EmptyBorder(0, 0, 4, 0));
        p.add(label, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private static void styleCombo(JComboBox<String> c) {
        c.setBackground(FIELD_BG);
        c.setForeground(TEXT_MAIN);
        c.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        ((JLabel) c.getRenderer()).setBorder(new EmptyBorder(4, 8, 4, 8));
    }

    private static JButton accentButton(String text) {
        JButton b = new JButton(text) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? ACCENT.brighter() : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setPreferredSize(new Dimension(0, 42));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    private static JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setForeground(TEXT_DIM);
        b.setBackground(BG_PANEL);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(6, 12, 6, 12)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setForeground(TEXT_MAIN); }
            public void mouseExited (MouseEvent e) { b.setForeground(TEXT_DIM);  }
        });
        return b;
    }

    private static void styleScrollBar(JScrollBar sb) {
        sb.setUI(new BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(0x30363D);
                trackColor = BG_DARK;
            }
            @Override protected JButton createDecreaseButton(int o) { return zeroButton(); }
            @Override protected JButton createIncreaseButton(int o) { return zeroButton(); }
            private JButton zeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
        sb.setPreferredSize(new Dimension(6, 0));
    }

    // ── Flight list panel ─────────────────────────────────────────────────────
    static class FlightListPanel extends JPanel {
        private final ArrayList<Flight> flights = new ArrayList<>();

        FlightListPanel() {
            setBackground(BG_DARK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(4, 16, 16, 16));
        }

        // FIX: il pannello riceve anche il riferimento alla finestra per poter
        //      triggerare refreshList() quando si rimuove un volo
        void setFlights(ArrayList<Flight> list, AirportUI owner) {
            flights.clear();
            flights.addAll(list);
            removeAll();
            if (flights.isEmpty()) {
                JLabel empty = new JLabel("No flights scheduled yet. Add one from the sidebar.");
                empty.setFont(new Font("SansSerif", Font.ITALIC, 13));
                empty.setForeground(TEXT_DIM);
                empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                empty.setBorder(new EmptyBorder(24, 4, 0, 0));
                add(empty);
            } else {
                for (Flight f : flights) {
                    // FIX: passo anche airport e owner per il pulsante di rimozione
                    add(new FlightCard(f, owner.airport, owner));
                    add(Box.createVerticalStrut(8));
                }
            }
            revalidate();
            repaint();
        }
    }

    // ── Single flight card ────────────────────────────────────────────────────
    static class FlightCard extends JPanel {

        FlightCard(Flight f, Airport airport, AirportUI owner) {
            setLayout(new BorderLayout(12, 0));
            setBackground(BG_CARD);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(12, 16, 12, 16)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 88));  // FIX: altezza aumentata per la riga di arrivo
            setAlignmentX(LEFT_ALIGNMENT);

            boolean isCargo = f.getPlane() instanceof Cargo;
            Color dot = isCargo ? CARGO_CLR : ACCENT2;

            // Left: type label
            JPanel left = new JPanel();
            left.setOpaque(false);
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            JLabel typeLbl = new JLabel(isCargo ? "CARGO" : "AIRLINE");
            typeLbl.setFont(new Font("Monospaced", Font.BOLD, 10));
            typeLbl.setForeground(dot);
            typeLbl.setAlignmentX(CENTER_ALIGNMENT);

            JPanel dotPanel = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(dot);
                    int s = 10;
                    g2.fillOval((getWidth() - s) / 2, (getHeight() - s) / 2, s, s);
                    g2.dispose();
                }
            };
            dotPanel.setOpaque(false);
            dotPanel.setPreferredSize(new Dimension(30, 20));

            left.add(dotPanel);
            left.add(typeLbl);

            // Center: route + details
            JPanel center = new JPanel(new GridBagLayout());
            center.setOpaque(false);

            JLabel depLbl  = new JLabel(f.getDeparture());
            depLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
            depLbl.setForeground(TEXT_MAIN);

            JLabel arrow = new JLabel("  →  ");
            arrow.setFont(new Font("SansSerif", Font.PLAIN, 14));
            arrow.setForeground(TEXT_DIM);

            JLabel arrLbl = new JLabel(f.getArrival());
            arrLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
            arrLbl.setForeground(TEXT_MAIN);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            center.add(depLbl, gbc);
            center.add(arrow,  gbc);
            center.add(arrLbl, gbc);

            JLabel detail = new JLabel(
                f.getPlane().getCode() + "  ·  " + f.getPlane().getModel()
                + "  ·  " + f.getPlane().getDescription());
            detail.setFont(new Font("Monospaced", Font.PLAIN, 11));
            detail.setForeground(TEXT_DIM);

            JPanel routePanel = new JPanel();
            routePanel.setOpaque(false);
            routePanel.setLayout(new BoxLayout(routePanel, BoxLayout.Y_AXIS));
            center.setAlignmentX(LEFT_ALIGNMENT);
            detail.setAlignmentX(LEFT_ALIGNMENT);
            routePanel.add(center);
            routePanel.add(Box.createVerticalStrut(3));
            routePanel.add(detail);

            // Right: dep date/time + arr date/time + FIX remove button
            DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

            JPanel dateTimePanel = new JPanel();
            dateTimePanel.setOpaque(false);
            dateTimePanel.setLayout(new BoxLayout(dateTimePanel, BoxLayout.Y_AXIS));

            // Departure line
            JLabel depDateLbl = new JLabel("✈ " + f.getDepartureDateTime().format(dateFmt)
                                           + "  " + f.getDepartureDateTime().format(timeFmt));
            depDateLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
            depDateLbl.setForeground(ACCENT);
            depDateLbl.setAlignmentX(RIGHT_ALIGNMENT);

            // FIX: Arrival line — prima non veniva mostrata
            JLabel arrDateLbl = new JLabel("⬇ " + f.getArrivalDateTime().format(dateFmt)
                                           + "  " + f.getArrivalDateTime().format(timeFmt));
            arrDateLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
            arrDateLbl.setForeground(ACCENT2);
            arrDateLbl.setAlignmentX(RIGHT_ALIGNMENT);

            // FIX: pulsante rimozione singolo volo
            JButton btnRemove = new JButton("✕");
            btnRemove.setFont(new Font("SansSerif", Font.BOLD, 11));
            btnRemove.setForeground(TEXT_DIM);
            btnRemove.setBackground(BG_CARD);
            btnRemove.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1, true));
            btnRemove.setFocusPainted(false);
            btnRemove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnRemove.setAlignmentX(RIGHT_ALIGNMENT);
            btnRemove.setPreferredSize(new Dimension(28, 22));
            btnRemove.setMaximumSize(new Dimension(28, 22));
            btnRemove.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btnRemove.setForeground(ERROR_CLR); }
                public void mouseExited (MouseEvent e) { btnRemove.setForeground(TEXT_DIM);  }
            });
            btnRemove.addActionListener(e -> {
                airport.removeFlight(f);
                owner.refreshList();
            });

            dateTimePanel.add(depDateLbl);
            dateTimePanel.add(Box.createVerticalStrut(2));
            dateTimePanel.add(arrDateLbl);
            dateTimePanel.add(Box.createVerticalStrut(4));
            dateTimePanel.add(btnRemove);

            add(left,          BorderLayout.WEST);
            add(routePanel,    BorderLayout.CENTER);
            add(dateTimePanel, BorderLayout.EAST);
        }
    }
}