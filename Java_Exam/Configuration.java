import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Configuration {

    private static final String FILE_NAME = "configuration.txt";
    private static int totalConfigurations = 0;

    private String name;
    private CPU cpu;
    private GPU gpu;
    private RAM ram;
    private Storage storage;
    private Motherboard motherboard;
    private PSU psu;
    private Case pcCase;



    public Configuration(String name) { // Il costruttore ora richiede solo il nome, gli altri componenti possono essere aggiunti dopo
        this.name = name;
        totalConfigurations++;
    }



    public String getName() { 
        return name; 
    }

    public CPU getCpu() { 
        return cpu; 
    }

    public GPU getGpu() {
        return gpu; 
    }
    public RAM getRam() { 
        return ram; 
    }
    
    public Storage getStorage() { 
        return storage; 
    }

    public Motherboard getMotherboard() {
        return motherboard; 
    }
    
    public PSU getPsu() { 
        return psu; 
    }

    public Case getPcCase() {
        return pcCase; 
    }

    public static int getTotalConfigurations() { 
        return totalConfigurations; 
    }



    public void setName(String name) {
        this.name = name; 
    }

    public void setCpu(CPU cpu) { 
        this.cpu = cpu; 
    }

    public void setGpu(GPU gpu) { 
        this.gpu = gpu; 
    }

    public void setRam(RAM ram) {
        this.ram = ram; 
    }

    public void setStorage(Storage storage) { 
        this.storage = storage; 
    }

    public void setMotherboard(Motherboard mb) { 
        this.motherboard = mb; 
    }

    public void setPsu(PSU psu) { 
        this.psu = psu; 
    }

    public void setPcCase(Case pcCase) { 
        this.pcCase = pcCase; 
    }


    
    public float getTotalPrice() {

        float total = 0;

        if (cpu != null)        
            total += cpu.getPrice();

        if (gpu != null)        
            total += gpu.getPrice();

        if (ram != null)        
            total += ram.getPrice();

        if (storage != null)    
            total += storage.getPrice();

        if (motherboard != null)
            total += motherboard.getPrice();

        if (psu != null)        
            total += psu.getPrice();

        if (pcCase != null)     
            total += pcCase.getPrice();

        return total;
    }



    public int getTotalPowerConsumption() {

        int totalWatt = 0;

        if (cpu != null)        
            totalWatt += cpu.getPowerConsumption();

        if (gpu != null)        
            totalWatt += gpu.getPowerConsumption();

        if (ram != null)        
            totalWatt += ram.getPowerConsumption();

        if (storage != null)    
            totalWatt += storage.getPowerConsumption();

        if (motherboard != null)
            totalWatt += motherboard.getPowerConsumption();

        return totalWatt;
    }



    public boolean isComplete() {

        return cpu != null && gpu != null && ram != null && storage != null && motherboard != null && psu != null && pcCase != null;
    }



    public void checkCompatibility() throws IncompatibleComponentException {

        if (cpu != null && motherboard != null) { // Se entrambi sono presenti, controlliamo la compatibilità
            if (!cpu.getSocket().equals(motherboard.getSocket())) { // ! perché vogliamo l'errore se i socket sono diversi
                throw new IncompatibleComponentException(
                    "Socket CPU (" + cpu.getSocket() + ") non compatibile con la motherboard (" + motherboard.getSocket() + ")"
                );
            }
        }

        if (ram != null && motherboard != null) {
            if (!ram.getDdrType().equals(motherboard.getDdrType())) {
                throw new IncompatibleComponentException(
                    "RAM " + ram.getDdrType() + " non compatibile con la motherboard (" + motherboard.getDdrType() + ")"
                );
            }
        }

        if (pcCase != null && motherboard != null) {
            if (!pcCase.supportsFormFactor(motherboard.getFormFactor())) {
                throw new IncompatibleComponentException(
                    "Case " + pcCase.getFormFactor() + " non supporta la motherboard " + motherboard.getFormFactor()
                );
            }
        }

        if (psu != null) { 
            int wattsNecessari = getTotalPowerConsumption(); // Calcoliamo i watt necessari per la configurazione attuale
            if (psu.getWattage() < wattsNecessari) { // Se la PSU non eroga abbastanza watt, lanciamo un'eccezione
                throw new IncompatibleComponentException(
                    "PSU insufficiente: servono " + wattsNecessari + "W ma la PSU eroga solo " + psu.getWattage() + "W"
                );
            }
        }
    }



    public static void salvaConfigurazioneSuFile(ArrayList<Configuration> configurazioni) { // Salva solo le configurazioni complete, una per riga, con i campi separati da virgola

        try {
            FileWriter fw = new FileWriter(FILE_NAME, false); // false per sovrascrivere il file ad ogni salvataggio
            for (int i = 0; i < configurazioni.size(); i++) { // Scorreremo tutte le configurazioni e salveremo solo quelle complete
                Configuration c = configurazioni.get(i); // Prendiamo la configurazione
                if (c.isComplete()) { // Se la configurazione è completa, la salviamo, altrimenti la ignoriamo
                    fw.write(
                        c.name + "," + c.cpu.getName() + "," + c.gpu.getName() + "," + c.ram.getName() + "," + c.storage.getName() + "," + c.motherboard.getName() + "," + c.psu.getName() + "," + c.pcCase.getName() + "\n"
                    );
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Errore salvataggio: " + e.getMessage());
        }
    }

    public static ArrayList<Configuration> caricaConfigurazioniDaFile( // Carica le configurazioni da file, restituendo una lista di configurazioni. Per ogni riga del file.

            ArrayList<CPU> cpuList,
            ArrayList<GPU> gpuList,
            ArrayList<RAM> ramList, 
            ArrayList<Storage> storageList,
            ArrayList<Motherboard> mbList, 
            ArrayList<PSU> psuList,
            ArrayList<Case> caseList
        ) {

        ArrayList<Configuration> configurazioni = new ArrayList<>(); // Creiamo una lista vuota di configurazioni, che andremo a riempire con le configurazioni lette dal file 

        try {
            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);
            String riga; // Variabile temporanea per leggere ogni riga del file

            while ((riga = br.readLine()) != null) { // Assegniamo a riga ogni riga del file, finché non arriviamo alla fine (null)
                String[] parti = riga.split(",");

                if (parti.length < 8) 
                    continue; // Se la riga non ha abbastanza campi, la ignoriamo

                Configuration c = new Configuration(parti[0]); // Creiamo una nuova configurazione con il nome letto dal file (parti[0])
                c.setCpu(trovaCPU(cpuList, parti[1])); // Cerchiamo nella lista di CPU quella con il nome letto dal file (parti[1]) e la assegnamo alla configurazione e così per tutte le altre componenti
                c.setGpu(trovaGPU(gpuList, parti[2]));
                c.setRam(trovaRAM(ramList, parti[3]));
                c.setStorage(trovaStorage(storageList, parti[4]));
                c.setMotherboard(trovaMB(mbList, parti[5]));
                c.setPsu(trovaPSU(psuList, parti[6]));
                c.setPcCase(trovaCase(caseList, parti[7]));
                configurazioni.add(c); // Aggiungiamo la configurazione alla lista di configurazioni, che alla fine restituiremo
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            //
        }
        return configurazioni;
    }

    private static CPU trovaCPU(ArrayList<CPU> lista, String nome) { // Funzione di supporto per trovare un componente nella lista, dato il nome. Restituisce null se non lo trova 
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
        return name + " - €" + String.format("%.2f", getTotalPrice());
    }
}
