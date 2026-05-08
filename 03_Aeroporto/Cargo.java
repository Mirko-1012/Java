public class Cargo extends Plane {

    private double capacityTon;

    public Cargo(String code, String model, double capacityTon) {
        super(code, model);
        this.capacityTon = capacityTon;
    }

    @Override
    public String getDescription() {
        return "Cargo (Capacity: " + capacityTon + "t)";
    }
}