public abstract class Component {

    private String name;
    private double price;

    public Component(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getType();

    public abstract int getPowerConsumption();

    @Override
    public String toString() {
        return name + " (€" + String.format("%.2f", price) + ")";
    }
}
