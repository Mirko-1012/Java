import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {


    private String departure;
    private String arrival;
    private LocalDateTime departureDateTime;
    private Plane plane;

    public Flight(String departure, String arrival, LocalDateTime departureDateTime, Plane plane) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureDateTime = departureDateTime;
        this.plane = plane;
    }

    public String getDeparture() { return departure; }
    public String getArrival() { return arrival; }
    public LocalDateTime getDepartureDateTime() { return departureDateTime; }
    public Plane getPlane() { return plane; }

    public String getFormattedDateTime() {
        return departureDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return getFormattedDateTime() + ":  " + departure + "  -->  " + arrival
               + "   [" + plane.getDescription() + "]";
    }
}