public class Vehicle {
    private String plate;

    public Vehicle(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public int getPriceMultiplier() {
        return 1;
    }

    @Override
    public String toString() {
        return plate;
    }
}