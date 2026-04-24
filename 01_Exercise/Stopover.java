import java.time.LocalDateTime;
import java.time.Duration;

public class Stopover {
    private Car car;
    private int position;
    private long timeIn;
    private long timeOut;
    private double price;

    private static final double HOURLY_RATE = 0.80;

    public Stopover(Car car, int position) {
        this.car = car;
        this.position = position;
        this.timeIn = System.currentTimeMillis();
    }

    public void registerExit() {
        this.timeOut = System.currentTimeMillis();
        calculatePrice();
    }

    private void calculatePrice() {
        long diff = timeOut - timeIn;

        double hours = diff / 3600000.0;

        if (hours < 1.0) {
            hours = 1.0;
        }

        this.price = hours * HOURLY_RATE;
    }
    public Car getCar() { return car; }
    public int getPosition() { return position; }
    public LocalDateTime getTimeIn() { return timeIn; }
    public LocalDateTime getTimeOut() { return timeOut; }
    public double getPrice() { return price; }
}