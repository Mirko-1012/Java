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

    public boolean addCar(Car car, int position) {
        for (Stopover stopover : this.Stopovers) {
            if (stopover.isCarIntoTheParking() && stopover.getPosition() == position) {
                return false;
            }
        }
        this.Stopovers.add(new Stopover(car, position, this.priceForMillis));
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

    public void exitCarFromParking(String plate) {
        for (Stopover stopover : this.Stopovers) {
            if (stopover.getCar().getPlate().equals(plate) && stopover.isCarIntoTheParking()) {
                stopover.exit();
                return;
            }
        }
    }
}
