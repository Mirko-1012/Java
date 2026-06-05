import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {


    private String departure;
    private String arrival;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime; 
    private Plane plane;

    public Flight(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Plane plane) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.plane = plane;
    }

    public String getDeparture() { return departure; }
    public String getArrival() { return arrival; }
    public LocalDateTime getDepartureDateTime() { return departureDateTime; }
    public LocalDateTime getArrivalDateTime()     { return arrivalDateTime; }
    public Plane getPlane() { return plane; }

    public String getFormattedDeparture() {
        return departureDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getFormattedArrival() {
        return arrivalDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    @Override
    public String toString() {
        return getFormattedDeparture() + " → " + getFormattedArrival()
               + ":  " + departure + "  -->  " + arrival
               + "   [" + plane.getDescription() + "]";
    }

}