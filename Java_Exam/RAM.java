public class RAM extends Component {

    private String ddrType;
    private int capacityGB;

    public RAM(String name, String ddrType, int capacityGB, float price) {
        super(name, price);
        this.ddrType = ddrType;
        this.capacityGB = capacityGB;
    }

    public String getDdrType() {
        return ddrType;
    }

    @Override
    public String getType() {
        return "RAM";
    }

    @Override
    public int getPowerConsumption() {
        return 5;
    }

    @Override
    public String toString() {
        return getName() + " [" + capacityGB + "GB " + ddrType + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
