import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConfiguratorUI extends JFrame {

    // ==================== CATALOGO COMPONENTI ====================
    private ArrayList<CPU> cpuList = new ArrayList<>();
    private ArrayList<GPU> gpuList = new ArrayList<>();
    private ArrayList<RAM> ramList = new ArrayList<>();
    private ArrayList<Storage> storageList = new ArrayList<>();
    private ArrayList<Motherboard> mbList = new ArrayList<>();
    private ArrayList<PSU> psuList = new ArrayList<>();
    private ArrayList<Case> caseList = new ArrayList<>();

    // ==================== CONFIGURAZIONI SALVATE ====================
    private ArrayList<Configuration> configurazioni = new ArrayList<>();
    private Configuration currentConfig = new Configuration("Nuova Build");

    // ==================== COMPONENTI GUI ====================
    private JComboBox<CPU> comboCPU;
    private JComboBox<GPU> comboGPU;
    private JComboBox<RAM> comboRAM;
    private JComboBox<Storage> comboStorage;
    private JComboBox<Motherboard> comboMotherboard;
    private JComboBox<PSU> comboPSU;
    private JComboBox<Case> comboCase;

    private JLabel lblPrezzo;
    private JLabel lblWatt;
    private JLabel lblStato;
    private JLabel lblTotaleConfig;

    private JList<Configuration> listConfigurations;
    private DefaultListModel<Configuration> listModel;

    private JTextField txtConfigName;

    public ConfiguratorUI() {
        inizializzaCatalogo();
        caricaConfigurazioni();
        buildUI();
        setVisible(true);
    }

    // ==================== CATALOGO ====================

    private void inizializzaCatalogo() {
        // CPU
        cpuList.add(new CPU("Intel Core i9-14900K", "LGA1700", 125, 589.00));
        cpuList.add(new CPU("Intel Core i7-14700K", "LGA1700", 125, 409.00));
        cpuList.add(new CPU("Intel Core i5-14600K", "LGA1700", 125, 299.00));
        cpuList.add(new CPU("AMD Ryzen 9 7950X", "AM5", 170, 549.00));
        cpuList.add(new CPU("AMD Ryzen 7 7700X", "AM5", 105, 299.00));
        cpuList.add(new CPU("AMD Ryzen 5 7600X", "AM5", 105, 199.00));

        // GPU
        gpuList.add(new GPU("NVIDIA RTX 4090", 24, 450, 1599.00));
        gpuList.add(new GPU("NVIDIA RTX 4080 Super", 16, 320, 999.00));
        gpuList.add(new GPU("NVIDIA RTX 4070 Ti", 12, 285, 749.00));
        gpuList.add(new GPU("NVIDIA RTX 4070", 12, 200, 549.00));
        gpuList.add(new GPU("AMD RX 7900 XTX", 24, 355, 849.00));
        gpuList.add(new GPU("AMD RX 7800 XT", 16, 263, 449.00));

        // RAM
        ramList.add(new RAM("Corsair Vengeance DDR5 32GB", "DDR5", 32, 109.00));
        ramList.add(new RAM("Corsair Vengeance DDR5 64GB", "DDR5", 64, 199.00));
        ramList.add(new RAM("G.Skill Trident DDR4 32GB", "DDR4", 32, 79.00));
        ramList.add(new RAM("G.Skill Trident DDR4 16GB", "DDR4", 16, 49.00));
        ramList.add(new RAM("Kingston Fury DDR5 32GB", "DDR5", 32, 99.00));

        // Storage
        storageList.add(new Storage("Samsung 990 Pro 2TB", "NVMe", 2000, 179.00));
        storageList.add(new Storage("Samsung 990 Pro 1TB", "NVMe", 1000, 99.00));
        storageList.add(new Storage("WD Black SN850X 2TB", "NVMe", 2000, 159.00));
        storageList.add(new Storage("Samsung 870 EVO 4TB", "SSD", 4000, 249.00));
        storageList.add(new Storage("Seagate Barracuda 4TB", "HDD", 4000, 79.00));

        // Motherboard
        mbList.add(new Motherboard("ASUS ROG Strix Z790-E", "LGA1700", "DDR5", "ATX", 399.00));
        mbList.add(new Motherboard("MSI MAG Z790 Tomahawk", "LGA1700", "DDR5", "ATX", 249.00));
        mbList.add(new Motherboard("Gigabyte B760M DS3H", "LGA1700", "DDR4", "mATX", 149.00));
        mbList.add(new Motherboard("ASUS ROG Crosshair X670E", "AM5", "DDR5", "ATX", 499.00));
        mbList.add(new Motherboard("MSI MAG X670E Tomahawk", "AM5", "DDR5", "ATX", 299.00));
        mbList.add(new Motherboard("ASRock B650M Pro RS", "AM5", "DDR5", "mATX", 169.00));

        // PSU
        psuList.add(new PSU("Corsair RM1000x 1000W", 1000, 179.00));
        psuList.add(new PSU("Corsair RM850x 850W", 850, 149.00));
        psuList.add(new PSU("be quiet! Straight Power 750W", 750, 129.00));
        psuList.add(new PSU("Seasonic Focus GX-650W", 650, 109.00));
        psuList.add(new PSU("EVGA SuperNOVA 550W", 550, 79.00));

        // Case
        caseList.add(new Case("Fractal Design Torrent ATX", "ATX", 189.00));
        caseList.add(new Case("NZXT H7 Flow ATX", "ATX", 149.00));
        caseList.add(new Case("Lian Li O11 Dynamic ATX", "ATX", 139.00));
        caseList.add(new Case("Cooler Master MasterBox mATX", "mATX", 89.00));
        caseList.add(new Case("Fractal Design Node 304 mITX", "mITX", 99.00));
    }

    private void caricaConfigurazioni() {
        configurazioni = Configuration.caricaConfigurazioni(
            cpuList, gpuList, ramList, storageList, mbList, psuList, caseList
        );
    }

    // ==================== BUILD UI ====================

    private void buildUI() {
        setTitle("PC Configurator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        // Layout principale: sinistra (form) | destra (lista configurazioni)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 250));

        mainPanel.add(buildFormPanel(), BorderLayout.CENTER);
        mainPanel.add(buildSidePanel(), BorderLayout.EAST);
        mainPanel.add(buildBottomPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        // Titolo + nome build
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);

        JLabel title = new JLabel("⚙ PC Configurator");
        title.setFont(new Font("Calibri", Font.BOLD, 22));
        title.setForeground(new Color(30, 80, 160));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        namePanel.setOpaque(false);
        namePanel.add(new JLabel("Nome build: "));
        txtConfigName = new JTextField("Nuova Build", 18);
        txtConfigName.setFont(new Font("Calibri", Font.PLAIN, 13));
        namePanel.add(txtConfigName);

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(namePanel, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // Grid dei componenti
        JPanel grid = new JPanel(new GridLayout(7, 1, 5, 8));
        grid.setOpaque(false);

        comboCPU = new JComboBox<>(cpuList.toArray(new CPU[0]));
        comboGPU = new JComboBox<>(gpuList.toArray(new GPU[0]));
        comboRAM = new JComboBox<>(ramList.toArray(new RAM[0]));
        comboStorage = new JComboBox<>(storageList.toArray(new Storage[0]));
        comboMotherboard = new JComboBox<>(mbList.toArray(new Motherboard[0]));
        comboPSU = new JComboBox<>(psuList.toArray(new PSU[0]));
        comboCase = new JComboBox<>(caseList.toArray(new Case[0]));

        grid.add(buildRow("🔲 CPU", comboCPU));
        grid.add(buildRow("🎮 GPU", comboGPU));
        grid.add(buildRow("💾 RAM", comboRAM));
        grid.add(buildRow("💿 Storage", comboStorage));
        grid.add(buildRow("🔌 Motherboard", comboMotherboard));
        grid.add(buildRow("⚡ PSU", comboPSU));
        grid.add(buildRow("🖥 Case", comboCase));

        // Listener su tutti i combo
        ActionListener updateListener = e -> aggiornaPrezzo();
        comboCPU.addActionListener(updateListener);
        comboGPU.addActionListener(updateListener);
        comboRAM.addActionListener(updateListener);
        comboStorage.addActionListener(updateListener);
        comboMotherboard.addActionListener(updateListener);
        comboPSU.addActionListener(updateListener);
        comboCase.addActionListener(updateListener);

        panel.add(grid, BorderLayout.CENTER);

        // Pannello prezzo/stato
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        infoPanel.setOpaque(false);

        lblPrezzo = new JLabel("Prezzo totale: —");
        lblPrezzo.setFont(new Font("Calibri", Font.BOLD, 16));
        lblPrezzo.setForeground(new Color(30, 130, 30));

        lblWatt = new JLabel("Consumo stimato: — W");
        lblWatt.setFont(new Font("Calibri", Font.PLAIN, 13));
        lblWatt.setForeground(new Color(100, 100, 100));

        lblStato = new JLabel(" ");
        lblStato.setFont(new Font("Calibri", Font.ITALIC, 12));
        lblStato.setForeground(new Color(180, 60, 60));

        infoPanel.add(lblPrezzo);
        infoPanel.add(lblWatt);
        infoPanel.add(lblStato);

        panel.add(infoPanel, BorderLayout.SOUTH);

        aggiornaPrezzo();
        return panel;
    }

    private JPanel buildRow(String label, JComboBox<?> combo) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Calibri", Font.BOLD, 13));
        lbl.setPreferredSize(new Dimension(120, 20));
        combo.setFont(new Font("Calibri", Font.PLAIN, 12));
        row.add(lbl, BorderLayout.WEST);
        row.add(combo, BorderLayout.CENTER);
        return row;
    }

    private JPanel buildSidePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(300, 0));

        JLabel titleList = new JLabel("📋 Configurazioni salvate");
        titleList.setFont(new Font("Calibri", Font.BOLD, 14));
        titleList.setForeground(new Color(30, 80, 160));
        panel.add(titleList, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        for (Configuration c : configurazioni) listModel.addElement(c);
        listConfigurations = new JList<>(listModel);
        listConfigurations.setFont(new Font("Calibri", Font.PLAIN, 12));
        listConfigurations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listConfigurations.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) caricaConfigurazioneSelezionata();
        });
        JScrollPane scroll = new JScrollPane(listConfigurations);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210)));
        panel.add(scroll, BorderLayout.CENTER);

        lblTotaleConfig = new JLabel("Totale build: " + configurazioni.size());
        lblTotaleConfig.setFont(new Font("Calibri", Font.ITALIC, 11));
        lblTotaleConfig.setForeground(Color.GRAY);
        panel.add(lblTotaleConfig, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(false);

        JButton btnSalva = createButton("💾 Salva Build", new Color(30, 120, 200));
        JButton btnNuova = createButton("➕ Nuova Build", new Color(60, 160, 60));
        JButton btnElimina = createButton("🗑 Elimina", new Color(200, 60, 60));

        btnSalva.addActionListener(e -> salvaBuild());
        btnNuova.addActionListener(e -> nuovaBuild());
        btnElimina.addActionListener(e -> eliminaBuild());

        panel.add(btnSalva);
        panel.add(btnNuova);
        panel.add(btnElimina);
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Calibri", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ==================== LOGICA UI ====================

    private void aggiornaPrezzo() {
        currentConfig.setCPU((CPU) comboCPU.getSelectedItem());
        currentConfig.setGPU((GPU) comboGPU.getSelectedItem());
        currentConfig.setRAM((RAM) comboRAM.getSelectedItem());
        currentConfig.setStorage((Storage) comboStorage.getSelectedItem());
        currentConfig.setMotherboard((Motherboard) comboMotherboard.getSelectedItem());
        currentConfig.setPSU((PSU) comboPSU.getSelectedItem());
        currentConfig.setCase((Case) comboCase.getSelectedItem());

        lblPrezzo.setText("Calcolo in corso...");
        lblStato.setText(" ");

        // Calcolo prezzo in un thread separato per non bloccare la GUI
        PriceCalculatorWorker worker = new PriceCalculatorWorker(currentConfig);
        worker.start();

        // Thread che aspetta il risultato e aggiorna la GUI
        Thread aggiornaUI = new Thread() {
            @Override
            public void run() {
                try {
                    worker.join(); // aspetta che il calcolo finisca
                } catch (InterruptedException e) {
                    System.out.println("Attesa interrotta: " + e.getMessage());
                }
                double price = worker.getRisultatoPrezzo();
                int watts = worker.getRisultatoWatt();

                // SwingUtilities.invokeLater per aggiornare la GUI dal thread
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        lblPrezzo.setText("Prezzo totale: €" + String.format("%.2f", price));
                        lblWatt.setText("Consumo stimato: " + watts + " W");
                        try {
                            currentConfig.checkCompatibility();
                            lblStato.setText("✓ Componenti compatibili");
                            lblStato.setForeground(new Color(30, 130, 30));
                        } catch (IncompatibleComponentException ex) {
                            lblStato.setText("⚠ " + ex.getMessage());
                            lblStato.setForeground(new Color(180, 60, 60));
                        }
                    }
                });
            }
        };
        aggiornaUI.start();
    }

    private void salvaBuild() {
        String nome = txtConfigName.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci un nome per la build!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            currentConfig.checkCompatibility();
        } catch (IncompatibleComponentException ex) {
            int scelta = JOptionPane.showConfirmDialog(this,
                "La build ha problemi di compatibilità:\n" + ex.getMessage() + "\nVuoi salvarla comunque?",
                "Compatibilità", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (scelta != JOptionPane.YES_OPTION) return;
        }

        // Aggiorna il nome
        currentConfig = new Configuration(nome);
        currentConfig.setCPU((CPU) comboCPU.getSelectedItem());
        currentConfig.setGPU((GPU) comboGPU.getSelectedItem());
        currentConfig.setRAM((RAM) comboRAM.getSelectedItem());
        currentConfig.setStorage((Storage) comboStorage.getSelectedItem());
        currentConfig.setMotherboard((Motherboard) comboMotherboard.getSelectedItem());
        currentConfig.setPSU((PSU) comboPSU.getSelectedItem());
        currentConfig.setCase((Case) comboCase.getSelectedItem());

        // Controlla se esiste già una config con lo stesso nome e la sostituisce
        boolean trovata = false;
        for (int i = 0; i < configurazioni.size(); i++) {
            if (configurazioni.get(i).getConfigName().equals(nome)) {
                configurazioni.set(i, currentConfig);
                listModel.set(i, currentConfig);
                trovata = true;
                break;
            }
        }
        if (!trovata) {
            configurazioni.add(currentConfig);
            listModel.addElement(currentConfig);
        }

        Configuration.salvaConfigurazioni(configurazioni);
        aggiornaContatore();
        JOptionPane.showMessageDialog(this, "Build \"" + nome + "\" salvata!", "Salvato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nuovaBuild() {
        currentConfig = new Configuration("Nuova Build");
        txtConfigName.setText("Nuova Build");
        listConfigurations.clearSelection();
        comboCPU.setSelectedIndex(0);
        comboGPU.setSelectedIndex(0);
        comboRAM.setSelectedIndex(0);
        comboStorage.setSelectedIndex(0);
        comboMotherboard.setSelectedIndex(0);
        comboPSU.setSelectedIndex(0);
        comboCase.setSelectedIndex(0);
        aggiornaPrezzo();
    }

    private void eliminaBuild() {
        int idx = listConfigurations.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Seleziona una build da eliminare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(this,
            "Eliminare la build \"" + configurazioni.get(idx).getConfigName() + "\"?",
            "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            configurazioni.remove(idx);
            listModel.remove(idx);
            Configuration.salvaConfigurazioni(configurazioni);
            aggiornaContatore();
            nuovaBuild();
        }
    }

    private void caricaConfigurazioneSelezionata() {
        Configuration sel = listConfigurations.getSelectedValue();
        if (sel == null) return;
        txtConfigName.setText(sel.getConfigName());
        if (sel.getCpu() != null) comboCPU.setSelectedItem(sel.getCpu());
        if (sel.getGpu() != null) comboGPU.setSelectedItem(sel.getGpu());
        if (sel.getRam() != null) comboRAM.setSelectedItem(sel.getRam());
        if (sel.getStorage() != null) comboStorage.setSelectedItem(sel.getStorage());
        if (sel.getMotherboard() != null) comboMotherboard.setSelectedItem(sel.getMotherboard());
        if (sel.getPsu() != null) comboPSU.setSelectedItem(sel.getPsu());
        if (sel.getPcCase() != null) comboCase.setSelectedItem(sel.getPcCase());
        currentConfig = sel;
        aggiornaPrezzo();
    }

    private void aggiornaContatore() {
        lblTotaleConfig.setText("Totale build: " + configurazioni.size() +
            " | Configurazioni totali create: " + Configuration.getTotalConfigurations());
    }
}
