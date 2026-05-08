public abstract class Plane {

    private String code;
    private String model;

    public Plane(String code, String model) {
        this.code = code;
        this.model = model;
    }

    public String getCode() {
        return code;
    }

    public String getModel() {
        return model;
    }

    public abstract String getDescription();
}