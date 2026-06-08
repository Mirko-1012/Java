public class Case extends Component {

    private String supportedFormFactor;

    public Case(String name, String supportedFormFactor, double price) {
        super(name, price);
        this.supportedFormFactor = supportedFormFactor;
    }

    public String getSupportedFormFactor() {
        return supportedFormFactor;
    }

    public boolean supportsFormFactor(String formFactor) {
        if (supportedFormFactor.equals("ATX")) {
            return true;
        } else if (supportedFormFactor.equals("mATX")) {
            return formFactor.equals("mATX") || formFactor.equals("mITX");
        } else if (supportedFormFactor.equals("mITX")) {
            return formFactor.equals("mITX");
        } else {
            return false;
        }
    }

    @Override
    public String getType() {
        return "Case";
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public String toString() {
        return getName() + " [" + supportedFormFactor + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
