import java.util.ArrayList;
import java.util.Comparator;

public class Airport {

    private String name;
    private ArrayList<Flight> flightList;

    public Airport(String name) {
        this.name = name;
        this.flightList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addFlight(Flight f) {
        flightList.add(f);
    }

    public boolean removeFlight(Flight f) {
        return flightList.remove(f);
    }

    public ArrayList<Flight> getAllFlights() {
        return flightList;
    }

    public ArrayList<Flight> getFlightsSorted() {
    ArrayList<Flight> sortedList = new ArrayList<>(flightList);
    
        sortedList.sort(new Comparator<Flight>() {
            @Override
            public int compare(Flight f1, Flight f2) {
                return f1.getDepartureDateTime().compareTo(f2.getDepartureDateTime());
            }
        });
    return sortedList;
    }
}