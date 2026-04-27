import java.util.Date;

public class Stopover {

    private long start;
    private long end;
    private Vehicle vehicle;
    private int position;
    private int priceForMillis = 0;

    public Stopover(Vehicle vehicle, int position, int basePrice) {
        this.start = (new Date()).getTime();
        this.vehicle = vehicle;
        this.position = position;
        this.priceForMillis = basePrice * vehicle.getPriceMultiplier();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void exit() {
        this.end = System.currentTimeMillis();
    }

    public boolean isOver() {
        return this.end != 0;
    }

    public boolean isCarIntoTheParking() {
        return this.end == 0;
    }

    public double getPrice() {
        if (this.isOver()) {
            return ((double)(this.end - this.start) * this.priceForMillis) / 10000.0;
        } else {
            return 0;
        }
    }

    public long getStartTime() {
        return start;
    }

    public int getPosition() {
        return position;
    }
}