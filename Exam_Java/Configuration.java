import java.io.*;
import java.util.ArrayList;

public class Configuration {

    private static int totalConfigurations = 0;
    private static final String FILE_NAME = "configurazioni.txt";

    private String configName;
    private CPU cpu;
    private GPU gpu;
    private RAM ram;
    private Storage storage;
    private Motherboard motherboard;
    private PSU psu;
    private Case pcCase;

    public Configuration(String configName) {
        this.configName = configName;
        totalConfigurations++;
    }

    public static int getTotalConfigurations() {
        return totalConfigurations;
    }

    public void setCPU(CPU cpu) {
        this.cpu = cpu;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    public void setRAM(RAM ram) {
        this.ram = ram;
    }

    public void setGPU(GPU gpu) {
        this.gpu = gpu;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setPSU(PSU psu) {
        this.psu = psu;
    }

    public void setCase(Case pcCase) {
        this.pcCase = pcCase;
    }


    public String getConfigName() { return configName; }
    public CPU getCpu() { return cpu; }
    public GPU getGpu() { return gpu; }
    public RAM getRam() { return ram; }
    public Storage getStorage() { return storage; }
    public Motherboard getMotherboard() { return motherboard; }
    public PSU getPsu() { return psu; }
    public Case getPcCase() { return pcCase; }


    public double getTotalPrice() {
        double total = 0;
        if (cpu != null) total += cpu.getPrice();
        if (gpu != null) total += gpu.getPrice();
        if (ram != null) total += ram.getPrice();
        if (storage != null) total += storage.getPrice();
        if (motherboard != null) total += motherboard.getPrice();
        if (psu != null) total += psu.getPrice();
        if (pcCase != null) total += pcCase.getPrice();
        return total;
    }

    public int getTotalPowerConsumption() {
        int total = 0;
        if (cpu != null) total += cpu.getPowerConsumption();
        if (gpu != null) total += gpu.getPowerConsumption();
        if (ram != null) total += ram.getPowerConsumption();
        if (storage != null) total += storage.getPowerConsumption();
        if (motherboard != null) total += motherboard.getPowerConsumption();
        return total;
    }

    public boolean isComplete() {
        return cpu != null && gpu != null && ram != null && storage != null && motherboard != null && psu != null && pcCase != null;
    }

    // Controlla tutte le compatibilità e lancia eccezione se qualcosa non va
    public void checkCompatibility() throws IncompatibleComponentException {
        if (cpu != null && motherboard != null) {
            if (!cpu.getSocket().equals(motherboard.getSocket())) {
                throw new IncompatibleComponentException(
                    "Incompatibilità socket: CPU " + cpu.getSocket() + " non è compatibile con la motherboard " + motherboard.getSocket()
                );
            }
        }
        if (ram != null && motherboard != null) {
            if (!ram.getDdrType().equals(motherboard.getDdrType())) {
                throw new IncompatibleComponentException(
                    "Incompatibilità RAM: " + ram.getDdrType() + " non supportata dalla motherboard (" + motherboard.getDdrType() + ")"
                );
            }
        }
        if (pcCase != null && motherboard != null) {
            if (!pcCase.supportsFormFactor(motherboard.getFormFactor())) {
                throw new IncompatibleComponentException(
                    "Incompatibilità case: il case " + pcCase.getSupportedFormFactor() + " non supporta la motherboard " + motherboard.getFormFactor()
                );
            }
        }
        if (psu != null) {
            int totalWatts = getTotalPowerConsumption();
            if (psu.getWattage() < totalWatts) {
                throw new IncompatibleComponentException(
                    "PSU insufficiente: il sistema richiede " + totalWatts + "W ma la PSU eroga solo " + psu.getWattage() + "W"
                );
            }
        }
    }


    public static void salvaConfigurazioni(ArrayList<Configuration> configurazioni) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, false);
            for (Configuration c : configurazioni) {
                if (c.isComplete()) {
                    fw.write(c.toFileLine() + "\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio: " + e.getMessage());
        }
    }

    public static ArrayList<Configuration> caricaConfigurazioni(
            ArrayList<CPU> cpuList, ArrayList<GPU> gpuList, ArrayList<RAM> ramList,
            ArrayList<Storage> storageList, ArrayList<Motherboard> mbList,
            ArrayList<PSU> psuList, ArrayList<Case> caseList) {

        ArrayList<Configuration> configurazioni = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);
            String riga;
            while ((riga = br.readLine()) != null) {
                String[] parti = riga.split(";");
                if (parti.length < 8) continue;

                Configuration c = new Configuration(parti[0]);
                c.setCPU(trovaCPU(cpuList, parti[1]));
                c.setGPU(trovaGPU(gpuList, parti[2]));
                c.setRAM(trovaRAM(ramList, parti[3]));
                c.setStorage(trovaStorage(storageList, parti[4]));
                c.setMotherboard(trovaMB(mbList, parti[5]));
                c.setPSU(trovaPSU(psuList, parti[6]));
                c.setCase(trovaCase(caseList, parti[7]));
                configurazioni.add(c);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
        }
        return configurazioni;
    }

    private String toFileLine() {
        return configName + ";" +
               (cpu != null ? cpu.getName() : "") + ";" +
               (gpu != null ? gpu.getName() : "") + ";" +
               (ram != null ? ram.getName() : "") + ";" +
               (storage != null ? storage.getName() : "") + ";" +
               (motherboard != null ? motherboard.getName() : "") + ";" +
               (psu != null ? psu.getName() : "") + ";" +
               (pcCase != null ? pcCase.getName() : "");
    }

    private static CPU trovaCPU(ArrayList<CPU> lista, String nome) {
        for (CPU c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static GPU trovaGPU(ArrayList<GPU> lista, String nome) {
        for (GPU c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static RAM trovaRAM(ArrayList<RAM> lista, String nome) {
        for (RAM c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static Storage trovaStorage(ArrayList<Storage> lista, String nome) {
        for (Storage c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static Motherboard trovaMB(ArrayList<Motherboard> lista, String nome) {
        for (Motherboard c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static PSU trovaPSU(ArrayList<PSU> lista, String nome) {
        for (PSU c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    private static Case trovaCase(ArrayList<Case> lista, String nome) {
        for (Case c : lista) {
            if (c.getName().equals(nome)) return c;
        }
        return null;
    }

    @Override
    public String toString() {
        return configName + " — €" + String.format("%.2f", getTotalPrice());
    }
}
