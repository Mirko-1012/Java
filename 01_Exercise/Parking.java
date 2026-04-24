public class Parking {
    private Car[] lots;
    private static int DEFAULT_SIZE = 10;

    public Parking() {
        this.lots = new Car[DEFAULT_SIZE];
    }

    public void addCar(Car car, int place) {
        if (car == null || place < 0 || place >= this.lots.length || this.lots[place] != null || this.contains(car)) {
            return;
        } else {
            this.lots[place] = car;
        }
    }

    public boolean contains(Car car) {
        for (Car carInParking : this.lots) {
            if (carInParking != null && carInParking.equals(car)) {
                return true;
            }
        }
        return false;
    }

    public void removeCar(int place) {
        if (place < 0 || place >= this.lots.length) {
            return;
        } else {
            this.lots[place] = null;
        }
    }

    public int availablePlaces() {
        int count = 0;
        for (Car carInParking : this.lots) {
            if (carInParking == null) {
                count++;
            }
        }
        return count;
    }

    public int occupatedPlaces() {
        return this.lots.length - this.availablePlaces();
    }

    public boolean isFull() {
        return this.availablePlaces() == 0;
    }

    public Car getCarAt(int place) {
        if (place < 0 || place >= this.lots.length) return null;
        return this.lots[place];
    }

    public int getSize() {
        return this.lots.length;
    }
}