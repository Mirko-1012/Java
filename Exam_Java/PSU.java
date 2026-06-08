public class PSU extends Component {

    private int wattage;

    public PSU(String name, int wattage, double price) {
        super(name, price);
        this.wattage = wattage;
    }

    public int getWattage() {
        return wattage;
    }

    @Override
    public String getType() {
        return "PSU";
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public String toString() {
        return getName() + " [" + wattage + "W] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
