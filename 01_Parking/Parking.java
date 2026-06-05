import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parking {
    int priceForMillis = 1;
    int places = 10;
    ArrayList<Stopover> Stopovers = new ArrayList<>();

    private static final String FILE_NAME = "parcheggio.txt";

    public Parking() {
        caricaVeicoliDaFile();
    }

    public Parking(int places) {
        this.places = places;
        caricaVeicoliDaFile();
    }

    public boolean addVehicle(Vehicle vehicle, int position) {
        for (Stopover stopover : this.Stopovers) {
            if (stopover.isCarIntoTheParking() && stopover.getPosition() == position) {
                return false;
            }
        }
        this.Stopovers.add(new Stopover(vehicle, position, this.priceForMillis));
        salvaVeicoliSuFile();
        return true;
    }

    public int getAvailablePlaces() {
        int busyPlaces = 0;
        for (Stopover stopover : this.Stopovers) {
            if (stopover.isCarIntoTheParking()) {
                busyPlaces++;
            }
        }
        return this.places - busyPlaces;
    }

    public double getAmount() {
        double amount = 0;
        for (Stopover stopover : this.Stopovers) {
            amount += stopover.getPrice();
        }
        return amount;
    }

    public void exitVehicle(String plate) {
        for (Stopover stopover : this.Stopovers) {
            if (stopover.getVehicle().getPlate().equals(plate) && stopover.isCarIntoTheParking()) {
                stopover.exit();
                salvaVeicoliSuFile();
                return;
            }
        }
    }

    private void caricaVeicoliDaFile() {
        try {
            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);

            String riga;
            while ((riga = br.readLine()) != null) {
                String[] parti = riga.split(",");   // divide "AB123CD,Auto,3" in un array
                String plate   = parti[0];
                String tipo    = parti[1];
                int position   = Integer.parseInt(parti[2]);

                Vehicle v = tipo.equals("Camion") ? new Truck(plate) : new Car(plate);
                this.Stopovers.add(new Stopover(v, position, this.priceForMillis));
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            // Se il file non esiste ancora (primo avvio), non è un errore grave
            System.out.println("Nessun file trovato, parcheggio vuoto.");
        }
    }

    private void salvaVeicoliSuFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, false);

            for (Stopover s : this.Stopovers) {
                if (s.isCarIntoTheParking()) {
                    String tipo = (s.getVehicle() instanceof Truck) ? "Camion" : "Auto";
                    String riga = s.getVehicle().getPlate() + "," + tipo + "," + s.getPosition();
                    fw.write(riga + "\n");
                }
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del file: " + e.getMessage());
        }
    }

    public void stampaContenutoFile() {
        try {
            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);

            System.out.println("=== Contenuto di " + FILE_NAME + " ===");
            String riga;
            while ((riga = br.readLine()) != null) {
                System.out.println(riga);
            }
            System.out.println("=====================================");

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }
}