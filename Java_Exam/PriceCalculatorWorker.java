// Thread in background che calcola il prezzo senza bloccare la GUI
public class PriceCalculatorWorker extends Thread {

    private Configuration configuration;
    private float risultatoPrezzo;
    private int risultatoWatt;

    public PriceCalculatorWorker(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300); // Simuliamo un calcolo che richiede tempo giusto per usare i thread e mostrare il caricamento
        } catch (InterruptedException e) {
            System.out.println("Thread interrotto: " + e.getMessage());
        }
        this.risultatoPrezzo = configuration.getTotalPrice();
        this.risultatoWatt = configuration.getTotalPowerConsumption();
    }

    public float getRisultatoPrezzo() {
        return risultatoPrezzo;
    }

    public int getRisultatoWatt() {
        return risultatoWatt;
    }
}
