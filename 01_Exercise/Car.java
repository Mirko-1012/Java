public class Car {
    private String plate;

    public Car(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return plate != null && plate.equals(car.plate);
    }

    @Override
    public int hashCode() {
        return plate != null ? plate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return plate;
    }
}