public class Storage extends Component {

    private int capacityGB;
    private String storageType;

    public Storage(String name, String storageType, int capacityGB, float price) {
        super(name, price);
        this.storageType = storageType;
        this.capacityGB = capacityGB;
    }

    public String getStorageType() {
        return storageType;
    }

    @Override
    public String getType() {
        return "Storage";
    }

    @Override
    public int getPowerConsumption() {
        if (storageType.equals("HDD")) {
            return 10; // 10 watt per HDD
        } else {
            return 5; // 5 watt per SSD
        }
    }

    @Override
    public String toString() {
        return getName() + " [" + capacityGB + "GB " + storageType + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
