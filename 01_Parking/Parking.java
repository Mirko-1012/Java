import java.util.ArrayList;

public class Parking {
    int priceForMillis = 1;
    int places = 10;
    ArrayList<Stopover> Stopovers = new ArrayList<>();

    public Parking() {
    }

    public Parking(int places) {
        this.places = places;
    }

    public boolean addVehicle(Vehicle vehicle, int position) {
        for (Stopover stopover : this.Stopovers) {
            if (stopover.isCarIntoTheParking() && stopover.getPosition() == position) {
                return false;
            }
        }
        this.Stopovers.add(new Stopover(vehicle, position, this.priceForMillis));
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
                return;
            }
        }
    }
}