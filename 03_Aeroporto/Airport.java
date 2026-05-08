import java.util.ArrayList;

public class Airport {

    private ArrayList<Flight> flightList;

    public Airport() {
        this.flightList = new ArrayList<>();
    }

    public void addFlight(Flight f) {
        flightList.add(f);
    }

    public ArrayList<Flight> getAllFlights() {
        return flightList;
    }
}