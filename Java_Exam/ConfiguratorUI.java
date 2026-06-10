import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConfiguratorUI extends JFrame {

    // ========== CATALOGO COMPONENTI ==========

    private ArrayList<CPU> cpuList = new ArrayList<>();
    private ArrayList<GPU> gpuList = new ArrayList<>();
    private ArrayList<RAM> ramList = new ArrayList<>();
    private ArrayList<Storage> storageList = new ArrayList<>();
    private ArrayList<Motherboard> mbList = new ArrayList<>();
    private ArrayList<PSU> psuList = new ArrayList<>();
    private ArrayList<Case> caseList = new ArrayList<>();

    // ========== CONFIGURAZIONI ==========

    private ArrayList<Configuration> configurazioni = new ArrayList<>();
    private Configuration currentConfig = new Configuration("Nuova Build");

    // ========== COMPONENTI GUI ==========

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
    private JTextField txtNome;

    private DefaultListModel<Configuration> listModel;
    private JList<Configuration> listConfig;

    public ConfiguratorUI() {
        inizializzaCatalogo();
        configurazioni = Configuration.caricaConfigurazioniDaFile(
            cpuList, gpuList, ramList, storageList, mbList, psuList, caseList
        );
        buildUI();
        setVisible(true);
    }

    // ========== CATALOGO ==========

    private void inizializzaCatalogo() {
        cpuList.add(new CPU("Intel Core i9-14900K", "LGA1700", 125, 589.00f));
        cpuList.add(new CPU("Intel Core i7-14700K", "LGA1700", 125, 409.00f));
        cpuList.add(new CPU("Intel Core i5-14600K", "LGA1700", 125, 299.00f));
        cpuList.add(new CPU("AMD Ryzen 9 7950X",    "AM5",     170, 549.00f));
        cpuList.add(new CPU("AMD Ryzen 7 7700X",    "AM5",     105, 299.00f));
        cpuList.add(new CPU("AMD Ryzen 5 7600X",    "AM5",     105, 199.00f));

        gpuList.add(new GPU("NVIDIA RTX 4090",       24, 450, 1599.00f));
        gpuList.add(new GPU("NVIDIA RTX 4080 Super", 16, 320,  999.00f));
        gpuList.add(new GPU("NVIDIA RTX 4070 Ti",    12, 285,  749.00f));
        gpuList.add(new GPU("NVIDIA RTX 4070",       12, 200,  549.00f));
        gpuList.add(new GPU("AMD RX 7900 XTX",       24, 355,  849.00f));
        gpuList.add(new GPU("AMD RX 7800 XT",        16, 263,  449.00f));

        ramList.add(new RAM("Corsair Vengeance 32GB DDR5", "DDR5", 32, 109.00f));
        ramList.add(new RAM("Corsair Vengeance 64GB DDR5", "DDR5", 64, 199.00f));
        ramList.add(new RAM("G.Skill Trident 32GB DDR4",   "DDR4", 32,  79.00f));
        ramList.add(new RAM("G.Skill Trident 16GB DDR4",   "DDR4", 16,  49.00f));
        ramList.add(new RAM("Kingston Fury 32GB DDR5",     "DDR5", 32,  99.00f));

        storageList.add(new Storage("Samsung 990 Pro 2TB",   "NVMe", 2000, 179.00f));
        storageList.add(new Storage("Samsung 990 Pro 1TB",   "NVMe", 1000,  99.00f));
        storageList.add(new Storage("WD Black SN850X 2TB",   "NVMe", 2000, 159.00f));
        storageList.add(new Storage("Samsung 870 EVO 4TB",   "SSD",  4000, 249.00f));
        storageList.add(new Storage("Seagate Barracuda 4TB", "HDD",  4000,  79.00f));

        mbList.add(new Motherboard("ASUS ROG Strix Z790-E",   "LGA1700", "DDR5", "ATX",  399.00f));
        mbList.add(new Motherboard("MSI MAG Z790 Tomahawk",    "LGA1700", "DDR5", "ATX",  249.00f));
        mbList.add(new Motherboard("Gigabyte B760M DS3H",      "LGA1700", "DDR4", "mATX", 149.00f));
        mbList.add(new Motherboard("ASUS ROG Crosshair X670E", "AM5",     "DDR5", "ATX",  499.00f));
        mbList.add(new Motherboard("MSI MAG X670E Tomahawk",   "AM5",     "DDR5", "ATX",  299.00f));
        mbList.add(new Motherboard("ASRock B650M Pro RS",      "AM5",     "DDR5", "mATX", 169.00f));

        psuList.add(new PSU("Corsair RM1000x 1000W",        1000, 179.00f));
        psuList.add(new PSU("Corsair RM850x 850W",           850, 149.00f));
        psuList.add(new PSU("be quiet! Straight Power 750W", 750, 129.00f));
        psuList.add(new PSU("Seasonic Focus GX-650W",        650, 109.00f));
        psuList.add(new PSU("EVGA SuperNOVA 550W",           550,  79.00f));

        caseList.add(new Case("Fractal Design Torrent",  "ATX",  189.00f));
        caseList.add(new Case("NZXT H7 Flow",            "ATX",  149.00f));
        caseList.add(new Case("Lian Li O11 Dynamic",     "ATX",  139.00f));
        caseList.add(new Case("Cooler Master MasterBox", "mATX",  89.00f));
        caseList.add(new Case("Fractal Design Node 304", "mITX",  99.00f));
    }

    // ========== BUILD UI ==========

    private void buildUI() {
        setTitle("PC Configurator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 650);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(15, 15, 15, 15));
        main.setBackground(new Color(245, 245, 250));
        main.add(buildFormPanel(), BorderLayout.CENTER);
        main.add(buildSidePanel(), BorderLayout.EAST);
        main.add(buildBottomPanel(), BorderLayout.SOUTH);
        setContentPane(main);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        JLabel title = new JLabel("⚙ PC Configurator");
        title.setFont(new Font("Calibri", Font.BOLD, 22));
        title.setForeground(new Color(30, 80, 160));
        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        nomePanel.setOpaque(false);
        nomePanel.add(new JLabel("Nome build:"));
        txtNome = new JTextField("Nuova Build", 16);
        txtNome.setFont(new Font("Calibri", Font.PLAIN, 13));
        nomePanel.add(txtNome);
        top.add(title, BorderLayout.WEST);
        top.add(nomePanel, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        comboCPU         = new JComboBox<>(cpuList.toArray(new CPU[0]));
        comboGPU         = new JComboBox<>(gpuList.toArray(new GPU[0]));
        comboRAM         = new JComboBox<>(ramList.toArray(new RAM[0]));
        comboStorage     = new JComboBox<>(storageList.toArray(new Storage[0]));
        comboMotherboard = new JComboBox<>(mbList.toArray(new Motherboard[0]));
        comboPSU         = new JComboBox<>(psuList.toArray(new PSU[0]));
        comboCase        = new JComboBox<>(caseList.toArray(new Case[0]));

        JPanel grid = new JPanel(new GridLayout(7, 1, 5, 8));
        grid.setOpaque(false);
        grid.add(buildRiga("🔲 CPU",         comboCPU));
        grid.add(buildRiga("🎮 GPU",         comboGPU));
        grid.add(buildRiga("💾 RAM",         comboRAM));
        grid.add(buildRiga("💿 Storage",     comboStorage));
        grid.add(buildRiga("🔌 Motherboard", comboMotherboard));
        grid.add(buildRiga("⚡ PSU",         comboPSU));
        grid.add(buildRiga("🖥 Case",        comboCase));
        panel.add(grid, BorderLayout.CENTER);

        ActionListener al = e -> aggiornaPrezzo();
        comboCPU.addActionListener(al);
        comboGPU.addActionListener(al);
        comboRAM.addActionListener(al);
        comboStorage.addActionListener(al);
        comboMotherboard.addActionListener(al);
        comboPSU.addActionListener(al);
        comboCase.addActionListener(al);

        JPanel info = new JPanel(new GridLayout(3, 1, 2, 2));
        info.setOpaque(false);
        lblPrezzo = new JLabel("Prezzo totale: —");
        lblPrezzo.setFont(new Font("Calibri", Font.BOLD, 16));
        lblPrezzo.setForeground(new Color(30, 130, 30));
        lblWatt = new JLabel("Consumo stimato: — W");
        lblWatt.setFont(new Font("Calibri", Font.PLAIN, 13));
        lblWatt.setForeground(Color.GRAY);
        lblStato = new JLabel(" ");
        lblStato.setFont(new Font("Calibri", Font.ITALIC, 12));
        info.add(lblPrezzo);
        info.add(lblWatt);
        info.add(lblStato);
        panel.add(info, BorderLayout.SOUTH);

        aggiornaPrezzo();
        return panel;
    }

    private JPanel buildRiga(String etichetta, JComboBox<?> combo) {
        JPanel riga = new JPanel(new BorderLayout(10, 0));
        riga.setOpaque(false);
        JLabel lbl = new JLabel(etichetta);
        lbl.setFont(new Font("Calibri", Font.BOLD, 13));
        lbl.setPreferredSize(new Dimension(120, 20));
        combo.setFont(new Font("Calibri", Font.PLAIN, 12));
        riga.add(lbl, BorderLayout.WEST);
        riga.add(combo, BorderLayout.CENTER);
        return riga;
    }

    private JPanel buildSidePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(280, 0));
        JLabel titolo = new JLabel("📋 Build salvate");
        titolo.setFont(new Font("Calibri", Font.BOLD, 14));
        titolo.setForeground(new Color(30, 80, 160));
        panel.add(titolo, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        for (int i = 0; i < configurazioni.size(); i++) {
            listModel.addElement(configurazioni.get(i));
        }
        listConfig = new JList<>(listModel);
        listConfig.setFont(new Font("Calibri", Font.PLAIN, 12));
        listConfig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listConfig.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) caricaSelezionata();
        });
        panel.add(new JScrollPane(listConfig), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(false);
        JButton btnSalva   = creaBottone("💾 Salva Build", new Color(30, 120, 200));
        JButton btnNuova   = creaBottone("➕ Nuova Build", new Color(60, 160, 60));
        JButton btnElimina = creaBottone("🗑 Elimina",     new Color(200, 60, 60));
        btnSalva.addActionListener(e -> salvaBuild());
        btnNuova.addActionListener(e -> nuovaBuild());
        btnElimina.addActionListener(e -> eliminaBuild());
        panel.add(btnSalva);
        panel.add(btnNuova);
        panel.add(btnElimina);
        return panel;
    }

    private JButton creaBottone(String testo, Color colore) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Calibri", Font.BOLD, 13));
        btn.setBackground(colore);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ========== LOGICA UI ==========

    private void aggiornaPrezzo() {
        currentConfig.setCpu((CPU) comboCPU.getSelectedItem());
        currentConfig.setGpu((GPU) comboGPU.getSelectedItem());
        currentConfig.setRam((RAM) comboRAM.getSelectedItem());
        currentConfig.setStorage((Storage) comboStorage.getSelectedItem());
        currentConfig.setMotherboard((Motherboard) comboMotherboard.getSelectedItem());
        currentConfig.setPsu((PSU) comboPSU.getSelectedItem());
        currentConfig.setPcCase((Case) comboCase.getSelectedItem());

        lblPrezzo.setText("Calcolo in corso...");
        lblStato.setText(" ");

        PriceCalculatorWorker worker = new PriceCalculatorWorker(currentConfig);
        worker.start();

        Thread aggiornaUI = new Thread() {
            @Override
            public void run() {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    System.out.println("Attesa interrotta: " + e.getMessage());
                }
                double price = worker.getRisultatoPrezzo();
                int watts = worker.getRisultatoWatt();
                SwingUtilities.invokeLater(new Runnable() {
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
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci un nome per la build!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            currentConfig.checkCompatibility();
        } catch (IncompatibleComponentException ex) {
            int scelta = JOptionPane.showConfirmDialog(this,
                "Problema di compatibilità:\n" + ex.getMessage() + "\nVuoi salvarla comunque?",
                "Compatibilità", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (scelta != JOptionPane.YES_OPTION) return;
        }
        currentConfig.setName(nome);

        // Sostituisce se esiste già una build con lo stesso nome
        boolean trovata = false;
        for (int i = 0; i < configurazioni.size(); i++) {
            if (configurazioni.get(i).getName().equals(nome)) {
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

        Configuration.salvaConfigurazioneSuFile(configurazioni);
        JOptionPane.showMessageDialog(this, "Build \"" + nome + "\" salvata!", "Salvato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nuovaBuild() {
        currentConfig = new Configuration("Nuova Build");
        txtNome.setText("Nuova Build");
        listConfig.clearSelection();
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
        int idx = listConfig.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Seleziona una build da eliminare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(this,
            "Eliminare la build \"" + configurazioni.get(idx).getName() + "\"?",
            "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            configurazioni.remove(idx);
            listModel.remove(idx);
            Configuration.salvaConfigurazioneSuFile(configurazioni);
            nuovaBuild();
        }
    }

    private void caricaSelezionata() {
        Configuration sel = listConfig.getSelectedValue();
        if (sel == null) return;
        currentConfig = sel;
        txtNome.setText(sel.getName());
        if (sel.getCpu() != null)         comboCPU.setSelectedItem(sel.getCpu());
        if (sel.getGpu() != null)         comboGPU.setSelectedItem(sel.getGpu());
        if (sel.getRam() != null)         comboRAM.setSelectedItem(sel.getRam());
        if (sel.getStorage() != null)     comboStorage.setSelectedItem(sel.getStorage());
        if (sel.getMotherboard() != null) comboMotherboard.setSelectedItem(sel.getMotherboard());
        if (sel.getPsu() != null)         comboPSU.setSelectedItem(sel.getPsu());
        if (sel.getPcCase() != null)      comboCase.setSelectedItem(sel.getPcCase());
        aggiornaPrezzo();
    }
}
