public class CPU extends Component {

    private String socket;
    private int tdp;

    public CPU(String name, String socket, int tdp, double price) {
        super(name, price);
        this.socket = socket;
        this.tdp = tdp;
    }

    public String getSocket() {
        return socket;
    }

    @Override
    public String getType() {
        return "CPU";
    }

    @Override
    public int getPowerConsumption() {
        return tdp;
    }

    @Override
    public String toString() {
        return getName() + " [" + socket + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
