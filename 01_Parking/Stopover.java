import java.util.Date;

public class Stopover {

    private long start;
    private long end;
    private Car car;
    private int position;
    private int priceForMillis = 0;

    public Stopover(Car car, int position, int priceForMillis){
        this.start = (new Date()).getTime();
        this.car = car;
        this.position = position;
        this.priceForMillis = priceForMillis;
    }

    public void exit() {
        this.end = (new Date()).getTime();
    }

    public boolean isOver() {
        return this.end != 0;
    }

    public boolean isCarIntoTheParking() {
        return !this.isOver();
    }

    public double getPrice() {
        if (this.isOver()) {
            return (this.end - this.start) * this.priceForMillis;
        } else {
            return 0;
        }
    }

    public long getStartTime() {
        return start;
    }

    public Car getCar() {
        return car;
    }
    public int getPosition() {
        return position;
    }
}

