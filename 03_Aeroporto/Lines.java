public class Lines extends Plane {

    private int seats;

    public Lines(String code, String model, int seats) {
        super(code, model);
        this.seats = seats;
    }

    @Override
    public String getDescription() {
        return "Airline (" + seats + " seats)";
    }
}