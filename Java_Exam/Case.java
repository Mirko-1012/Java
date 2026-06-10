public class Case extends Component {

    private String formFactor;

    public Case(String name, String formFactor, float price) {
        super(name, price);
        this.formFactor = formFactor;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public boolean supportsFormFactor(String mbFormFactor) {
        if (formFactor.equals("ATX")) {
            return true;
        } else if (formFactor.equals("mATX")) {
            return mbFormFactor.equals("mATX") || mbFormFactor.equals("mITX");
        } else {
            return mbFormFactor.equals("mITX");
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
        return getName() + " [" + formFactor + "] (€" + String.format("%.2f", getPrice()) + ")";
    }
}
