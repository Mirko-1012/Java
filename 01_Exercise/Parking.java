import java.util.ArrayList;
import java.util.List;

public class Parking {
    private static final int DEFAULT_SIZE = 10;

    private Stopover[] currentStops;
    private List<Stopover> history;

    public Parking() {
        this.currentStops = new Stopover[DEFAULT_SIZE];
        this.history = new ArrayList<>();
    }

    public void addCar(Car car, int place) {
        if (car == null || place < 0 || place >= currentStops.length) return;

        if (currentStops[place] != null || this.contains(car)) {
            System.out.println("Errore: Posto occupato o auto già presente.");
            return;
        }

        currentStops[place] = new Stopover(car, place);
        System.out.println("Car " + car.getPlate() + " added to place " + place);
    }

    public boolean contains(Car car) {
        for (Stopover s : currentStops) {
            if (s != null && s.getCar().equals(car)) {
                return true;
            }
        }
        return false;
    }

    public double removeCar(int place) {
        if (place < 0 || place >= currentStops.length) return 0.0;

        Stopover s = currentStops[place];
        if (s != null) {
            s.registerExit();
            history.add(s);
            currentStops[place] = null;
            return s.getPrice();
        }
        return 0.0;
    }

    public int availablePlaces() {
        int count = 0;
        for (Stopover s : currentStops) {
            if (s == null) count++;
        }
        return count;
    }

    public int occupatedPlaces() {
        return currentStops.length - this.availablePlaces();
    }

    public boolean isFull() {
        return this.availablePlaces() == 0;
    }

    public Car getCarAt(int place) {
        if (place < 0 || place >= currentStops.length || currentStops[place] == null) {
            return null;
        }
        return currentStops[place].getCar();
    }

    public int getSize() {
        return currentStops.length;
    }

    public List<Stopover> getHistory() {
        return history;
    }
}