import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;

public class SoccerManager extends JFrame {

    // ── PALETTE LIGHT ────────────────────────────────────────
    private static final Color BG_APP = new Color(0xF4F6FB);
    private static final Color BG_PANEL = new Color(0xFFFFFF);
    private static final Color BG_HEADER = new Color(0x1E293B);
    private static final Color BG_INPUT = new Color(0xFFFFFF);
    private static final Color BG_ROW_ALT = new Color(0xF8FAFC);
    private static final Color BG_ROW_SEL = new Color(0xDCFCEF);
    private static final Color ACCENT = new Color(0x10B981);
    private static final Color ACCENT_BLUE = new Color(0x3B82F6);
    private static final Color ACCENT_RED = new Color(0xEF4444);
    private static final Color TEXT_DARK = new Color(0x1E293B);
    private static final Color TEXT_MUTED = new Color(0x64748B);
    private static final Color BORDER_COLOR = new Color(0xE2E8F0);
    private static final Color HEADER_TEXT = new Color(0x94A3B8);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 10);
    private static final Font FONT_SUB = new Font("Segoe UI", Font.PLAIN, 11);

    private DefaultTableModel tableModel;
    private JTable playerTable;
    private JTextField nameField, surnameField, shirtField, birthField;
    private JComboBox<Role> roleCombo;
    private JComboBox<Nationality> nationalityCombo;
    private JComboBox<Gender> genderCombo;
    private JCheckBox captainCheckBox;
    private JLabel playerCountLabel;

    public SoccerManager() {
        setTitle("World Soccer Manager 2026");
        setSize(1400, 760);
        setMinimumSize(new Dimension(1100, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_APP);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_APP);
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildTable(), BorderLayout.CENTER);
        root.add(buildSidePanel(), BorderLayout.EAST);
    }

    // ── HEADER ───────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(BG_HEADER);
        h.setBorder(new EmptyBorder(14, 24, 14, 24));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel logo = new JLabel("⚽");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JLabel title = new JLabel("World Soccer Manager");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel year = new JLabel("2026");
        year.setFont(FONT_TITLE);
        year.setForeground(ACCENT);

        left.add(logo);
        left.add(title);
        left.add(year);

        playerCountLabel = new JLabel("0 players");
        playerCountLabel.setFont(FONT_SUB);
        playerCountLabel.setForeground(HEADER_TEXT);

        h.add(left, BorderLayout.WEST);
        h.add(playerCountLabel, BorderLayout.EAST);
        return h;
    }

    // ── TABLE ────────────────────────────────────────────────
    private JScrollPane buildTable() {
        String[] cols = {"", "No.", "Surname", "Name", "Gender", "Birth Date", "Fiscal Code", "Position", "Captain"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        playerTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(BG_ROW_SEL);
                    c.setForeground(TEXT_DARK);
                } else {
                    c.setBackground(row % 2 == 0 ? BG_PANEL : BG_ROW_ALT);
                    c.setForeground(TEXT_DARK);
                }
                if (c instanceof JLabel lbl) {
                    lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
                }
                return c;
            }
        };

        styleTable();

        JScrollPane sp = new JScrollPane(playerTable);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(BG_PANEL);
        sp.setBackground(BG_APP);
        sp.setBorder(new EmptyBorder(16, 16, 16, 8));
        return sp;
    }

    private void styleTable() {
        playerTable.setFont(FONT_TABLE);
        playerTable.setRowHeight(40);
        playerTable.setShowGrid(false);
        playerTable.setIntercellSpacing(new Dimension(0, 3));
        playerTable.setBackground(BG_PANEL);
        playerTable.setForeground(TEXT_DARK);
        playerTable.setSelectionBackground(BG_ROW_SEL);
        playerTable.setSelectionForeground(TEXT_DARK);
        playerTable.setFocusable(false);
        playerTable.setRowSelectionAllowed(true);

        JTableHeader th = playerTable.getTableHeader();
        th.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel l = new JLabel(v == null ? "" : v.toString().toUpperCase());
                l.setFont(FONT_HEADER);
                l.setForeground(TEXT_MUTED);
                l.setBackground(BG_APP);
                l.setOpaque(true);
                l.setBorder(new CompoundBorder(
                        new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
                        new EmptyBorder(8, 12, 8, 12)
                ));
                l.setHorizontalAlignment(c == 1 ? CENTER : LEFT);
                return l;
            }
        });
        th.setPreferredSize(new Dimension(0, 34));
        th.setBackground(BG_APP);
        th.setBorder(null);

        int[] widths = {36, 48, 140, 130, 68, 110, 150, 120, 80};
        for (int i = 0; i < widths.length && i < playerTable.getColumnCount(); i++) {
            playerTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        playerTable.getColumnModel().getColumn(1).setCellRenderer(centeredRenderer());
        playerTable.getColumnModel().getColumn(8).setCellRenderer(captainRenderer());
    }

    private TableCellRenderer centeredRenderer() {
        return new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBackground(sel ? BG_ROW_SEL : (r % 2 == 0 ? BG_PANEL : BG_ROW_ALT));
                setForeground(TEXT_DARK);
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setBorder(new EmptyBorder(0, 0, 0, 0));
                return this;
            }
        };
    }

    private TableCellRenderer captainRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                boolean isCap = "YES ©".equals(v);
                JLabel l = new JLabel(isCap ? "● Captain" : "");
                l.setFont(isCap ? new Font("Segoe UI", Font.BOLD, 11) : FONT_TABLE);
                l.setForeground(isCap ? ACCENT : TEXT_MUTED);
                l.setOpaque(true);
                l.setBackground(sel ? BG_ROW_SEL : (r % 2 == 0 ? BG_PANEL : BG_ROW_ALT));
                l.setBorder(new EmptyBorder(0, 12, 0, 12));
                return l;
            }
        };
    }

    // ── SIDE PANEL ───────────────────────────────────────────
    private JPanel buildSidePanel() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(320, 0));
        side.setBackground(BG_PANEL);
        side.setBorder(new MatteBorder(0, 1, 0, 0, BORDER_COLOR));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(BG_PANEL);
        inner.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel sectionTitle = new JLabel("Player Details");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sectionTitle.setForeground(TEXT_DARK);
        sectionTitle.setAlignmentX(LEFT_ALIGNMENT);
        inner.add(sectionTitle);
        inner.add(Box.createVerticalStrut(3));

        JLabel sectionSub = new JLabel("Fill in the form to add or edit a player");
        sectionSub.setFont(FONT_SUB);
        sectionSub.setForeground(TEXT_MUTED);
        sectionSub.setAlignmentX(LEFT_ALIGNMENT);
        inner.add(sectionSub);
        inner.add(Box.createVerticalStrut(16));
        inner.add(makeSep());
        inner.add(Box.createVerticalStrut(14));

        nameField = makeTextField("e.g. Cristiano");
        surnameField = makeTextField("e.g. Ronaldo");
        shirtField = makeTextField("1 – 99");
        birthField = makeTextField("DD/MM/YYYY");
        roleCombo = makeCombo(Role.values());
        nationalityCombo = makeCombo(Nationality.values());
        genderCombo = makeCombo(Gender.values());

        inner.add(makeFieldBlock("First Name", nameField));
        inner.add(makeFieldBlock("Last Name", surnameField));
        inner.add(makeFieldBlock("Gender", genderCombo));
        inner.add(makeFieldBlock("Date of Birth", birthField));
        inner.add(makeFieldBlock("Shirt Number", shirtField));
        inner.add(makeFieldBlock("Position", roleCombo));
        inner.add(makeFieldBlock("Nationality", nationalityCombo));

        captainCheckBox = new JCheckBox("  Assign as Team Captain");
        captainCheckBox.setFont(FONT_LABEL);
        captainCheckBox.setForeground(TEXT_DARK);
        captainCheckBox.setBackground(BG_PANEL);
        captainCheckBox.setFocusPainted(false);
        captainCheckBox.setAlignmentX(LEFT_ALIGNMENT);
        inner.add(Box.createVerticalStrut(4));
        inner.add(captainCheckBox);
        inner.add(Box.createVerticalStrut(20));

        JButton addBtn = makeButton("＋  Add Player", ACCENT, Color.WHITE);
        JButton modBtn = makeButton("✎  Edit Selected", ACCENT_BLUE, Color.WHITE);
        JButton delBtn = makeButton("✕  Remove", new Color(0xFEF2F2), ACCENT_RED);
        delBtn.setBorder(new CompoundBorder(
                new LineBorder(new Color(0xFCA5A5), 1, true),
                new EmptyBorder(8, 18, 8, 18)
        ));

        for (JButton b : new JButton[]{addBtn, modBtn, delBtn}) {
            b.setAlignmentX(LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        }

        inner.add(addBtn);
        inner.add(Box.createVerticalStrut(7));
        inner.add(modBtn);
        inner.add(Box.createVerticalStrut(7));
        inner.add(delBtn);

        addBtn.addActionListener(e -> addOrModify(-1));
        modBtn.addActionListener(e -> {
            int row = playerTable.getSelectedRow();
            if (row == -1) {
                showError("Please select a player to edit.");
                return;
            }
            addOrModify(row);
        });
        delBtn.addActionListener(e -> {
            int row = playerTable.getSelectedRow();
            if (row == -1) {
                showError("Please select a player to remove.");
                return;
            }
            tableModel.removeRow(row);
            updateCount();
        });

        JScrollPane sp = new JScrollPane(inner);
        sp.setBorder(null);
        sp.setBackground(BG_PANEL);
        sp.getViewport().setBackground(BG_PANEL);
        sp.getVerticalScrollBar().setUnitIncrement(12);
        side.add(sp, BorderLayout.CENTER);
        return side;
    }

    // ── FORM HELPERS ─────────────────────────────────────────
    private JPanel makeFieldBlock(String label, Component field) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setBackground(BG_PANEL);
        block.setAlignmentX(LEFT_ALIGNMENT);
        block.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));

        JLabel lbl = new JLabel(label.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(LEFT_ALIGNMENT);

        if (field instanceof JComponent jc) {
            jc.setAlignmentX(LEFT_ALIGNMENT);
            jc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        }

        block.add(lbl);
        block.add(Box.createVerticalStrut(4));
        block.add(field);
        block.add(Box.createVerticalStrut(8));
        return block;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField f = new JTextField();
        f.setFont(FONT_INPUT);
        f.setBackground(BG_INPUT);
        f.setForeground(TEXT_DARK);
        f.setCaretColor(TEXT_DARK);
        f.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        f.putClientProperty("JTextField.placeholderText", placeholder);
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.setBorder(new CompoundBorder(
                        new LineBorder(ACCENT, 1, true),
                        new EmptyBorder(6, 10, 6, 10)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.setBorder(new CompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(6, 10, 6, 10)
                ));
            }
        });
        return f;
    }

    private <T> JComboBox<T> makeCombo(T[] items) {
        JComboBox<T> cb = new JComboBox<>(items);
        cb.setFont(FONT_INPUT);
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT_DARK);
        cb.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object v, int i, boolean sel, boolean foc) {
                super.getListCellRendererComponent(list, v, i, sel, foc);
                setBackground(sel ? new Color(0xE0F2FE) : BG_INPUT);
                setForeground(sel ? new Color(0x0369A1) : TEXT_DARK);
                setFont(FONT_INPUT);
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return this;
            }
        });
        cb.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton b = new JButton("▾");
                b.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                b.setBackground(BG_INPUT);
                b.setForeground(TEXT_MUTED);
                b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
                b.setContentAreaFilled(false);
                return b;
            }
        });
        return cb;
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(9, 18, 9, 18));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(bg.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(bg);
            }
        });
        return b;
    }

    private JSeparator makeSep() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDER_COLOR);
        s.setBackground(BG_PANEL);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return s;
    }

    // ── LOGIC ────────────────────────────────────────────────
    private void addOrModify(int rowIndex) {
        String shirt = shirtField.getText().trim();
        String surname = surnameField.getText().trim();
        if (surname.isEmpty() || shirt.isEmpty()) {
            showError("Last name and shirt number are required.");
            return;
        }
        try {
            Player p = new Player(
                    nameField.getText().trim(),
                    surname,
                    Integer.parseInt(shirt),
                    (Role) roleCombo.getSelectedItem(),
                    (Nationality) nationalityCombo.getSelectedItem(),
                    captainCheckBox.isSelected(),
                    birthField.getText().trim(),
                    (Gender) genderCombo.getSelectedItem()
            );
            Object[] row = {
                    p.getNationality().getFlag(),
                    p.getShirtNumber(),
                    p.getSurname(),
                    p.getName(),
                    p.getGender(),
                    p.getBirthDate(),
                    p.getFiscalCode(),
                    p.getRole().getShortName(),
                    p.isCaptain() ? "YES ©" : ""
            };
            if (rowIndex == -1) {
                tableModel.addRow(row);
            } else {
                for (int i = 0; i < row.length; i++) tableModel.setValueAt(row[i], rowIndex, i);
            }
            clearFields();
            updateCount();
        } catch (NumberFormatException ex) {
            showError("Shirt number must be a valid integer.");
        } catch (Exception ex) {
            showError("Error: please check your data.\n" + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        surnameField.setText("");
        shirtField.setText("");
        birthField.setText("DD/MM/YYYY");
        captainCheckBox.setSelected(false);
    }

    private void updateCount() {
        int n = tableModel.getRowCount();
        playerCountLabel.setText(n + (n == 1 ? " player" : " players"));
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}