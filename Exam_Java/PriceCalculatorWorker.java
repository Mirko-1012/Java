public class PriceCalculatorWorker extends Thread {

    private Configuration configuration;
    private double risultatoPrezzo;
    private int risultatoWatt;

    public PriceCalculatorWorker(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Thread interrotto: " + e.getMessage());
        }
        this.risultatoPrezzo = configuration.getTotalPrice();
        this.risultatoWatt = configuration.getTotalPowerConsumption();
    }

    public double getRisultatoPrezzo() {
        return risultatoPrezzo;
    }

    public int getRisultatoWatt() {
        return risultatoWatt;
    }
}
