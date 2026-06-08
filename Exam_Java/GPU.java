public class GPU extends Component {

    private int vramGB;
    private int tdp;

    public GPU(String name, int vramGB, int tdp, double price) {
        super(name, price);
        this.vramGB = vramGB;
        this.tdp = tdp;
    }

    public int getVramGB() {
        return vramGB;
    }

    @Override
    public String getType() {
        return "GPU";
    }

    @Override
    public int getPowerConsumption() {
        return tdp;
    }

    @Override
    public String toString() {
        return getName() + " [" + vramGB + "GB VRAM] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
