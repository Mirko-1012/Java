public class Storage extends Component {

    private int capacityGB;
    private String storageType;

    public Storage(String name, String storageType, int capacityGB, double price) {
        super(name, price);
        this.storageType = storageType;
        this.capacityGB = capacityGB;
    }

    public String getStorageType() {
        return storageType;
    }

    public int getCapacityGB() {
        return capacityGB;
    }

    @Override
    public String getType() {
        return "Storage";
    }

    @Override
    public int getPowerConsumption() {
        return storageType.equals("HDD") ? 10 : 5;
    }

    @Override
    public String toString() {
        return getName() + " [" + capacityGB + "GB " + storageType + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
