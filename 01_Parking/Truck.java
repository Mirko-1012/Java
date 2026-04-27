public class Truck extends Vehicle {
    public Truck(String plate) {
        super(plate);
    }

    @Override
    public int getPriceMultiplier() {
        return 3;
    }
}