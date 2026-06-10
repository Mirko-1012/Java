public abstract class Component {

    private String name;
    private float price;

    public Component(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public abstract String getType();

    public abstract int getPowerConsumption();

    @Override
    public String toString() {
        return name + " (€" + String.format("%.2f", price) + ")";
    }
}
