public class Motherboard extends Component {

    private String socket;
    private String ddrType;
    private String formFactor;

    public Motherboard(String name, String socket, String ddrType, String formFactor, double price) {
        super(name, price);
        this.socket = socket;
        this.ddrType = ddrType;
        this.formFactor = formFactor;
    }

    public String getSocket() {
        return socket;
    }

    public String getDdrType() {
        return ddrType;
    }

    public String getFormFactor() {
        return formFactor;
    }

    @Override
    public String getType() {
        return "Motherboard";
    }

    @Override
    public int getPowerConsumption() {
        return 30;
    }

    @Override
    public String toString() {
        return getName() + " [" + socket + " | " + ddrType + " | " + formFactor + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
